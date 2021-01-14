package bean;

import JSONEntity.CartItemJSON;
import entity.Order;
import entity.OrderProduct;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class OrderProductBean {

    @EJB
    private ProductBean productBean;

    @PersistenceContext(name = "project_2")
    private EntityManager em;

    @Transactional(rollbackOn = Exception.class)
    public void saveOrderProduct(OrderProduct orderProduct) {
        em.persist(orderProduct);
    }

    public OrderProduct createOrderProduct(Order order, CartItemJSON cartItemJSON) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(productBean.getProduct(cartItemJSON.getId()));
        orderProduct.setCount(cartItemJSON.getCount());
        return orderProduct;
    }
}
