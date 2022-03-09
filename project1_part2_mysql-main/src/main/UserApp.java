package main;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Task;
import model.User;

public class UserApp extends JFrame {
	protected static TaskManager taskManager;
	protected static Application app;

	public UserApp(Application app, User user) {
		this.app = app;
		init(user);
		Date currentDate = new Date();

		Runnable checkTask = new Runnable() {
			public void run() {
				List<Task> tasks;
				while(true) {
					try {
						tasks = taskManager.getTaskService().getTaskDao().findAll(user);
						for (Task task : tasks) {
							if (task.getStartDate().compareTo(currentDate) <= 0
									&& task.getEndDate().compareTo(currentDate) >= 0 && task.getWarning() == false) {
								JOptionPane.showMessageDialog(taskManager,
										"Task : " + task.getTaskname() + " is started! \nStartTime: "
										+task.getStartDate()+"\nEndTime: "+task.getEndDate()+"\nAddress: "+task.getAddress(), 
										"Warning",
										JOptionPane.WARNING_MESSAGE);
								task.setWarning(true);;
								taskManager.getTaskService().getTaskDao().editTask(task);
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		};
		Thread thread = new Thread(checkTask);
		thread.start();
	}

	public void init(User user) {
		taskManager = new TaskManager(this, user);
		setContentPane(taskManager);
		setSize(1090, 600);
		setVisible(true);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	try {
					List<Task> tasks = taskManager.getTaskService().getTaskDao().findAll(user);
					for (Task task : tasks) {
						task.setWarning(false);
						taskManager.getTaskService().getTaskDao().editTask(task);
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}finally {
					System.exit(0);
				}
            }
        });
	}

	public static Application getApp() {
		return app;
	}

	public static void setApp(Application app) {
		UserApp.app = app;
	}
}
