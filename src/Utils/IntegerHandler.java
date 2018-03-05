package Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import Interfaces.ResultSetHandler;

public class IntegerHandler implements ResultSetHandler{

	@Override
	public Object operate(ResultSet result) {
		try {
			if(result.next()){
				return new Integer(result.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
