package service;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import dao.TaskDao;
import main.TaskManager;
import model.Task;
import model.User;

public class TaskService {
	private TaskDao taskDao;
	private static TaskManager taskManager;
	private List<Task> tasks;
	
	public TaskService(TaskManager taskManager) {
		this.taskManager = taskManager;
		this.taskDao = new TaskDao();
	}
	
	public void remove(Task task) {
		try {
			taskDao.removeTask(task);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean editTask(Task task, User user) {
		int index = 1;
		try {
			tasks = taskDao.findAll(user);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(task.getStartDate().after(task.getEndDate())) {
			System.out.println("Start time if after End time");
			JOptionPane.showMessageDialog(taskManager, "Start time after End time",
		               "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(tasks != null) {
			for (Task t : tasks) {
				index ++;
				if((task.getStartDate().after(t.getStartDate()) &&task.getStartDate().before(t.getEndDate()))
				 ||(task.getEndDate().after(t.getStartDate()) && task.getEndDate().before(t.getEndDate()))
				 ||(task.getStartDate().before(t.getStartDate())&& task.getEndDate().after(t.getEndDate()))) {
					JOptionPane.showMessageDialog(taskManager, "The task you just enter has same time with another",
				               "Warning", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		try {
			taskDao.editTask(task);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean save(Task task, User user) {
		int index = 1;
		try {
			tasks = taskDao.findAll(user);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(task.getStartDate().after(task.getEndDate())) {
			System.out.println("Start time if after End time");
			JOptionPane.showMessageDialog(taskManager, "Start time after End time",
		               "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(tasks != null) {
			for (Task t : tasks) {
				index ++;
				if((task.getStartDate().after(t.getStartDate()) &&task.getStartDate().before(t.getEndDate()))
				 ||(task.getEndDate().after(t.getStartDate()) && task.getEndDate().before(t.getEndDate()))
				 ||(task.getStartDate().before(t.getStartDate())&& task.getEndDate().after(t.getEndDate()))) {
					JOptionPane.showMessageDialog(taskManager, "The task you just enter has same time with another",
				               "Warning", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		task.setID(index);
		try {
			return taskDao.saveTask(task, user);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	
}
