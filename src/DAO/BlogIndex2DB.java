package DAO;

import java.util.List;

import ServiceImpl.DataObject2DB;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import domain.blog_article;
import domain.blog_index;
import domain.blog_page;

public class BlogIndex2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_index";
		Object[] params = {};
 		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}

	@Override
	public int insertData(Object obj) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int deleteData(String id) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int updateData(Object obj) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select * from blog_index_view limit ?,?";
		Object[] params = {start, page.getPageContain()};
		page.setList(DBUtils.query(sql, params, new ListHandler<List<T>>(blog_index.class)));
		return page;
	}

	@Override
	public blog_page selectIndexData(int currentPage, int pageContain, int pageInFrame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T selectByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
