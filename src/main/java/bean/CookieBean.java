package bean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


@Stateless
public class CookieBean {

    @Inject
    private HttpServletRequest req;

    public String getCookie(String name) throws UnsupportedEncodingException {
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals(name)) {
                return URLDecoder.decode(cookie.getValue(),"utf-8");
            }
        }
        return "";
    }

    public Cookie createCookie(String name, String value, int time) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(name, URLEncoder.encode(value, "utf-8"));
        cookie.setMaxAge(time);
        return cookie;
    }

    public Cookie createCookie(String name, int value, int time) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(name, URLEncoder.encode(String.valueOf(value), "utf-8"));
        cookie.setMaxAge(time);
        return cookie;
    }
}
