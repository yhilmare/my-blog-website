package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogComment2DBService;
import Service.BlogCommentReply2DBService;
import domain.blog_comment;
import domain.blog_comment_reply;
import domain.blog_holder;
import domain.blog_page;


public class SelectCommentReplyController extends HttpServlet {
	
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
		String pageIndex = request.getParameter("pageIndex");
		if (pageIndex == null) {
			response.getWriter().write("参数传递错误");
			return;
		}
		int index = Integer.parseInt(pageIndex);
		BlogCommentReply2DBService commentReplyService = new BlogCommentReply2DBService();
		blog_page page = commentReplyService.selectCommentReplyForBackEnd(index, 10, 10);
		BlogComment2DBService commentService = new BlogComment2DBService();
		Map<String, Integer> map = new HashMap<>();
		for(int i = 0; i < page.getList().size(); i ++) {
			blog_comment_reply reply = (blog_comment_reply) page.getList().get(i);
			String commentID = reply.getComment_id();
			if (map.get(commentID) == null) {
				map.put(commentID, i);
				blog_comment comment = commentService.selectCommentById(commentID);
				reply.setComment(comment);
			}else {
				blog_comment_reply tmp = (blog_comment_reply) page.getList().get(map.get(commentID));
				reply.setComment(tmp.getComment());
			}
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
