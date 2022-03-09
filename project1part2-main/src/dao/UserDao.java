package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDao {
	private InputStreamReader reader;
	private OutputStreamWriter writer;
	
	public UserDao() {
	
	}
	
	public User findUser(String username) {
		User user = null;
		String data = "";
		int c;
		try {
			reader = new FileReader("src/data/accountdata.txt");
			try {
				while((c = reader.read()) != -1) {
					if(c == (int)'\n') {
						String[] temp = data.split("_");
						if(temp[0].equals(username)) {
							System.out.print("account :"+temp[3].substring(0, temp[3].length()));
							user = new User(temp[0], temp[1], temp[2], new File(temp[3].substring(0, temp[3].length())));
							return user;
						}
						data = "";
					}else {
						data = data + (char)c;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	public boolean saveUser(User user) {
		try {
			writer = new OutputStreamWriter(new FileOutputStream("src/data/accountdata.txt", true), StandardCharsets.UTF_8);
			String text = user.getUsername().toString() + "_"+user.getPassword()
			+"_"+user.getRole()+"_"+user.getDataFile().getAbsolutePath()+"\n";
			try {
				writer.write(text);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void removeUser(int index) {
		String data = "";
		File filetemp = new File("src/data/temp.txt");
		File filetask = new File("src/data/accountdata.txt");
		File datafile = null;
		int c, j = 0;
		try {
			reader = new InputStreamReader(new FileInputStream(filetask), StandardCharsets.UTF_8);
			writer = new OutputStreamWriter(new FileOutputStream(filetemp), StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					data = data + (char)c;
					if(c == (int)'\n') {
						if(j != index) {
							writer.write(data);
						}else {
							String[] datas = data.split("_");
							System.out.println("delete : "+ data);
							datafile =  new File(datas[3]);
						}
						j++;
						data = "";
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					writer.close();
					reader.close();
					filetask.delete();
					filetemp.renameTo(filetask);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void editUser(int index, User user)  {
		String data = "";
		File filetemp = new File("src/data/temp.txt");
		File accountFile = new File("src/data/accountdata.txt");
		System.out.println("Edit : "+ index);
		int c,j = 0;
		String username;
		try {
			reader = new InputStreamReader(new FileInputStream(accountFile), StandardCharsets.UTF_8);
			writer = new OutputStreamWriter(new FileOutputStream(filetemp), StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					data = data + (char)c;
					if(c == (int)'\n') {
						username = data.split("_")[0];
						if(j == index) {
							writer.write(user.toString() + "\n");
							System.out.println("write edit: " + user.toString());
						}else {
							writer.write(data);
							System.out.println("write : "+ data);
						}
						j++;
						data = "";
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					writer.close();
					reader.close();
					accountFile.delete();
					filetemp.renameTo(accountFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public List<User> findAll()  {
		String data = "";
		List<User> users = new ArrayList<>();
		int c;
		
		try {
			reader = new InputStreamReader(new FileInputStream("src/data/accountdata.txt"), StandardCharsets.UTF_8);
			try {
				while((c = reader.read()) != -1) {
					if(c == (int)'\n') {
						String[] temp = data.split("_");
						String userName= temp[0];
						String password = temp[1];
						String role = temp[2];
						File file = new File(temp[3]);
						System.out.println("path" + file.getAbsolutePath());
						users.add(0,new User(userName, password, role, file));
						data = "";
					}else {
						data = data + (char)c;
					}
				}
				return users;
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
