package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogStatus2DBService;
import domain.blog_page;
import net.sf.json.JSONObject;


public class SelectStatusForIndexController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex == null){
			response.getWriter().write("�봫����ȷ��ҳ��");
			return;
		}
		BlogStatus2DBService service = new BlogStatus2DBService();
		blog_page page = service.selectStatus(Integer.parseInt(pageIndex), 5, 5);
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
