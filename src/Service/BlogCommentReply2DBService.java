package Service;

import DAO.BlogCommentReply2DB;
import domain.blog_comment_reply;
import domain.blog_page;

public class BlogCommentReply2DBService {
	
	private BlogCommentReply2DB<blog_comment_reply> impl = null;
	
	public BlogCommentReply2DBService() {
		impl = new BlogCommentReply2DB<>();
	}
	//得到该数据的全部数目
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
	//得到该数据的全部数目
	public int getTotalRecordForFrontEnd(String commentID){
		if (commentID == null) {
			return -1;
		}
		return impl.getTotalRecordForCommentAndVisibility(commentID, true);
	}
	//得到该数据的全部数目
	public int getTotalRecordForBackEnd(String commentID){
		if (commentID == null) {
			return -1;
		}
		return impl.getTotalRecordForCommentAndVisibility(commentID, false);
	}
	//增加一个条目
	public int insertCommentReply(blog_comment_reply reply){
		return impl.insertData(reply);
	}
	
	public int deleteCommentReply(String commentReplyID) {
		return impl.deleteData(commentReplyID);
	}
	
	public int updateCommentReply(blog_comment_reply reply) {
		return impl.updateData(reply);
	}
	//查询一个条目
	public blog_page selectCommentReplyForFrontEnd(int currentPage, int pageContain, int pageInFrame, String commentID){
		return impl.selectDataForCommentAndVisibility(currentPage, pageContain, pageInFrame, commentID, true);
	}
		
	public blog_page selectCommentReplyForBackEnd(int currentPage, int pageContain, int pageInFrame, String commentID){
		return impl.selectDataForCommentAndVisibility(currentPage, pageContain, pageInFrame, commentID, false);
	}
	
	public blog_comment_reply selectCommentReplyById(String commentReplyID){
		return impl.selectByID(commentReplyID);
	}
}
