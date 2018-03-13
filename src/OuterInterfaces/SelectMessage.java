package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogMessage2DBService;
import domain.TipMessage;
import domain.blog_page;


public class SelectMessage extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String pageIndex = request.getParameter("pageIndex");
		TipMessage msg = new TipMessage();
		ObjectMapper mapper = new ObjectMapper();
		if (pageIndex == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("ҳ��ֵδ����");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("��¼�ѹ��ڻ�δ��¼");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		if(!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("��¼�ѹ��ڻ�δ��¼");
			response.getWriter().write(mapper.writeValueAsString(msg));
		}else{
			BlogMessage2DBService service = new BlogMessage2DBService();
			blog_page page;
			String pageContain = request.getParameter("pageContain");
			if (checkPageFrame(pageContain)){
				page = service.selectMessage(Integer.parseInt(pageIndex), Integer.parseInt(pageContain), 5);
			}else{
				page = service.selectMessage(Integer.parseInt(pageIndex), 5, 5);
			}
			if(page == null){
				msg.setMessageCode("-300");
				msg.setMessageDetail("˵˵��ȡʧ��");
				response.getWriter().write(mapper.writeValueAsString(msg));
			}else{
				response.getWriter().write(mapper.writeValueAsString(page));
			}
		}
	}
	
	private boolean checkPageFrame(String pageFrame){
		if (pageFrame == null){
			return false;
		}else{
			try{
				Integer.parseInt(pageFrame);
			}catch(Exception e){
				return false;
			}
			return true;
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
