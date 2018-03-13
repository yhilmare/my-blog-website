package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogArticle2DBService;
import domain.blog_page;


public class SelectArticleBriefInfoController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageIndex = request.getParameter("pageIndex");
		if(pageIndex == null){
			response.getWriter().write("请传递正确的页码");
			return;
		}
		BlogArticle2DBService service = new BlogArticle2DBService();
		blog_page page = service.selectArticleIndex(Integer.parseInt(pageIndex), 9, 5);
		if(page == null){
			response.getWriter().write("查询失败");
		}else{
			ObjectMapper mapper = new ObjectMapper();
			response.getWriter().write(mapper.writeValueAsString(page));
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
