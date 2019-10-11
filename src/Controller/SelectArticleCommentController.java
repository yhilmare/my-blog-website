package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogComment2DBService;
import Service.BlogCommentReply2DBService;
import domain.blog_comment;
import domain.blog_page;

public class SelectArticleCommentController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageIndex = request.getParameter("pageIndex");
		String articleID = request.getParameter("articleID");
		if (pageIndex == null || articleID == null) {
			response.getWriter().write("²ÎÊý´«µÝ´íÎó");
			return;
		}
		int index = Integer.parseInt(pageIndex);
		BlogComment2DBService commentService = new BlogComment2DBService();
		BlogCommentReply2DBService commentReplyService = new BlogCommentReply2DBService();
		blog_page page = commentService.selectCommentByArticleID(index, 5, 5, articleID);
		for(int i = 0; i < page.getList().size(); i ++) {
			blog_comment comment = (blog_comment) page.getList().get(i);
			blog_page subPage = commentReplyService.selectCommentReplyForFrontEnd(1, 3, 5, comment.getComment_id());
			comment.setSubComment(subPage);
		}
		ObjectMapper mapper = new ObjectMapper();
		String returnStr = mapper.writeValueAsString(page);
		response.getWriter().write(returnStr);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
