package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogCommentReply2DBService;
import domain.blog_comment_reply;
import domain.blog_holder;


public class UpdateCommentReplyController extends HttpServlet {
	
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
		String commenReplytVisibility = request.getParameter("commenReplytVisibility");
		if (commentReplyID == null) {
			response.getWriter().write("参数错误");
			return;
		}
		BlogCommentReply2DBService service = new BlogCommentReply2DBService();
		blog_comment_reply reply = service.selectCommentReplyById(commentReplyID);
		if (commenReplytVisibility == null) {
			Integer flag = reply.getComment_reply_visibility() == 0 ? 1 : 0;
			reply.setComment_reply_visibility(flag);
		}else {
			Integer flag = Integer.parseInt(commenReplytVisibility);
			reply.setComment_reply_visibility(flag);
		}
		if(service.updateCommentReply(reply) < 1) {
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
