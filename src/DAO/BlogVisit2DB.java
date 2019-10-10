package DAO;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;

import ServiceImpl.DataObject2DB;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import domain.blog_article;
import domain.blog_page;
import domain.blog_visit;

public class BlogVisit2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_visit";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}

	@Override
	public int insertData(Object obj) {
		blog_visit visit = (blog_visit)obj;
		String sql = "insert into blog_visit(visit_longitude,visit_latitude,visitor_id) values(?,?,?)";
		Encoder encoder = Base64.getEncoder();
		try {
			if (visit.getVisitor_id() != null) {
				String visitorID = encoder.encodeToString(visit.getVisitor_id().getBytes("UTF-8"));
				visit.setVisitor_id(visitorID);
			}
			Object[] params = {visit.getVisit_longitude(), visit.getVisit_latitude(), visit.getVisitor_id()};
			return DBUtils.update(sql, params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
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
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select * from blog_visit ORDER BY visit_date desc limit ?,?";
		Object[] params = {start, page.getPageContain()};
		List<blog_visit> list = DBUtils.query(sql, params, new ListHandler<List<blog_visit>>(blog_visit.class));
		Decoder decoder = Base64.getDecoder();
		for(int i = 0; i < list.size(); i ++) {
			blog_visit visit = list.get(i);
			if (visit.getVisitor_id() != null) {
				try {
					visit.setVisitor_id(new String(decoder.decode(visit.getVisitor_id()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		page.setList(list);
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
