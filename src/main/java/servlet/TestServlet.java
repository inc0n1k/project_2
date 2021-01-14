package servlet;

import JSONEntity.CategoryJSON;
import bean.TestBean;
import com.google.gson.Gson;
import entity.Category;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @EJB
    private TestBean testBean;

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException,
            IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");

        req.setAttribute("categories", testBean.getCategoryLVL(0));
        req.getRequestDispatcher("Test.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        List<Category> categories = testBean.getCategoryLVL(Long.parseLong(req.getParameter("id")));
        List<CategoryJSON> categoryJSONS = new ArrayList<>();
        for (Category category : categories) {
            categoryJSONS.add(new CategoryJSON(category));
        }
        resp.getWriter().write( new Gson().toJson(categoryJSONS));
    }
}