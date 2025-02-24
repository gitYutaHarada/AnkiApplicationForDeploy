package data_access_object;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import bean.UserBean;
import bean.UserInformationBean;
import utils.PasswordUtils;

public class UserDAO {
	DAO dao;
	
	public UserDAO() {
		this.dao = new DAO();
	}


	public UserInformationBean select() {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		UserInformationBean user_info_dto = new UserInformationBean();
		String sql = "SELECT * FROM user";

		try {
			dao.connectDB();
			preparedstatement = dao.getConnection().prepareStatement(sql);
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
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
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
			dao.connectDB();

			preparedstatement_serchMin = dao.getConnection().prepareStatement(searchNewMinId_aql);
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
			preparedstatement_insert = dao.getConnection().prepareStatement(insert_sql);
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
				dao.disconnect();
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
			dao.connectDB();
			preparedstatement = dao.getConnection().prepareStatement(getHashPassByName_sql);
			preparedstatement.setString(1,name);
			result_set = preparedstatement.executeQuery();
			if(result_set.next()) {
				hashPass = result_set.getString("password");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
		}
		return hashPass;
	}	
	
	public boolean isLogin(String name, String pass) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		PasswordUtils passwordutils = new PasswordUtils();
		String inputHashPass = passwordutils.hashPass(pass);
		String isLogin_sql = "SELECT COUNT(*) FROM user WHERE name = ? AND password = ?";
		Boolean isLogin = false;

		try {
			dao.connectDB();
			preparedstatement = dao.getConnection().prepareStatement(isLogin_sql);
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
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
		}
		return isLogin;
	}

	public boolean isNameAvailable(String name) {
		PreparedStatement preparedstatement = null;
		ResultSet result_set = null;
		String isNameAvailable_sql = "SELECT COUNT(*) FROM user WHERE name = ?";
		Boolean isNameAvailable = false;
		try {
			dao.connectDB();
			preparedstatement = dao.getConnection().prepareStatement(isNameAvailable_sql);
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
			dao.resourcesClose(preparedstatement, result_set);
			dao.disconnect();
		}
		return isNameAvailable;
	}



}
