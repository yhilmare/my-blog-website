package Service;

import Factory.DataAccessFactory;
import ServiceImpl.DataObject2DB;
import domain.blog_article;
import domain.blog_page;

public class BlogArticle2DBService {
	private DataObject2DB<blog_article> impl = null;
	public BlogArticle2DBService(){
		impl = DataAccessFactory.getFactory("DAO.BlogArticle2DB").getImpleInstance();
	}
	//得到该数据的全部数目
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
		//增加一个条目
	public int insertArticle(blog_article article){
		return impl.insertData(article);
	}
		
		//删除一个条目
	public int deleteArticle(String id){
		return impl.deleteData(id);
	}
		
		//修改一个条目
	public int updateArticle(blog_article article){
		return impl.updateData(article);
	}
		
		//查询一个条目
	public blog_page selectArticle(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
	
	public blog_page selectArticleIndex(int currentPage, int pageContain, int pageInFrame){
		return impl.selectIndexData(currentPage, pageContain, pageInFrame);
	}
	
	public blog_article selectArticleByID(String article_id){
		return impl.selectByID(article_id);
	}
}
