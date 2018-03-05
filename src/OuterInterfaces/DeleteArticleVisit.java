package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogVisitArticle2DBService;
import domain.TipMessage;
import net.sf.json.JSONObject;


public class DeleteArticleVisit extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String visitID = request.getParameter("visitID");
		String loginID = request.getParameter("loginID");
		TipMessage msg = new TipMessage();
		if (visitID == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("参数传递错误");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("没有登录或登录已过期");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
			return;
		}
		if (!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("没有登录或登录已过期");
		}else{
			BlogVisitArticle2DBService service = new BlogVisitArticle2DBService();
			if (service.deleteVisitArticle(visitID) < 1){
				msg.setMessageCode("-300");
				msg.setMessageDetail("记录删除失败");
			}else{
				msg.setMessageCode("200");
				msg.setMessageDetail("记录删除成功");
			}
		}
		JSONObject obj = JSONObject.fromObject(msg);
		response.getWriter().write(obj.toString());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
