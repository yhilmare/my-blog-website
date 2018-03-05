package Factory;

import ServiceImpl.DataObject2DB;

public class DataAccessFactory {
	
	private String classpath = null;
	private DataObject2DB impl = null;
	
	private DataAccessFactory(String classpath){
		this.classpath = classpath;
	}
	
	public static DataAccessFactory getFactory(String classpath){
		return new DataAccessFactory(classpath);
	}
	
	public DataObject2DB getImpleInstance(){
		
		try {
			Class clazz = Class.forName(this.classpath);
			this.impl = (DataObject2DB) clazz.newInstance();
			return impl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
