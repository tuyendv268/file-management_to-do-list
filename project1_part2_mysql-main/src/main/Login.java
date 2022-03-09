package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

import model.User;
import service.UserService;

public class Login extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1060222375776258433L;
	private Application app;
	private JTextField password;
	private JTextField username;
	private UserApp userapp;
	private UserService userService;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the application.
	 */
	public Login(Application app) {
		this.app = app;
		userService = new UserService();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton login = new JButton("Login");
		login.setForeground(Color.DARK_GRAY);
		login.setFont(new Font("Arial", Font.BOLD, 16));
		login.setBounds(360, 320, 120, 35);
		getContentPane().add(login);
		
		login.addActionListener(this);  
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		password.setBounds(300, 260, 250, 40);
		getContentPane().add(password);
		password.setColumns(10);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 16));
		username.setBounds(300, 200, 250, 40);
		getContentPane().add(username);
		username.setColumns(10);
		
		JLabel usernamelabel = new JLabel("User Name : ");
		usernamelabel.setFont(new Font("Arial", Font.BOLD, 16));
		usernamelabel.setBounds(150, 200, 100, 35);
		getContentPane().add(usernamelabel);
		
		JLabel passwordlabel = new JLabel("Password : ");
		passwordlabel.setFont(new Font("Arial", Font.BOLD, 16));
		passwordlabel.setBounds(151, 260, 100, 35);
		getContentPane().add(passwordlabel);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Sitka Subheading", Font.BOLD, 40));
		lblNewLabel.setBounds(350, 100, 120, 60);
		getContentPane().add(lblNewLabel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String name = username.getText().toString();
		String pass = password.getText().toString();
		
		User user = userService.login(name, pass);
		if(user != null) {
			if(user.getRole().equals("user")) {
				userapp = new UserApp(app, user);
				app.setUserApp(userapp);
				userapp.setVisible(true);
			}else if(user.getRole().equals("admin")) {
				userapp = new AdminApp(app,user);
				app.setUserApp(userapp);
				userapp.setVisible(true);
			}
			this.setVisible(false);
		}else {
			JOptionPane.showMessageDialog(this, "No exist account",
		               "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
	}
}
