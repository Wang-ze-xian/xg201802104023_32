package filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebFilter (filterName = "Filter 0 ",urlPatterns = {"/*"})
public class EncodingFilter implements Filter {

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        return df.format(date);
    }

    @Override
    public void destroy() { }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("EncodingFilter -encoding begins");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //HttpSession session = request.getSession(false);
        //JSONObject message = new JSONObject();


        /**if(session == null || session.getAttribute("currentUser") == null){
         message.put("message","请登录或重新登录");
         response.getWriter().println(message);
         return;
         }**/

        String path = request.getRequestURI();
        System.out.println("set response");
        response.setContentType("text/html;charset=UTF-8");
        String method = request.getMethod();
        if ("POST-PUT".contains(method)){
                request.setCharacterEncoding("UTF-8");
            }
        System.out.print(path + " @ ");
        System.out.println(this.getTime());
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("EncodingFilter -encoding ends");
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
}
