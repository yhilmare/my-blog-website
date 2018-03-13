package Listener;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import Service.BlogHolder2DBService;
import Utils.DBUtils;
import domain.blog_holder;


public class ServletContextListener implements javax.servlet.ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
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
	}

}
