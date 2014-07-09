package never.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:3306/db_news";
	private final String USER = "root";
	private final String PWD = "taobao1234";

	public DbUtil() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(URL, USER, PWD);
			System.out.println("Connect DB Successfully！");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
