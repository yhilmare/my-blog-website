package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogArticle2DBService;
import domain.blog_holder;
import domain.blog_page;
import net.sf.json.JSONObject;


public class SelectArticleBriefInfoController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex == null){
			response.getWriter().write("�봫����ȷ��ҳ��");
			return;
		}
		BlogArticle2DBService service = new BlogArticle2DBService();
		blog_page page = service.selectArticleIndex(Integer.parseInt(pageIndex), 9, 5);
		if(page == null){
			response.getWriter().write("��ѯʧ��");
		}else{
			JSONObject obj = JSONObject.fromObject(page);
			response.getWriter().write(obj.toString());
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
