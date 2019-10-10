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
	
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}		
	//����һ����Ŀ
	public int insertVisit(blog_login login){
		return impl.insertData(login);
	}		
	//��ѯһ����Ŀ
	public blog_page selectVisit(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
	
	public int deleteByID(String ID) {
		return impl.deleteData(ID);
	}
}
