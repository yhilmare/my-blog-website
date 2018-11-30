package DAO;

import java.util.List;

import ServiceImpl.DataObject2DB;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import domain.blog_page;
import domain.blog_visit;
import domain.blog_visit_article;

public class BlogVisitArticle2DB<T> implements DataObject2DB<T>{

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_visit_article";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}

	@Override
	public int insertData(Object obj) {
		blog_visit_article visit = (blog_visit_article) obj;
		String sql = "insert into blog_visit_article(visit_id,article_id,visitor_id) values(?,?,?)";
		Object[] params = {visit.getVisit_id(), visit.getArticle_id(), visit.getVisitor_id()};
		return DBUtils.update(sql, params);
	}

	@Override
	public int deleteData(String id) {
		String sql = "delete from blog_visit_article where visit_id=?";
		Object[] params = {id};
		return DBUtils.update(sql, params);
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
		String sql = "select * from blog_article_visit_view limit ?,?";
		Object[] params = {start, page.getPageContain()};
		page.setList(DBUtils.query(sql, params, new ListHandler<List<T>>(blog_visit_article.class)));
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
