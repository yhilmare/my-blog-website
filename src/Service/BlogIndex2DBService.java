package Service;

import java.util.List;

import Factory.DataAccessFactory;
import ServiceImpl.DataObject2DB;
import domain.blog_index;
import domain.blog_page;

public class BlogIndex2DBService {
	public DataObject2DB<blog_index> impl = null;
	public BlogIndex2DBService(){
		impl = DataAccessFactory.getFactory("DAO.BlogIndex2DB").getImpleInstance();
	}
	
	//得到该数据的全部数目
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
	//查询一个条目
	public blog_page selectIndex(int currentPage, int pageContain, int pageInFrame){
		blog_page page = impl.selectData(currentPage, pageContain, pageInFrame);
		return page;
	}
}
