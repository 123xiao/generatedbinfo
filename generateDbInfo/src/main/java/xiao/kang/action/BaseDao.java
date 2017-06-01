package xiao.kang.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	private final String DRIVER = "com.mysql.jdbc.Driver";
	public String URL = "";
	public String USERNAME = "";
	public String PWD = "";

	private Connection connection;
	private PreparedStatement ps;
	protected ResultSet rs;

	public void getConnection() {
		try {
			Class.forName(DRIVER);
			// 2在网络中查找数据库，创建连接对象
			connection = DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return connection;
	}

	public void closeAll() {
		try {
			// 释放资源
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 查询
	 * 
	 * @param sql
	 * @param params
	 */
	public void executeQuery(String sql, Object[] params) {
		try {
			getConnection();
			ps = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 增、删、改
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeUpdate(String sql, Object[] params) {
		int result = -1;
		try {
			getConnection();
			ps = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			this.closeAll();

		}
		return result;
	}

	/**
	 * 增、删、改
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeUpdate1(String sql, Object... params) {
		int result = -1;
		try {
			getConnection();
			ps = connection.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			this.closeAll();

		}
		return result;
	}
}
