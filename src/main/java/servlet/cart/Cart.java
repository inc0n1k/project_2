package servlet.cart;

import JSONEntity.CartItemJSON;
import bean.CartBean;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class Cart extends HttpServlet {

    @EJB
    private CartBean cartItemsBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private ValueBean valueBean;

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        List<CartItemJSON> cartItems = cartItemsBean.getCartItemsList();
        List<Long> productsId = new ArrayList<>();
        for (CartItemJSON cartItem : cartItems) {
            productsId.add(cartItem.getId());
        }

        if (productsId.isEmpty()) {
            resp.sendRedirect("HomePage.jsp");
        } else {
            List<Product> products = productBean.getListOfProductsByListOfProductIds(productsId);
//            Map<Product, List<Value>> productMap = new TreeMap<>();
//            for (Product product : products) {
//                List<Value> values = valueBean.getValueListForProduct(product.getId());
//                productMap.put(product, values);
//            }
//            req.setAttribute("all_p_v", productMap);
            req.setAttribute("products", products);
            req.getRequestDispatcher("Cart.jsp").forward(req, resp);
        }
    }
}