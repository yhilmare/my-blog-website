package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogArticle2DBService;
import Service.BlogStatus2DBService;
import domain.blog_info;
import net.sf.json.JSONObject;


public class GetTotalRecordController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BlogArticle2DBService service = new BlogArticle2DBService();
		BlogStatus2DBService service1 = new BlogStatus2DBService();
		blog_info info = new blog_info();
		info.setTotalArticle(service.getTotalRecord());
		info.setTotalStatus(service1.getTotalRecord());
		JSONObject obj = JSONObject.fromObject(info);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
