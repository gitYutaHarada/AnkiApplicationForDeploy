package dataAccessObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
	DAO dao = new DAO();
	
	public List<String> getAllFileName(String name) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> fileNamesList = new ArrayList<>();
		String getAllFileNameSql = "SELECT TABLE_NAME "
		        + "FROM INFORMATION_SCHEMA.TABLES "
		        + "WHERE TABLE_NAME LIKE ?";
		
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getAllFileNameSql);
			preparedStatement.setString(1,  "DATAOF\\_%\\_" + name);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
			    String dataOfFileNameUser = resultSet.getString("TABLE_NAME");
			    String[] fileNameParts = dataOfFileNameUser.split("_");
			    fileNamesList.add(fileNameParts[1]);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return fileNamesList;
	}
	
	public void addFileName(String name, String fileName) {
		PreparedStatement preparedStatement = null;
		int addFileDataInt = 0;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String addFileDataSql = "CREATE TABLE " + tableName + "("
				+ "data_id INT PRIMARY KEY AUTO_INCREMENT,"
				+ "question varchar(100),"
				+ "answer varchar(100)"
				+ ");";
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(addFileDataSql);
			addFileDataInt = preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
	}
	
	public int deleteFile(String name, String fileName) {
		PreparedStatement preparedStatement = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String deleteDataSql = "DROP TABLE " + tableName;
		int deleteDataInt = 0;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(deleteDataSql);
			System.out.println("isFile=" + isFile(fileName, name));
			if(isFile(fileName, name)) {
				deleteDataInt = preparedStatement.executeUpdate();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
		return deleteDataInt;
	}
	
	
	public boolean isFile(String fileName, String name) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String isDataSql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
		Boolean isData = false;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(isDataSql);
			preparedStatement.setString(1, "DATAOF_" + fileName + "_" + name);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();

			if (resultSet.getInt(1) == 1) {
				isData = true;
			} else {
				isData = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return isData;
	}

	
}
