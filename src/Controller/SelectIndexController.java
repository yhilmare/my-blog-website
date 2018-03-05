package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogIndex2DBService;
import domain.blog_page;
import net.sf.json.JSONObject;


public class SelectIndexController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex == null){
			response.getWriter().write("{'code':'error'}");
			return;
		}
		BlogIndex2DBService service = new BlogIndex2DBService();
		blog_page page = service.selectIndex(Integer.parseInt(pageIndex), 5, 5);
		JSONObject obj = JSONObject.fromObject(page);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
