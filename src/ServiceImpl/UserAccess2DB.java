package ServiceImpl;

import domain.blog_page;

public interface UserAccess2DB {//设置与用户有关的数据库操作接口
	
	public int getTotalRecord();
	//插入一个用户
	public int insertUser(Object obj);
	//删除一个用户
	public int deleteUser(Object obj);
	//更改一个用户的信息
	public int updateUser(Object obj);
	//查询一个用户，此处需要传入用户的名字
	public Object selectUser(String name);
	//查看用户是否存在
	public boolean isExist(String name);
	
	public Object selectUserByIndex();
	
	public blog_page selectUserByList(int currentPage, int pageContain, int pageInFrame);
}
