package Units;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.BlogVisitArticle2DB;
import DAO.BlogVisitor2DB;
import Factory.DataAccessFactory;
import Factory.UserAccessFactory;
import Service.BlogArticle2DBService;
import Service.BlogHolder2DBService;
import Service.BlogIndex2DBService;
import Service.BlogMessage2DBService;
import Service.BlogNotice2DBService;
import Service.BlogStatus2DBService;
import Service.BlogVisit2DBService;
import Service.BlogVisitArticle2DBService;
import Service.BlogVisitor2DBService;
import ServiceImpl.DataObject2DB;
import ServiceImpl.UserAccess2DB;
import Utils.BeanHandler;
import Utils.DBUtils;
import Utils.DateFormateUtil;
import Utils.MD5Utils;
import Utils.UniqueCode;
import domain.TipMessage;
import domain.blog_article;
import domain.blog_holder;
import domain.blog_index;
import domain.blog_message;
import domain.blog_notice;
import domain.blog_page;
import domain.blog_status;
import domain.blog_visit;
import domain.blog_visit_article;
import domain.blog_visitor;
import sun.misc.BASE64Encoder;

public class DBUnit {
	
	@Test
	public void test1(){
		String sql = "insert into blog_article(article_id,article_content,article_title,holder_id) values(?,?,?,?)";
		Object[] params = {"1234567", new StringReader("woiruweioruwieruwoieurweruw"), "I have a dream", "10000"};
		System.out.println(DBUtils.update(sql, params));
	}
	@Test
	public void test2(){
		
	}
	
	@Test
	public void test3(){
		String sql = "insert into blog_holder(holder_id,holder_name_zh,holder_email,holder_pwd) values(?,?,?,?)";
		String pwd = MD5Utils.getToken("yanghang19940805");
		Object[] params = {UniqueCode.getUUID(), "�", "yh_swjtu@163.com", pwd};
		System.out.println(DBUtils.update(sql, params));
	}
	
	@Test
	public void test4(){
		blog_holder holder = new blog_holder();
		holder.setHolder_id("8f1c106e-70ba-4928-88a0-742a8b83debe");
		holder.setHolder_name_zh("�");
		holder.setHolder_city_en("Chengdu");
		holder.setHolder_city_zh("�ɶ�");
		holder.setHolder_name_en("IL MARE");
		holder.setHolder_province_en("Sichuan");
		holder.setHolder_province_zh("�Ĵ�");
		holder.setHolder_school_en("Southwest Jiaotong University");
		holder.setHolder_school_year(2013);
		holder.setHolder_school_zh("���Ͻ�ͨ��ѧ");
		holder.setHolder_email("yh_swjtu@163.com");
		holder.setHolder_pwd(MD5Utils.getToken("yanghang19940805"));
		UserAccess2DB impl = UserAccessFactory.getFactory("DAO.BlogHolder2DB").getImplInstance();
		System.out.println(impl.updateUser(holder));
	}
	
	@Test
	public void test5() throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		UserAccess2DB impl = UserAccessFactory.getFactory("DAO.BlogHolder2DB").getImplInstance();
		blog_holder holder = (blog_holder) impl.selectUser("�");
		BeanInfo info = Introspector.getBeanInfo(blog_holder.class, Object.class);
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		for(PropertyDescriptor pd : pds){
			Method method = pd.getReadMethod();
			Object obj = method.invoke(holder, null);
			System.out.println(pd.getName() + ": " + obj);
		}
	}
	
	@Test
	public void test6() throws Exception{
		blog_page page = new blog_page(70, 5, 10, 10);
		BeanInfo info = Introspector.getBeanInfo(blog_page.class, Object.class);
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		for(PropertyDescriptor pd : pds){
			Method method = pd.getReadMethod();
			Object obj = method.invoke(page, null);
			System.out.println(pd.getName() + ": " + obj);
		}
	}
	
	@Test
	public void test7(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogArticle2DB").getImpleInstance();
		System.out.println(impl.getTotalRecord());
	}
	
	@Test
	public void test8(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogArticle2DB").getImpleInstance();
		UserAccess2DB impls = UserAccessFactory.getFactory("DAO.BlogHolder2DB").getImplInstance();
		blog_holder holder = (blog_holder) impls.selectUser("�");
		blog_article article = new blog_article();
		article.setArticle_content("<div>����һ�����ˣ��Ҳ�֪����ʲô��������,��ÿ��Ŭ��������</div><div>�Ҳ����ҵ�������ɮ�������ݡ�</div>");
		article.setArticle_id(UniqueCode.getUUID());
		article.setArticle_title("�ҵ���80��");
		article.setHolder_id(holder.getHolder_id());
		System.out.println(impl.insertData(article));
	}
	
	@Test
	public void test9(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogArticle2DB").getImpleInstance();
		UserAccess2DB impls = UserAccessFactory.getFactory("DAO.BlogHolder2DB").getImplInstance();
		blog_holder holder = (blog_holder) impls.selectUser("�");
		blog_article article = new blog_article();
		article.setArticle_content("����64564654646һ�����ˣ������Ҳ�֪����ʲô��������,��ÿ��Ŭ��������  sadsdad");
		article.setArticle_id("e94c6fbf-b172-4a43-8057-7f3c2ffc8da0");
		article.setArticle_title("�ҵ���80��");
		article.setHolder_id(holder.getHolder_id());
		System.out.println(impl.updateData(article));
	}
	
	@Test
	public void test10(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogArticle2DB").getImpleInstance();
		blog_page page = impl.selectData(1, 3, 10);
		List list = page.getList();
		for(int i = 0; i < list.size(); i++){
			blog_article article = (blog_article) list.get(i);
			System.out.println(article.getCreate_time() + article.getArticle_content());
		}
	}
	
	@Test
	public void test11(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogStatus2DB").getImpleInstance();
//		UserAccess2DB impl1 = UserAccessFactory.getFactory("DAO.BlogHolder2DB").getImplInstance();
//		blog_holder holder = (blog_holder) impl1.selectUser("�");
//		blog_status status = new blog_status();
//		status.setHolder_id(holder.getHolder_id());
//		status.setStatus_content("��woshs��ĺ�");
//		status.setStatus_id("f94433eb-93f0-4a59-ad27-571c2372f8b5");
		blog_page page = impl.selectData(1, 10, 10);
		List list = page.getList();
		for(int i = 0; i < list.size(); i++){
			blog_status status = (blog_status) list.get(i);
			System.out.println(status.getPublish_date() + ": " + status.getStatus_content());
		}
	}
	
	@Test
	public void test12(){
		DataObject2DB impl1 = DataAccessFactory.getFactory("DAO.BlogMessage2DB").getImpleInstance();
		blog_page page = impl1.selectData(1, 10, 10);
		List list = page.getList();
		for(int i = 0; i < list.size(); i++){
			blog_message message = (blog_message) list.get(i);
			System.out.println(message.getMessage_date() + " : " + message.getMessage_content());
		}
		String sql = "aaaaa";
		System.out.println(sql.length());
	}
	
	@Test
	public void test13(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogStatus2DB").getImpleInstance();
		blog_status status = new blog_status();
		status.setStatus_id(UniqueCode.getUUID());
		status.setStatus_content("���������̫���ˣ���ֱ�ܲ����ˣ����Ѿ��ɶҪ����㴩���ˣ�������");
		status.setHolder_id("8f1c106e-70ba-4928-88a0-742a8b83debe");
		System.out.println(impl.insertData(status));
	}
	
	@Test
	public void test14(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogIndex2DB").getImpleInstance();
		blog_page page = impl.selectData(1, 10, 10);
		List list = page.getList();
		for(int i = 0; i < list.size(); i++){
			blog_index index = (blog_index) list.get(i);
			System.out.println(index.getIndex_date() + " : " + index.getIndex_glance());
		}
	}
	
	@Test
	public void test15(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogNotice2DB").getImpleInstance();
//		blog_notice notice = new blog_notice();
//		notice.setHolder_id("8f1c106e-70ba-4928-88a0-742a8b83debe");
//		notice.setNotice_content("��������Ҫ��������!");
//		notice.setNotice_id(UniqueCode.getUUID());
//		System.out.println(impl.insertData(notice));
		System.out.println(impl.getTotalRecord());
		blog_page page = impl.selectData(1, 1, 10);
		List list = page.getList();
		blog_notice notice = (blog_notice) list.get(0);
		System.out.println(notice.getPublish_date() + " : " + notice.getNotice_content());
	}

	@Test
	public void test16(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogVisit2DB").getImpleInstance();
//		blog_visit visit = new blog_visit();
//		visit.setVisit_latitude(120.256688997);
//		visit.setVisit_longitude(30.2544897);
//		visit.setVisitor_id("d2d2882c-e206-4559-87e5-55f6a69dd56e");
//		System.out.println(impl.insertData(visit));
		blog_page page = impl.selectData(1, 10, 10);
		List list = page.getList();
		for(int i = 0; i < list.size(); i++){
			blog_visit visit = (blog_visit) list.get(i);
			System.out.println(visit.getVisit_date() + " : " + visit.getVisitor_id());
		}
	}
	
	@Test
	public void test17(){
		DataObject2DB impl = DataAccessFactory.getFactory("DAO.BlogVisitArticle2DB").getImpleInstance();
		//System.out.println(impl.getTotalRecord());
		blog_page page = impl.selectData(1, 10, 10);
		List list = page.getList();
		for(int i = 0; i < list.size(); i++){
			blog_visit_article visit = (blog_visit_article) list.get(i);
			System.out.println(visit.getVisit_date());
		}
//		blog_visit_article visit = new blog_visit_article();
//		visit.setVisit_id(UniqueCode.getUUID());
//		visit.setArticle_id("6fa82756-29c3-48ad-ab89-1cb7665d4f7c");
//		visit.setVisitor_id("d2d2882c-e206-4559-87e5-55f6a69dd56e");
//		System.out.println(impl.insertData(visit));
	}
	
	@Test
	public void test18() throws Exception{
		BlogIndex2DBService service = new BlogIndex2DBService();
		
	}
	
	@Test
	public void test19() throws UnsupportedEncodingException{
		String msg = "���Ƕ�";
		byte[] buffer = msg.getBytes();
		for(int i = 0; i < buffer.length; i++){
			byte temp = buffer[i];
			System.out.print(temp);
		}
		buffer = msg.getBytes("utf-8");
		System.out.println("");
		for(int i = 0; i < buffer.length; i++){
			byte temp = buffer[i];
			System.out.print(temp);
		}
//		System.out.println(msg.getBytes());
//		System.out.println(msg);
	}
	@Test
	public void test20() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//System.out.println(MD5Utils.getToken("940805"));
		MessageDigest digest = MessageDigest.getInstance("md5");
		byte[] buffer = digest.digest("yanghang19940805".getBytes());
		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println(encoder.encode(buffer));
	}
	
	@Test
	public void test21() throws JsonProcessingException{
		BlogArticle2DBService service = new BlogArticle2DBService();
		blog_page page = service.selectArticleIndex(1, 9, 5);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(page));
	}
	
	@Test
	public void test22(){
		BlogMessage2DBService service = new BlogMessage2DBService();
		blog_message msg = new blog_message();
		msg.setMessage_content("��ã�������Ļ���̫����");
		msg.setMessage_id(UniqueCode.getUUID());
		msg.setMessage_latitude(30.458965);
		msg.setMessage_longitude(120.568989797);
		msg.setVisitor_id("�������");
		System.out.println(service.insertMessage(msg));
	}
	
	@Test
	public void test23(){
		BlogHolder2DBService service = new BlogHolder2DBService();
		blog_holder msg = new blog_holder();
		msg.setHolder_id("0ca06e64-01a1-47f9-94b6-12599e9abbbf");
		msg.setHolder_name_zh("�");
		msg.setHolder_email("yh_swjtu@163.com");
		msg.setHolder_pwd(MD5Utils.getToken("yanghang19940805"));
		System.out.println(service.updateHolder(msg));
	}
	
	@Test
	public void test24(){
		String value = "-1";
		String msg = value == "-1"?"1":"-1";
		System.out.println(msg);
	}
	
	@Test
	public void test25() throws SQLException{
		BlogVisitArticle2DBService ser = new BlogVisitArticle2DBService();
		blog_visit_article va = new blog_visit_article();
		va.setArticle_id("554d9a41-7cd6-4bb1-92c8-ea31cf367700");
		va.setVisit_id(UniqueCode.getUUID());
		//va.setVisitor_id(visitor_id);
		System.out.println(ser.insertVisitArticle(va));
	}
	
	@Test
	public void test26(){
		try{
			System.out.println(checkPageFrame(null));
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public boolean checkPageFrame(String pageFrame){
		if (pageFrame == null){
			return false;
		}else{
			try{
				Integer.parseInt(pageFrame);
			}catch(Exception e){
				return false;
			}
			return true;
		}
	}
	@Test
	public void test27() throws NoSuchFieldException, SecurityException , IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException{
		blog_holder holder = new blog_holder();
		Field field = blog_holder.class.getDeclaredField("holder_name_zh");
		field.setAccessible(true);
		field.set(holder, "david");
		BeanInfo info = Introspector.getBeanInfo(blog_holder.class,Object.class);
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds){
			Method mt = pd.getReadMethod();
			System.out.println(pd.getName() + mt.invoke(holder, null));
		}
	}
}

class UnitUtils{
	public static void getBeanInfo(Class clazz, Object obj) throws Exception{
		BeanInfo info = Introspector.getBeanInfo(clazz, Object.class);
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		for(PropertyDescriptor pd : pds){
			String name = pd.getName();
			Object value = pd.getReadMethod().invoke(obj, null);
			System.out.println(name + " : " + value);
		}
		System.out.println("===================================================");
	}
}