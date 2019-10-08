package filter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.OpenID;
import domain.QQApp;
import domain.QQUserReturnMsg;
import domain.blog_visitor1;


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
		try {
			BeanInfo info = Introspector.getBeanInfo(QQUserReturnMsg.class, Object.class);
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			for(PropertyDescriptor pd : pds) {
				Method method = pd.getReadMethod();
				System.out.println(pd.getName() + ": " + method.invoke(user, null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将腾讯服务器返回的用户资料对象转换成数据库能用的对象
		
		blog_visitor1 visitor = parseObj(user);
		visitor.setVisitor_id(obj.getOpenid());
		
		//将解析好的对象传入到数据库中作持久化存储
		chain.doFilter(request, response);
	}
	
	private blog_visitor1 parseObj(QQUserReturnMsg obj) {
		if (obj == null) {
			return null;
		}
		blog_visitor1 visitor = new blog_visitor1();
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
		byte[] buffer = input.readAllBytes();
		input.close();
		return new String(buffer, "UTF-8");
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
