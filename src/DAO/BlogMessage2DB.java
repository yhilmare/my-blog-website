package DAO;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import ServiceImpl.DataObject2DB;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import domain.blog_message;
import domain.blog_page;
import domain.blog_visit;

public class BlogMessage2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_message";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}

	@Override
	public int insertData(Object obj) {
		blog_message message = (blog_message)obj;
		String sql = "insert blog_message(message_id,message_content,message_longitude,message_latitude,visitor_id) values(?,?,?,?,?)";
		Encoder encoder = Base64.getEncoder();
		try {
			if (message.getVisitor_id() != null) {
				String visitorID = encoder.encodeToString(message.getVisitor_id().getBytes("UTF-8"));
				message.setVisitor_id(visitorID);
			}
			Object[] params = {message.getMessage_id(), message.getMessage_content(), message.getMessage_longitude(), 
					message.getMessage_latitude(), message.getVisitor_id()};
			return DBUtils.update(sql, params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteData(String id) {
		String sql = "delete from blog_message where message_id=?";
		Object[] params = {id};
		return DBUtils.update(sql, params);
	}

	@Override
	public int updateData(Object obj) {
		blog_message message = (blog_message)obj;
		String sql = "update blog_message set message_content=? where message_id=?";
		Object[] params = {message.getMessage_content(), message.getMessage_id()};
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
		String sql = "select * from blog_message_view limit ?,?";
		Object[] params = {start, page.getPageContain()};
		List<blog_message> list = DBUtils.query(sql, params, new ListHandler<List<blog_message>>(blog_message.class));
		Decoder decoder = Base64.getDecoder();
		for(int i = 0; i < list.size(); i ++) {
			blog_message visit = list.get(i);
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
