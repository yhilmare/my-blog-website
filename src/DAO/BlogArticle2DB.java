package DAO;

import java.io.StringReader;
import java.util.List;

import ServiceImpl.DataObject2DB;
import Utils.BeanHandler;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import Utils.MetaMsgUtils;
import domain.blog_article;
import domain.blog_index;
import domain.blog_page;

public class BlogArticle2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_article";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler<Integer>());
	}

	@Override
	public int insertData(Object obj) {
		blog_article article = (blog_article) obj;
		blog_index index = new blog_index();
		index.setIndex_id(article.getArticle_id());
		index.setIndex_glance(MetaMsgUtils.getMetaMessage(article.getArticle_content()));
		index.setIndex_title(article.getArticle_title());
		index.setIndex_type("1");
		String sql1 = "insert into blog_article(article_id,article_content,article_title,holder_id) values(?,?,?,?)";
		String sql2 = "insert into blog_index(index_id,index_type,index_glance,index_title) values(?,?,?,?)";
		Object[] param1 = {article.getArticle_id(), new StringReader(article.getArticle_content()), article.getArticle_title(), article.getHolder_id()};
		Object[] param2 = {index.getIndex_id(), index.getIndex_type(), index.getIndex_glance(), index.getIndex_title()};
		String[] sql = {sql1, sql2};
		Object[][] params = {param1, param2};
		return DBUtils.updateTranscation(sql, params);
	}

	@Override
	public int deleteData(String id) {
		String sql1 = "delete from blog_article where article_id=?";
		String sql2 = "delete from blog_index where index_id=?";
		Object[] param1 = {id};
		Object[] param2 = {id};
		String[] sql = {sql1, sql2};
		Object[][] params = {param1, param2};
		return DBUtils.updateTranscation(sql, params);
	}

	@Override
	public int updateData(Object obj) {
		blog_article article = (blog_article)obj;
		blog_index index = new blog_index();
		index.setIndex_id(article.getArticle_id());
		index.setIndex_glance(MetaMsgUtils.getMetaMessage(article.getArticle_content()));
		index.setIndex_title(article.getArticle_title());
		String sql1 = "update blog_article set article_content=?,article_title=? where article_id=?";
		String sql2 = "update blog_index set index_glance=?,index_title=? where index_id=?";
		Object[] param1 = {new StringReader(article.getArticle_content()), article.getArticle_title(), article.getArticle_id()};
		Object[] param2 = {index.getIndex_glance(), index.getIndex_title(), index.getIndex_id()};
		String[] sql = {sql1, sql2};
		Object[][] params = {param1, param2};
		return DBUtils.updateTranscation(sql, params);
	}

	@Override
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select article_id, article_content, article_title, holder_id, create_time from blog_artilce_view limit ?,?";
		Object[] params = {start, page.getPageContain()};
		page.setList(DBUtils.query(sql, params, new ListHandler<List>(blog_article.class)));
		return page;
	}

	@Override
	public blog_page selectIndexData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select article_id, article_title, holder_id, create_time from blog_artilce_view limit ?,?";
		Object[] params = {start, page.getPageContain()};
		page.setList(DBUtils.query(sql, params, new ListHandler<List>(blog_article.class)));
		return page;
	}

	@Override
	public T selectByID(String id) {
		String sql = "select * from blog_article where article_id=?";
		Object[] params = {id};
		return DBUtils.query(sql, params, new BeanHandler<T>(blog_article.class));
	}
}
