package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogStatus2DBService;
import Utils.UniqueCode;
import domain.TipMessage;
import domain.blog_holder;
import domain.blog_status;
import net.sf.json.JSONObject;


public class InsertStatus extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String statusContent = request.getParameter("statusContent");
		TipMessage msg = new TipMessage();
		if (statusContent == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("说说内容不能为空");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		blog_holder holder = (blog_holder) this.getServletContext().getAttribute("holder");
		if (temp == null || holder == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("服务器内部错误");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		if (!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("登录已过期或未登录");
		}else{
			blog_status status = new blog_status();
			status.setStatus_content(statusContent);
			status.setStatus_id(UniqueCode.getUUID());
			status.setHolder_id(holder.getHolder_id());
			BlogStatus2DBService service = new BlogStatus2DBService();
			if(service.insertStatus(status) >= 1){
				msg.setMessageCode("200");
				msg.setMessageDetail("说说发布成功");
			}else{
				msg.setMessageCode("-300");
				msg.setMessageDetail("说说发布失败");
			}
		}
		JSONObject obj = JSONObject.fromObject(msg);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
