package domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class blog_index {
	
	private String index_id;
	private String index_type;
	private String index_glance;
	private String index_title;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="zh",timezone="GMT+8")
	private Date index_date;
	public String getIndex_id() {
		return index_id;
	}
	public void setIndex_id(String index_id) {
		this.index_id = index_id;
	}
	public String getIndex_type() {
		return index_type;
	}
	public void setIndex_type(String index_type) {
		this.index_type = index_type;
	}
	public String getIndex_glance() {
		return index_glance;
	}
	public void setIndex_glance(String index_glance) {
		this.index_glance = index_glance;
	}
	public String getIndex_title() {
		return index_title;
	}
	public void setIndex_title(String index_title) {
		this.index_title = index_title;
	}
	public Date getIndex_date() {
		return index_date;
	}
	public void setIndex_date(Date index_date) {
		this.index_date = index_date;
	}
}
