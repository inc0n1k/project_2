package bean;

import entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@Stateless
public class UserBean {

    @Inject
    private HttpSession httpSession;

    @PersistenceContext(unitName = "project_2")
    private EntityManager em;

    public Boolean isAuth(String login, String password) {
        List<User> users = (login != null && password != null) ?
                em.createQuery("select u from User u where u.login = ?1 and u.password = ?2", User.class).
                        setParameter(1, login).
                        setParameter(2, DigestUtils.md5Hex(password)).
                        getResultList() : null;
        if (users.size() != 0) {
            httpSession.setAttribute("id", users.get(0).getId());
            return true;
        } else {
            httpSession.setAttribute("login_error", "Увы и ах...");
            return false;
        }
    }//close isAuth

    public User getUser(long user_id) {
        return em.find(User.class, user_id);
    }
}
