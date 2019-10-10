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
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		String msg;
		if(service.insertVisitor(visitor) > 0){
			Decoder decoder = Base64.getDecoder();
			if (visitor.getVisitor_nickname() != null && visitor.getVisitor_nickname().length() != 0) {
				String nickName = new String(decoder.decode(visitor.getVisitor_nickname()), "UTF-8");
				visitor.setVisitor_nickname(nickName);
			}
			session.setAttribute("visitor", visitor);
			
			Thread loginWriteThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String IPAddress = request.getRemoteAddr();
						boolean flag = Pattern.matches("^([0-9]{1,3}\\.){3,3}[0-9]{1,3}$", IPAddress);
						if (!flag) {
							return;
						}
						URL url = new URL("https://m.so.com/position?ip=" + IPAddress);
						HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
						con.setConnectTimeout(10000);
						con.setReadTimeout(10000);
						con.setDoInput(true);
						con.setDoOutput(true);
						con.setRequestMethod("GET");
						InputStream input = con.getInputStream();
						byte[] buffer = input.readAllBytes();
						input.close();
						String returnStr = new String(buffer, "UTF-8");
						ObjectMapper mapper = new ObjectMapper();
						IPResolveData data = mapper.readValue(returnStr, IPResolveData.class);
						blog_login login = new blog_login();
						login.setVisitor_id(visitor.getVisitor_id());
						login.setLogin_nickname(visitor.getVisitor_nickname());
						login.setLogin_ip(data.getData().getIp());
						login.setLogin_province(data.getData().getPosition().getProvince());
						login.setLogin_isp(data.getData().getPosition().getIsp());
						login.setLogin_area(data.getData().getPosition().getArea());
						login.setLogin_address(data.getData().getPosition().getAddress());
						login.setLogin_city(data.getData().getPosition().getCity());
						login.setLogin_country(data.getData().getPosition().getCountry());
						login.setLogin_street(data.getData().getPosition().getStreet());
						BlogLogin2DBService service = new BlogLogin2DBService();
						service.insertVisit(login);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			loginWriteThread.start();
			
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
