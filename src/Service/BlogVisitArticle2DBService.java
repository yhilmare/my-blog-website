package Service;

import Factory.DataAccessFactory;
import ServiceImpl.DataObject2DB;
import domain.blog_page;
import domain.blog_visit_article;

public class BlogVisitArticle2DBService {
	private DataObject2DB<blog_visit_article> impl = null;
	public BlogVisitArticle2DBService(){
		impl = DataAccessFactory.getFactory("DAO.BlogVisitArticle2DB").getImpleInstance();
	}
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
	
	//增加一个条目
	public int insertVisitArticle(blog_visit_article visitArticle){
		return impl.insertData(visitArticle);
	}
	
	//查询一个条目
	public blog_page selectVisitArticle(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
	//删除一个条目
	public int deleteVisitArticle(String visit_id){
		return impl.deleteData(visit_id);
	}
}
