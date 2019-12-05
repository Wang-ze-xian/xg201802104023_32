package session;

import cn.edu.sdjzu.xg.bysj.domain.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        JSONObject message = new JSONObject();
        try{
            User loggedUser = UserService.getInstance().findByPasswordAndUsername(password,username);
            if (loggedUser != null){
                message.put("message","登陆成功");
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(2 * 60);
                session.setAttribute("currentUser",loggedUser);
                response.getWriter().println(message);
                return;
            }else{
                message.put("message","用户名或密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message","数据库操作异常");
        }
    }
}
