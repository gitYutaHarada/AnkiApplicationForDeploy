package dataAccessObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class FileDAO {
	DAO dao = new DAO();
	
	public HashMap<Integer, String> getAllFileName(int userId) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		HashMap<Integer, String> fileNamesMap = new HashMap<>();
		String getAllFileNameSql = "SELECT file_id, file_name FROM files WHERE user_id = ?";
		
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getAllFileNameSql);
			preparedStatement.setInt(1,  userId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				fileNamesMap.put(resultSet.getInt("file_id"), resultSet.getString("file_name"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return fileNamesMap;
	}
	
	public void addFileName(int userId, String fileName) {
		PreparedStatement preparedStatement = null;
		int addFileDataInt = 0;
		String addFileDataSql = "INSERT INTO files VALUES(?, ?)";
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(addFileDataSql);
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, fileName);
			addFileDataInt = preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
	}
	
	public int deleteFile(int fileId) {
		PreparedStatement preparedStatement = null;
		String deleteFileSql = "DLETE FROM files where file_id = ?";
		int deleteDataInt = 0;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(deleteFileSql);
			preparedStatement.setInt(1, fileId);
			if(isFile(fileId)) {
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
	
	
	public boolean isFile(int fileId) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String isDataSql = "SELECT COUNT(*) FROM files WHERE file_id = ?";
		Boolean isData = false;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(isDataSql);
			preparedStatement.setInt(1, fileId);
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
		}
		return isData;
	}

	public int getFileIdByFileName(String fileName) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String getFileIdByFileNameSql = "SELECT file_id FROM files WHERE file_name = ?";
		int fileId = 0;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getFileIdByFileNameSql);
			preparedStatement.setString(1, fileName);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			fileId = resultSet.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return fileId;
	}
}
