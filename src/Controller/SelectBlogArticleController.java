package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogArticle2DBService;
import domain.blog_article;
import domain.blog_holder;
import net.sf.json.JSONObject;


public class SelectBlogArticleController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("�Բ�����δ��¼�����½��ʱ");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("�Բ�����δ��¼");
			return;
		}
		String article_id = request.getParameter("article_id");
		if(article_id == null){
			response.getWriter().write("�봫����ȷ������id");
			return;
		}
		BlogArticle2DBService service = new BlogArticle2DBService();
		blog_article article = service.selectArticleByID(article_id);
		if(article == null){
			response.getWriter().write("��ѯʧ��");
		}else{
			JSONObject obj = JSONObject.fromObject(article);
			response.getWriter().write(obj.toString());
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
