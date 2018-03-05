package DAO;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import ServiceImpl.UserAccess2DB;
import Utils.BeanHandler;
import Utils.DBUtils;
import Utils.MD5Utils;
import domain.blog_holder;
import domain.blog_page;

public class BlogHolder2DB implements UserAccess2DB{

	@Override
	public int insertUser(Object obj) {
		blog_holder holder = (blog_holder) obj;
		holder.setHolder_pwd(MD5Utils.getToken(holder.getHolder_pwd()));
		StringBuffer sql = new StringBuffer("insert into blog_holder(");
		try {
			BeanInfo info = Introspector.getBeanInfo(blog_holder.class, Object.class);
			PropertyDescriptor pds[] = info.getPropertyDescriptors();
			Object[] params = new Object[pds.length];
			for(int i = 0; i < pds.length; i++){
				if(i == pds.length - 1){
					sql.append(pds[i].getName());
				}else{
					sql.append(pds[i].getName() + ",");
				}
				Method method = pds[i].getReadMethod();
				if(method.getName().equals("getHolder_school_year")){
					int value = (int) method.invoke(holder, null);
					params[i] = value;
				}else{
					String value = (String) method.invoke(holder, null);
					params[i] = value;
				}
			}
			sql.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			return DBUtils.update(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteUser(Object obj) {
		blog_holder holder = (blog_holder) obj;
		String sql = "delete from blog_holder where holder_id=?";
		Object[] params = {holder.getHolder_id()};
		return DBUtils.update(sql, params);
	}

	@Override
	public int updateUser(Object obj) {
		blog_holder holder = (blog_holder) obj;
		StringBuffer sql = new StringBuffer("update blog_holder set ");
		BeanInfo info;
		try {
			info = Introspector.getBeanInfo(blog_holder.class, Object.class);
			PropertyDescriptor pds[] = info.getPropertyDescriptors();
			Object[] params = new Object[pds.length];
			for(int i = 0, j = 0; i < pds.length; i++){
				if(pds[i].getName().equals("holder_id")) continue;
				if(i == pds.length - 1){
					sql.append(pds[i].getName() + "=? ");
				}else{
					sql.append(pds[i].getName() + "=?,");
				}
				Method method = pds[i].getReadMethod();
				if(method.getName().equals("getHolder_school_year")){
					int value = (int) method.invoke(holder, null);
					params[j++] = value;
				}else{
					String value = (String) method.invoke(holder, null);
					params[j++] = value;
				}
			}
			sql.append("where holder_id=?");
			params[pds.length - 1] = holder.getHolder_id();
			return DBUtils.update(sql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Object selectUser(String name) {
		String sql = "select holder_city_en,holder_city_zh,holder_email,holder_img,holder_name_en,holder_name_zh,holder_province_en,holder_province_zh,holder_pwd,holder_school_en,holder_school_year,holder_school_zh,holder_id from blog_holder where holder_name_zh=?";
		Object[] params = {name};
		return DBUtils.query(sql, params, new BeanHandler(blog_holder.class));
	}

	@Override
	public boolean isExist(String name) {
		
		String sql = "select holder_city_en,holder_city_zh,holder_email,holder_img,holder_name_en,holder_name_zh,holder_province_en,holder_province_zh,holder_pwd,holder_school_en,holder_school_year,holder_school_zh,holder_id from blog_holder where holder_name_zh=?";
		Object[] params = {name};
		blog_holder holder = (blog_holder) DBUtils.query(sql, params, new BeanHandler(blog_holder.class));
		return holder == null?false:true;
	}

	@Override
	public Object selectUserByIndex() {
		String sql = "select holder_city_en,holder_city_zh,holder_email,holder_img,holder_name_en,holder_name_zh,holder_province_en,holder_province_zh,holder_pwd,holder_school_en,holder_school_year,holder_school_zh,holder_id from blog_holder limit 0,1";
		Object[] params = {};
		return DBUtils.query(sql, params, new BeanHandler(blog_holder.class));
	}

	@Override
	public blog_page selectUserByList(int currentPage, int pageContain, int pageInFrame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRecord() {
		// TODO Auto-generated method stub
		return 0;
	}
}
