package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.blog_holder;


public class CancelHolderController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("���Ѿ��˳�ϵͳ");
			response.setHeader("refresh", "3;url=/blog");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder  == null){
			response.getWriter().write("���Ѿ��˳�ϵͳ");
			response.setHeader("refresh", "3;url=/blog");
			return;
		}
		session.removeAttribute("holder");
		session.invalidate();
		response.getWriter().write("���ã����Ѿ�ע������������󷵻���ҳ�����޷�Ӧ����<a href='/blog/index.html'>������ҳ</a>");
		response.setHeader("refresh", "3;url=/blog");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
