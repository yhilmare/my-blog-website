package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.BlogArticle2DBService;
import Service.BlogVisitArticle2DBService;
import Utils.UniqueCode;
import domain.blog_article;
import domain.blog_visit_article;


public class GenArticleController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String article_id = request.getParameter("article_id");
		String visitor_id = request.getParameter("visitor_id");
		if(article_id == null){
			response.getWriter().write("请传递正确的文章id");
			return;
		}
		if(visitor_id == null || visitor_id.equals("notlogin") || visitor_id.trim().length() == 0){
			visitor_id = request.getRemoteAddr();
		}
		BlogArticle2DBService service = new BlogArticle2DBService();
		blog_article article = service.selectArticleByID(article_id);
		BlogVisitArticle2DBService ser = new BlogVisitArticle2DBService();
		blog_visit_article va = new blog_visit_article();
		va.setArticle_id(article_id);
		va.setVisit_id(UniqueCode.getUUID());
		va.setVisitor_id(visitor_id);
		ser.insertVisitArticle(va);
		if(article == null){
			response.getWriter().write("查询失败");
		}else{
			request.setAttribute("article", article);
			request.getRequestDispatcher("/wapIndex/articleDetail.jsp").forward(request, response);
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
