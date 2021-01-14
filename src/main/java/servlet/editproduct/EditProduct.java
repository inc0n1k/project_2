package servlet.editproduct;

import bean.ProductBean;
import bean.ValueBean;
import entity.Product;
import entity.Value;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/editproduct")
public class EditProduct extends HttpServlet {

    @EJB
    private ValueBean valueBean;

    @EJB
    private ProductBean productBean;

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        Product product = productBean.getProduct(Long.parseLong(req.getParameter("product")));
        if (!req.getParameter("pro_name").isEmpty()) {
            product.setName(req.getParameter("pro_name"));
        }
        if (!req.getParameter("pro_price").isEmpty()) {
            product.setPrice(Integer.parseInt(req.getParameter("pro_price")));
        }
        productBean.updateProduct(product);

        List<Value> values = valueBean.getValuesForProduct(product.getId());
        for (Value value : values) {
            if (!req.getParameter(value.getId().toString()).isEmpty()) {
                value.setValue(req.getParameter(value.getId().toString()));
            }
        }
        valueBean.updateValuesList(values);
        resp.sendRedirect("allproducts?for=edit");
    }
}
