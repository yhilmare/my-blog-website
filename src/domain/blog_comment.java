package domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class blog_comment {
	
	private String comment_id;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",locale="zh",timezone="GMT+8")
	private Date comment_date;
	private String article_id;
	private String visitor_id;
	private String visitor_nickname;
	private String figureurl_qq;
	private String comment_content;
	private String comment_ip;
	private Integer comment_visibility;
	private String article_title;
	private blog_page subComment;
	
	public blog_page getSubComment() {
		return subComment;
	}
	public void setSubComment(blog_page subComment) {
		this.subComment = subComment;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	public String getArticle_id() {
		return article_id;
	}
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}
	public String getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
	}
	public String getVisitor_nickname() {
		return visitor_nickname;
	}
	public void setVisitor_nickname(String visitor_nickname) {
		this.visitor_nickname = visitor_nickname;
	}
	
	public String getFigureurl_qq() {
		return figureurl_qq;
	}
	public void setFigureurl_qq(String figureurl_qq) {
		this.figureurl_qq = figureurl_qq;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_ip() {
		return comment_ip;
	}
	public void setComment_ip(String comment_ip) {
		this.comment_ip = comment_ip;
	}
	public Integer getComment_visibility() {
		return comment_visibility;
	}
	public void setComment_visibility(Integer comment_visibility) {
		this.comment_visibility = comment_visibility;
	}

}
