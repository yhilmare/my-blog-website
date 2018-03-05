package Listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import Service.BlogHolder2DBService;
import domain.blog_holder;


public class ServletContextListener implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		BlogHolder2DBService service = new BlogHolder2DBService();
		blog_holder holder = service.selectHolder();
		arg0.getServletContext().setAttribute("holder", holder);
	}

}
