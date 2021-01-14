package servlet.editproduct;

import bean.CategoryBean;
import bean.ProductBean;
import bean.ValueBean;
import entity.Product;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/allproducts")
public class AllProducts extends HttpServlet {

    @EJB
    private ProductBean productBean;

    @EJB
    private ValueBean valueBean;

    @EJB
    private CategoryBean categoryBean;

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        Product product = productBean.getProduct(Long.parseLong(req.getParameter("edit_product")));
        req.setAttribute("full_category", categoryBean.getFullBranchOnTheLastCategoryPoint(product.getCategory()));
        req.setAttribute("product", product);
        req.setAttribute("values", valueBean.getValuesForProduct(Long.parseLong(req.getParameter("edit_product"))));
        req.getRequestDispatcher("EditProduct.jsp").forward(req, resp);
    }

    @Override
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
        List<Product> products = productBean.getAllProductsByCategory(Long.parseLong(req.getParameter("select_category")));
//        System.out.println(products.size());
        req.setAttribute("products", products);
        req.getRequestDispatcher("AllProducts.jsp").forward(req, resp);
    }
}
