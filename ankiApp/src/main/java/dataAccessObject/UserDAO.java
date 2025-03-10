package dataAccessObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.UserBean;
import bean.UserInformationBean;
import utils.PasswordUtils;

public class UserDAO {
	DAO dao;
	
	public UserDAO() {
		this.dao = new DAO();
	}


	public UserInformationBean select() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserInformationBean userInfoDto = new UserInformationBean();
		String sql = "SELECT * FROM user";

		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				UserBean userBean = new UserBean();
				userBean.setUserId(resultSet.getInt("user_id"));
				userBean.setName(resultSet.getString("name"));
				userBean.setPassword(resultSet.getString("password"));

				userInfoDto.add(userBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return userInfoDto;

	}
	
	public int createUser(String name, String hashPass) {
		PreparedStatement preparedStatementInsert = null;
		int insertInt = 0;
		String insertSql = "INSERT INTO users (name, hashPass) VALUES(?, ?)";
		try {
			if (!isNameAvailable(name)) {
				return 0;
			}
			dao.connectDB();
			preparedStatementInsert = dao.getConnection().prepareStatement(insertSql);
			preparedStatementInsert.setString(2, name);
			preparedStatementInsert.setString(3, hashPass);
			insertInt = preparedStatementInsert.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(preparedStatementInsert != null) preparedStatementInsert.close();
				dao.disconnect();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//新しいIDをセットしたSQL文
		return insertInt;
	}
	
	public String getHashPassByUserId(int userId) {
		String hashPass = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String getHashPassByNameSql = "SELECT password FROM users WHERE userId = ?";
		
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getHashPassByNameSql);
			preparedStatement.setInt(1,userId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				hashPass = resultSet.getString("password");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return hashPass;
	}	
	
	public boolean isLogin(String name, String pass) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PasswordUtils passwordUtils = new PasswordUtils();
		String inputHashPass = passwordUtils.hashPass(pass);
		String isLoginSql = "SELECT COUNT(*) FROM users WHERE name = ? AND password = ?";
		Boolean isLogin = false;

		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(isLoginSql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, inputHashPass);
			resultSet = preparedStatement.executeQuery();

			int count = 0;
			resultSet.next();
			count = resultSet.getInt(1);
			if (count == 1) {
				isLogin = true;
			} else {
				isLogin = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return isLogin;
	}

	public boolean isNameAvailable(String name) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String isNameAvailableSql = "SELECT COUNT(*) FROM users WHERE name = ?";
		Boolean isNameAvailable = false;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(isNameAvailableSql);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();

			if (resultSet.getInt(1) == 0) {
				isNameAvailable = true;
			} else {
				isNameAvailable = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return isNameAvailable;
	}

	public int getUserId(String name) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String getUserIdSql = "SELECT user_id FROM users WHERE name = ?";
		int userId = 0;
		try {
			dao.connectDB();
			preparedStatement = dao.getConnection().prepareStatement(getUserIdSql);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			userId = resultSet.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedStatement, resultSet);
			dao.disconnect();
		}
		return userId;
	}

}
