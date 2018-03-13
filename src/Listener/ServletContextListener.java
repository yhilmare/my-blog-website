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
			System.out.println("���ݿ�����Դ�ͷųɹ���");
		} catch (SQLException e) {
			System.out.println("���ݿ�����Դ�ͷ�ʧ�ܣ����ܵ����ڴ�й¶");
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
