package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogNotice2DBService;
import domain.blog_notice;
import domain.blog_page;
import net.sf.json.JSONObject;


public class SelectNoticeController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BlogNotice2DBService service = new BlogNotice2DBService();
		blog_page page = service.selectNotice(1, 1, 10);
		blog_notice notice;
		if(page.getList().size() == 0){
			notice = new blog_notice();
		}else{
			notice = (blog_notice) page.getList().get(0);
		}
		JSONObject obj = JSONObject.fromObject(notice);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
