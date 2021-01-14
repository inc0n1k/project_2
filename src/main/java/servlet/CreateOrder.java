package servlet;

import JSONEntity.CartItemJSON;
import bean.*;
import entity.Order;
import entity.OrderProduct;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/createorder")
public class CreateOrder extends HttpServlet {

    @EJB
    private CartBean cartBean;

    @EJB
    private UserBean userBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private OrderProductBean orderProductBean;

    @EJB
    private CookieBean cookieBean;

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

//        String comment = req.getParameter("comment");

        List<CartItemJSON> cartItems = cartBean.getCartItemsList();
        int count = cartBean.getCartCountValue();

        if (count == 0 || cartItems.isEmpty()) {
            resp.sendRedirect("homepage");
            return;
        }

        Order order = orderBean.saveOrder(orderBean.createOrder(/*comment*/));

        /*new Order();
        order.setComment(comment);
        if (req.getSession().getAttribute("id") != null) {
            order.setUser(userBean.getUser(Long.parseLong(req.getSession().getAttribute("id").toString())));
        }
        order.setActive(true);
        order.setComplited(false);*/

        for (CartItemJSON cartItem : cartItems) {
            OrderProduct orderProduct = orderProductBean.createOrderProduct(order, cartItem);
            orderProductBean.saveOrderProduct(orderProduct);

        /*new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(productBean.getProduct(cartItem.getId()));
            orderProduct.setCount(cartItem.getCount());*/

        }
        resp.addCookie(cookieBean.createCookie("cart", "", 0));
        resp.addCookie(cookieBean.createCookie("count", "", 0));
        resp.sendRedirect("homepage");
    }
}
