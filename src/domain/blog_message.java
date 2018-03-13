package domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class blog_message {
	
	private String message_id;
	private String message_content;
	private double message_longitude;
	private double message_latitude;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="zh",timezone="GMT+8")
	private Date message_date;
	private String visitor_id;
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	
	public double getMessage_longitude() {
		return message_longitude;
	}
	public void setMessage_longitude(double message_longitude) {
		this.message_longitude = message_longitude;
	}
	public double getMessage_latitude() {
		return message_latitude;
	}
	public void setMessage_latitude(double message_latitude) {
		this.message_latitude = message_latitude;
	}
	public Date getMessage_date() {
		return message_date;
	}
	public void setMessage_date(Date message_date) {
		this.message_date = message_date;
	}
	public String getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
	}
}
