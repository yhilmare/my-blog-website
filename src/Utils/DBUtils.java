package Utils;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import Interfaces.ResultSetHandler;

public class DBUtils {
	
	private static ComboPooledDataSource com = null;
	
	static{
		com = new ComboPooledDataSource("blog");
	}
	
	private static Connection getConnection() throws SQLException{
		return com.getConnection();
	}
	
	public static ComboPooledDataSource getDataSource(){
		return com;
	}
	
	private static void getRelease(Connection con, Statement state, ResultSet result){
		
		if(result != null){
			try {
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(state != null){
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static int updateTranscation(String[] sql, Object[][] params){
		
		Connection con = null;
		PreparedStatement state = null;
		ResultSet result = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			for(int i = 0; i < sql.length; i++){
				Object[] param = params[i];
				String msg = sql[i];
				state = con.prepareStatement(msg);
				for(int j = 0; j < state.getParameterMetaData().getParameterCount(); j++){
					if(param[j] != null && (param[j].getClass().getName().equals("java.io.StringReader"))){
						StringReader reader = (StringReader) param[j];
						state.setCharacterStream(j+1, reader);
						continue;
					}
					state.setObject(j + 1, param[j]);
				}
				state.executeUpdate();
			}
			con.commit();
			return sql.length;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			getRelease(con, state, result);
		}
		return -1;
	}
	
	public static int update(String sql, Object[] params){
		
		Connection con = null;
		PreparedStatement state = null;
		ResultSet result = null;
		
		try {
			con = getConnection();
			state = con.prepareStatement(sql);
			ParameterMetaData metaData = state.getParameterMetaData();
			int count = metaData.getParameterCount();
			for(int i = 0; i < count; i++){
				if(params[i] != null && (params[i].getClass().getName().equals("java.io.StringReader"))){
					StringReader reader = (StringReader) params[i];
					state.setCharacterStream(i+1, reader);
					continue;
				}
				state.setObject(i + 1, params[i]);
			}
			return state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			getRelease(con, state, result);
		}
		return -1;
	}
	
	public static <T> T query(String sql, Object[] params, ResultSetHandler<T> resHandler){
		
		Connection con = null;
		PreparedStatement state = null;
		ResultSet res = null;
		
		try {
			con = getConnection();
			state = con.prepareStatement(sql);
			ParameterMetaData metaData = state.getParameterMetaData();
			int count = metaData.getParameterCount();
			for(int i = 0; i < count; i++){
				state.setObject(i + 1, params[i]);
			}
			res = state.executeQuery();
			return resHandler.operate(res);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			getRelease(con, state, res);
		}
		return null;
	}
}
