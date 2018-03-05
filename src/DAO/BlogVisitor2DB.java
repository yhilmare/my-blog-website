package DAO;

import java.util.List;

import ServiceImpl.UserAccess2DB;
import Utils.BeanHandler;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import Utils.MD5Utils;
import Utils.UniqueCode;
import domain.blog_page;
import domain.blog_visitor;

public class BlogVisitor2DB implements UserAccess2DB{

	@Override
	public int insertUser(Object obj) {
		blog_visitor visitor = (blog_visitor) obj;
		visitor.setVisitor_pwd(MD5Utils.getToken(visitor.getVisitor_pwd()));
		visitor.setVisitor_id(UniqueCode.getUUID());
		String sql = "insert blog_visitor(visitor_id,visitor_pwd,visitor_nickname,visitor_gender) values(?,?,?,?)";
		Object[] params = {visitor.getVisitor_id(), visitor.getVisitor_pwd(), visitor.getVisitor_nickname(), visitor.getVisitor_gender()};
		return DBUtils.update(sql, params);
	}

	@Override
	public int deleteUser(Object obj) {
		blog_visitor visitor = (blog_visitor) obj;
		String sql = "delete from blog_visitor where visitor_id=?";
		Object[] params = {visitor.getVisitor_id()};
		return DBUtils.update(sql, params);
	}

	@Override
	public int updateUser(Object obj) {
		blog_visitor visitor = (blog_visitor)obj;
		String sql = "update blog_visitor set visitor_pwd=?,visitor_nickname=?,visitor_gender=? where visitor_id=?";
		Object[] params = {visitor.getVisitor_pwd(), visitor.getVisitor_nickname(), visitor.getVisitor_gender(), visitor.getVisitor_id()};
		return DBUtils.update(sql, params);
	}

	@Override
	public Object selectUser(String name) {
		String sql = "select visitor_id,visitor_pwd,visitor_nickname,visitor_gender from blog_visitor where visitor_nickname=?";
		Object[] params = {name};
		return DBUtils.query(sql, params, new BeanHandler(blog_visitor.class));
	}

	@Override
	public boolean isExist(String name) {
		String sql = "select visitor_id,visitor_pwd,visitor_nickname,visitor_gender from blog_visitor where visitor_nickname=?";
		Object[] params = {name};
		return DBUtils.query(sql, params, new BeanHandler(blog_visitor.class)) == null?false:true;
	}

	@Override
	public Object selectUserByIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public blog_page selectUserByList(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select * from blog_visitor limit ?,?";
		Object[] params = {start, page.getPageContain()};
		page.setList((List) DBUtils.query(sql, params, new ListHandler(blog_visitor.class)));
		return page;
	}

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_visitor";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}
}
