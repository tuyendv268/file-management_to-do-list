package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDao {

	public UserDao() {

	}

	public User findUser(String userName) throws ClassNotFoundException, SQLException {

		// Lấy ra đối tượng Connection kết nối vào DB.
		Connection connection = MySQLConnUtils.getMySQLConnection();

		// Tạo đối tượng Statement.
		Statement statement = connection.createStatement();

		String sql = "Select * from user";

		// Thực thi câu lệnh SQL trả về đối tượng ResultSet.
		ResultSet rs = statement.executeQuery(sql);


		while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
			int id = rs.getInt(1);
			String username = rs.getString(2);
			String password = rs.getString(3);
			String role = rs.getString(4);
			if(userName.equals(username)) {
//				System.out.println("--------------------");
//				System.out.println("EmpId:" + id);
//				System.out.println("EmpNo:" + userName);
//				System.out.println("EmpName:" + password);
				User user = new User(username, password, role);
				return user;
			}
		}
		// Đóng kết nối
		connection.close();
		return null;
	}
	
	public int findUserId(String userName) throws ClassNotFoundException, SQLException {
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "Select * from user";
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt(1);
			String username = rs.getString(2);
			if(userName.equals(username)) {
				return id;
			}
		}
		connection.close();
		return -1;
	}

	public boolean saveUser(User user) throws ClassNotFoundException, SQLException {
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "Insert into user(userName,password,userRole) values('"+user.getUsername()+"','"+user.getPassword()+"','"+user.getRole()+"')";
		
		statement.executeUpdate(sql);
		return true;
	}

	public void removeUser(User user) throws ClassNotFoundException, SQLException {
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "DELETE FROM user WHERE userName = "+"\"" + user.getUsername()+"\"";
		statement.executeUpdate(sql);
	}

	public void editUser(User user) throws ClassNotFoundException, SQLException {
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "Update user set password='"+user.getPassword()+"', userRole='"+user.getRole()+"' where userName = '"+user.getUsername()+"'";
		
		statement.executeUpdate(sql);
	}

	public List<User> findAll() throws ClassNotFoundException, SQLException {
		List<User> users = new ArrayList<>();
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "Select * from user";
		
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			String userName = rs.getString(2);
			String password = rs.getString(3);
			String role = rs.getString(4);
			
			users.add(new User(userName, password, role));
		}
		if(users.size() != 0) {
			return users;
		}
		return null;
	}
}
