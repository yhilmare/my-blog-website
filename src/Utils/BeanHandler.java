package Utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import Interfaces.ResultSetHandler;

public class BeanHandler implements ResultSetHandler {

	private Class clazz = null;
	
	public BeanHandler(Class clazz){
		this.clazz = clazz;
	}
	@Override
	public Object operate(ResultSet result) {
		
		if(result == null){
			return null;
		}
		
		try {
			if(result.next()){
				Object obj = clazz.newInstance();
				ResultSetMetaData metaData = result.getMetaData();
				int count = metaData.getColumnCount();
				for(int i = 0; i < count; i++){
					String name = metaData.getColumnName(i + 1);
					Object value = result.getObject(i + 1);
					PropertyDescriptor pd = new PropertyDescriptor(name, clazz);
					Method method = pd.getWriteMethod();
					method.invoke(obj, value);
				}
				return obj;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
