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
			response.getWriter().write("δ֪����");
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
			response.getWriter().write("���������ύ�ɹ�������3��󷵻���ҳ�����޶�������<a href='/blog'>������ҳ</a>");
		}else{
			response.getWriter().write("���������ύʧ�ܣ�����3��󷵻���ҳ�����޶�������<a href='/blog'>������ҳ</a>");
		}
		response.setHeader("refresh", "3;url=/blog");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
