package data_access_object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bean.FileOfData;
import bean.UserBean;
import bean.UserInformationBean;

public class CreateUserDAO {
	private final String URL = "jdbc:mysql://d6uoc91fueyqgpyr:ht52jrafaw0f8qlh@nba02whlntki5w2p.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/yngwlvduglko28ms";
	private final String USER = "d6uoc91fueyqgpyr";
	private final String PASS = "ht52jrafaw0f8qlh";
	private Connection connection = null;

	public void connectDB() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(URL, USER, PASS);
	}

	public UserInformationBean select() {
		Statement statement = null;
		ResultSet result_set = null;
		UserInformationBean user_info_dto = new UserInformationBean();
		String sql = "SELECT * FROM user";

		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(sql);

			while (result_set.next()) {
				UserBean user_bean = new UserBean();
				user_bean.setNo(result_set.getInt("id"));
				user_bean.setName(result_set.getString("name"));
				user_bean.setPassword(result_set.getString("password"));

				user_info_dto.add(user_bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result_set != null)
					result_set.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		disconnect();
		return user_info_dto;

	}
	
	public int createUser(String name, String password) {
		Statement statement = null;
		ResultSet serch_id = null;
		String searchNewMinId_aql = "SELECT id From user ORDER BY id";

		int newId = 1;

		try {
			if (!isNameAvailable(name)) {
				return 0;
			}
			connectDB();

			statement = connection.createStatement();
			serch_id = statement.executeQuery(searchNewMinId_aql);

			Set<Integer> userIds = new HashSet<>();
			//userIdsにすべてのIDを入れる
			while (serch_id.next()) {
				userIds.add(serch_id.getInt("id"));
			}
			//新しい最小のIDを探す
			while (userIds.contains(newId)) {
				newId++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//新しいIDをセットしたSQL文
		String insert_sql = "INSERT INTO user VALUES (" + newId + ", '" + name + "', '" + password + "')";
		return executeUpdateSql(insert_sql);
	}
	
	public String getHashPassByName(String name) {
		String hashPass = null;
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String getHashPassByName_sql = "SELECT pass FROM user WHERE name = ?";
		
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(getHashPassByName_sql);
			preparedstatement.setString(1,name);
			result_set = preparedstatement.executeQuery();
			if(result_set.next()) {
				hashPass = result_set.getString("pass");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(result_set != null) result_set.close();
				if(preparedstatement != null) preparedstatement.close();	
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return hashPass;
	}
	public List<String> getAllFileName(String name) {
		Statement statement = null;
		ResultSet result_set = null;
		List<String> fileNamesList = new ArrayList<>();
		String getAllFileName_sql = "SELECT TABLE_NAME "
		        + "FROM INFORMATION_SCHEMA.TABLES "
		        + "WHERE TABLE_NAME LIKE 'DATAOF\\_%\\_" + name + "';";
		
		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(getAllFileName_sql);
			
			while(result_set.next()) {
			    String dataof_filename_user = result_set.getString("TABLE_NAME");
			    String[] fileNameParts = dataof_filename_user.split("_");
			    fileNamesList.add(fileNameParts[1]);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(result_set != null)result_set.close();
				if(statement != null)statement.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		disconnect();
		return fileNamesList;
	}
	
	public void setDataOfFile(FileOfData fileofdata, String fileName, String name){
		Statement statement = null;
		ResultSet result_set = null;
		String getDataOfFile_sql = "SELECT * FROM DATAOF_" + fileName + "_" + name;	
		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(getDataOfFile_sql);
			
			while(result_set.next()) {
				fileofdata.setElement(result_set.getInt("dataId"), result_set.getString("question"), result_set.getString("answer"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(result_set != null) result_set.close();
				if(statement != null) statement.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		disconnect();
	}
	
	public int getDataOfFile_max_min(String fileName, String name, String max_min) {
		Statement statement = null;
		ResultSet result_set = null;
		int DataId_max_min = 0;
		String getDataOfFile_sql;
		if("max".equals(max_min)) {
			getDataOfFile_sql = "SELECT MAX(dataId) AS maxDataId FROM DATAOF_" + fileName + "_" + name;
		}else {
			getDataOfFile_sql = "SELECT MIN(dataId) AS maxDataId FROM DATAOF_" + fileName + "_" + name;
		}
	
		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(getDataOfFile_sql);
			if(result_set.next()) {
				DataId_max_min = result_set.getInt("maxDataId");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(result_set != null) result_set.close();
				if(statement != null) statement.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		disconnect();
		return DataId_max_min;
	}


	public boolean isLogin(String name, String Hashpass) {
		Statement statement = null;
		ResultSet result_set = null;
		String isLogin_sql = "SELECT COUNT(*) FROM user WHERE name = '" + name + "' AND password = '" + Hashpass + "'";
		Boolean isLogin = false;

		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(isLogin_sql);

			int count = 0;
			result_set.next();
			count = result_set.getInt(1);

			if (count == 1) {
				isLogin = true;
			} else {
				isLogin = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result_set != null)
					result_set.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		disconnect();
		return isLogin;
	}

	public boolean isNameAvailable(String name) {
		Statement statement = null;
		ResultSet result_set = null;
		String isNameAvailable_sql = "SELECT COUNT(*) FROM user WHERE name = '" + name + "'";
		Boolean isNameAvailable = false;
		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(isNameAvailable_sql);
			result_set.next();

			if (result_set.getInt(1) == 0) {
				isNameAvailable = true;
			} else {
				isNameAvailable = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result_set != null)
					result_set.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		disconnect();
		return isNameAvailable;
	}

	public void addFileName(String name, String fileName) {
		String addFileData_sql = "CREATE TABLE DATAOF_" + fileName + "_" + name + "("
				+ "dataId INT PRIMARY KEY AUTO_INCREMENT,"
				+ "question varchar(100),"
				+ "answer varchar(100)"
				+ ");";
		int addFileData_int = executeUpdateSql(addFileData_sql);
	}
	
	public void addData(FileOfData fileofdata, String name, String fileName, String question, String answer) {
		String addData_sql = "INSERT INTO DATAOF_" + fileName + "_" + name + " (question, answer) VALUES('"
				+ question + "', '" + answer + "');";
		int addData_int = executeUpdateSql(addData_sql);
		
		int id = serchidByQuestion(name, fileName, question, answer);
		fileofdata.setElement(id, question, answer);
	}
	
	public int serchidByQuestion(String name, String fileName, String question, String answer) {
		Statement statement = null;
		ResultSet result_set = null;
		String serchId_sql = "SELECT dataId FROM DATAOF_" + fileName + "_" + name + " WHERE question = '" + question + "'";
		int id = 0;
		
		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(serchId_sql);
			if(result_set.next()) {
				id = result_set.getInt("dataId");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result_set != null)
					result_set.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		disconnect();
		return id;

	}
	
	public boolean isData(String fileName, String name) {
		Statement statement = null;
		ResultSet result_set = null;
		String isData_sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'DATAOF_" + fileName + "_" + name + "';";
		Boolean isData = false;
		try {
			connectDB();
			statement = connection.createStatement();
			result_set = statement.executeQuery(isData_sql);
			result_set.next();

			if (result_set.getInt(1) == 1) {
				isData = true;
			} else {
				isData = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (result_set != null)
					result_set.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		disconnect();
		return isData;
	}
	public int deleteData(String name, String fileName) {
		String deleteData_sql = "DROP TABLE DATAOF_" + fileName + "_" + name;
		int deleteData_int = 0;

		if(isData(fileName, name) == true) deleteData_int = executeUpdateSql(deleteData_sql);
		return deleteData_int;
	}

	public void deleteFileOfData(FileOfData fileofdata, int id, String name) {
		String deleteFileOfData_sql = "delete from DATAOF_" + fileofdata.getFileName() + "_" + name + " where dataId = '" + id + "'";
		int deleteFileOfData_int = executeUpdateSql(deleteFileOfData_sql);
		
		fileofdata.removeElementById(id);
	}
	
	public void editFileOfData(FileOfData fileofdata, int select_id, String name, String edit_question, String edit_answer) {
		String editFileOfData_sql = "UPDATE DATAOF_" + fileofdata.getFileName() + "_" + name
				 + " SET question = '" + edit_question + "',"
				 + "answer = '" + edit_answer + "' "
				 + "WHERE dataId = " + select_id;
		int editFileOfData_int = executeUpdateSql(editFileOfData_sql);
		fileofdata.editElement(select_id, edit_question, edit_answer);
	}
	public int executeUpdateSql(String sql) {
		Statement statement = null;
		int result = 1;

		try {
			connectDB();
			statement = connection.createStatement();
			result = statement.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		disconnect();
		return result;

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
