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


public class DeleteArticleCommentController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.getWriter().write("Äú»¹Î´µÇÂ¼");
			return;
		}
		blog_visitor visitor = (blog_visitor) session.getAttribute("visitor");
		if (visitor == null) {
			response.getWriter().write("Äú»¹Î´µÇÂ¼");
			return;
		}
		String commentID = request.getParameter("commentID");
		if (commentID == null) {
			response.getWriter().write("²ÎÊý´íÎó");
			return;
		}
		BlogComment2DBService service = new BlogComment2DBService();
		blog_comment comment = service.selectCommentById(commentID);
		if (!comment.getVisitor_id().equals(visitor.getVisitor_id())) {
			response.getWriter().write("ID´íÎó£¬É¾³ýÊ§°Ü");
			return;
		}
		comment.setComment_visibility(0);
		if(service.updateComment(comment) < 1) {
			response.getWriter().write("É¾³ýÊ§°Ü");
		}else {
			response.getWriter().write("É¾³ý³É¹¦");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
