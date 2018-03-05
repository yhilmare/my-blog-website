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
import Utils.UniqueCode;
import domain.blog_article;
import domain.blog_holder;

public class InsertArticleController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("����û�е�¼�����½��ʱ");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("����û�е�¼�����½��ʱ");
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
		article.setArticle_id(UniqueCode.getUUID());
		article.setHolder_id(holder.getHolder_id());
		BlogArticle2DBService service = new BlogArticle2DBService();
		if(service.insertArticle(article) >= 1){
			response.getWriter().write("����ɹ�");
		}else{
			response.getWriter().write("����ʧ�ܣ�δ֪����");
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
