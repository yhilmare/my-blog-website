package domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class blog_status {
	private String status_id;
	private String status_content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="zh",timezone="GMT+8")
	private Date publish_date;
	private String holder_id;
	public String getStatus_id() {
		return status_id;
	}
	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}
	public String getStatus_content() {
		return status_content;
	}
	public void setStatus_content(String status_content) {
		this.status_content = status_content;
	}
	public Date getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}
	public String getHolder_id() {
		return holder_id;
	}
	public void setHolder_id(String holder_id) {
		this.holder_id = holder_id;
	}
}
