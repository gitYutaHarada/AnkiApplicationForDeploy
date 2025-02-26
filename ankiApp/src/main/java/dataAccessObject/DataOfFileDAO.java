package dataAccessObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.DataOfFile;

public class DataOfFileDAO {
	DAO dao = new DAO();
	
	public void setDataOfFile(DataOfFile dataOfFile, String fileName, String name){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String getDataOfFileSql = "SELECT * FROM " + tableName;	
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getDataOfFileSql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				dataOfFile.setElement(resultSet.getInt("data_id"), resultSet.getString("question"), resultSet.getString("answer"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
	}
	
	public int getDataOfFileMaxMin(String fileName, String name, String maxMin) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int dataIdMaxMin = 0;
		String getDataOfFileSql;
		String tableName = "DATAOF_" + fileName + "_" + name;
		if("max".equals(maxMin)) {
			getDataOfFileSql = "SELECT MAX(data_id) AS maxMinDataId FROM " + tableName;
		}else {
			getDataOfFileSql = "SELECT MIN(data_id) AS maxMinDataId FROM " + tableName;
		}
	
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getDataOfFileSql);
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

	
	public void addData(DataOfFile dataOfFile, String name, String fileName, String question, String answer) {
		PreparedStatement preparedStatement = null;
		String tableName = "DATAOF_" + dataOfFile.getFileName() + "_" + name;
		String addDataSql = "INSERT INTO " + tableName + " (question, answer) VALUES(?, ?)";
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(addDataSql);
			preparedStatement.setString(1, question);
			preparedStatement.setString(2, answer);
			int addDataInt = preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
		int id = serchIdByQuestion(name, fileName, question, answer);
		dataOfFile.setElement(id, question, answer);
	}
	
	public int serchIdByQuestion(String name, String fileName, String question, String answer) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String serchIdSql = "SELECT data_id FROM " + tableName + " WHERE question = ?";
		int id = 0;
		
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(serchIdSql);
			preparedStatement.setString(1,  question);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				id = resultSet.getInt("data_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return id;

	}

	public void deleteFileOfData(DataOfFile dataOfFile, int id, String name) {
		PreparedStatement preparedStatement = null;
		String tableName = "DATAOF_" + dataOfFile.getFileName() + "_" + name;
		String deleteFileOfDataSql = "delete from " + tableName + " where data_id = ?";
		int deleteFileOfDataInt = 0;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(deleteFileOfDataSql);
			preparedStatement.setInt(1, id);
			deleteFileOfDataInt = preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement);
			dao.disconnect();
		}
		dataOfFile.removeElementById(id);
	}
	
	public void editFileOfData(DataOfFile dataOfFile, int selectId, String name, String editQuestion, String editAnswer) {
		PreparedStatement preparedStatement = null;
		String tableName = "DATAOF_" + dataOfFile.getFileName() + "_" + name;
		String editFileOfDataSql = "UPDATE " + tableName + " SET question = ?,answer = ? WHERE data_id = ?";
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
