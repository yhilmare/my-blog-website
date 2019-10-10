package Service;

import DAO.BlogLogin2DB;
import ServiceImpl.DataObject2DB;
import domain.blog_login;
import domain.blog_page;

public class BlogLogin2DBService {
	
	private DataObject2DB impl = null;
	
	public BlogLogin2DBService() {
		impl = new BlogLogin2DB();
	}
	
	//得到该数据的全部数目
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}		
	//增加一个条目
	public int insertVisit(blog_login login){
		return impl.insertData(login);
	}		
	//查询一个条目
	public blog_page selectVisit(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
	
	public int deleteByID(String ID) {
		return impl.deleteData(ID);
	}
}
