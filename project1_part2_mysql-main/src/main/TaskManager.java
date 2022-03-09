package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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

import dao.TaskDao;
import model.Task;
import model.User;
import service.TaskService;

public class TaskManager extends JPanel implements ActionListener {
	private JScrollPane scrollPane;
	private UserApp app;
	private User user;
	private Task taskForDelete;
	private JTable table;
	private String dataForEdit = null;
	private int indexForEditANDDelete;
	private NewTaskForm taskForm;
	private JButton editButton, deleteButton, newButton;
	private TaskService taskService;
	private JLabel label;
	private static TaskDao task;
	private List<Task> tasks;
	private DefaultTableModel tableModel;
	private ListSelectionModel select;
	private JButton signoutButton;

	public TaskManager(User user) {
		task = new TaskDao();
		taskForm = new NewTaskForm(this);
		taskService = new TaskService(this);
		init();
		initData(user);
		this.user = user;
	}

	public TaskManager(UserApp app, User user) {
		this.app = app;
		task = new TaskDao();
		taskForm = new NewTaskForm(this);
		taskService = new TaskService(this);
		init();
		initData(user);
		this.user = user;
		label.setText(user.getRole());
	}

	public void initData(User user) {
		try {
			tasks = task.findAll(user);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tasks != null) {
			for (Task t : tasks) {
				insertFirst(t);
			}
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void insertFirst(Task t) {
		tableModel.insertRow(0, new Object[] { t.getId(), t.getTaskname(), t.getStartDate(), t.getEndDate(),
				t.getAddress(), t.getStatus() });
	}

	public void insertLast(Task t) {
		tableModel.addRow(new Object[] { t.getId(), t.getTaskname(), t.getStartDate(), t.getEndDate(), t.getAddress(),
				t.getStatus() });
	}

	public void init() {
		setBounds(5, 50, 1060, 550);
		setLayout(null);

		table = new JTable();
		String[] column = new String[] { "ID", "Tasks", "Start Date", "End Date", "Address", "Status" };
		Object[][] data = new Object[][] {

		};
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableModel = new DefaultTableModel(data, column) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { Object.class, Object.class, Object.class, Object.class, Object.class,
					Boolean.class };

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
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setMinWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(65);
		table.getColumnModel().getColumn(2).setMinWidth(65);
		table.getColumnModel().getColumn(3).setPreferredWidth(65);
		table.getColumnModel().getColumn(3).setMinWidth(65);
		table.getColumnModel().getColumn(4).setPreferredWidth(15);
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
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
					System.out.println(data);
					String[] datas = data.split("<s>");
					int id = Integer.parseInt(datas[0]);
					String taskname = datas[1];
					Date start;
					try {
						System.out.println(datas[2]);
						start = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(datas[2]);
						Timestamp startDate = new Timestamp(start.getTime());
						Date end = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(datas[3]);
						Timestamp endDate = new Timestamp(end.getTime());
						String address = datas[4];
						Boolean status = Boolean.parseBoolean(datas[5]);
						taskForDelete = new Task(id, taskname, startDate, endDate, address, status, false);

					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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

		newButton = new JButton("New Task");
		newButton.setFont(new Font("Arial", Font.BOLD, 15));
		newButton.setBounds(190, 500, 150, 35);
		add(newButton);
		newButton.addActionListener(this);

		deleteButton = new JButton("Delete Task");
		deleteButton.setFont(new Font("Arial", Font.BOLD, 15));
		deleteButton.setBounds(510, 500, 150, 35);
		deleteButton.addActionListener(this);
		add(deleteButton);

		editButton = new JButton("Edit Task");
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

	public boolean saveTask(Task task, User user) {
		return taskService.save(task, user);
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public static TaskDao getTask() {
		return task;
	}
	
	

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public static void setTask(TaskDao task) {
		TaskManager.task = task;
	}

	public void updateData() {
		try {
			tasks = task.findAll(this.user);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int rowCount = tableModel.getRowCount();

		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		if (tasks != null) {
			for (Task t : tasks) {
				insertFirst(t);
			}
		}
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (newButton == (JButton) e.getSource()) {
			taskForm.setNewTask(null);
			taskForm.setVisible(true);
		} else if (deleteButton == (JButton) e.getSource()) {
			tableModel.removeRow(indexForEditANDDelete);
			taskService.remove(taskForDelete);
			taskForDelete = null;
			updateData();
		} else if (editButton == (JButton) e.getSource()) {
			if (dataForEdit != null) {
				String[] datas = dataForEdit.split("<s>");
				taskForm.setText(taskForDelete);
				taskForm.setNewTask(taskForDelete);
				taskForm.setVisible(true);
				dataForEdit = null;
			}
		}
	}
}
