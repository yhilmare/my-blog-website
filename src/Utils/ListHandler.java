package Utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Interfaces.ResultSetHandler;

public class ListHandler<T> implements ResultSetHandler<T> {

	private Class clazz = null;
	public ListHandler(Class clazz){
		this.clazz = clazz;
	}
	public <T> T operate(ResultSet result) {
		
		if(result== null) return null;
		List list = new LinkedList();
		try {
			while(result.next()){
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
				list.add(obj);
			}
			return (T) list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
