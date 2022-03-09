package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
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
import service.TaskService;

public class TaskManager extends JPanel implements ActionListener{
	private JScrollPane scrollPane;
	private UserApp app;
	private JTable table;
	private File dataFile;
	private String dataForEdit = null;
	private int indexForEditANDDelete;
	private NewTaskForm taskForm;
	private JButton editButton,deleteButton, newButton;
	private TaskService taskService;
	private JLabel label;
	private static TaskDao task;
	private List<Task> tasks;
	private DefaultTableModel tableModel;
	private ListSelectionModel select;
	private JButton signoutButton;
	
	public TaskManager() {
		task = new TaskDao();
		taskForm = new NewTaskForm(this);
		taskService = new TaskService(this);
		init();
		initData();
	}
	public TaskManager(UserApp app, String role, File dataFile) {
		this.app = app;
		this.dataFile = dataFile;
		task = new TaskDao();
		taskForm = new NewTaskForm(this);
		taskService = new TaskService(this);
		init();
		initData();
		label.setText(role);
	}
	
	public void initData() {
		tasks = task.findAll(dataFile);
		if(tasks != null) {
			for (Task t : tasks) {
//				System.out.println(t.getTaskname());
//				System.out.println(t.getStatus());
				insertFirst(t);
			}
		}
	}
	
	public void insertFirst(Task t) {
		tableModel.insertRow(0, new Object[] {t.getId(), t.getTaskname(), t.getStartDate(),t.getEndDate(), t.getAddress(),t.getStatus()});
	}
	public void insertLast(Task t) {
		tableModel.addRow(new Object[] {t.getId(), t.getTaskname(), t.getStartDate(),t.getEndDate(), t.getAddress(),t.getStatus()});
	}
	public void init() {
		setBounds(5, 50, 1060, 550);
		setLayout(null);

		
		table = new JTable();
		String[] column = new String[] {
				"ID", "Tasks", "Start Date", "End Date", "Address", "Status"
			};
		Object[][] data = new Object[][] {
			
		};
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableModel = new DefaultTableModel(data,column) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, Object.class, Object.class, Boolean.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
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
		table.getTableHeader().setFont( new Font( "Arial" , Font.BOLD, 16 ));
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
				for(int i =0 ; i < row.length; i++) {
					for(int j =0 ; j < tableModel.getColumnCount(); j++) {
						indexForEditANDDelete = row[i];
						if(j == tableModel.getColumnCount()-1) {
							data =data+ table.getValueAt(row[i], j).toString();
						}else {
							data =data+ table.getValueAt(row[i], j).toString()+"_";
						}
					}
					dataForEdit = data;
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
	
	public boolean saveTask(Task task) {
		return taskService.save(task, dataFile);
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
	
	public void updateData() {
		tasks = task.findAll(dataFile);
		int rowCount = tableModel.getRowCount();

		for (int i = rowCount - 1; i >= 0; i--) {
		    tableModel.removeRow(i);
		}
		if(tasks != null) {
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
	

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (newButton == (JButton)e.getSource()) {
			taskForm.setVisible(true);
		}else if(deleteButton == (JButton) e.getSource()) {
			tableModel.removeRow(indexForEditANDDelete);
			taskService.remove(indexForEditANDDelete+1, dataFile);
			initData();
			updateData();
		}else if(editButton == (JButton) e.getSource()) {
			if(dataForEdit != null) {
				String[] datas = dataForEdit.split("_");
				Task task = new Task(Integer.parseInt(datas[0]), datas[1], LocalDate.parse(datas[2]), LocalDate.parse(datas[3]), datas[4], Boolean.parseBoolean(datas[5]));
				taskForm.setText(task);
				taskForm.setNewTask(task);
				taskForm.setVisible(true);
			}
		}
	}
}
