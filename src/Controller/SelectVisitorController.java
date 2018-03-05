package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogVisitor2DBService;
import domain.blog_page;
import net.sf.json.JSONObject;


public class SelectVisitorController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("ÄúµÇÂ¼³¬Ê±£¬ÇëÖØÐÂµÇÂ¼");
			return;
		}
		if(session.getAttribute("holder") == null){
			response.getWriter().write("Î´Öª´íÎó");
			return;
		}
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex == null){
			response.getWriter().write("Çë´«µÝÕýÈ·µÄÒ³Âë");
			return;
		}
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		blog_page page = service.selectUserByList(Integer.parseInt(pageIndex), 12, 5);
		if(page != null){
			JSONObject obj = JSONObject.fromObject(page);
			response.getWriter().write(obj.toString());
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
