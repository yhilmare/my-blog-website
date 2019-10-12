package Service;

import DAO.BlogComment2DB;
import domain.blog_comment;
import domain.blog_page;

public class BlogComment2DBService {
	
	private BlogComment2DB<blog_comment> impl = null;
	
	public BlogComment2DBService() {
		impl = new BlogComment2DB<>();
	}
	//得到该数据的全部数目
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
	//得到该数据的全部数目
	public int getTotalRecordByArticleID(String articleID){
		if (articleID == null) {
			return -1;
		}
		return impl.getTotalRecordForArticle(articleID);
	}
	//增加一个条目
	public int insertComment(blog_comment comment){
		return impl.insertData(comment);
	}
	
	public int deleteComment(String commentID) {
		return impl.deleteData(commentID);
	}
	
	public int updateComment(blog_comment comment) {
		return impl.updateData(comment);
	}
	//查询一个条目
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
