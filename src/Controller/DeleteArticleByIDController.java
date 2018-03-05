package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogArticle2DBService;
import domain.blog_holder;


public class DeleteArticleByIDController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("�Բ�����δ��¼�����½��ʱ");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("�Բ�����δ��¼");
			return;
		}
		String article_id = request.getParameter("article_id");
		if(article_id == null){
			response.getWriter().write("�������ݴ���");
			return;
		}
		BlogArticle2DBService service = new BlogArticle2DBService();
		
		if(service.deleteArticle(article_id) < 1){
			response.getWriter().write("ɾ��ʧ��");
		}else{
			response.getWriter().write("ɾ���ɹ�");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
