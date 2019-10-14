package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogLogin2DBService;
import Service.BlogVisitor2DBService;
import Utils.Login2DBThread;
import Utils.MD5Utils;
import domain.blog_login;
import domain.blog_visitor;
import domain.IPResolve.IPResolveData;


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
		if (visitor.getVisitor_pwd() == null || visitor.getVisitor_pwd().length() == 0) {
			response.getWriter().write("登录用户类型错误");
			return;
		}
		if(!visitor.getVisitor_pwd().equals(MD5Utils.getToken(password))){
			response.getWriter().write("密码错误");
			return;
		}
		request.getSession().setAttribute("visitor", visitor);
		Login2DBThread thread = new Login2DBThread(request.getRemoteAddr(), visitor.getVisitor_id(), visitor.getVisitor_nickname());
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(visitor));
		thread.customerJoin();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
