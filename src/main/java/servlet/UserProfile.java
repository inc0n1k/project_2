package servlet;

import bean.UserBean;
import entity.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userprofile")
public class UserProfile extends HttpServlet {

    @EJB
    private UserBean userBean;

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        User user = userBean.getUser(Long.parseLong(req.getSession().getAttribute("id").toString()));
        req.setAttribute("user", user);
        req.getRequestDispatcher("UserProfile.jsp").forward(req, resp);
    }
}
