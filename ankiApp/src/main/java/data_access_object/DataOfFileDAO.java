package data_access_object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.DataOfFile;

public class DataOfFileDAO {
	DAO dao = new DAO();
	Connection connection = dao.getConnection();
	
	public void setDataOfFile(DataOfFile dataoffile, String fileName, String name){
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String getDataOfFile_sql = "SELECT * FROM " + tableName;	
		try {
			dao.connectDB();
			preparedstatement = connection.prepareStatement(getDataOfFile_sql);
			result_set = preparedstatement.executeQuery();
			
			while(result_set.next()) {
				dataoffile.setElement(result_set.getInt("dataId"), result_set.getString("question"), result_set.getString("answer"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
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
			dao.connectDB();
			preparedstatement = connection.prepareStatement(getDataOfFile_sql);
			result_set = preparedstatement.executeQuery();
			if(result_set.next()) {
				DataId_max_min = result_set.getInt("maxDataId");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
		}		
		return DataId_max_min;
	}

	
	public void addData(DataOfFile dataoffile, String name, String fileName, String question, String answer) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + dataoffile.getFileName() + "_" + name;
		String addData_sql = "INSERT INTO " + tableName + " (question, answer) VALUES(?, ?)";
		try {
			dao.connectDB();
			preparedstatement = connection.prepareStatement(addData_sql);
			preparedstatement.setString(1, question);
			preparedstatement.setString(2, answer);
			int addData_aql = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement);
			dao.disconnect();
		}
		int id = serchIdByQuestion(name, fileName, question, answer);
		dataoffile.setElement(id, question, answer);
	}
	
	public int serchIdByQuestion(String name, String fileName, String question, String answer) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String tableName = "DATAOF_" + fileName + "_" + name;
		String serchId_sql = "SELECT dataId FROM " + tableName + " WHERE question = ?";
		int id = 0;
		
		try {
			dao.connectDB();
			preparedstatement = connection.prepareStatement(serchId_sql);
			preparedstatement.setString(1,  question);
			result_set = preparedstatement.executeQuery();
			while(result_set.next()) {
				id = result_set.getInt("dataId");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
		}
		return id;

	}

	public void deleteFileOfData(DataOfFile dataoffile, int id, String name) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + dataoffile.getFileName() + "_" + name;
		String deleteFileOfData_sql = "delete from " + tableName + " where dataId = ?";
		int deleteFileOfData_int = 0;
		try {
			dao.connectDB();
			preparedstatement = connection.prepareStatement(deleteFileOfData_sql);
			preparedstatement.setInt(1, id);
			deleteFileOfData_int = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement);
			dao.disconnect();
		}
		dataoffile.removeElementById(id);
	}
	
	public void editFileOfData(DataOfFile dataoffile, int select_id, String name, String edit_question, String edit_answer) {
		PreparedStatement preparedstatement = null;
		String tableName = "DATAOF_" + dataoffile.getFileName() + "_" + name;
		String editFileOfData_sql = "UPDATE " + tableName + " SET question = ?,answer = ? WHERE dataId = ?";
		try {
			dao.connectDB();
			preparedstatement = connection.prepareStatement(editFileOfData_sql);
			preparedstatement.setString(1, edit_question);
			preparedstatement.setString(2, edit_answer);
			preparedstatement.setInt(3, select_id);
			int editFileOfData_int = preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement);
			dao.disconnect();
		}
		dataoffile.editElement(select_id, edit_question, edit_answer);
	}

}
