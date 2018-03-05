package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogVisitArticle2DBService;
import Service.BlogVisitor2DBService;
import domain.blog_visitor;


public class DeleteArticleVisitController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("����¼��ʱ�������µ�¼");
			return;
		}
		if(session.getAttribute("holder") == null){
			response.getWriter().write("δ֪����");
			return;
		}
		String visit_id = request.getParameter("visit_id");
		if(visit_id == null){
			response.getWriter().write("ɾ��ʧ�ܣ�û�д�����ȷ�ļ�¼ID");
			return;
		}
		BlogVisitArticle2DBService service = new BlogVisitArticle2DBService();
		if(service.deleteVisitArticle(visit_id) >= 1){
			response.getWriter().write("��¼ɾ���ɹ�");
		}else{
			response.getWriter().write("��¼ɾ��ʧ��");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
