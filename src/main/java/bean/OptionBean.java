package bean;

import entity.Option;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class OptionBean {

    @PersistenceContext(unitName = "project_2")
    private EntityManager em;

    @Transactional(rollbackOn = Exception.class)
    public void saveOption(Option option) {
        em.persist(option);
    }

    public List<Option> getOptionsForCategory(long category_id) {
        return em.createQuery("select o from Option o where o.category.id = ?1", Option.class).
                setParameter(1, category_id).
                getResultList();
    }

    public Option getOption(long option_id) {
        return em.find(Option.class, option_id);
    }
}
