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
import Utils.UniqueCode;
import domain.blog_comment_reply;
import domain.blog_page;

public class BlogCommentReply2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_comment_reply";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler<Integer>());
	}
	
	public int getTotalRecordForCommentAndVisibility(String commentID, boolean useVisibility){
		if (useVisibility) {
			String sql = "select count(*) from blog_comment_reply where comment_id=? and comment_reply_visibility=1";
			Object[] params = {commentID};
			return (int)DBUtils.query(sql, params, new IntegerHandler<Integer>());
		}else {
			String sql = "select count(*) from blog_comment_reply where comment_id=?";
			Object[] params = {commentID};
			return (int)DBUtils.query(sql, params, new IntegerHandler<Integer>());
		}
	}

	@Override
	public int insertData(Object obj) {
		blog_comment_reply reply = (blog_comment_reply) obj;
		
		String sql = "insert into blog_comment_reply(comment_reply_id,comment_id,visitor_id,"
				+ "comment_reply_content,comment_reply_ip,comment_reply_visibility) values(?,?,?,?,?,?)";
		if (reply.getComment_reply_id() == null) {
			reply.setComment_reply_id(UniqueCode.getUUID());
		}
		Encoder encoder = Base64.getEncoder();
		try {
			if (reply.getComment_reply_content() != null) {
				String commentContent = encoder.encodeToString(reply.getComment_reply_content().getBytes("UTF-8"));
				reply.setComment_reply_content(commentContent);
			}
			Object[] params = {reply.getComment_reply_id(),reply.getComment_id(),reply.getVisitor_id(),
					reply.getComment_reply_content(),reply.getComment_reply_ip(),reply.getComment_reply_visibility()};
			return DBUtils.update(sql, params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return -1;
	}

	@Override
	public int deleteData(String id) {
		String sql = "delete from blog_comment_reply where comment_reply_id=?";
		Object[] params = {id};
		return DBUtils.update(sql, params);
	}

	@Override
	public int updateData(Object obj) {
		blog_comment_reply reply = (blog_comment_reply)obj;
		String sql = "update blog_comment_reply set comment_reply_date=?,comment_reply_visibility=? where comment_reply_id=?";
		Object[] params = {reply.getComment_reply_date(), reply.getComment_reply_visibility(), reply.getComment_reply_id()};
		return DBUtils.update(sql, params);
	}
	
	public blog_page selectDataForCommentAndVisibility(int currentPage, int pageContain, int pageInFrame, 
			String commentID, boolean useVisibility) {
		int totalRecord = getTotalRecordForCommentAndVisibility(commentID, useVisibility);
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = null;
		if(useVisibility) {
			sql = "select blog_comment_reply.*,blog_visitor.visitor_nickname,blog_visitor.figureurl_qq_1 " 
					+ "from blog_comment_reply,blog_visitor where blog_comment_reply.visitor_id=blog_visitor.visitor_id " 
					+ "and blog_comment_reply.comment_id=? and blog_comment_reply.comment_reply_visibility=1 ORDER BY " 
					+ "comment_reply_date desc limit ?,?";
		}else {
			sql = "select blog_comment_reply.*,blog_visitor.visitor_nickname,blog_visitor.figureurl_qq_1 " 
					+ "from blog_comment_reply,blog_visitor where blog_comment_reply.visitor_id=blog_visitor.visitor_id " 
					+ "and blog_comment_reply.comment_id=? ORDER BY comment_reply_date desc limit ?,?";
		}
		Object[] params = {commentID, start, page.getPageContain()};
		List<blog_comment_reply> list = DBUtils.query(sql, params, new ListHandler<List<blog_comment_reply>>(blog_comment_reply.class));
		Decoder decoder = Base64.getDecoder();
		for(int i = 0; i < list.size(); i ++) {
			blog_comment_reply reply = list.get(i);
			if (reply.getComment_reply_content() != null) {
				try {
					reply.setComment_reply_content(new String(decoder.decode(reply.getComment_reply_content()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (reply.getVisitor_nickname() != null) {
				try {
					reply.setVisitor_nickname(new String(decoder.decode(reply.getVisitor_nickname()), "UTF-8"));
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
	public <T> T selectByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select blog_comment_reply.*,blog_visitor.visitor_nickname,blog_visitor.figureurl_qq_1 " 
				+ "from blog_comment_reply,blog_visitor where blog_comment_reply.visitor_id=blog_visitor.visitor_id " 
				+ "ORDER BY comment_reply_date desc limit ?,?";
		Object[] params = {start, page.getPageContain()};
		List<blog_comment_reply> list = DBUtils.query(sql, params, new ListHandler<List<blog_comment_reply>>(blog_comment_reply.class));
		Decoder decoder = Base64.getDecoder();
		for(int i = 0; i < list.size(); i ++) {
			blog_comment_reply reply = list.get(i);
			if (reply.getComment_reply_content() != null) {
				try {
					reply.setComment_reply_content(new String(decoder.decode(reply.getComment_reply_content()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (reply.getVisitor_nickname() != null) {
				try {
					reply.setVisitor_nickname(new String(decoder.decode(reply.getVisitor_nickname()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		page.setList(list);
		return page;
	}

}
