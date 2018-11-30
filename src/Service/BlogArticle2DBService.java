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
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
		
		//����һ����Ŀ
	public int insertArticle(blog_article article){
		return impl.insertData(article);
	}
		
		//ɾ��һ����Ŀ
	public int deleteArticle(String id){
		return impl.deleteData(id);
	}
		
		//�޸�һ����Ŀ
	public int updateArticle(blog_article article){
		return impl.updateData(article);
	}
		
		//��ѯһ����Ŀ
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
