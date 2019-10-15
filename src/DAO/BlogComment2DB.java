package DAO;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import ServiceImpl.DataObject2DB;
import Utils.BeanHandler;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import Utils.UniqueCode;
import domain.blog_comment;
import domain.blog_page;

public class BlogComment2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_comment";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler<Integer>());
	}
	
	public int getTotalRecordForArticle(String articleID){
		String sql = "select count(*) from blog_comment where article_id=? and comment_visibility=1";
		Object[] params = {articleID};
		return (int)DBUtils.query(sql, params, new IntegerHandler<Integer>());
	}
	
	@Override
	public int insertData(Object obj) {
		blog_comment comment = (blog_comment) obj;
		
		String sql = "insert into blog_comment(comment_id,article_id,visitor_id,comment_content,comment_ip,comment_visibility) values(?,?,?,?,?,?)";
		if (comment.getComment_id() == null) {
			comment.setComment_id(UniqueCode.getUUID());
		}
		Encoder encoder = Base64.getEncoder();
		try {
			if (comment.getComment_content() != null) {
				String commentContent = encoder.encodeToString(comment.getComment_content().getBytes("UTF-8"));
				comment.setComment_content(commentContent);
			}
			Object[] params = {comment.getComment_id(),comment.getArticle_id(),comment.getVisitor_id(),
					comment.getComment_content(),comment.getComment_ip(),comment.getComment_visibility()};
			return DBUtils.update(sql, params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return -1;
	}

	@Override
	public int deleteData(String id) {
		String sql = "delete from blog_comment where comment_id=?";
		Object[] params = {id};
		return DBUtils.update(sql, params);
	}

	@Override
	public int updateData(Object obj) {
		blog_comment comment = (blog_comment)obj;
		String sql = "update blog_comment set comment_date=?,comment_visibility=? where comment_id=?";
		Object[] params = {comment.getComment_date(), comment.getComment_visibility(), comment.getComment_id()};
		return DBUtils.update(sql, params);
	}

	
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame, String articleID) {
		int totalRecord = getTotalRecordForArticle(articleID);
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select blog_comment.*,blog_visitor.visitor_nickname,blog_visitor.figureurl_qq,blog_article.article_title " 
				+ "from blog_comment,blog_visitor,blog_article where blog_comment.visitor_id=blog_visitor.visitor_id " 
				+ "and blog_comment.article_id=? and blog_comment.article_id=blog_article.article_id and " 
				+ "blog_comment.comment_visibility=1 ORDER BY comment_date desc limit ?,?";
		Object[] params = {articleID, start, page.getPageContain()};
		List<blog_comment> list = DBUtils.query(sql, params, new ListHandler<List<blog_comment>>(blog_comment.class));
		Decoder decoder = Base64.getDecoder();
		for(int i = 0; i < list.size(); i ++) {
			blog_comment comment = list.get(i);
			if (comment.getComment_content() != null) {
				try {
					comment.setComment_content(new String(decoder.decode(comment.getComment_content()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (comment.getVisitor_nickname() != null) {
				try {
					comment.setVisitor_nickname(new String(decoder.decode(comment.getVisitor_nickname()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		page.setList(list);
		return page;
	}
	
	@Override
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select blog_comment.*,blog_visitor.visitor_nickname,blog_visitor.figureurl_qq," 
				+ "blog_article.article_title from blog_comment,blog_visitor,blog_article where " 
				+ "blog_comment.visitor_id=blog_visitor.visitor_id and " 
				+ "blog_comment.article_id=blog_article.article_id ORDER BY comment_date desc limit ?,?";
		Object[] params = {start, page.getPageContain()};
		List<blog_comment> list = DBUtils.query(sql, params, new ListHandler<List<blog_comment>>(blog_comment.class));
		Decoder decoder = Base64.getDecoder();
		for(int i = 0; i < list.size(); i ++) {
			blog_comment comment = list.get(i);
			if (comment.getComment_content() != null) {
				try {
					comment.setComment_content(new String(decoder.decode(comment.getComment_content()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (comment.getVisitor_nickname() != null) {
				try {
					comment.setVisitor_nickname(new String(decoder.decode(comment.getVisitor_nickname()), "UTF-8"));
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
		String sql = "select * from blog_comment where comment_id=?";
		Decoder decoder = Base64.getDecoder();
		try {
			Object[] params = {id};
			blog_comment obj = DBUtils.query(sql, params, new BeanHandler<blog_comment>(blog_comment.class));
			if (obj == null) {
				return null;
			}
			if (obj.getComment_content()!= null && obj.getComment_content().length() != 0) {
				String constellation = new String(decoder.decode(obj.getComment_content()), "UTF-8");
				obj.setComment_content(constellation);
			}
			return (T) obj;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
