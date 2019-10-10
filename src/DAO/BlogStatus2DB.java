package DAO;

import java.util.List;

import ServiceImpl.DataObject2DB;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import Utils.MetaMsgUtils;
import domain.blog_article;
import domain.blog_index;
import domain.blog_page;
import domain.blog_status;

public class BlogStatus2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_status";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}

	@Override
	public int insertData(Object obj) {
		blog_status status = (blog_status)obj;
		blog_index index = new blog_index();
		index.setIndex_id(status.getStatus_id());
		index.setIndex_glance(status.getStatus_content());
		index.setIndex_type("2");
		String sql1 = "insert into blog_status(status_id,status_content,holder_id) values(?,?,?)";
		String sql2 = "insert into blog_index(index_id,index_type,index_glance,index_title) values(?,?,?,?)";
		Object[] param1 = {status.getStatus_id(), status.getStatus_content(), status.getHolder_id()};
		Object[] param2 = {index.getIndex_id(), index.getIndex_type(), index.getIndex_glance(), index.getIndex_title()};
		String[] sql = {sql1, sql2};
		Object[][] params = {param1, param2};
		return DBUtils.updateTranscation(sql, params);
	}

	@Override
	public int deleteData(String id) {
		String sql1 = "delete from blog_status where status_id=?";
		String sql2 = "delete from blog_index where index_id=?";
		Object[] param1 = {id};
		Object[] param2 = {id};
		String[] sql = {sql1, sql2};
		Object[][] params = {param1, param2};
		return DBUtils.updateTranscation(sql, params);
	}

	@Override
	public int updateData(Object obj) {
		blog_status status = (blog_status)obj;
		String sql = "update blog_status set status_content=? where status_id=?";
		Object[] params = {status.getStatus_content(), status.getStatus_id()};
 		return DBUtils.update(sql, params);
	}

	@Override
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select status_id, status_content, publish_date, holder_id from blog_status order by publish_date desc limit ?,?";
		Object[] params = {start, page.getPageContain()};
		page.setList(DBUtils.query(sql, params, new ListHandler<List<T>>(blog_status.class)));
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