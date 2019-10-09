package Service;

import DAO.BlogVisitor2DB;
import Factory.UserAccessFactory;
import ServiceImpl.UserAccess2DB;
import domain.blog_page;
import domain.blog_visitor;

public class BlogVisitor2DBService {
	
	private BlogVisitor2DB<blog_visitor> impl = new BlogVisitor2DB();
	public BlogVisitor2DBService(){
		super();
	}
	//����һ���û�
	public int insertVisitor(blog_visitor visitor){
		return impl.insertUser(visitor);
	}
		//ɾ��һ���û�
	public int deleteVisitor(blog_visitor visitor){
		return impl.deleteUser(visitor);
	}
		//����һ���û�����Ϣ
	public int updateVisitor(blog_visitor visitor){
		return impl.updateUser(visitor);
	}
		//��ѯһ���û����˴���Ҫ�����û�������
	public blog_visitor selectVisitor(String name){
		return impl.selectUser(name);
	}
	public blog_visitor selectVisitorByID(String ID){
		return impl.selectUserByID(ID);
	}
		//�鿴�û��Ƿ����
	public boolean isExist(String name){
		return impl.isExist(name);
	}
	
	public blog_page selectUserByList(int currentPage, int pageContain, int pageInFrame){
		return impl.selectUserByList(currentPage, pageContain, pageInFrame);
	}
}
