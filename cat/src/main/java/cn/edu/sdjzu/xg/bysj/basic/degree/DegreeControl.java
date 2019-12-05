package cn.edu.sdjzu.xg.bysj.basic.degree;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import service.DegreeService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/degreeControl.ctl")
public class DegreeControl extends HttpServlet {
    //请使用以下JSON测试增加功能（id为空）
    //{"description":"id为null的新学院","no":"05","remarks":""}
    //请使用以下JSON测试修改功能
    //{"description":"修改id=1的学院","id":1,"no":"05","remarks":""}
    //49.234.210.182:8080/bysj2324/degreeControl.ctl
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String degree_json = JSONUtil.getJSON(request);

        //将JSON字串解析为School对象
        Degree degreeToAdd = JSON.parseObject(degree_json, Degree.class);
        //前台没有为id赋值，此处模拟自动生成id。如果Dao能真正完成数据库操作，删除下一行。

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加School对象
        HttpSession session = request.getSession(false);
        try {
            if(session == null || session.getAttribute("currentUser") == null) {
                message.put("message", "请登录或重新登录");
                response.getWriter().println(message);
                return;
            }
            DegreeService.getInstance().add(degreeToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    //49.234.210.182:8080/bysj2324/degreeControl.ctl?id=33
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //到数据库表中删除对应的学院
        try {
            DegreeService.getInstance().delete(id);
            message.put("message", "删除成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    //49.234.210.182:8080/bysj2324/degreeControl.ctl
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String degree_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        Degree degreeToAdd = JSON.parseObject(degree_json, Degree.class);

        //设置响应字符编码为UTF-8
        //response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            DegreeService.getInstance().update(degreeToAdd);
            message.put("message", "修改成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    //49.234.210.182:8080/bysj2324/degreeControl.ctl
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            if (id_str == null) {
                responseDegrees(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseDegree(id, response);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }
    //响应一个学院对象
    private void responseDegree(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        Degree degree = DegreeService.getInstance().find(id);
        String degree_json = JSON.toJSONString(degree);

        //响应school_json到前端
        response.getWriter().println(degree_json);
    }
    //响应所有学院对象
    private void responseDegrees(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<Degree> degrees = DegreeService.getInstance().findAll();
        String degree_json = JSON.toJSONString(degrees, SerializerFeature.DisableCircularReferenceDetect);

        //响应schools_json到前端
        response.getWriter().println(degree_json);
    }
}