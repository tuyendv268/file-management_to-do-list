package service;

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
		User user = userDao.findUser(name);
		if(user != null) {
			if(compare(user, name, password)) {
				return user;
			}
		}
		return null;			
	}
	
	public void remove(int index) {
		userDao.removeUser(index);
	}
	
	public List<User> findAll(){
		return userDao.findAll();
	}
	
	public void editAccount(int index, User user) {
		userDao.editUser(index,user);
	}
	
	public boolean save(User user) {
		List<User> users = userDao.findAll();
		for (User user2 : users) {
			if(user.getUsername().equals(user2.getUsername())) {
				return false;
			}
		}
		return userDao.saveUser(user);
	}
	
	public boolean compare(User user, String name, String password) {
		if(user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
}
