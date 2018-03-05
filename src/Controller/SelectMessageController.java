package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogMessage2DBService;
import domain.blog_holder;
import domain.blog_page;
import net.sf.json.JSONObject;


public class SelectMessageController extends HttpServlet {
	
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
		BlogMessage2DBService service = new BlogMessage2DBService();
		blog_page page = service.selectMessage(Integer.parseInt(pageIndex), 5, 5);
		if(page == null){
			response.getWriter().write("��ѯʧ��");
		}else{
			JSONObject obj = JSONObject.fromObject(page);
			response.getWriter().write(obj.toString());
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
