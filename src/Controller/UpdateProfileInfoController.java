package Controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Base64.Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogHolder2DBService;
import Service.BlogVisitor2DBService;
import Utils.MD5Utils;
import domain.blog_holder;
import domain.blog_visitor;


public class UpdateProfileInfoController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("您还没有登录，或登陆超时");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("您还没有登录，或登陆超时");
			return;
		}
		Enumeration<String> enums = request.getParameterNames();
		blog_holder holder_new = new blog_holder();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			try {
				if(name.equals("holder_school_year")){
					PropertyDescriptor pd = new PropertyDescriptor(name, blog_holder.class);
					pd.getWriteMethod().invoke(holder_new, Integer.parseInt(request.getParameter(name)));
				}else{
					PropertyDescriptor pd = new PropertyDescriptor(name, blog_holder.class);
					pd.getWriteMethod().invoke(holder_new, request.getParameter(name));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		holder_new.setHolder_id(holder.getHolder_id());
		holder_new.setHolder_pwd(MD5Utils.getToken(holder_new.getHolder_pwd()));
		holder_new.setHolder_img(holder.getHolder_img());
		
		BlogVisitor2DBService ser = new BlogVisitor2DBService();
		blog_visitor visitor = ser.selectVisitor("IL MARE");
		if (visitor == null) {
			visitor = new blog_visitor();
			visitor.setVisitor_nickname("IL MARE");
			visitor.setVisitor_gender("男");
			visitor.setProvince(holder_new.getHolder_province_zh());
			visitor.setCity(holder_new.getHolder_city_zh());
			visitor.setFigureurl("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_1("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_2("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_qq("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_qq_1("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_qq_2("/blog/" + holder_new.getHolder_img());
			if (ser.insertVisitor(visitor) != 1) {
				response.getWriter().write("visitor对象插入失败，错误");
				return;
			}
		}else {
			visitor.setProvince(holder_new.getHolder_province_zh());
			visitor.setCity(holder_new.getHolder_city_zh());
			visitor.setFigureurl("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_1("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_2("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_qq("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_qq_1("/blog/" + holder_new.getHolder_img());
			visitor.setFigureurl_qq_2("/blog/" + holder_new.getHolder_img());
			if (ser.updateVisitor(visitor) != 1) {
				response.getWriter().write("visitor对象更新失败，错误");
				return;
			}
		}
		
		BlogHolder2DBService service = new BlogHolder2DBService();
		if(service.updateHolder(holder_new) >= 1){
			this.getServletContext().setAttribute("holder", holder_new);
			session.setAttribute("holder", holder_new);
			response.getWriter().write("修改成功");
		}else{
			response.getWriter().write("修改失败，未知错误");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
