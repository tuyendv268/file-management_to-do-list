package main;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AdminApp extends UserApp implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenuItem accountManagerItem, taskManagerItem;
	private File accountFile;
	private static AccountManager accountManager;
	
	public AdminApp(Application app, String role, File dataFile) {
		super(app, role, dataFile);
		initAdminApp();
		accountManager = new AccountManager(this, "admin");
	}
	
	public void initAdminApp() {
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 15));
		menuBar = new JMenuBar();
		menuBar.setBounds(120, 5, 950, 40);
		getContentPane().add(menuBar);
		
		accountManagerItem = new JMenuItem("Account Manager");
		accountManagerItem.setFont(new Font("Arial", Font.BOLD, 15));
		accountManagerItem.addActionListener(this);
		
		taskManagerItem = new JMenuItem("Task Manager");
		taskManagerItem.setFont(new Font("Arial", Font.BOLD, 15));
		taskManagerItem.addActionListener(this);
		
		menuBar.add(accountManagerItem);
		menuBar.add(taskManagerItem);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem)e.getSource();
		System.out.println("Hello world");
		if(source == accountManagerItem) {
			taskManager.setVisible(false);
			getContentPane().remove(menuBar);
			setContentPane(accountManager);
			getContentPane().add(menuBar);
			accountManager.setVisible(true);
		}else if(source == taskManagerItem) {
			accountManager.setVisible(false);
			getContentPane().remove(menuBar);
			setContentPane(taskManager);
			getContentPane().add(menuBar);
			taskManager.setVisible(true);
		}
	}
}
