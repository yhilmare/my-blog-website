package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogComment2DBService;
import Service.BlogCommentReply2DBService;
import domain.blog_comment;
import domain.blog_holder;
import domain.blog_page;

public class SelectCommentController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("对不起您未登录，或登陆超时");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("对不起您未登录");
			return;
		}
		String pageIndex = request.getParameter("pageIndex");
		if (pageIndex == null) {
			response.getWriter().write("参数传递错误");
			return;
		}
		int index = Integer.parseInt(pageIndex);
		BlogComment2DBService commentService = new BlogComment2DBService();
		BlogCommentReply2DBService commentReplyService = new BlogCommentReply2DBService();
		blog_page page = commentService.selectComment(index, 10, 10);
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
		doGet(request, response);
	}

}
