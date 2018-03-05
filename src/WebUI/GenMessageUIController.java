package WebUI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.blog_visitor;


public class GenMessageUIController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if(session == null){
			request.setAttribute("message", "��⵽����û�е�¼���¼��ʱ����ȷ��Ҫ��������������");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
			return;
		}
		blog_visitor visitor = (blog_visitor) session.getAttribute("visitor");
		if(visitor == null){
			request.setAttribute("message", "��⵽����û�е�¼����ȷ��Ҫ��������������");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request, response);
			return;
		}
		response.setHeader("expires", "-1");
		request.getRequestDispatcher("/message.html").forward(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
