
package facades;

import entitys.Picture;
import entitys.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PictureFacade extends AbstractFacade<Picture> {

    @PersistenceContext(unitName = "jptv20_webPasswordManagerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PictureFacade() {
        super(Picture.class);
    }
    
    public List<Picture> findAllForUser(User user) {
        try {
            return em.createQuery("SELECT p FROM Picture p WHERE p.user = :user")
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
