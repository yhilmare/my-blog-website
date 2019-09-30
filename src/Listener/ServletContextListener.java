package Listener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import domain.QQApp;
import Service.BlogHolder2DBService;
import Utils.DBUtils;
import domain.blog_holder;


public class ServletContextListener implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		arg0.getServletContext().removeAttribute("APPINFO");
		ComboPooledDataSource datasource = DBUtils.getDataSource();
		try {
			DataSources.destroy(datasource);
			System.out.println("数据库数据源释放成功！");
		} catch (SQLException e) {
			System.out.println("数据库数据源释放失败，可能导致内存泄露");
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		BlogHolder2DBService service = new BlogHolder2DBService();
		blog_holder holder = service.selectHolder();
		arg0.getServletContext().setAttribute("holder", holder);
		
		Properties property = new Properties();
    	try {
			property.load(this.getClass().getClassLoader().getResourceAsStream("appidFile.properties"));
			QQApp app = new QQApp();
			app.setAPPID(property.getProperty("APPID"));
			app.setAPPKey(property.getProperty("APPKEY"));
			app.setRedirectUri(property.getProperty("REDIRECTURI"));
			arg0.getServletContext().setAttribute("APPINFO", app);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
