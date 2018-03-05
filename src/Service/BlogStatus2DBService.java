package Service;

import Factory.DataAccessFactory;
import ServiceImpl.DataObject2DB;
import domain.blog_page;
import domain.blog_status;

public class BlogStatus2DBService {
	private DataObject2DB impl = null;
	public BlogStatus2DBService(){
		impl = DataAccessFactory.getFactory("DAO.BlogStatus2DB").getImpleInstance();
	}
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
	
	//增加一个条目
	public int insertStatus(blog_status status){
		return impl.insertData(status);
	}
	
	//删除一个条目
	public int deleteStatus(String id){
		return impl.deleteData(id);
	}
	
	//修改一个条目
	public int updateStatus(blog_status status){
		return impl.updateData(status);
	}
	
	//查询一个条目
	public blog_page selectStatus(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}

}
