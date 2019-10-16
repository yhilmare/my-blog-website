package Controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.regex.Pattern;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogLogin2DBService;
import Service.BlogVisitor2DBService;
import Utils.Login2DBThread;
import domain.blog_login;
import domain.blog_visitor;
import domain.IPResolve.IPResolveData;

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
		if (visitor.getVisitor_nickname().trim().equalsIgnoreCase("IL MARE") 
				|| visitor.getVisitor_nickname().trim().equalsIgnoreCase("ILMARE")) {
			response.getWriter().write("用户名ILMARE为保留字，注册失败");
			return;
		}
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		String msg;
		Login2DBThread thread = null;
		if(service.insertVisitor(visitor) > 0){
			Decoder decoder = Base64.getDecoder();
			if (visitor.getVisitor_nickname() != null && visitor.getVisitor_nickname().length() != 0) {
				String nickName = new String(decoder.decode(visitor.getVisitor_nickname()), "UTF-8");
				visitor.setVisitor_nickname(nickName);
			}
			session.setAttribute("visitor", visitor);
			
			thread = new Login2DBThread(request.getRemoteAddr(), visitor.getVisitor_id(), visitor.getVisitor_nickname());
			
			msg = "注册成功，3秒钟后返回首页。若返回失败请点击这里<a href='/blog'>返回首页</a>";
		}else{
			msg = "注册失败，3秒钟后返回首页。若返回失败请点击这里<a href='/blog'>返回首页</a>";
		}
		response.getWriter().write(msg);
		response.setHeader("refresh", "3;url=/blog");
		if (thread != null) {
			thread.customerJoin();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
