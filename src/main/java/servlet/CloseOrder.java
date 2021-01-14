package servlet;

import bean.OrderBean;
import entity.Order;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/closeorder")
public class CloseOrder extends HttpServlet {

    @EJB
    private OrderBean orderBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Order order = orderBean.closeOrder();

       /*         orderBean.getOrder(Long.parseLong(req.getParameter("order")));
        order.setActive(false);
        order.setComplited(Boolean.parseBoolean(req.getParameter("complite")));
        order.setDate_complit(new Date());
        if (req.getParameter("comment") != null || !req.getParameter("comment").trim().isEmpty()) {
            order.setComment(order.getComment()+" *"+req.getParameter("comment")+"*");
        }*/

        orderBean.updateOrder(order);
    }
}
