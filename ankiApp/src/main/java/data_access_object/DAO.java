package data_access_object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	private final String URL = "jdbc:mysql://d6uoc91fueyqgpyr:ht52jrafaw0f8qlh@nba02whlntki5w2p.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/yngwlvduglko28ms";
	private final String USER = "d6uoc91fueyqgpyr";
	private final String PASS = "ht52jrafaw0f8qlh";
	private Connection connection = null;

	public Connection getConnection() {
		return connection;
	}
	public void connectDB() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(URL, USER, PASS);
	}
	
	
	public void resourcesClose(PreparedStatement preparedstatement) {
		try {
			if(preparedstatement != null) preparedstatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resourcesClose(PreparedStatement preparedstatement, ResultSet result_set) {
		try {
			if(result_set != null) result_set.close();
			if(preparedstatement != null) preparedstatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
