package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.User;
import service.UserService;

public class AccountManager extends JPanel implements ActionListener {
	private JScrollPane scrollPane;
	private JTable table;
	private String dataForEdit = null;
	private int indexForEditANDDelete;
	private User userForDelete;
	private JButton editButton, deleteButton, newButton;
	private JLabel label;
	private DefaultTableModel tableModel;
	private ListSelectionModel select;
	private static NewAccountForm accountForm;
	private static UserService userService;
	private JButton signoutButton;
	private UserApp app;

	public AccountManager(UserApp userApp, String role) {
		init();
		this.app = userApp;
		label.setText(role);
		accountForm = new NewAccountForm(this);
		userService = new UserService(this);
		initData();
	}

	public void initData() {
		System.out.println("Init data");
		List<User> users;
		try {
			users = userService.findAll();
			if (users != null) {
				for (User t : users) {
					insertFirst(t);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertFirst(User t) {
		tableModel.insertRow(0, new Object[] { t.getUsername(), t.getPassword(), t.getRole() });
	}

	public void insertLast(User t) {
		tableModel.addRow(new Object[] { t.getUsername(), t.getPassword(), t.getRole() });
	}

	public void init() {
		setBounds(5, 50, 1060, 550);
		setLayout(null);

		table = new JTable();
		String[] column = new String[] { "Account", "Password", "Role" };
		Object[][] data = new Object[][] {

		};
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableModel = new DefaultTableModel(data, column) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { Object.class, Object.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(400);
		table.getColumnModel().getColumn(0).setMinWidth(400);
		table.getColumnModel().getColumn(0).setMaxWidth(400);
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
		table.getColumnModel().getColumn(1).setMinWidth(400);
		table.getColumnModel().getColumn(1).setMaxWidth(400);
//		table.getColumnModel().getColumn(2).setPreferredWidth(200);
//		table.getColumnModel().getColumn(2).setMinWidth(200);
//		table.getColumnModel().getColumn(2).setMaxWidth(9999);
		table.getColumnModel().getColumn(2).setMinWidth(300);
		table.getColumnModel().getColumn(2).setMaxWidth(300);
		table.setBounds(5, 5, 1060, 500);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		table.setRowHeight(table.getRowHeight() + 20);
//		table.setEnabled(false);

		table.setCellSelectionEnabled(true);
		select = table.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		select.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String data = "";
				int[] row = table.getSelectedRows();
				int[] column = table.getSelectedColumns();
				for (int i = 0; i < row.length; i++) {
					for (int j = 0; j < tableModel.getColumnCount(); j++) {
						indexForEditANDDelete = row[i];
						if (j == tableModel.getColumnCount() - 1) {
							data = data + table.getValueAt(row[i], j).toString();
						} else {
							data = data + table.getValueAt(row[i], j).toString() + "<s>";
						}
					}
					dataForEdit = data;
					String datas[] = data.split("<s>");
					userForDelete = new User(datas[0], datas[1], datas[2]);
					System.out.println(data);
				}
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 50, 1060, 430);
		scrollPane.setViewportView(table);
		add(scrollPane);

		label = new JLabel();
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(30, 0, 80, 40);
		add(label);

		newButton = new JButton("New Account");
		newButton.setFont(new Font("Arial", Font.BOLD, 15));
		newButton.setBounds(190, 500, 150, 35);
		add(newButton);
		newButton.addActionListener(this);

		deleteButton = new JButton("Delete Account");
		deleteButton.setFont(new Font("Arial", Font.BOLD, 15));
		deleteButton.setBounds(510, 500, 150, 35);
		deleteButton.addActionListener(this);
		add(deleteButton);

		editButton = new JButton("Edit Account");
		editButton.setFont(new Font("Arial", Font.BOLD, 15));
		editButton.setBounds(350, 500, 150, 35);
		add(editButton);
		editButton.addActionListener(this);

		JButton helpButton = new JButton("Help");
		helpButton.setFont(new Font("Arial", Font.BOLD, 15));
		helpButton.setBounds(670, 500, 150, 35);
		add(helpButton);

		signoutButton = new JButton("Sign Out");
		signoutButton.setFont(new Font("Arial", Font.BOLD, 15));
		signoutButton.setBounds(830, 500, 150, 35);
		signoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				app.setVisible(false);
				new Application();
			}
		});
		add(signoutButton);
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public boolean saveUser(User user) {
		return userService.save(user);
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void updateData() {
		List<User> users;
		try {
			users = userService.findAll();
			int rowCount = tableModel.getRowCount();

			for (int i = rowCount - 1; i >= 0; i--) {
				tableModel.removeRow(i);
			}
			if (users != null) {
				for (User t : users) {
					insertFirst(t);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getIndex() {
		return this.indexForEditANDDelete;
	}

	public static UserService getUserService() {
		return userService;
	}

	public static void setUserService(UserService userService) {
		AccountManager.userService = userService;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (newButton == (JButton) e.getSource()) {
			accountForm.setVisible(true);
		} else if (deleteButton == (JButton) e.getSource()) {
			tableModel.removeRow(indexForEditANDDelete);
			userService.remove(userForDelete);
			userForDelete = null;
			System.out.println("delete : " + indexForEditANDDelete);
		} else if (editButton == (JButton) e.getSource()) {
			if (dataForEdit != null) {
				String[] datas = dataForEdit.split("<s>");
				User user = new User(datas[0], datas[1], datas[2]);
				accountForm.setText(user);
//				taskForm.setNewTask(task);
				accountForm.getAccountName().setEditable(false);
				accountForm.setVisible(true);

			}
		}
	}
}
