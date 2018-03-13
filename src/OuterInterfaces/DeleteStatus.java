package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogStatus2DBService;
import domain.TipMessage;


public class DeleteStatus extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String statusID = request.getParameter("statusID");
		String loginID = request.getParameter("loginID");
		TipMessage msg = new TipMessage();
		ObjectMapper mapper = new ObjectMapper();
		if (statusID == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("说说ID值未传送");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("登录已过期或未登录");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		if (!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("登录已过期或未登录");
		}else{
			BlogStatus2DBService service = new BlogStatus2DBService();
			if(service.deleteStatus(statusID) < 1){
				msg.setMessageCode("-300");
				msg.setMessageDetail("删除说说失败");
			}else{
				msg.setMessageCode("200");
				msg.setMessageDetail("删除说说成功");
			}
		}
		response.getWriter().write(mapper.writeValueAsString(msg));
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
