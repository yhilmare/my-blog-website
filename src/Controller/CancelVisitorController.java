package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.blog_visitor;


public class CancelVisitorController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("���ѱ�ע��");
			return;
		}
		blog_visitor visitor = (blog_visitor) session.getAttribute("visitor");
		if(visitor == null){
			response.getWriter().write("���ѱ�ע��");
			return;
		}
		session.removeAttribute("visitor");
		session.invalidate();
		response.getWriter().write("ע���ɹ�");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
