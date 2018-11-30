package Service;

import Factory.DataAccessFactory;
import ServiceImpl.DataObject2DB;
import domain.blog_message;
import domain.blog_page;

public class BlogMessage2DBService {
	private DataObject2DB<blog_message> impl = null;
	public BlogMessage2DBService(){
		impl = DataAccessFactory.getFactory("DAO.BlogMessage2DB").getImpleInstance();
	}
	//得到该数据的全部数目
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
		//增加一个条目
	public int insertMessage(blog_message message){
		return impl.insertData(message);
	}
		
		//删除一个条目
	public int deleteMessage(String id){
		return impl.deleteData(id);
	}
		
		//修改一个条目
	public int updateMessage(blog_message message){
		return impl.updateData(message);
	}
		
		//查询一个条目
	public blog_page selectMessage(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}

}
