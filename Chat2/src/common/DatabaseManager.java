package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseManager {
	Connection conn = null;

	public DatabaseManager() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/forum", "root", "");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean loginUser(String username, String password) {

		ResultSet rs;

		try {
			rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE Username = '" + username + "' AND Password = '" + password + "'");

			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public void close() throws SQLException {
		this.conn.close();
	}

	
}
