package Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import Interfaces.ResultSetHandler;

public class IntegerHandler<T> implements ResultSetHandler<T>{

	@Override
	public <T> T operate(ResultSet result) {
		try {
			if(result.next()){
				return (T) new Integer(result.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
