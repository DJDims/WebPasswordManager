
package facades;

import entitys.AccountBox;
import entitys.Picture;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AccountBoxFacade extends AbstractFacade<AccountBox> {

    @PersistenceContext(unitName = "jptv20_webPasswordManagerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountBoxFacade() {
        super(AccountBox.class);
    }
    
    public List<AccountBox> findAccountsWithThisPictureBond(Picture pictureBoundWithAccounds) {
        try {
            return em.createQuery("SELECT ab FROM AccountBox ab WHERE ab.picture = :picture")
                    .setParameter("picture", pictureBoundWithAccounds)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
