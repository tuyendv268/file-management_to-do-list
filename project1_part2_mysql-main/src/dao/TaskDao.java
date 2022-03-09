package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Task;
import model.User;

public class TaskDao {
	UserDao userDao;
	public TaskDao() {
		userDao = new UserDao();
	}
	

	public boolean saveTask(Task task, User user) throws ClassNotFoundException, SQLException {
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		
		int id = userDao.findUserId(user.getUsername());
		String sql = "Insert into task(taskName,startTime,endTime,address,status,userId) values('"
						+task.getTaskname()+"','"
						+task.getStartDate()+"','"+task.getEndDate()+"','"
						+task.getAddress()+"',"+(task.getStatus() ? 1 : 0)+","+id+")";
		
		statement.executeUpdate(sql);
		return true;
	}
	
	public Task findTask(String name)  {
		return null;
	}
	
	
	
	public Task findTask(int id)  {
		return null;
	}
	
	public void editTask(Task task) throws ClassNotFoundException, SQLException  {
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "Update task set taskName='"+task.getTaskname()+"', startTime='"+task.getStartDate()
		+"', endTime='"+task.getEndDate()+"', address='"+task.getAddress()+"', status=" + (task.getStatus()?1:0)+", isWarned=" + (task.getWarning()?1:0)
		+" where taskId = '"+task.getId()+"'";
		statement.executeUpdate(sql);
	}
	
	public void removeTask(Task task) throws ClassNotFoundException, SQLException {
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "DELETE FROM task WHERE taskId = "+"\"" + task.getId()+"\"";
		statement.executeUpdate(sql);
	}
	
	public List<Task> findAll(User user) throws ClassNotFoundException, SQLException  {
		List<Task> tasks = new ArrayList<>();
		
		Connection connection = MySQLConnUtils.getMySQLConnection();
		Statement statement = connection.createStatement();
		String sql = "select taskId, taskName, startTime, endTime, address,status, isWarned\r\n"
				+ "from (task inner join user \r\n"
				+ "on task.userId = user.userId)\r\n"
				+ "where user.userName= \""+user.getUsername()+"\"";
		ResultSet rs = statement.executeQuery(sql);
		
		while(rs.next()) {
			int id = rs.getInt(1);
			String taskName = rs.getString(2);
			Timestamp startTime = rs.getTimestamp(3);
			Timestamp endTime = rs.getTimestamp(4);
			String address = rs.getString(5);
			Boolean status = rs.getBoolean(6);
			Boolean isWarned = rs.getBoolean(7);
//			System.out.println("----------");
//			System.out.println(taskName);
//			System.out.println(address);
//			System.out.println(status);
			tasks.add(new Task(id, taskName, startTime, endTime, address, status,isWarned));
		}
		if(tasks.size() != 0) {
			return tasks;
		}
		return null;
	}
}
