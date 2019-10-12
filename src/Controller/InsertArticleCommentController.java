package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogComment2DBService;
import domain.blog_comment;
import domain.blog_visitor;


public class InsertArticleCommentController extends HttpServlet {
	
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
		String articleID = request.getParameter("articleID");
		String commentContent = request.getParameter("commentContent");
		if (articleID == null || commentContent == null) {
			response.getWriter().write("参数错误");
			return;
		}
		String remoteAddr = request.getRemoteAddr();
		blog_comment comment = new blog_comment();
		comment.setArticle_id(articleID);
		comment.setVisitor_id(visitor.getVisitor_id());
		comment.setComment_content(commentContent);
		comment.setComment_ip(remoteAddr);
		comment.setComment_visibility(1);
		BlogComment2DBService service = new BlogComment2DBService();
		if(service.insertComment(comment) < 1) {
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
