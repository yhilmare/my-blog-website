package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogCommentReply2DBService;
import domain.blog_comment_reply;
import domain.blog_visitor;


public class InsertCommentReplyController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.getWriter().write("您还未登录");
			return;
		}
		blog_visitor visitor = (blog_visitor) session.getAttribute("visitor");
		if (visitor == null) {
			response.getWriter().write("您还未登录");
			return;
		}
		String commentID = request.getParameter("commentID");
		String commentReplyContent = request.getParameter("commentReplyContent");
		if (commentID == null || commentReplyContent == null) {
			response.getWriter().write("参数错误");
			return;
		}
		String remoteAddr = request.getRemoteAddr();
		blog_comment_reply reply = new blog_comment_reply();
		reply.setComment_id(commentID);
		reply.setVisitor_id(visitor.getVisitor_id());
		reply.setComment_reply_content(commentReplyContent);
		reply.setComment_reply_ip(remoteAddr);
		reply.setComment_reply_visibility(1);
		BlogCommentReply2DBService service = new BlogCommentReply2DBService();
		if(service.insertCommentReply(reply) < 1) {
			response.getWriter().write("评论发布失败");
		}else {
			response.getWriter().write("评论发布成功");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
