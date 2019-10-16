package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import Service.BlogComment2DBService;
import domain.blog_comment;
import domain.blog_holder;


public class UpdateCommentController extends HttpServlet {
	
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
		String commentVisibility = request.getParameter("commentVisibility");
		if (commentID == null) {
			response.getWriter().write("参数错误");
			return;
		}
		BlogComment2DBService service = new BlogComment2DBService();
		blog_comment comment = service.selectCommentById(commentID);
		if (commentVisibility == null) {
			Integer flag = comment.getComment_visibility() == 0 ? 1 : 0;
			comment.setComment_visibility(flag);
		}else {
			Integer flag = Integer.parseInt(commentVisibility);
			comment.setComment_visibility(flag);
		}
		if(service.updateComment(comment) < 1) {
			response.getWriter().write("修改失败");
		}else {
			response.getWriter().write("修改成功");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
