package Service;

import Factory.DataAccessFactory;
import ServiceImpl.DataObject2DB;
import domain.blog_page;
import domain.blog_visit;

public class BlogVisit2DBService {
	private DataObject2DB<blog_visit> impl = null;
	public BlogVisit2DBService(){
		impl = DataAccessFactory.getFactory("DAO.BlogVisit2DB").getImpleInstance();
	}
	//得到该数据的全部数目
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
		//增加一个条目
	public int insertVisit(blog_visit visit){
		return impl.insertData(visit);
	}
		
		//查询一个条目
	public blog_page selectVisit(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
}
