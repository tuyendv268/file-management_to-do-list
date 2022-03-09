package service;

import java.sql.SQLException;
import java.util.List;

import dao.UserDao;
import main.AccountManager;
import model.User;

public class UserService {
	private UserDao userDao;
	private AccountManager accountManager;
	
	public UserService(AccountManager accountManager) {
		userDao = new UserDao();
		this.accountManager = accountManager;
	}
	
	public UserService() {
		userDao = new UserDao();
	}
	
	public User login(String name, String password) {
		User user;
		try {
			user = userDao.findUser(name);
			if(user != null) {
				if(compare(user, name, password)) {
					return user;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;			
	}
	
	public void remove(User user) {
		try {
			userDao.removeUser(user);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<User> findAll() throws ClassNotFoundException, SQLException{
		return userDao.findAll();
	}
	
	public void editAccount( User user) {
		try {
			userDao.editUser(user);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean save(User user) {
		List<User> users;
		try {
			users = userDao.findAll();
			for (User user2 : users) {
				if(user.getUsername().equals(user2.getUsername())) {
					return false;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			return userDao.saveUser(user);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean compare(User user, String name, String password) {
		if(user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
}
