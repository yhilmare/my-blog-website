package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogLogin2DBService;
import Service.BlogVisitor2DBService;
import Utils.MD5Utils;
import domain.blog_login;
import domain.blog_visitor;
import domain.IPResolve.IPResolveData;


public class VisitorLoginController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		blog_visitor visitor = service.selectVisitor(username);
		if(visitor == null){
			response.getWriter().write("用户不存在");
			return;
		}
		if (visitor.getVisitor_pwd() == null || visitor.getVisitor_pwd().length() == 0) {
			response.getWriter().write("登录用户类型错误");
			return;
		}
		if(!visitor.getVisitor_pwd().equals(MD5Utils.getToken(password))){
			response.getWriter().write("密码错误");
			return;
		}
		request.getSession().setAttribute("visitor", visitor);
		
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
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(visitor));
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
