package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import Service.BlogComment2DBService;
import domain.blog_holder;


public class DeleteCommentController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("�Բ�����δ��¼");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("�Բ�����δ��¼");
			return;
		}
		String commentID = request.getParameter("commentID");
		if (commentID == null) {
			response.getWriter().write("�������ݴ���");
			return;
		}
		BlogComment2DBService commentService = new BlogComment2DBService();
		if (commentService.deleteComment(commentID) == 1) {
			response.getWriter().write("ɾ���ɹ�");
		}else {
			response.getWriter().write("ɾ��ʧ��");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
