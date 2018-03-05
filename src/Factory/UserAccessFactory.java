package Factory;

import ServiceImpl.UserAccess2DB;

public class UserAccessFactory {
	
	private String classpath = null;
	private UserAccess2DB impl = null;
	
	private UserAccessFactory(String classpath){
		this.classpath = classpath;
	}
	
	public static UserAccessFactory getFactory(String classpath){
		return new UserAccessFactory(classpath);
	}
	
	public UserAccess2DB getImplInstance(){
		if(classpath != null){
			try {
				Class clazz = Class.forName(classpath);
				impl = (UserAccess2DB) clazz.newInstance();
				return impl;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return null;
		}
		return null;
	}
}
