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
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
		//����һ����Ŀ
	public int insertVisit(blog_visit visit){
		return impl.insertData(visit);
	}
		
		//��ѯһ����Ŀ
	public blog_page selectVisit(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
}
