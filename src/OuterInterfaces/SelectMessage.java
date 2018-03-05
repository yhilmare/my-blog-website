package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogMessage2DBService;
import domain.TipMessage;
import domain.blog_page;
import net.sf.json.JSONObject;


public class SelectMessage extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String pageIndex = request.getParameter("pageIndex");
		TipMessage msg = new TipMessage();
		if (pageIndex == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("ҳ��ֵδ����");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("��¼�ѹ��ڻ�δ��¼");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		if(!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("��¼�ѹ��ڻ�δ��¼");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
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
				JSONObject obj = JSONObject.fromObject(msg);
				response.getWriter().write(obj.toString());
			}else{
				JSONObject obj = JSONObject.fromObject(page);
				response.getWriter().write(obj.toString());
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
