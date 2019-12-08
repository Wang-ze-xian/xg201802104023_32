package cn.edu.sdjzu.xg.bysj.basic.sessionAndcookie;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/showCookies")
public class CookieControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] allCookies = request.getCookies();
        if(allCookies == null){
            response.getWriter().println("no Cookie available");
        }else {
            for(Cookie cookie:allCookies){
                response.getWriter().println(cookie.getName() + "=" + cookie.getValue());
            }
        }
    }
}
