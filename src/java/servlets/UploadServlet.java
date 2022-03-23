
package servlets;

import entitys.Picture;
import entitys.User;
import facades.PictureFacade;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.imgscalr.Scalr;

@WebServlet(name = "UploadServlet", urlPatterns = {
    "/uploadFile", 
    "/showUploadFile",
    "/deletePicture",
})

@MultipartConfig()
public class UploadServlet extends HttpServlet {
    @EJB private PictureFacade pictureFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.setAttribute("info", "Авторизуйтесь");
            request.getRequestDispatcher("/showLogin").forward(request, response);
            return;
        }
        
        User authUser = (User) session.getAttribute("authUser");
        if (authUser == null) {
            request.setAttribute("info", "Авторизуйтесь");
            request.getRequestDispatcher("/showLogin").forward(request, response);
            return;
        }
        
        String path = request.getServletPath();
        switch(path) {
            case "/showUploadFile":
                List<Picture> pictures = pictureFacade.findAllForUser(authUser);
                request.setAttribute("pictures", pictures);
                request.getRequestDispatcher("/uploadFile.jsp").forward(request, response);
                break;
                
            case "/uploadFile":
                List<Part> fileParts = request.getParts()
                        .stream()
                        .filter(part -> "file".equals(part.getName()))
                        .collect(Collectors.toList());
                
                String imagesFolder = "D:\\UploadDir\\jptv20_WebPasswordManager\\";
                String imagesUserFolder = imagesFolder + authUser.getLogin();
                for (Part filePart : fileParts) {
                    File dirForUserFiles = new File(imagesUserFolder);
                    dirForUserFiles.mkdirs();
                    String pathToFile = imagesUserFolder + File.separatorChar + getFileName(filePart);
                    String pathToTempFile = imagesFolder + File.separatorChar + "tmp" + File.separatorChar + getFileName(filePart);
                    File tempFile = new File(pathToTempFile);
                    tempFile.mkdirs();
                    try(InputStream fileContent = filePart.getInputStream()) {
                        Files.copy(fileContent, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        writeToFile(resize(tempFile), pathToFile);
                        tempFile.delete();
                    }
                    String description = request.getParameter("description");
                    Picture picture = new Picture();
                    picture.setDescription(description);
                    picture.setPathToFile(pathToFile);
                    picture.setUser(authUser);
                    pictureFacade.create(picture);
                }
                request.setAttribute("info", "Файл успешно сохранен");
                request.getRequestDispatcher("/showUploadFile").forward(request, response);
                break;
            case "/deletePicture":
                String pictureId = request.getParameter("pictureId");
                Picture deletePicture = pictureFacade.find(Long.parseLong(pictureId));
                try {
                    pictureFacade.remove(deletePicture);
                    File deleteFile = new File(deletePicture.getPathToFile());
                    if (deleteFile.delete()) {
                        request.setAttribute("info", "Файл успешно удален");
                    } else {
                        request.setAttribute("info", "Не удалось удалить файл");
                    }
                    request.getRequestDispatcher("/showUploadFile").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("info", "Изображение связано с аккаунтом");
                    request.setAttribute("pictureId", deletePicture.getId());
                    request.getRequestDispatcher("/showAccountsWithThisPicureBound").forward(request, response);
                }
                break;
        }
        
    }
    
    private String getFileName(Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=")+1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
    public void writeToFile(byte[] data, String fileName) throws IOException {
        try(FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(data);
        }
    }
    
    public byte[] resize(File icon) {
        try {
            BufferedImage originalImage = ImageIO.read(icon);
            originalImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
