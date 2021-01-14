package servlet.cart;

import JSONEntity.CartItemJSON;
import bean.CartBean;
import bean.CookieBean;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("removefromcart")
public class RemoveFromCart extends HttpServlet {

    @EJB
    private CookieBean cookieBean;

    @EJB
    private CartBean cartBean;

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        int count = cartBean.getCartCountValue();
        String gsonString = cookieBean.getCookie("cart");

     /*   for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("count")) {
                if (cookie.getValue().equals("1")) {*/
        if (count == 1) {
            resp.addCookie(cookieBean.createCookie("cart", "", 0));
            resp.addCookie(cookieBean.createCookie("count", "", 0));
            return;
        }
//        else {
//                    count = Integer.parseInt(cookie.getValue());
//                }
//            }
//            if (cookie.getName().equals("cart")) {
//                gsonString = cookie.getValue();
//            }
//        }
//        Gson gson = new Gson();
//        Cookie cartCookie;
//        Cookie countCookie = new Cookie("count", String.valueOf(count - 1));

        long id = Long.parseLong(req.getParameter("id"));

        List<CartItemJSON> cartItems = new ArrayList<>();
        cartItems.addAll(Arrays.asList(new Gson().fromJson(gsonString, CartItemJSON[].class)));
        for (CartItemJSON cartItem : cartItems) {
            if (cartItem.getId() == id) {
                cartItems.remove(cartItem);
                break;
            }
        }
//        cartCookie = new Cookie("cart", gson.toJson(cartItems));
//        countCookie.setMaxAge(60);
//        cartCookie.setMaxAge(60);
//        resp.addCookie(countCookie);
//        resp.addCookie(cartCookie);
        resp.addCookie(cookieBean.createCookie("count", count - 1, 60 * 60));
        resp.addCookie(cookieBean.createCookie("cart", new Gson().toJson(cartItems), 60 * 60));
    }
}