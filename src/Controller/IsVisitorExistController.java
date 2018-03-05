package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogVisitor2DBService;


public class IsVisitorExistController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("visitor_nickname");
		if(name == null){
			response.getWriter().write("nodata");
		}
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		boolean result = service.isExist(name);
		if(result){
			response.getWriter().write("error");
		}else{
			response.getWriter().write("success");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
