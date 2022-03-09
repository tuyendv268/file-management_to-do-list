package service;

import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import dao.TaskDao;
import main.TaskManager;
import model.Task;

public class TaskService {
	private TaskDao taskDao;
	private static TaskManager taskManager;
	private List<Task> tasks;
	
	public TaskService(TaskManager taskManager) {
		this.taskManager = taskManager;
		this.taskDao = new TaskDao();
	}
	
	public void remove(int index, File dataFile) {
		taskDao.removeTask(index, dataFile);
	}
	
	public void editTask(Task task, File dataFile) {
		taskDao.editTask(task, dataFile);
	}
	
	public boolean save(Task task, File dataFile) {
		int index = 1;
		tasks = taskDao.findAll(dataFile);
		if(task.getStartDate().isAfter(task.getEndDate())) {
			System.out.println("Start time if after End time");
			return false;
		}
		if(tasks != null) {
			for (Task t : tasks) {
				index ++;
				if((task.getStartDate().isAfter(t.getStartDate()) &&task.getStartDate().isBefore(t.getEndDate()))
				 ||(task.getEndDate().isAfter(t.getStartDate()) && task.getEndDate().isBefore(t.getEndDate()))
				 ||(task.getStartDate().isBefore(t.getStartDate())&& task.getEndDate().isAfter(t.getEndDate()))) {
					JOptionPane.showMessageDialog(taskManager, "The task you just enter has same time with another",
				               "Warning", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		task.setID(index);
		return taskDao.saveTask(task, dataFile);
	}
}
