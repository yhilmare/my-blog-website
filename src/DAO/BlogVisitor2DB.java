package DAO;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
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

public class BlogVisitor2DB<T> implements UserAccess2DB<T>{

	@Override
	public int insertUser(Object obj) {
		blog_visitor visitor = (blog_visitor) obj;
		if (visitor.getVisitor_pwd() != null && visitor.getVisitor_pwd().length() != 0) {
			visitor.setVisitor_pwd(MD5Utils.getToken(visitor.getVisitor_pwd()));
		}
		if (visitor.getVisitor_id() == null) {
			visitor.setVisitor_id(UniqueCode.getUUID());
		}
		Encoder encoder = Base64.getEncoder();
		try {
			if (visitor.getConstellation() != null && visitor.getConstellation().length() != 0) {
				visitor.setConstellation(encoder.encodeToString(visitor.getConstellation().getBytes("UTF-8")));
			}
			if (visitor.getVisitor_nickname() != null && visitor.getVisitor_nickname().length() != 0) {
				visitor.setVisitor_nickname(encoder.encodeToString(visitor.getVisitor_nickname().getBytes("UTF-8")));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return -1;
		}
		String sql = "insert blog_visitor(visitor_id,visitor_pwd,visitor_nickname,visitor_gender,"
		        + "city,constellation,figureurl,figureurl_1,"
				+ "figureurl_2,figureurl_qq,figureurl_qq_1,figureurl_qq_2,"
		        + "figureurl_type,is_lost,is_yellow_vip,is_yellow_year_vip,"
				+ "level,province,vip,year,yellow_vip_level) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {visitor.getVisitor_id(), visitor.getVisitor_pwd(), visitor.getVisitor_nickname(), visitor.getVisitor_gender(), 
				visitor.getCity(), visitor.getConstellation(), visitor.getFigureurl(), visitor.getFigureurl_1(), 
				visitor.getFigureurl_2(), visitor.getFigureurl_qq(), visitor.getFigureurl_qq_1(), visitor.getFigureurl_qq_2(), 
				visitor.getFigureurl_type(), visitor.getIs_lost(), visitor.getIs_yellow_vip(), visitor.getIs_yellow_year_vip(), 
				visitor.getLevel(), visitor.getProvince(), visitor.getVip(), visitor.getYear(), visitor.getYellow_vip_level()};
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
		String sql = "update blog_visitor set visitor_pwd=?,visitor_nickname=?,visitor_gender=?,city=?,"
				+ "constellation=?,figureurl=?,figureurl_1=?,figureurl_2=?,"
				+ "figureurl_qq=?,figureurl_qq_1=?,figureurl_qq_2=?,figureurl_type=?,"
				+ "is_lost=?,is_yellow_vip=?,is_yellow_year_vip=?,level=?,"
				+ "province=?,vip=?,year=?,yellow_vip_level=? where visitor_id=?";
		Encoder encoder = Base64.getEncoder();
		try {
			if (visitor.getConstellation() != null && visitor.getConstellation().length() != 0) {
				visitor.setConstellation(encoder.encodeToString(visitor.getConstellation().getBytes("UTF-8")));
			}
			if (visitor.getVisitor_nickname() != null && visitor.getVisitor_nickname().length() != 0) {
				visitor.setVisitor_nickname(encoder.encodeToString(visitor.getVisitor_nickname().getBytes("UTF-8")));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return -1;
		}
		Object[] params = {visitor.getVisitor_pwd(), visitor.getVisitor_nickname(), visitor.getVisitor_gender(), visitor.getCity(), 
				visitor.getConstellation(), visitor.getFigureurl(), visitor.getFigureurl_1(), visitor.getFigureurl_2(), 
				visitor.getFigureurl_qq(), visitor.getFigureurl_qq_1(), visitor.getFigureurl_qq_2(), visitor.getFigureurl_type(), 
				visitor.getIs_lost(), visitor.getIs_yellow_vip(), visitor.getIs_yellow_year_vip(), visitor.getLevel(), 
				visitor.getProvince(), visitor.getVip(), visitor.getYear(), visitor.getYellow_vip_level(), 
				visitor.getVisitor_id()};
		return DBUtils.update(sql, params);
	}

	@Override
	public T selectUser(String name) {
		String sql = "select * from blog_visitor where visitor_nickname=?";
		Encoder encoder = Base64.getEncoder();
		Decoder decoder = Base64.getDecoder();
		try {
			String temp = encoder.encodeToString(name.getBytes("UTF-8"));
			Object[] params = {temp};
			blog_visitor obj = DBUtils.query(sql, params, new BeanHandler<blog_visitor>(blog_visitor.class));
			if (obj == null) {
				return null;
			}
			if (obj.getVisitor_nickname() != null && obj.getVisitor_nickname().length() != 0) {
				String nickName = new String(decoder.decode(obj.getVisitor_nickname()), "UTF-8");
				obj.setVisitor_nickname(nickName);
			}
			if (obj.getConstellation() != null && obj.getConstellation().length() != 0) {
				String constellation = new String(decoder.decode(obj.getConstellation()), "UTF-8");
				obj.setConstellation(constellation);
			}
			return (T) obj;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public T selectUserByID(String ID) {
		String sql = "select * from blog_visitor where visitor_id=?";
		Decoder decoder = Base64.getDecoder();
		try {
			Object[] params = {ID};
			blog_visitor obj = DBUtils.query(sql, params, new BeanHandler<blog_visitor>(blog_visitor.class));
			if (obj == null) {
				return null;
			}
			if (obj.getVisitor_nickname() != null && obj.getVisitor_nickname().length() != 0) {
				String nickName = new String(decoder.decode(obj.getVisitor_nickname()), "UTF-8");
				obj.setVisitor_nickname(nickName);
			}
			if (obj.getConstellation() != null && obj.getConstellation().length() != 0) {
				String constellation = new String(decoder.decode(obj.getConstellation()), "UTF-8");
				obj.setConstellation(constellation);
			}
			return (T) obj;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isExist(String name) {
		String sql = "select * from blog_visitor where visitor_nickname=?";
		Encoder encoder = Base64.getEncoder();
		try {
			String temp = encoder.encodeToString(name.getBytes("UTF-8"));
			Object[] params = {temp};
			return DBUtils.query(sql, params, new BeanHandler(blog_visitor.class)) == null ? false : true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public T selectUserByIndex() {
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
		List<blog_visitor> list = DBUtils.query(sql, params, new ListHandler<List<blog_visitor>>(blog_visitor.class));
		for(int i = 0; i < list.size(); i ++) {
			blog_visitor obj = list.get(i);
			Decoder decoder = Base64.getDecoder();
			try {
				if (obj.getVisitor_nickname() != null && obj.getVisitor_nickname().length() != 0) {
					String nickName = new String(decoder.decode(obj.getVisitor_nickname()), "UTF-8");
					obj.setVisitor_nickname(nickName);
				}
				if (obj.getConstellation() != null && obj.getConstellation().length() != 0) {
					String constellation = new String(decoder.decode(obj.getConstellation()), "UTF-8");
					obj.setConstellation(constellation);
				}
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		page.setList(list);
		return page;
	}

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_visitor";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler<Integer>());
	}
}
