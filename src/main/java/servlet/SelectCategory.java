package servlet;

import bean.OptionBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/selectcategory")
public class SelectCategory extends HttpServlet {

  /*  @EJB
    private CategoryBean categoryBean;*/

    @EJB
    private OptionBean optionBean;

   /* @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        if (req.getParameter("for") == null) {
            resp.sendRedirect("homepage");
            return;
        }
        List<Category> lastPointsAllCategories = categoryBean.getLastPointOfEachBranch();
        List<List<Category>> allFullCategories = new ArrayList<>();
        for (Category category : lastPointsAllCategories) {
            allFullCategories.add(categoryBean.getFullBranchOnTheLastCategoryPoint(category));
        }
        req.setAttribute("allFullCategories", allFullCategories);
        req.getRequestDispatcher("SelectCategory.jsp").forward(req, resp);
    }*/

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        req.setAttribute("opt_for_category",
                optionBean.getOptionsForCategory(Long.parseLong(req.getParameter("select_category"))));
        if (req.getParameter("for").equals("addp")) {
            req.getRequestDispatcher("AddProduct.jsp").forward(req, resp);
        }
        if (req.getParameter("for").equals("addo")) {
            req.getRequestDispatcher("AddOption.jsp").forward(req, resp);
        }
    }
}
