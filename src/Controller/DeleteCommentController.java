package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import Service.BlogComment2DBService;
import domain.blog_holder;


public class DeleteCommentController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("对不起您未登录");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("对不起您未登录");
			return;
		}
		String commentID = request.getParameter("commentID");
		if (commentID == null) {
			response.getWriter().write("参数传递错误");
			return;
		}
		BlogComment2DBService commentService = new BlogComment2DBService();
		if (commentService.deleteComment(commentID) == 1) {
			response.getWriter().write("删除成功");
		}else {
			response.getWriter().write("删除失败");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
