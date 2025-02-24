package data_access_object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
	DAO dao = new DAO();
	Connection connection = dao.getConnection();
	
	public List<String> getAllFileName(String name) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		List<String> fileNamesList = new ArrayList<>();
		String getAllFileName_sql = "SELECT TABLE_NAME "
		        + "FROM INFORMATION_SCHEMA.TABLES "
		        + "WHERE TABLE_NAME LIKE ?";
		
		try {
			dao.connectDB();
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
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
		}
		return fileNamesList;
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
			dao.connectDB();
			preparedstatement = connection.prepareStatement(addFileData_sql);
			addFileData_int = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement);
			dao.disconnect();
		}
	}
	
	public int deleteFile(String name, String fileName) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String deleteData_sql = "DROP TABLE " + tableName;
		int deleteData_int = 0;
		try {
			dao.connectDB();
			preparedstatement = connection.prepareStatement(deleteData_sql);
			if(isFile(fileName, name)) {
				deleteData_int = preparedstatement.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement);
			dao.disconnect();
		}
		return deleteData_int;
	}
	
	
	public boolean isFile(String fileName, String name) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String isData_sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
		Boolean isData = false;
		try {
			dao.connectDB();
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
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
		}
		return isData;
	}

	
}
