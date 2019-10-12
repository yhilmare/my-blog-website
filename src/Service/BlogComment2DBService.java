package Service;

import DAO.BlogComment2DB;
import domain.blog_comment;
import domain.blog_page;

public class BlogComment2DBService {
	
	private BlogComment2DB<blog_comment> impl = null;
	
	public BlogComment2DBService() {
		impl = new BlogComment2DB<>();
	}
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecordByArticleID(String articleID){
		if (articleID == null) {
			return -1;
		}
		return impl.getTotalRecordForArticle(articleID);
	}
	//����һ����Ŀ
	public int insertComment(blog_comment comment){
		return impl.insertData(comment);
	}
	
	public int deleteComment(String commentID) {
		return impl.deleteData(commentID);
	}
	
	public int updateComment(blog_comment comment) {
		return impl.updateData(comment);
	}
	//��ѯһ����Ŀ
	public blog_page selectCommentByArticleID(int currentPage, int pageContain, int pageInFrame, String articleID){
		return impl.selectData(currentPage, pageContain, pageInFrame, articleID);
	}
		
	public blog_page selectComment(int currentPage, int pageContain, int pageInFrame){
		return impl.selectData(currentPage, pageContain, pageInFrame);
	}
	
	public blog_comment selectCommentById(String commentID){
		return impl.selectByID(commentID);
	}
}
