package Interfaces;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {
	public <T> T operate(ResultSet result);
}
