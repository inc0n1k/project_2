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
import java.util.List;

@WebServlet("/addtocart")
public class AddToCart extends HttpServlet {

    @EJB
    private CartBean cartBean;

    @EJB
    private CookieBean cookieBean;

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws
            ServletException, IOException {
        //Установка параметров запроса и ответа
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        long id = Long.parseLong(req.getParameter("id"));

        int new_count = req.getParameter("count") != null ?
                Integer.parseInt(req.getParameter("count")) : 0;

        Gson gson = new Gson();

        List<CartItemJSON> cartItems = cartBean.getCartItemsList();
        int count = cartBean.getCartCountValue();
        String gsonString = "";

//        Cookie cartCookie = null;
//        Cookie countCookie = null;
        switch (count) {
            //count == 0
//    if (count == 0) {
            case 0:
                cartItems.add(new CartItemJSON(id, 1));
                gsonString = gson.toJson(cartItems);
                count = 1;
                break;
//        cartCookie = new Cookie("cart", URLEncoder.encode(gson.toJson(new CartItemJSON(id, 1)), "utf-8"));
//        countCookie = new Cookie("count", "1");
//    }
            //count == 1
//            if (count == 1) {
            case 1:
                if (cartItems.get(0).getId().equals(id)) {
                    cartItems.get(0).setCount(new_count == 0 ? cartItems.get(0).getCount() + 1 : new_count);
//                    gsonString = gson.toJson(new CartItemJSON(cartItems.get(0).getId(), new_count == 0 ? cartItems.get(0).getCount() + 1 : new_count));
                    gsonString = gson.toJson(cartItems);
//                    cartCookie = new Cookie("cart", URLEncoder.encode(gson.toJson(new CartItemJSON(cartItems.get(0).getId(), new_count == 0 ? cartItems.get(0).getCount() + 1 : new_count)), "utf-8"));
//                    countCookie = new Cookie("count", "1");
                } else {
                    cartItems.add(new CartItemJSON(id, 1));
                    gsonString = gson.toJson(cartItems);
                    count++;
//                    cartCookie = new Cookie("cart", gson.toJson(cartItems));
//                    countCookie = new Cookie("count", "2");
                }
                break;
//            }
            //count > 1
//            if (count > 1) {
            default:
                boolean key = false;
                for (CartItemJSON cartItem : cartItems) {
                    if (cartItem.getId().equals(id)) {
                        cartItem.setCount(new_count == 0 ? cartItem.getCount() + 1 : new_count);
                        key = true;
                        break;
                    }
                }
                if (key) {
                    gsonString = gson.toJson(cartItems);
//                    cartCookie = new Cookie("cart", gson.toJson(cartItems));
//                    countCookie = new Cookie("count", String.valueOf(count));
                } else {
                    cartItems.add(new CartItemJSON(id, 1));
                    gsonString = gson.toJson(cartItems);
                    count++;
//                    cartCookie = new Cookie("cart", gson.toJson(cartItems));
//                    countCookie = new Cookie("count", String.valueOf(count + 1));
                }
//            }
        }
//        cartCookie.setMaxAge(60);
//        countCookie.setMaxAge(60);
//        resp.addCookie(cartCookie);
//        resp.addCookie(countCookie);
        resp.addCookie(cookieBean.createCookie("cart", gsonString, 60 * 60));
        resp.addCookie(cookieBean.createCookie("count", count, 60 * 60));
    }
}
