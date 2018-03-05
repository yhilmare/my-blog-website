package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogStatus2DBService;
import domain.blog_holder;


public class DeleteStatusController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("对不起您未登录，或登陆超时");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("对不起您未登录");
			return;
		}
		String status_id = request.getParameter("status_id");
		if(status_id == null){
			response.getWriter().write("参数传递错误");
			return;
		}
		BlogStatus2DBService service = new BlogStatus2DBService();
		
		if(service.deleteStatus(status_id) < 1){
			response.getWriter().write("删除失败");
		}else{
			response.getWriter().write("删除成功");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
