package Service;

import DAO.BlogCommentReply2DB;
import domain.blog_comment_reply;
import domain.blog_page;

public class BlogCommentReply2DBService {
	
	private BlogCommentReply2DB<blog_comment_reply> impl = null;
	
	public BlogCommentReply2DBService() {
		impl = new BlogCommentReply2DB<>();
	}
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecord(){
		return impl.getTotalRecord();
	}
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecordForFrontEnd(String commentID){
		if (commentID == null) {
			return -1;
		}
		return impl.getTotalRecordForCommentAndVisibility(commentID, true);
	}
	//�õ������ݵ�ȫ����Ŀ
	public int getTotalRecordForBackEnd(String commentID){
		if (commentID == null) {
			return -1;
		}
		return impl.getTotalRecordForCommentAndVisibility(commentID, false);
	}
	//����һ����Ŀ
	public int insertCommentReply(blog_comment_reply reply){
		return impl.insertData(reply);
	}
	
	public int deleteCommentReply(String commentReplyID) {
		return impl.deleteData(commentReplyID);
	}
	
	public int updateCommentReply(blog_comment_reply reply) {
		return impl.updateData(reply);
	}
	//��ѯһ����Ŀ
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
