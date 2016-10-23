package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

	Connection connection = null;
	PreparedStatement preparedStatement;

	private final String dbName;
	private final String dbUser;
	private final String dbPass;
	private final String url;

	public DatabaseConnection() {
		this.dbName = "jsfworkshop";
		this.dbUser = "root";
		this.dbPass = "4023";
		this.url = "jdbc:mysql://localhost/" + dbName;
	}

	public Connection setConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, dbUser, dbPass);
			System.out.println("connection sucsess");
		} catch (ClassNotFoundException driverEx) {
			driverEx.printStackTrace();
			return null;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			return null;
		}

		return connection;
	}

	public void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
