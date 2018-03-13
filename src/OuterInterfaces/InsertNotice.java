package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogNotice2DBService;
import Utils.UniqueCode;
import domain.TipMessage;
import domain.blog_holder;
import domain.blog_notice;


public class InsertNotice extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String noticeContent = request.getParameter("noticeContent");
		TipMessage msg = new TipMessage();
		ObjectMapper mapper = new ObjectMapper();
		if (noticeContent == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("公告内容不能为空");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		blog_holder holder = (blog_holder) this.getServletContext().getAttribute("holder");
		if (temp == null || holder == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("服务器内部错误");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		if (!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("登录已过期或未登录");
		}else{
			blog_notice notice = new blog_notice();
			notice.setNotice_content(noticeContent);
			notice.setNotice_id(UniqueCode.getUUID());
			notice.setHolder_id(holder.getHolder_id());
			BlogNotice2DBService service = new BlogNotice2DBService();
			if(service.insertNotice(notice) >= 1){
				msg.setMessageCode("200");
				msg.setMessageDetail("公告发布成功");
			}else{
				msg.setMessageCode("-300");
				msg.setMessageDetail("公告发布失败");
			}
		}
		response.getWriter().write(mapper.writeValueAsString(msg));
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
