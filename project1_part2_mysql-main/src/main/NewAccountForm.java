package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.User;

public class NewAccountForm extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AccountManager accountManager;
	private JTextField accountName;
	private JTextField password;
	private JTextField role;
	private User newUser = null;
	private JButton btnNewButton;
	/**
	 * Create the application.
	 */
	public NewAccountForm(AccountManager accountManager) {
		NewAccountForm.accountManager = accountManager;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(null);
		
		accountName = new JTextField();
		accountName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		accountName.setBounds(115, 30, 250, 35);
		getContentPane().add(accountName);
		accountName.setColumns(10);
		
		password = new JTextField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 14));
		password.setColumns(10);
		password.setBounds(115, 100, 250, 35);
		getContentPane().add(password);
		
		role = new JTextField();
		role.setFont(new Font("Tahoma", Font.PLAIN, 14));
		role.setColumns(10);
		role.setBounds(115, 180, 250, 35);
		getContentPane().add(role);
		
		JLabel accountLabel = new JLabel("Account: ");
		accountLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		accountLabel.setBounds(15, 30, 90, 30);
		getContentPane().add(accountLabel);
		
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLbl.setBounds(15, 100, 90, 30);
		getContentPane().add(passwordLbl);
		
		JLabel rolelbl = new JLabel("Roles :");
		rolelbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		rolelbl.setBounds(15, 180, 90, 30);
		getContentPane().add(rolelbl);
		
		btnNewButton = new JButton("New");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(175, 250, 120, 35);
		getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = accountName.getText().toString();
					String pass = password.getText().toString();
					String user_role = role.getText().toString();
					
					if(newUser == null) {
						newUser = new User(name, pass, user_role);
						if(accountManager.saveUser(newUser)) {
							accountManager.insertLast(newUser);
						}else {
							JOptionPane.showMessageDialog(accountManager, "username was exist","Warning", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						newUser.setUsername(name);
						newUser.setPassword(pass);
						newUser.setRole(user_role);
						AccountManager.getUserService().editAccount(newUser);
						accountManager.updateData();
					}
					
					newUser = null;
					setVisible(false);
					accountName.setEditable(true);
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		});
	}
	
	public JTextField getAccountName() {
		return accountName;
	}

	public void setAccountName(JTextField accountName) {
		this.accountName = accountName;
	}

	public void setText(User user) {
		this.newUser = user;
		accountName.setText(user.getUsername());
		password.setText(user.getPassword());
		role.setText(user.getRole());
	}
}
