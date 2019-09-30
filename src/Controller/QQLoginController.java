package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.QQApp;


public class QQLoginController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Properties property = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("appidFile.properties");
		property.load(input);
		QQApp app = (QQApp) this.getServletContext().getAttribute("APPINFO");
		String redirect_uri = URLEncoder.encode(app.getRedirectUri(), "GBK");
		if (app == null) {
			response.getWriter().write("服务器内部错误");
			return;
		}
		String display = request.getParameter("display");
		if (display != null) {
			String path = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" 
					+ app.getAPPID() + "&redirect_uri=" + redirect_uri + "&state=login_status_1&display=mobile";
			response.sendRedirect(path);
		}else {
			String path = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" 
					+ app.getAPPID() + "&redirect_uri=" + redirect_uri + "&state=login_status_1";
		    response.sendRedirect(path);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
