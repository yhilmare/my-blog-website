package ServiceImpl;

import domain.blog_page;

public interface DataObject2DB<T> {
	
	//得到该数据的全部数目
	public int getTotalRecord();
	
	//增加一个条目
	public int insertData(Object obj);
	
	//删除一个条目
	public int deleteData(String id);
	
	//修改一个条目
	public int updateData(Object obj);
	
	//查询一个条目
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame);
	
	//查询文章索引一个条目
	public blog_page selectIndexData(int currentPage, int pageContain, int pageInFrame);
	
	//查询文id章索引一个条目
	public <T> T selectByID(String id);

}
