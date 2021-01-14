package bean;

import entity.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TestBean {

    @PersistenceContext(name = "project_2")
    private EntityManager em;

    public List<Category> getCategoryLVL(long id) {
        if (id == 0) {
            return em.createQuery("select c from Category c where c.level = 1", Category.class).
                    getResultList();
        } else {
            Category category = em.find(Category.class, id);
            return em.createQuery("select c from Category c where c.level = ?1 and c.left_key > ?2 and c.right_key < ?3", Category.class).
                    setParameter(1, category.getLevel() + 1).
                    setParameter(2, category.getLeft_key()).
                    setParameter(3, category.getRight_key()).
                    getResultList();
        }
    }
}
