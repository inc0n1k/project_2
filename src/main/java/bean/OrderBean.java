package bean;

import entity.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {

    @Inject
    private HttpServletRequest req;

    @Inject
    private HttpSession session;

    @Inject
    private UserBean userBean;

    @PersistenceContext(name = "project_2")
    private EntityManager em;

    @Transactional
    public Order saveOrder(Order order) {
        em.persist(order);
        return order;
    }

    @Transactional
    public void updateOrder(Order order) {
        em.merge(order);
    }

    public Order createOrder(/*String comment*/) {
        Order order = new Order();
        order.setComment(req.getParameter("comment"));
        if (session.getAttribute("id") != null) {
            order.setUser(userBean.getUser(Long.parseLong(session.getAttribute("id").toString())));
        }
        order.setActive(true);
        order.setComplited(false);
        return order;
    }

    public Order closeOrder() {
        Order order = getOrder(Long.parseLong(req.getParameter("order")));
        order.setActive(false);
        order.setComplited(Boolean.parseBoolean(req.getParameter("complite")));
        order.setDate_complit(new Date());
        if (req.getParameter("comment") != null || !req.getParameter("comment").trim().isEmpty()) {
            order.setComment(order.getComment() + " *" + req.getParameter("comment") + "*");
        }
        return order;
    }

    public List<Order> getAllActiveOrders() {
        return em.createQuery("select o from Order o where o.active = true", Order.class).getResultList();
    }

    public List<Order> getAllOrderByUserId(long id) {
        return em.createQuery("select o from Order o where o.user.id = ?1", Order.class).setParameter(1, id).getResultList();
    }

    public Order getOrder(long id) {
        return em.find(Order.class, id);
    }

    public Order getFullOrder(long id) {
        return em.createQuery("select o from Order o join fetch o.orderProducts where o.id =?1", Order.class).
                setParameter(1, id).
                getSingleResult();
    }
}