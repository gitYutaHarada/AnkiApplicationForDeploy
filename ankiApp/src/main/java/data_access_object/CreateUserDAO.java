package data_access_object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bean.FileOfData;
import bean.UserBean;
import bean.UserInformationBean;
import utils.Utils;

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
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		UserInformationBean user_info_dto = new UserInformationBean();
		String sql = "SELECT * FROM user";

		try {
			connectDB();
			preparedstatement = connection.prepareStatement(sql);
			result_set = preparedstatement.executeQuery();

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
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
		return user_info_dto;

	}
	
	public int createUser(String name, String hash_pass) {
		PreparedStatement preparedstatement_serchMin = null;
		PreparedStatement preparedstatement_insert = null;

		ResultSet serch_id_resultSet = null;
		int insert_int = 0;
		String searchNewMinId_aql = "SELECT id From user ORDER BY id";
		String insert_sql = "INSERT INTO user VALUES (?, ?, ?)";


		int newId = 1;

		try {
			if (!isNameAvailable(name)) {
				return 0;
			}
			connectDB();

			preparedstatement_serchMin = connection.prepareStatement(searchNewMinId_aql);
			serch_id_resultSet = preparedstatement_serchMin.executeQuery();

			Set<Integer> userIds = new HashSet<>();
			//userIdsにすべてのIDを入れる
			while (serch_id_resultSet.next()) {
				userIds.add(serch_id_resultSet.getInt("id"));
			}
			//新しい最小のIDを探す
			while (userIds.contains(newId)) {
				newId++;
			}
			preparedstatement_insert = connection.prepareStatement(insert_sql);
			preparedstatement_insert.setInt(1, newId);
			preparedstatement_insert.setString(2, name);
			preparedstatement_insert.setString(3, hash_pass);
			insert_int = preparedstatement_insert.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(serch_id_resultSet != null) serch_id_resultSet.close();
				if(preparedstatement_insert != null) preparedstatement_insert.close();
				if(preparedstatement_serchMin != null) preparedstatement_serchMin.close();
				disconnect();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//新しいIDをセットしたSQL文
		return insert_int;
	}
	
	public String getHashPassByName(String name) {
		String hashPass = null;
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String getHashPassByName_sql = "SELECT password FROM user WHERE name = ?";
		
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(getHashPassByName_sql);
			preparedstatement.setString(1,name);
			result_set = preparedstatement.executeQuery();
			if(result_set.next()) {
				hashPass = result_set.getString("password");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
		return hashPass;
	}
	public List<String> getAllFileName(String name) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		List<String> fileNamesList = new ArrayList<>();
		String getAllFileName_sql = "SELECT TABLE_NAME "
		        + "FROM INFORMATION_SCHEMA.TABLES "
		        + "WHERE TABLE_NAME LIKE ?";
		
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(getAllFileName_sql);
			preparedstatement.setString(1,  "DATAOF\\_%\\_" + name);
			result_set = preparedstatement.executeQuery();
			
			while(result_set.next()) {
			    String dataof_filename_user = result_set.getString("TABLE_NAME");
			    String[] fileNameParts = dataof_filename_user.split("_");
			    fileNamesList.add(fileNameParts[1]);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
		return fileNamesList;
	}
	
	public void setDataOfFile(FileOfData fileofdata, String fileName, String name){
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String getDataOfFile_sql = "SELECT * FROM " + tableName;	
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(getDataOfFile_sql);
			result_set = preparedstatement.executeQuery();
			
			while(result_set.next()) {
				fileofdata.setElement(result_set.getInt("dataId"), result_set.getString("question"), result_set.getString("answer"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
	}
	
	public int getDataOfFile_max_min(String fileName, String name, String max_min) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		int DataId_max_min = 0;
		String getDataOfFile_sql;
		String tableName = "DATAOF_" + fileName + "_" + name;
		if("max".equals(max_min)) {
			getDataOfFile_sql = "SELECT MAX(dataId) AS maxDataId FROM " + tableName;
		}else {
			getDataOfFile_sql = "SELECT MIN(dataId) AS maxDataId FROM " + tableName;
		}
	
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(getDataOfFile_sql);
			result_set = preparedstatement.executeQuery();
			if(result_set.next()) {
				DataId_max_min = result_set.getInt("maxDataId");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}		
		return DataId_max_min;
	}


	public boolean isLogin(String name, String pass) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		Utils utils = new Utils();
		String inputHashPass = utils.hashPass(pass);
		String isLogin_sql = "SELECT COUNT(*) FROM user WHERE name = ? AND password = ?";
		Boolean isLogin = false;

		try {
			System.out.println(inputHashPass);
			connectDB();
			preparedstatement = connection.prepareStatement(isLogin_sql);
			preparedstatement.setString(1, name);
			preparedstatement.setString(2, inputHashPass);
			result_set = preparedstatement.executeQuery();

			int count = 0;
			result_set.next();
			count = result_set.getInt(1);
			System.out.println(count);

			if (count == 1) {
				isLogin = true;
			} else {
				isLogin = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
		return isLogin;
	}

	public boolean isNameAvailable(String name) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String isNameAvailable_sql = "SELECT COUNT(*) FROM user WHERE name = ?";
		Boolean isNameAvailable = false;
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(isNameAvailable_sql);
			preparedstatement.setString(1, name);
			result_set = preparedstatement.executeQuery();
			result_set.next();

			if (result_set.getInt(1) == 0) {
				isNameAvailable = true;
			} else {
				isNameAvailable = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
		return isNameAvailable;
	}

	public void addFileName(String name, String fileName) {
		PreparedStatement preparedstatement = null;
		int addFileData_int = 0;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String addFileData_sql = "CREATE TABLE " + tableName + "("
				+ "dataId INT PRIMARY KEY AUTO_INCREMENT,"
				+ "question varchar(100),"
				+ "answer varchar(100)"
				+ ");";
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(addFileData_sql);
			addFileData_int = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement);
			disconnect();
		}
	}
	
	public void addData(FileOfData fileofdata, String name, String fileName, String question, String answer) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + fileofdata.getFileName() + "_" + name;
		String addData_sql = "INSERT INTO " + tableName + " (question, answer) VALUES(?, ?)";
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(addData_sql);
			preparedstatement.setString(1, question);
			preparedstatement.setString(2, answer);
			int addData_aql = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement);
			disconnect();
		}
		int id = serchidByQuestion(name, fileName, question, answer);
		fileofdata.setElement(id, question, answer);
	}
	
	public int serchidByQuestion(String name, String fileName, String question, String answer) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String serchId_sql = "SELECT dataId FROM " + tableName + " WHERE question = ?";
		int id = 0;
		
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(serchId_sql);
			preparedstatement.setString(1,  question);
			result_set = preparedstatement.executeQuery();
			while(result_set.next()) {
				id = result_set.getInt("dataId");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
		return id;

	}
	
	public boolean isData(String fileName, String name) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String isData_sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
		Boolean isData = false;
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(isData_sql);
			preparedstatement.setString(1, "DATAOF_" + fileName + "_" + name);
			result_set = preparedstatement.executeQuery();
			result_set.next();

			if (result_set.getInt(1) == 1) {
				isData = true;
			} else {
				isData = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resourcesClose(preparedstatement, result_set);
			disconnect();
		}
		return isData;
	}
	public int deleteData(String name, String fileName) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String deleteData_sql = "DROP TABLE " + tableName;
		int deleteData_int = 0;
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(deleteData_sql);
			if(isData(fileName, name) == true) {
				deleteData_int = preparedstatement.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement);
			disconnect();
		}
		return deleteData_int;
	}

	public void deleteFileOfData(FileOfData fileofdata, int id, String name) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + fileofdata.getFileName() + "_" + name;
		String deleteFileOfData_sql = "delete from " + tableName + " where dataId = ?";
		int deleteFileOfData_int = 0;
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(deleteFileOfData_sql);
			preparedstatement.setInt(1, id);
			deleteFileOfData_int = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement);
			disconnect();
		}
		fileofdata.removeElementById(id);
	}
	
	public void editFileOfData(FileOfData fileofdata, int select_id, String name, String edit_question, String edit_answer) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + fileofdata.getFileName() + "_" + name;
		String editFileOfData_sql = "UPDATE " + tableName + " SET question = ?,answer = ? WHERE dataId = ?";
		try {
			connectDB();
			preparedstatement = connection.prepareStatement(editFileOfData_sql);
			preparedstatement.setString(1, edit_question);
			preparedstatement.setString(2, edit_answer);
			preparedstatement.setInt(3, select_id);
			int editFileOfData_int = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			resourcesClose(preparedstatement);
			disconnect();
		}
		fileofdata.editElement(select_id, edit_question, edit_answer);
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
