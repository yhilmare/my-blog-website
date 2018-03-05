package Service;

import Factory.DataAccessFactory;
import ServiceImpl.DataObject2DB;
import domain.blog_notice;
import domain.blog_page;

public class BlogNotice2DBService {
	private DataObject2DB impl = null;
	public BlogNotice2DBService(){
		impl = DataAccessFactory.getFactory("DAO.BlogNotice2DB").getImpleInstance();
	}
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
	
	//����һ����Ŀ
	public int insertNotice(blog_notice notice){
		return impl.insertData(notice);
	}
	
	//��ѯһ����Ŀ
	public blog_page selectNotice(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}

}
