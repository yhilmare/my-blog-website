package Controller;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogVisit2DBService;
import domain.blog_visit;


public class InsertVisitLogController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> enums = request.getParameterNames();
		blog_visit visit = new blog_visit();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			try {
				PropertyDescriptor pd = new PropertyDescriptor(name, blog_visit.class);
				if(name.equals("visitor_id")){
					String value = request.getParameter(name);
					value = value.equals("notlogin")?request.getRemoteAddr():value;
					pd.getWriteMethod().invoke(visit, value);
					continue;
				}
				double value = request.getParameter(name) == null?0:Double.parseDouble(request.getParameter(name));
				pd.getWriteMethod().invoke(visit, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BlogVisit2DBService service = new BlogVisit2DBService();
		service.insertVisit(visit);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
