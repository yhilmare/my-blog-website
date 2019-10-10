package domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class blog_login {
	
	private String login_id;
	private String login_nickname;
	private String visitor_id;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="zh",timezone="GMT+8")
	private Date login_date;
	private String login_ip;
	private String login_province;
	private String login_isp;
	private String login_area;
	private String login_address;
	private String login_city;
	private String login_country;
	private String login_street;
	
	
	public String getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getLogin_nickname() {
		return login_nickname;
	}
	public void setLogin_nickname(String login_nickname) {
		this.login_nickname = login_nickname;
	}
	public Date getLogin_date() {
		return login_date;
	}
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	public String getLogin_province() {
		return login_province;
	}
	public void setLogin_province(String login_province) {
		this.login_province = login_province;
	}
	public String getLogin_isp() {
		return login_isp;
	}
	public void setLogin_isp(String login_isp) {
		this.login_isp = login_isp;
	}
	public String getLogin_area() {
		return login_area;
	}
	public void setLogin_area(String login_area) {
		this.login_area = login_area;
	}
	public String getLogin_address() {
		return login_address;
	}
	public void setLogin_address(String login_address) {
		this.login_address = login_address;
	}
	public String getLogin_city() {
		return login_city;
	}
	public void setLogin_city(String login_city) {
		this.login_city = login_city;
	}
	public String getLogin_country() {
		return login_country;
	}
	public void setLogin_country(String login_country) {
		this.login_country = login_country;
	}
	public String getLogin_street() {
		return login_street;
	}
	public void setLogin_street(String login_street) {
		this.login_street = login_street;
	}
}
