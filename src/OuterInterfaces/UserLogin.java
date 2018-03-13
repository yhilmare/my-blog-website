package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogHolder2DBService;
import Utils.UniqueCode;
import domain.TipMessage;
import domain.blog_holder;

public class UserLogin extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		TipMessage msg = new TipMessage();
		ObjectMapper mapper = new ObjectMapper();
		if (username == null || password == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("用户名或密码缺失");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		BlogHolder2DBService service = new BlogHolder2DBService();
		blog_holder holder = service.selectHolder(username);
		if (holder == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("用户不存在");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		if (holder.getHolder_pwd().equals(password)){
			String loginID = UniqueCode.getUUID();
			this.getServletContext().setAttribute("loginID", loginID);
			msg.setMessageCode("200");
			msg.setMessageDetail(loginID);
			response.getWriter().write(mapper.writeValueAsString(msg));
		}else{
			msg.setMessageCode("-300");
			msg.setMessageDetail("用户存在但密码错误");
			response.getWriter().write(mapper.writeValueAsString(msg));
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
