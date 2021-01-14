package servlet;

import bean.OrderBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userorder")
public class UserOrder extends HttpServlet {

	@EJB
	private OrderBean orderBean;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("id") == null) {
			resp.sendRedirect("homepage");
		} else if ((req.getParameter("id") != null) && !req.getParameter("id").replace("null", "").isEmpty()) {
			long id = Long.parseLong(req.getParameter("id"));
			req.setAttribute("orders", orderBean.getAllOrderByUserId(id));
			req.getRequestDispatcher("UserOrders.jsp").forward(req, resp);
		} else if (req.getParameter("ord") != null && !req.getParameter("ord").replace("null", "").isEmpty()) {
			long ord = Long.parseLong(req.getParameter("ord"));
			req.setAttribute("order", orderBean.getOrder(ord));
			req.getRequestDispatcher("UserOrders.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("homepage");
		}
	}
}