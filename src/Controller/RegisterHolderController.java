package Controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogHolder2DBService;
import Utils.UniqueCode;
import domain.blog_holder;

//博主注册接口，不使用，此接口也不好用！勿用，只是做将来扩展用
public class RegisterHolderController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> enums = request.getParameterNames();
		blog_holder holder = new blog_holder();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			try {
				if(name.equals("holder_school_year")){
					PropertyDescriptor pd = new PropertyDescriptor(name, blog_holder.class);
					pd.getWriteMethod().invoke(holder, Integer.parseInt(request.getParameter(name)));
					continue;
				}
				PropertyDescriptor pd = new PropertyDescriptor(name, blog_holder.class);
				pd.getWriteMethod().invoke(holder, request.getParameter(name));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BlogHolder2DBService service = new BlogHolder2DBService();
		holder.setHolder_id(UniqueCode.getUUID());
		if(service.insertHolder(holder) > 0){ 
			response.getOutputStream().write("注入成功".getBytes("UTF-8"));
		}else{
			response.getOutputStream().write("注入失败".getBytes("UTF-8"));
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
