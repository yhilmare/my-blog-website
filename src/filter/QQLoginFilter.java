package filter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.Base64.Decoder;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.BlogVisitor2DB;
import Service.BlogLogin2DBService;
import Service.BlogVisitor2DBService;
import Utils.Login2DBThread;
import domain.OpenID;
import domain.QQApp;
import domain.QQUserReturnMsg;
import domain.blog_login;
import domain.blog_visitor;
import domain.IPResolve.IPResolveData;


public class QQLoginFilter implements Filter {
    
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String AuthorizationCode = request.getParameter("code");
		if (AuthorizationCode == null) {
			chain.doFilter(request, response);
			return;
		}
		QQApp app = (QQApp) request.getServletContext().getAttribute("APPINFO");
		if (app == null) {
			response.getWriter().write("获取网站ID失败，登录未完成");
			return;
		}
		String uriStr = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + 
		app.getAPPID() + "&client_secret=" + app.getAPPKey() + "&code=" + AuthorizationCode + 
		"&redirect_uri=" + URLEncoder.encode(app.getRedirectUri(), "GBK");
		
		String returnMat = sendHttpsGet(uriStr, null);
		String[] strSplit = returnMat.split("&");
		String accessToken = null;
		for(int i = 0; i < strSplit.length; i ++) {
			String item = strSplit[i];
			String[] keyValue = item.split("=");
			String key = keyValue[0];
			if (key.equals("access_token")) {
				accessToken = keyValue[1];
				break;
			}
		}
		if (accessToken == null) {
			response.getWriter().write("获取access_token失败，登录未完成");
			return;
		}
		
		uriStr = "https://graph.qq.com/oauth2.0/me?" + "access_token=" + accessToken;
		returnMat = sendHttpsGet(uriStr, null);
		if (returnMat.length() <= 15) {
			response.getWriter().write("获取openid失败，登录未完成");
			return;
		}
		returnMat = returnMat.substring(9, returnMat.length() - 2);
		ObjectMapper mapper = new ObjectMapper();
		OpenID obj = mapper.readValue(returnMat, OpenID.class);
		if (obj == null) {
			response.getWriter().write("获取openid失败，登录未完成");
			return;
		}
		
		uriStr = "https://graph.qq.com/user/get_user_info?access_token=" + 
		accessToken + "&oauth_consumer_key=" + app.getAPPID() + "&openid=" + obj.getOpenid();
		returnMat = sendHttpsGet(uriStr, null);
		QQUserReturnMsg user = mapper.readValue(returnMat, QQUserReturnMsg.class);

		//将腾讯服务器返回的用户资料对象转换成数据库能用的对象
		blog_visitor visitor = parseObj(user);
		visitor.setVisitor_id(obj.getOpenid());
		
		BlogVisitor2DBService service = new BlogVisitor2DBService();
		blog_visitor visitorFromDB = service.selectVisitorByID(visitor.getVisitor_id());
		if (visitorFromDB == null) {
			if (service.insertVisitor(visitor) <= 0) {
				response.getWriter().write("数据库写入失败，登录失败");
				return;
			}
		}else {
			if (!isEqual(visitor, visitorFromDB)) {
				service.updateVisitor(visitor);
			}
		}
		HttpSession session = request.getSession(true);
		Decoder decoder = Base64.getDecoder();
		if (visitor.getVisitor_nickname() != null && visitor.getVisitor_nickname().length() != 0) {
			String nickName = new String(decoder.decode(visitor.getVisitor_nickname()), "UTF-8");
			visitor.setVisitor_nickname(nickName);
		}
		if (visitor.getConstellation() != null && visitor.getConstellation().length() != 0) {
			String constellation = new String(decoder.decode(visitor.getConstellation()), "UTF-8");
			visitor.setConstellation(constellation);
		}
		session.setAttribute("visitor", visitor);
		Login2DBThread thread = new Login2DBThread(request.getRemoteAddr(), visitor.getVisitor_id(), visitor.getVisitor_nickname());
		
//		Thread loginWriteThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				
//				try {
//					String IPAddress = request.getRemoteAddr();
//					boolean flag = Pattern.matches("^([0-9]{1,3}\\.){3,3}[0-9]{1,3}$", IPAddress);
//					if (!flag) {
//						return;
//					}
//					URL url = new URL("https://m.so.com/position?ip=" + IPAddress);
//					HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//					con.setConnectTimeout(10000);
//					con.setReadTimeout(10000);
//					con.setDoInput(true);
//					con.setDoOutput(true);
//					con.setRequestMethod("GET");
//					InputStream input = con.getInputStream();
//					byte[] buffer = new byte[input.available()];
//					int len = input.read(buffer);
//					while(len != -1) {
//						len = input.read(buffer, 0, len);
//					}
//					input.close();
//					String returnStr = new String(buffer, "UTF-8");
//					ObjectMapper mapper = new ObjectMapper();
//					IPResolveData data = mapper.readValue(returnStr, IPResolveData.class);
//					blog_login login = new blog_login();
//					login.setVisitor_id(visitor.getVisitor_id());
//					login.setLogin_nickname(visitor.getVisitor_nickname());
//					login.setLogin_ip(data.getData().getIp());
//					login.setLogin_province(data.getData().getPosition().getProvince());
//					login.setLogin_isp(data.getData().getPosition().getIsp());
//					login.setLogin_area(data.getData().getPosition().getArea());
//					login.setLogin_address(data.getData().getPosition().getAddress());
//					login.setLogin_city(data.getData().getPosition().getCity());
//					login.setLogin_country(data.getData().getPosition().getCountry());
//					login.setLogin_street(data.getData().getPosition().getStreet());
//					BlogLogin2DBService service = new BlogLogin2DBService();
//					service.insertVisit(login);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		loginWriteThread.start();
		
		chain.doFilter(request, response);
		thread.customerJoin();
	}
	
	private boolean isEqual(blog_visitor v1, blog_visitor v2) {
		try {
			BeanInfo info = Introspector.getBeanInfo(blog_visitor.class, Object.class);
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				Method method = pd.getReadMethod();
				Object value1 = method.invoke(v1, null);
				Object value2 = method.invoke(v2, null);
				if (value1 != null && value2 != null) {
					if (!value1.equals(value2)) {
						return false;
					}
				}else {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private blog_visitor parseObj(QQUserReturnMsg obj) {
		if (obj == null) {
			return null;
		}
		blog_visitor visitor = new blog_visitor();
		visitor.setIs_lost(Integer.toString(obj.getIs_lost()));
		visitor.setVisitor_nickname(obj.getNickname());
		visitor.setVisitor_gender(obj.getGender());
		visitor.setProvince(obj.getProvince());
		visitor.setCity(obj.getCity());
		visitor.setYear(obj.getYear());
		visitor.setConstellation(obj.getConstellation());
		visitor.setFigureurl(obj.getFigureurl());
		visitor.setFigureurl_1(obj.getFigureurl_1());
		visitor.setFigureurl_2(obj.getFigureurl_2());
		visitor.setFigureurl_qq(obj.getFigureurl_qq());
		visitor.setFigureurl_qq_1(obj.getFigureurl_qq_1());
		visitor.setFigureurl_qq_2(obj.getFigureurl_qq_2());
		visitor.setFigureurl_type(obj.getFigureurl_type());
		visitor.setIs_yellow_vip(obj.getIs_yellow_vip());
		visitor.setVip(obj.getVip());
		visitor.setYellow_vip_level(obj.getYellow_vip_level());
		visitor.setLevel(obj.getLevel());
		visitor.setIs_yellow_year_vip(obj.getIs_yellow_year_vip());
		return visitor;
	}

	private String sendHttpsGet(String uri, Map<String, String> parameters) throws IOException {
		if (parameters != null) {
			Set<Map.Entry<String, String>> set = parameters.entrySet();
			Iterator<Map.Entry<String, String>> iter = set.iterator();
			StringBuffer buffer = new StringBuffer();
			while(iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				buffer.append("&" + entry.getKey() + "=" + entry.getValue());
			}
			if (buffer.length() != 0) {
				String param = buffer.substring(1);
				uri = uri + "?" + param;
			}
		}
		URL url = new URL(uri);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setConnectTimeout(10000);
		con.setReadTimeout(10000);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		InputStream input = con.getInputStream();
		byte[] buffer = new byte[input.available()];
		int len = input.read(buffer);
		while(len != -1) {
			len = input.read(buffer, 0, len);
		}
		input.close();
		return new String(buffer, "UTF-8");
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
