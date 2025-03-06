package dataAccessObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.DataOfFile;

public class DataOfFileDAO {
	DAO dao = new DAO();
	
	public void setDataOfFile(DataOfFile dataOfFile, int fileId){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String getDataOfFileSql = "SELECT * FROM file_contents WHERE file_id = ?";	
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getDataOfFileSql);
			preparedStatement.setInt(1, fileId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				dataOfFile.setElement(resultSet.getInt("file_content_id"), resultSet.getString("question"), resultSet.getString("answer"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
	}
	
	public int getDataOfFileMaxMin(int fileId, String maxMin) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int dataIdMaxMin = 0;
		String getDataOfFileSql;
		if("max".equals(maxMin)) {
			getDataOfFileSql = "SELECT MAX(file_content_id) AS maxMinDataId FROM file_contents WHERE file_id = ?";
		}else {
			getDataOfFileSql = "SELECT MIN(file_content_id) AS maxMinDataId FROM file_contents WHERE file_id = ?";
		}
	
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getDataOfFileSql);
			preparedStatement.setInt(1, fileId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				dataIdMaxMin = resultSet.getInt("maxMinDataId");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}		
		return dataIdMaxMin;
	}

	
	public void addData(DataOfFile dataOfFile, int fileId, String question, String answer) {
		PreparedStatement preparedStatement = null;
		String addDataSql = "INSERT INTO file_contents (file_id, question, answer) VALUES(?, ?, ?)";
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(addDataSql);
			preparedStatement.setInt(1, fileId);
			preparedStatement.setString(2, question);
			preparedStatement.setString(3, answer);
			int addDataInt = preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
		int id = getId(fileId, question, answer);
		dataOfFile.setElement(id, question, answer);
	}
	
	public int getId(int fileId, String question, String answer) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String getIdSql = "SELECT file_content_id FROM file_contents WHERE file_id = ? AND question = ? AND answer = ?";
		int id = 0;
		
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getIdSql);
			preparedStatement.setInt(1, fileId);
			preparedStatement.setString(2,  question);
			preparedStatement.setString(3, answer);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				id = resultSet.getInt("file_content_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return id;

	}

	public void deleteDataOfFile(DataOfFile dataOfFile, int selectId) {
		PreparedStatement preparedStatement = null;
		String deleteDataOfFileSql = "delete from file_contents where file_content_id = ?";
		int deleteDataOfFileInt = 0;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(deleteDataOfFileSql);
			preparedStatement.setInt(1, selectId);
			deleteDataOfFileInt = preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
		dataOfFile.removeElementById(selectId);
	}
	
	public void editFileOfData(DataOfFile dataOfFile, int selectId, String editQuestion, String editAnswer) {
		PreparedStatement preparedStatement = null;
		String editFileOfDataSql = "UPDATE file_contents SET question = ?,answer = ? WHERE file_content_id = ?";
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(editFileOfDataSql);
			preparedStatement.setString(1, editQuestion);
			preparedStatement.setString(2, editAnswer);
			preparedStatement.setInt(3, selectId);
			int editFileOfDataInt = preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
		dataOfFile.editElement(selectId, editQuestion, editAnswer);
	}

}
