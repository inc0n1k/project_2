package bean;

import entity.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext(unitName = "project_2")
    private EntityManager em;

    public Product getProduct(long product_id) {
        return em.find(Product.class, product_id);
    }

    public Product getFullProduct(long productId) {
        return em.createQuery("select p from Product p join fetch p.values where p.id = ?1", Product.class).
                setParameter(1, productId).
                getSingleResult();
    }

    public List<Product> getListOfProductsByListOfProductIds(List<Long> productsId) {
        List<Product> productList = new ArrayList<>();
        for (Long proId : productsId) {
            productList.add(getFullProduct(proId));
        }
        return productList;
    }

    public List<Product> getAllProducts() {
        return em.createQuery("select p from Product p order by p.name", Product.class).getResultList();
    }

    public List<Product> getAllProductsByCategory(long category_id) {
        List<Product> pro = em.createQuery("select distinct p from Product p join fetch p.values where p.category.id = ?1 order by p.name", Product.class).
                setParameter(1, category_id).getResultList();
//        pro = new ArrayList<>(new HashSet<>(pro));
        return pro;
    }

    @Transactional
    public Product saveProduct(Product product) {
        em.persist(product);
        return product;
    }

    @Transactional
    public void updateProduct(Product product) {
        em.merge(product);
    }
}