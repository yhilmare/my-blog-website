package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogLogin2DBService;


public class DeleteLoginRecordController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("����¼��ʱ�������µ�¼");
			return;
		}
		if(session.getAttribute("holder") == null){
			response.getWriter().write("δ֪����");
			return;
		}
		String login_id = request.getParameter("login_id");
		if(login_id == null){
			response.getWriter().write("ɾ��ʧ�ܣ�û�д�����ȷ�ļ�¼ID");
			return;
		}
		BlogLogin2DBService service = new BlogLogin2DBService();
		if(service.deleteByID(login_id) >= 1){
			response.getWriter().write("��¼ɾ���ɹ�");
		}else{
			response.getWriter().write("��¼ɾ��ʧ��");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
