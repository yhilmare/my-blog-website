package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Service.BlogHolder2DBService;
import domain.blog_holder;


public class HolderLoginController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username == null || password == null){
			return;
		}
		BlogHolder2DBService service = new BlogHolder2DBService();
		blog_holder holder = service.selectHolder(username);
		if(holder == null){
			response.getOutputStream().write("用户不存在".getBytes(response.getCharacterEncoding()));
			return;
		}
		
		if(holder.getHolder_pwd().equals(password)){
			request.getSession(true).setAttribute("holder", holder);
			request.getRequestDispatcher("/WEB-INF/managementPage/blogManagement.html").forward(request, response);
		}else{
			response.getWriter().write("登录失败");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
