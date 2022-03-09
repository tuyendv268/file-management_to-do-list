package model;

import java.io.File;
import java.io.IOException;

public class User {
	private String username;
	private String password;
	private String role;
	private File dataFile;
	
	public User(String username, String password, String role,String path) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.dataFile = new File(path);
		try {
			dataFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public User(String username, String password, String role, File file) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.dataFile = file;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}
	
	public String toString() {
		return username + "_" + password + "_"+role+"_"+dataFile.getAbsolutePath();
	}
	
}
