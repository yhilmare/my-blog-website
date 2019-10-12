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


public class SelectArticleCommentReplyController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageIndex = request.getParameter("pageIndex");
		String commentID = request.getParameter("commentID");
		String pageContain = request.getParameter("pageContain");
		if (pageIndex == null || commentID == null) {
			response.getWriter().write("²ÎÊý´«µÝ´íÎó");
			return;
		}
		if (pageContain == null) {
			pageContain = "3";
		}
		int index = Integer.parseInt(pageIndex);
		BlogCommentReply2DBService commentReplyService = new BlogCommentReply2DBService();
		
		blog_page page = commentReplyService.selectCommentReplyForFrontEnd(index, Integer.parseInt(pageContain), 5, commentID);
		ObjectMapper mapper = new ObjectMapper();
		String returnStr = mapper.writeValueAsString(page);
		response.getWriter().write(returnStr);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
