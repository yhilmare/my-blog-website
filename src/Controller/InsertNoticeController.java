package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogNotice2DBService;
import Utils.UniqueCode;
import domain.blog_holder;
import domain.blog_notice;


public class InsertNoticeController extends HttpServlet {
	
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
		blog_notice notice = new blog_notice();
		notice.setNotice_content(request.getParameter("notice_content"));
		notice.setNotice_id(UniqueCode.getUUID());
		notice.setHolder_id(holder.getHolder_id());
		BlogNotice2DBService service = new BlogNotice2DBService();
		if(service.insertNotice(notice) >= 1){
			response.getWriter().write("����ɹ�");
		}else{
			response.getWriter().write("����ʧ�ܣ�δ֪����");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
