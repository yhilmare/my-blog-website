package Controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogHolder2DBService;
import Utils.MD5Utils;
import domain.blog_holder;


public class UpdateProfileInfoController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("����û�е�¼�����½��ʱ");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("����û�е�¼�����½��ʱ");
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
		BlogHolder2DBService service = new BlogHolder2DBService();
		if(service.updateHolder(holder_new) >= 1){
			this.getServletContext().setAttribute("holder", holder_new);
			session.setAttribute("holder", holder_new);
			response.getWriter().write("�޸ĳɹ�");
		}else{
			response.getWriter().write("�޸�ʧ�ܣ�δ֪����");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
