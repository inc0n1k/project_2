package bean;

import JSONEntity.CartItemJSON;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class CartBean {

    @EJB
    private CookieBean cookieBean;

    public List<CartItemJSON> getCartItemsList() throws UnsupportedEncodingException {
        List<CartItemJSON> cartItems = new ArrayList<>();

        String gsonString = cookieBean.getCookie("cart");
        switch (gsonString.lastIndexOf("{")) {
            case -1:
                return cartItems;
            case 0:
                cartItems.add(new Gson().fromJson(gsonString, CartItemJSON.class));
                break;
            default:
                cartItems.addAll(Arrays.asList(new Gson().fromJson(gsonString, CartItemJSON[].class)));
        }
        return cartItems;
    }

    public Integer getCartCountValue() throws UnsupportedEncodingException {
        return cookieBean.getCookie("count").isEmpty() ?
                0 : Integer.parseInt(cookieBean.getCookie("count"));
    }
}
