package bean;

import entity.Value;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class ValueBean {

    @PersistenceContext(unitName = "project_2")
    private EntityManager em;

    public List<Value> getValuesForProduct(Long id) {
        return em.createQuery("select v from Value v where v.product.id = ?1", Value.class).
                setParameter(1, id).
                getResultList();
    }

    @Transactional
    public void saveValuesList(List<Value> values) {
        for (Value value : values) {
            em.persist(value);
        }
    }

    @Transactional
    public void updateValuesList(List<Value> values) {
        for (Value value : values) {
            em.merge(value);
        }
    }
}
