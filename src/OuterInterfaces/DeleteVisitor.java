package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogVisitor2DBService;
import domain.TipMessage;
import domain.blog_visitor;
import net.sf.json.JSONObject;

public class DeleteVisitor extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String visitorID = request.getParameter("visitorID");
		TipMessage msg = new TipMessage();
		if (visitorID == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("ע���û�IDֵδ����");
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
		}else{
			BlogVisitor2DBService service = new BlogVisitor2DBService();
			blog_visitor visitor = new blog_visitor();
			visitor.setVisitor_id(visitorID);
			if(service.deleteVisitor(visitor) >= 1){
				msg.setMessageCode("200");
				msg.setMessageDetail("�û�ɾ���ɹ�");
			}else{
				msg.setMessageCode("-300");
				msg.setMessageDetail("�û�ɾ��ʧ��");
			}
		}
		JSONObject obj = JSONObject.fromObject(msg);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
