
package entitys;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import tools.SymmetricCrypt;

@Entity
public class AccountBox implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String urlLogin;
    private String urlPassword;
    private String url;
    @OneToOne(cascade = CascadeType.DETACH)
    private Picture picture = new Picture();
    @Transient
    private final SymmetricCrypt sc;

    public AccountBox() {
        sc = new SymmetricCrypt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlLogin() {
        return urlLogin;
    }

    public void setUrlLogin(String urlLogin) {
        this.urlLogin = urlLogin;
    }

    public String getUrlPassword() {
        return urlPassword;
    }

    public void setUrlPassword(String urlPassword) {
        this.urlPassword = urlPassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "AccountBox{"
                + "id=" + id 
                + ", name=" + name 
                + ", urlLogin=" + urlLogin 
                + ", urlPassword=" + urlPassword 
                + ", url=" + url 
                + ", picture=" + picture 
                + ", sc=" + sc 
                + '}';
    }
    
    private String addProtocolToUrl(String url) {
        String pattern1 = "http://";
        String pattern2 = "https://";
        String localhost = "localhost";
        String subStr1 = "";
        String subStr2 = "";
        String subStr3 = "";
        
        try {
            subStr1 = url.substring(0, 7);
            subStr2 = url.substring(0, 8);
            subStr3 = url.substring(0, 9);
            
            if (subStr1.equals(pattern1) || subStr2.equals(pattern2)) {
                return url;
            } else {
                if (subStr3.equals(localhost)) {
                    url = pattern1 + url;
                    return url;
                }
                url = pattern2 + url;
                return url;
            }
        } catch (Exception e) {
            if (subStr3.equals(localhost)) {
                url = pattern1 + url;
                return url;
            }
            url = pattern2 + url;
            return url;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.urlLogin);
        hash = 23 * hash + Objects.hashCode(this.urlPassword);
        hash = 23 * hash + Objects.hashCode(this.url);
        hash = 23 * hash + Objects.hashCode(this.picture);
        hash = 23 * hash + Objects.hashCode(this.sc);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountBox other = (AccountBox) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.urlLogin, other.urlLogin)) {
            return false;
        }
        if (!Objects.equals(this.urlPassword, other.urlPassword)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.picture, other.picture)) {
            return false;
        }
        if (!Objects.equals(this.sc, other.sc)) {
            return false;
        }
        return true;
    }

    
}
