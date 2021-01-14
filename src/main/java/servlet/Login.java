package servlet;

import bean.UserBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @EJB
    private UserBean userBean;

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        System.out.println("Test");
        if (req.getSession().getAttribute("id") == null) {
            resp.sendRedirect("Login.jsp");
        } else {
            resp.sendRedirect("homepage");
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        resp.sendRedirect(userBean.isAuth(
                req.getParameter("login"),
                req.getParameter("pass"))
                ? "homepage" : "Login.jsp");
    }
}