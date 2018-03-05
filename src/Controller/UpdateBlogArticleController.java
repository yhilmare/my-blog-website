package Controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogArticle2DBService;
import domain.blog_article;
import domain.blog_holder;


public class UpdateBlogArticleController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("您还没有登录，或登陆超时");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("您还没有登录，或登陆超时");
			return;
		}
		Enumeration<String> enums = request.getParameterNames();
		blog_article article = new blog_article();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			try {
				PropertyDescriptor pd = new PropertyDescriptor(name, blog_article.class);
				pd.getWriteMethod().invoke(article, request.getParameter(name));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		article.setHolder_id(holder.getHolder_id());
		BlogArticle2DBService service = new BlogArticle2DBService();
		if(service.updateArticle(article) >= 1){
			response.getWriter().write("修改成功");
		}else{
			response.getWriter().write("修改失败，未知错误");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
