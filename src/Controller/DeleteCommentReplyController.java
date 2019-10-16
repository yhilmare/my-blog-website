package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogCommentReply2DBService;
import domain.blog_holder;


public class DeleteCommentReplyController extends HttpServlet {
	
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
		String commentReplyID = request.getParameter("commentReplyID");
		if (commentReplyID == null) {
			response.getWriter().write("参数传递错误");
			return;
		}
		BlogCommentReply2DBService commentService = new BlogCommentReply2DBService();
		if (commentService.deleteCommentReply(commentReplyID) == 1) {
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
