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
import java.util.List;

@WebServlet("/allorders")
public class AllOrders extends HttpServlet {

    @EJB
    private OrderBean orderBean;

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        if (req.getParameter("id") == null) {
            List<Order> orders = orderBean.getAllActiveOrders();
            req.setAttribute("orders", orders);
        } else {
            Order order = orderBean.getFullOrder(Long.parseLong(req.getParameter("id")));
            req.setAttribute("order", order);
        }
        req.getRequestDispatcher("AllOrders.jsp").forward(req, resp);
    }
}
