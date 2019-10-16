package Controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogCommentReply2DBService;
import Service.BlogVisitor2DBService;
import domain.blog_comment_reply;
import domain.blog_holder;
import domain.blog_visitor;


public class InsertCommentReplyForHolderController extends HttpServlet {
	
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
		String commentReplyContent = request.getParameter("commentReplyContent");
		if (commentID == null || commentReplyContent == null) {
			response.getWriter().write("参数错误");
			return;
		}
		BlogVisitor2DBService ser = new BlogVisitor2DBService();
		blog_visitor visitor = ser.selectVisitor("IL MARE");
		if (visitor == null) {
			visitor = new blog_visitor();
			visitor.setVisitor_nickname("IL MARE");
			visitor.setVisitor_gender("男");
			visitor.setProvince(holder.getHolder_province_zh());
			visitor.setCity(holder.getHolder_city_zh());
			visitor.setFigureurl("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_1("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_2("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq_1("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq_2("/blog/" + holder.getHolder_img());
			if (ser.insertVisitor(visitor) != 1) {
				response.getWriter().write("visitor对象插入失败，错误");
				return;
			}
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
