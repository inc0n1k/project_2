package servlet.addproduct;

import bean.CategoryBean;
import bean.OptionBean;
import bean.ProductBean;
import bean.ValueBean;
import entity.Option;
import entity.Product;
import entity.Value;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("addnewproduct")
public class AddNewProduct extends HttpServlet {

    @EJB
    private OptionBean optionBean;

    @EJB
    private CategoryBean categoryBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private ValueBean valueBean;

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        Product new_product = new Product();
        new_product.setName(req.getParameter("name_prod"));
        new_product.setPrice(Integer.parseInt(req.getParameter("price_prod")));
        new_product.setCategory(categoryBean.getCategory(Long.parseLong(req.getParameter("category"))));
        new_product = productBean.saveProduct(new_product);

        List<Option> options = optionBean.getOptionsForCategory(Long.parseLong(req.getParameter("category")));
        List<Value> values = new ArrayList<>();
        for (Option option : options) {
            Value value = new Value();
            value.setProduct(new_product);
            value.setOption(option);
            value.setValue(req.getParameter(option.getId().toString()));
            values.add(value);
        }
        valueBean.saveValuesList(values);
        resp.sendRedirect("homepage");
    }
}
