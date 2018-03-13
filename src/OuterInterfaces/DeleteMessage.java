package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogMessage2DBService;
import domain.TipMessage;


public class DeleteMessage extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String messageID = request.getParameter("messageID");
		TipMessage msg = new TipMessage();
		ObjectMapper mapper = new ObjectMapper();
		if (messageID == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("ÁôÑÔIDÖµÎ´´«ËÍ");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("µÇÂ¼ÒÑ¹ýÆÚ»òÎ´µÇÂ¼");
			response.getWriter().write(mapper.writeValueAsString(msg));
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
		response.getWriter().write(mapper.writeValueAsString(msg));
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
