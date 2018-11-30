package Service;

import Factory.UserAccessFactory;
import ServiceImpl.UserAccess2DB;
import domain.blog_holder;

public class BlogHolder2DBService {
	
	private UserAccess2DB<blog_holder> impl = null;
	public BlogHolder2DBService(){
		impl = UserAccessFactory.getFactory("DAO.BlogHolder2DB").getImplInstance();
	}
	//插入一个用户
	public int insertHolder(blog_holder holder){
		return impl.insertUser(holder);
	}
		//删除一个用户
	public int deleteHolder(blog_holder holder){
		return impl.deleteUser(holder);
	}
		//更改一个用户的信息
	public int updateHolder(blog_holder holder){
		return impl.updateUser(holder);
	}
		//查询一个用户，此处需要传入用户的名字
	public blog_holder selectHolder(String name){
		return (blog_holder)impl.selectUser(name);
	}
		//查看用户是否存在
	public boolean isExist(String name){
		return impl.isExist(name);
	}
	
	public blog_holder selectHolder(){
		return impl.selectUserByIndex();
	}

}
