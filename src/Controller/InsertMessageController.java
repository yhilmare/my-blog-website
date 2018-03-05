package Controller;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogMessage2DBService;
import Utils.UniqueCode;
import domain.blog_message;
import domain.blog_visitor;


public class InsertMessageController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null){
			response.getWriter().write("未知错误");
			return;
		}
		blog_visitor visitor = (blog_visitor) session.getAttribute("visitor");
		blog_message message = new blog_message();
		Enumeration<String> enums = request.getParameterNames();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			try {
				if(name.equals("message_content")){
					PropertyDescriptor pd = new PropertyDescriptor(name, blog_message.class);
					pd.getWriteMethod().invoke(message, request.getParameterValues(name));
				}else{
					double value = Double.parseDouble(request.getParameter(name));
					PropertyDescriptor pd = new PropertyDescriptor(name, blog_message.class);
					pd.getWriteMethod().invoke(message, value);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(visitor == null){
			message.setVisitor_id(request.getRemoteAddr());
		}else{
			message.setVisitor_id(visitor.getVisitor_nickname());
		}
		message.setMessage_id(UniqueCode.getUUID());
		BlogMessage2DBService service = new BlogMessage2DBService();
		if(service.insertMessage(message) >= 1){
			response.getWriter().write("您的留言提交成功，将在3秒后返回首页，若无动作请点击<a href='/blog'>返回首页</a>");
		}else{
			response.getWriter().write("您的留言提交失败，将在3秒后返回首页，若无动作请点击<a href='/blog'>返回首页</a>");
		}
		response.setHeader("refresh", "3;url=/blog");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
