package DAO;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import ServiceImpl.DataObject2DB;
import Utils.DBUtils;
import Utils.IntegerHandler;
import Utils.ListHandler;
import Utils.UniqueCode;
import domain.blog_login;
import domain.blog_page;

public class BlogLogin2DB<T> implements DataObject2DB<T> {

	@Override
	public int getTotalRecord() {
		String sql = "select count(*) from blog_login";
		Object[] params = {};
		return (int)DBUtils.query(sql, params, new IntegerHandler());
	}

	@Override
	public int insertData(Object obj) {
		blog_login login = (blog_login) obj;
		
		String sql = "insert into blog_login(login_id,login_nickname,login_ip,login_province," 
				+ "login_isp,login_area,login_address,login_city," 
				+ "login_country,login_street,visitor_id) values(?,?,?,?,?,?,?,?,?,?,?)";
		if (login.getLogin_id() == null) {
			login.setLogin_id(UniqueCode.getUUID());
		}
		Encoder encoder = Base64.getEncoder();
		try {
			if (login.getLogin_nickname() != null) {
				String loginNickname = encoder.encodeToString(login.getLogin_nickname().getBytes("UTF-8"));
				login.setLogin_nickname(loginNickname);
			}
			Object[] params = {login.getLogin_id(), login.getLogin_nickname(), login.getLogin_ip(), login.getLogin_province(),
					login.getLogin_isp(), login.getLogin_area(), login.getLogin_address(), login.getLogin_city(),
					login.getLogin_country(), login.getLogin_street(), login.getVisitor_id()};
			return DBUtils.update(sql, params);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return -1;
	}

	@Override
	public int deleteData(String id) {
		String sql = "delete from blog_login where login_id=?";
		Object[] params = {id};
		return DBUtils.update(sql, params);
	}

	@Override
	public int updateData(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public blog_page selectData(int currentPage, int pageContain, int pageInFrame) {
		int totalRecord = getTotalRecord();
		blog_page page = new blog_page(totalRecord, currentPage, pageContain, pageInFrame);
		int start = ((page.getCurrentPage() - 1) * page.getPageContain());
		if(start < 0){
			start = 0;
		}
		String sql = "select * from blog_login order by login_date desc limit ?,?";
		Object[] params = {start, page.getPageContain()};
		List<blog_login> list = DBUtils.query(sql, params, new ListHandler<List<blog_login>>(blog_login.class));
		Decoder decoder = Base64.getDecoder();
		for(int i = 0; i < list.size(); i ++) {
			blog_login login = list.get(i);
			if (login.getLogin_nickname() != null) {
				try {
					login.setLogin_nickname(new String(decoder.decode(login.getLogin_nickname()), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		page.setList(list);
		return page;
	}

	@Override
	public blog_page selectIndexData(int currentPage, int pageContain, int pageInFrame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T selectByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
