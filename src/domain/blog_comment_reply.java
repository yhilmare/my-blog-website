package domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class blog_comment_reply {
	
	private String comment_reply_id;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="zh",timezone="GMT+8")
	private Date comment_reply_date;
	private String comment_id;
	private String visitor_id;
	private String comment_reply_content;
	private String comment_reply_ip;
	private Integer comment_reply_visibility;
	private String visitor_nickname;
	private String figureurl_qq_1;
	
	public String getComment_reply_id() {
		return comment_reply_id;
	}
	public void setComment_reply_id(String comment_reply_id) {
		this.comment_reply_id = comment_reply_id;
	}
	public Date getComment_reply_date() {
		return comment_reply_date;
	}
	public void setComment_reply_date(Date comment_reply_date) {
		this.comment_reply_date = comment_reply_date;
	}
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
	}
	public String getComment_reply_content() {
		return comment_reply_content;
	}
	public void setComment_reply_content(String comment_reply_content) {
		this.comment_reply_content = comment_reply_content;
	}
	public String getComment_reply_ip() {
		return comment_reply_ip;
	}
	public void setComment_reply_ip(String comment_reply_ip) {
		this.comment_reply_ip = comment_reply_ip;
	}
	public Integer getComment_reply_visibility() {
		return comment_reply_visibility;
	}
	public void setComment_reply_visibility(Integer comment_reply_visibility) {
		this.comment_reply_visibility = comment_reply_visibility;
	}
	public String getVisitor_nickname() {
		return visitor_nickname;
	}
	public void setVisitor_nickname(String visitor_nickname) {
		this.visitor_nickname = visitor_nickname;
	}
	public String getFigureurl_qq_1() {
		return figureurl_qq_1;
	}
	public void setFigureurl_qq_1(String figureurl_qq_1) {
		this.figureurl_qq_1 = figureurl_qq_1;
	}

}
