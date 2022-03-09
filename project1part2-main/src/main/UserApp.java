package main;

import java.io.File;

import javax.swing.JFrame;

public class UserApp extends JFrame {
	protected static TaskManager taskManager;
	private String role;
	private File dataFile;
	protected static Application app;

	public UserApp(Application app, String role, File dataFile) {
		this.role = role;
		this.dataFile = dataFile;
		this.app = app;
		init();
	}
	
	public void init() {
		taskManager = new TaskManager(this, role, dataFile);
		setContentPane(taskManager);
		setSize(1090,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static Application getApp() {
		return app;
	}

	public static void setApp(Application app) {
		UserApp.app = app;
	}
}
