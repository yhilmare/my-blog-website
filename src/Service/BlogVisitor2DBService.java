package Service;

import Factory.UserAccessFactory;
import ServiceImpl.UserAccess2DB;
import domain.blog_page;
import domain.blog_visitor;

public class BlogVisitor2DBService {
	
	private UserAccess2DB impl = null;
	public BlogVisitor2DBService(){
		this.impl = UserAccessFactory.getFactory("DAO.BlogVisitor2DB").getImplInstance();
	}
	//插入一个用户
	public int insertVisitor(blog_visitor visitor){
		return impl.insertUser(visitor);
	}
		//删除一个用户
	public int deleteVisitor(blog_visitor visitor){
		return impl.deleteUser(visitor);
	}
		//更改一个用户的信息
	public int updateVisitor(blog_visitor visitor){
		return impl.updateUser(visitor);
	}
		//查询一个用户，此处需要传入用户的名字
	public blog_visitor selectVisitor(String name){
		return (blog_visitor)impl.selectUser(name);
	}
		//查看用户是否存在
	public boolean isExist(String name){
		return impl.isExist(name);
	}
	
	public blog_page selectUserByList(int currentPage, int pageContain, int pageInFrame){
		return impl.selectUserByList(currentPage, pageContain, pageInFrame);
	}
}
