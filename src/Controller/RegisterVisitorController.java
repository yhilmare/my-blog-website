package Controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogVisitor2DBService;
import domain.TipMessage;
import domain.blog_visitor;
import net.sf.json.JSONObject;

//访客注册接口
public class RegisterVisitorController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		if(token == null){
			return;
		}
		HttpSession session = request.getSession();
		if(session == null){
			return;
		}
		String sessionValue = (String) session.getAttribute("token");
		if(!sessionValue.equalsIgnoreCase(token)){
			response.getWriter().write("{'code':'error','detail':'验证码错误'}");
			return;
		}
		Enumeration<String> enums = request.getParameterNames();
		blog_visitor visitor = new blog_visitor();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			if(name.equals("token")){
				continue;
			}
			try {
				PropertyDescriptor pd = new PropertyDescriptor(name, blog_visitor.class);
				pd.getWriteMethod().invoke(visitor, request.getParameter(name));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		String msg;
		if(service.insertVisitor(visitor) > 0){
			session.setAttribute("visitor", visitor);
			msg = "注册成功，3秒钟后返回首页。若返回失败请点击这里<a href='/blog'>返回首页</a>";
		}else{
			msg = "注册失败，3秒钟后返回首页。若返回失败请点击这里<a href='/blog'>返回首页</a>";
		}
		response.getWriter().write(msg);
		response.setHeader("refresh", "3;url=/blog");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
