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
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
		//����һ����Ŀ
	public int insertMessage(blog_message message){
		return impl.insertData(message);
	}
		
		//ɾ��һ����Ŀ
	public int deleteMessage(String id){
		return impl.deleteData(id);
	}
		
		//�޸�һ����Ŀ
	public int updateMessage(blog_message message){
		return impl.updateData(message);
	}
		
		//��ѯһ����Ŀ
	public blog_page selectMessage(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}

}
