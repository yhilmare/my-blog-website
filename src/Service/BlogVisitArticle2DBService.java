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
	
	//����һ����Ŀ
	public int insertVisitArticle(blog_visit_article visitArticle){
		return impl.insertData(visitArticle);
	}
	
	//��ѯһ����Ŀ
	public blog_page selectVisitArticle(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
	//ɾ��һ����Ŀ
	public int deleteVisitArticle(String visit_id){
		return impl.deleteData(visit_id);
	}
}
