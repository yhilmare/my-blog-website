package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogLogin2DBService;


public class DeleteLoginRecordController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("您登录超时，请重新登录");
			return;
		}
		if(session.getAttribute("holder") == null){
			response.getWriter().write("未知错误");
			return;
		}
		String login_id = request.getParameter("login_id");
		if(login_id == null){
			response.getWriter().write("删除失败：没有传递正确的记录ID");
			return;
		}
		BlogLogin2DBService service = new BlogLogin2DBService();
		if(service.deleteByID(login_id) >= 1){
			response.getWriter().write("记录删除成功");
		}else{
			response.getWriter().write("记录删除失败");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
