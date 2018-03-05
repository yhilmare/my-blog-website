package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogStatus2DBService;
import Utils.UniqueCode;
import domain.blog_holder;
import domain.blog_status;


public class InsertStatusController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("����û�е�¼�����½��ʱ");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("����û�е�¼�����½��ʱ");
			return;
		}
		blog_status status = new blog_status();
		status.setStatus_content(request.getParameter("status_content"));
		status.setStatus_id(UniqueCode.getUUID());
		status.setHolder_id(holder.getHolder_id());
		BlogStatus2DBService service = new BlogStatus2DBService();
		if(service.insertStatus(status) >= 1){
			response.getWriter().write("����ɹ�");
		}else{
			response.getWriter().write("����ʧ�ܣ�δ֪����");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
