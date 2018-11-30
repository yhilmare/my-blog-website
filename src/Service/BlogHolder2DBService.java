package Service;

import Factory.UserAccessFactory;
import ServiceImpl.UserAccess2DB;
import domain.blog_holder;

public class BlogHolder2DBService {
	
	private UserAccess2DB<blog_holder> impl = null;
	public BlogHolder2DBService(){
		impl = UserAccessFactory.getFactory("DAO.BlogHolder2DB").getImplInstance();
	}
	//����һ���û�
	public int insertHolder(blog_holder holder){
		return impl.insertUser(holder);
	}
		//ɾ��һ���û�
	public int deleteHolder(blog_holder holder){
		return impl.deleteUser(holder);
	}
		//����һ���û�����Ϣ
	public int updateHolder(blog_holder holder){
		return impl.updateUser(holder);
	}
		//��ѯһ���û����˴���Ҫ�����û�������
	public blog_holder selectHolder(String name){
		return (blog_holder)impl.selectUser(name);
	}
		//�鿴�û��Ƿ����
	public boolean isExist(String name){
		return impl.isExist(name);
	}
	
	public blog_holder selectHolder(){
		return impl.selectUserByIndex();
	}

}
