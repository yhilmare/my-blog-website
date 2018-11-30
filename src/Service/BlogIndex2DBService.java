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
	
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
	//��ѯһ����Ŀ
	public blog_page selectIndex(int currentPage, int pageContain, int pageInFrame){
		blog_page page = impl.selectData(currentPage, pageContain, pageInFrame);
		return page;
	}
}
