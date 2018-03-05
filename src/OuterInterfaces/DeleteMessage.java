package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogMessage2DBService;
import domain.TipMessage;
import net.sf.json.JSONObject;


public class DeleteMessage extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String messageID = request.getParameter("messageID");
		TipMessage msg = new TipMessage();
		if (messageID == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("ÁôÑÔIDÖµÎ´´«ËÍ");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("µÇÂ¼ÒÑ¹ýÆÚ»òÎ´µÇÂ¼");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		if (!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("µÇÂ¼ÒÑ¹ýÆÚ»òÎ´µÇÂ¼");
		}else{
			BlogMessage2DBService service = new BlogMessage2DBService();
			if(service.deleteMessage(messageID) < 1){
				msg.setMessageCode("-300");
				msg.setMessageDetail("É¾³ýÁôÑÔÊ§°Ü");
			}else{
				msg.setMessageCode("200");
				msg.setMessageDetail("É¾³ýÁôÑÔ³É¹¦");
			}
		}
		JSONObject obj = JSONObject.fromObject(msg);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
