package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import Service.BlogComment2DBService;
import domain.blog_comment;
import domain.blog_holder;


public class UpdateCommentController extends HttpServlet {
	
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
		String commentVisibility = request.getParameter("commentVisibility");
		if (commentID == null) {
			response.getWriter().write("��������");
			return;
		}
		BlogComment2DBService service = new BlogComment2DBService();
		blog_comment comment = service.selectCommentById(commentID);
		if (commentVisibility == null) {
			Integer flag = comment.getComment_visibility() == 0 ? 1 : 0;
			comment.setComment_visibility(flag);
		}else {
			Integer flag = Integer.parseInt(commentVisibility);
			comment.setComment_visibility(flag);
		}
		if(service.updateComment(comment) < 1) {
			response.getWriter().write("�޸�ʧ��");
		}else {
			response.getWriter().write("�޸ĳɹ�");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
