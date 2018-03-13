package domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class blog_visit {
	private double visit_longitude;
	private double visit_latitude;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="zh",timezone="GMT+8")
	private Date visit_date;
	private String visitor_id;
	public double getVisit_longitude() {
		return visit_longitude;
	}
	public void setVisit_longitude(double visit_longitude) {
		this.visit_longitude = visit_longitude;
	}
	public double getVisit_latitude() {
		return visit_latitude;
	}
	public void setVisit_latitude(double visit_latitude) {
		this.visit_latitude = visit_latitude;
	}
	public Date getVisit_date() {
		return visit_date;
	}
	public void setVisit_date(Date visit_date) {
		this.visit_date = visit_date;
	}
	public String getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
	}
}
