package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogVisitor2DBService;
import domain.blog_visitor;


public class DeleteVisitorController extends HttpServlet {
	
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
		String visitor_id = request.getParameter("visitor_id");
		if(visitor_id == null){
			response.getWriter().write("ɾ��ʧ�ܣ�û�д�����ȷ���û�ID");
			return;
		}
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		blog_visitor visitor = new blog_visitor();
		visitor.setVisitor_id(visitor_id);
		if(service.deleteVisitor(visitor) >= 1){
			response.getWriter().write("�û�ɾ���ɹ�");
		}else{
			response.getWriter().write("�û�ɾ��ʧ��");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
