package servlet.addoption;

import bean.CategoryBean;
import bean.OptionBean;
import entity.Category;
import entity.Option;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addnewoption")
public class AddNewOption extends HttpServlet {

    @EJB
    private CategoryBean categoryBean;

    @EJB
    private OptionBean optionBean;

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        Category category = categoryBean.getCategory(Long.parseLong(req.getParameter("category")));
        for (String new_opt : req.getParameterValues("options")) {
            if (!new_opt.trim().isEmpty()) {
                Option option = new Option();
                option.setName(new_opt);
                option.setCategory(category);
                optionBean.saveOption(option);
            }
        }
    }
}
