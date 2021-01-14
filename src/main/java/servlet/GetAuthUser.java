package servlet;

import JSONEntity.UserJSON;
import bean.UserBean;
import com.google.gson.Gson;
import entity.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/getauthuser")
public class GetAuthUser extends HttpServlet {

    @EJB
    private UserBean userBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Writer wr = resp.getWriter();
        if (session.getAttribute("id") == null) {
            wr.write("null");
        } else {
            User user = userBean.getUser(Long.parseLong(session.getAttribute("id").toString()));
            UserJSON userJSON = new UserJSON(user);
            wr.write(new Gson().toJson(userJSON));
        }
        wr.close();
    }
}
