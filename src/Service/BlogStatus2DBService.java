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
	
	//����һ����Ŀ
	public int insertStatus(blog_status status){
		return impl.insertData(status);
	}
	
	//ɾ��һ����Ŀ
	public int deleteStatus(String id){
		return impl.deleteData(id);
	}
	
	//�޸�һ����Ŀ
	public int updateStatus(blog_status status){
		return impl.updateData(status);
	}
	
	//��ѯһ����Ŀ
	public blog_page selectStatus(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}

}
