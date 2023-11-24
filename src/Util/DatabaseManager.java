package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

	public static final int STUDENT = 0;
	public static final int TEACHER = 1;
	public static final int ADMIN = 2;
	private static Connection connection = null;

	private static void initialize() {
		String username = "root";
		String password = "qq598877662";
		String url = "jdbc:mysql://127.0.0.1:4485/studentscoremanager";
		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			System.out.println("数据库连接失败");
			try {
				connection.close();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public static Connection getConnection() {
		if (connection != null) {
			return connection;
		}
		initialize();
		if (connection == null) {
			throw new IllegalStateException("数据库初始化失败");
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}