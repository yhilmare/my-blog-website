package DAO;

import java.util.List;

import ServiceImpl.DataObject2DB;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import domain.blog_notice;
import domain.blog_page;

public class BlogNotice2DB implements DataObject2DB {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_notice";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}

	@Override
	public int insertData(Object obj) {
		blog_notice notice = (blog_notice)obj;
		String sql = "insert into blog_notice(notice_id,notice_content,holder_id) values(?,?,?)";
		Object[] params = {notice.getNotice_id(), notice.getNotice_content(), notice.getHolder_id()};
		return DBUtils.update(sql, params);
	}

	@Override
	public int deleteData(String id) {
		
		return -1;
	}

	@Override
	public int updateData(Object obj) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = this.getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select * from blog_notice order by publish_date desc limit ?,?";
		Object[] params = {start, page.getPageContain()};
		page.setList((List) DBUtils.query(sql, params, new ListHandler(blog_notice.class)));
		return page;
	}

	@Override
	public blog_page selectIndexData(int currentPage, int pageContain, int pageInFrame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object selectByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
