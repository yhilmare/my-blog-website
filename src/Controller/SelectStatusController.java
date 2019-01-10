package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogStatus2DBService;
import domain.blog_holder;
import domain.blog_page;


public class SelectStatusController extends HttpServlet {
	
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
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex == null){
			response.getWriter().write("�봫����ȷ��ҳ��");
			return;
		}
		BlogStatus2DBService service = new BlogStatus2DBService();
		blog_page page = service.selectStatus(Integer.parseInt(pageIndex), 8, 5);
		if(page == null){
			response.getWriter().write("��ѯʧ��");
		}else{
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().write(mapper.writeValueAsString(page));
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
