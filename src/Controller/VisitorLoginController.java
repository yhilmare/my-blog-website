package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogVisitor2DBService;
import Utils.MD5Utils;
import domain.blog_visitor;
import net.sf.json.JSONObject;


public class VisitorLoginController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		blog_visitor visitor = service.selectVisitor(username);
		if(visitor == null){
			response.getWriter().write("用户不存在");
			return;
		}
		if(!visitor.getVisitor_pwd().equals(MD5Utils.getToken(password))){
			response.getWriter().write("密码错误");
			return;
		}
		request.getSession().setAttribute("visitor", visitor);
		JSONObject obj = JSONObject.fromObject(visitor);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
