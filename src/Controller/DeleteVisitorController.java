package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogVisitor2DBService;
import domain.blog_visitor;


public class DeleteVisitorController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("您登录超时，请重新登录");
			return;
		}
		if(session.getAttribute("holder") == null){
			response.getWriter().write("未知错误");
			return;
		}
		String visitor_id = request.getParameter("visitor_id");
		if(visitor_id == null){
			response.getWriter().write("删除失败：没有传递正确的用户ID");
			return;
		}
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		blog_visitor visitor = new blog_visitor();
		visitor.setVisitor_id(visitor_id);
		if(service.deleteVisitor(visitor) >= 1){
			response.getWriter().write("用户删除成功");
		}else{
			response.getWriter().write("用户删除失败");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
