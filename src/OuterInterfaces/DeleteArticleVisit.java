package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogVisitArticle2DBService;
import domain.TipMessage;


public class DeleteArticleVisit extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String visitID = request.getParameter("visitID");
		String loginID = request.getParameter("loginID");
		TipMessage msg = new TipMessage();
		ObjectMapper mapper = new ObjectMapper();
		if (visitID == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("�������ݴ���");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("û�е�¼���¼�ѹ���");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		if (!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("û�е�¼���¼�ѹ���");
		}else{
			BlogVisitArticle2DBService service = new BlogVisitArticle2DBService();
			if (service.deleteVisitArticle(visitID) < 1){
				msg.setMessageCode("-300");
				msg.setMessageDetail("��¼ɾ��ʧ��");
			}else{
				msg.setMessageCode("200");
				msg.setMessageDetail("��¼ɾ���ɹ�");
			}
		}
		response.getWriter().write(mapper.writeValueAsString(msg));
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
