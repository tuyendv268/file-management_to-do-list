package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.Task;

public class NewTaskForm extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static TaskManager taskManager;
	private JTextField taskNameText;
	private JTextField startDateText;
	private JTextField endDateText;
	private Task newTask = null;
	private JTextField addressText;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private JLabel lblFormYymmdd;
	private JRadioButton rdbtnNewRadioButton;

	/**
	 * Create the application.
	 */
	public NewTaskForm(TaskManager taskManager) {
		NewTaskForm.taskManager = taskManager;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(null);

		taskNameText = new JTextField();
		taskNameText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		taskNameText.setBounds(115, 30, 250, 35);
		getContentPane().add(taskNameText);
		taskNameText.setColumns(10);

		startDateText = new JTextField();
		startDateText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		startDateText.setColumns(10);
		startDateText.setBounds(115, 100, 250, 35);
		getContentPane().add(startDateText);

		endDateText = new JTextField();
		endDateText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		endDateText.setColumns(10);
		endDateText.setBounds(115, 180, 250, 35);
		getContentPane().add(endDateText);

		addressText = new JTextField();
		addressText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addressText.setColumns(10);
		addressText.setBounds(115, 250, 250, 35);
		getContentPane().add(addressText);

		JLabel taskname = new JLabel("Task : ");
		taskname.setFont(new Font("Tahoma", Font.BOLD, 14));
		taskname.setBounds(15, 30, 90, 30);
		getContentPane().add(taskname);

		JLabel lblStartDate = new JLabel("Start Date :");
		lblStartDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStartDate.setBounds(15, 100, 90, 30);
		getContentPane().add(lblStartDate);

		JLabel lblEndDate = new JLabel("End Date : ");
		lblEndDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEndDate.setBounds(15, 180, 90, 30);
		getContentPane().add(lblEndDate);

		JLabel lblAddress = new JLabel("Address :");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddress.setBounds(15, 250, 90, 30);
		getContentPane().add(lblAddress);

		btnNewButton = new JButton("New");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(173, 348, 120, 35);
		getContentPane().add(btnNewButton);

		lblNewLabel = new JLabel("Form : yyyy-MM-dd HH:mm:ss");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(115, 140, 250, 30);
		getContentPane().add(lblNewLabel);

		lblFormYymmdd = new JLabel("Form : yyyy-MM-dd HH:mm:ss");
		lblFormYymmdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFormYymmdd.setBounds(115, 70, 250, 30);
		getContentPane().add(lblFormYymmdd);

		JLabel lblStatus = new JLabel("Status : ");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatus.setBounds(15, 310, 90, 30);
		getContentPane().add(lblStatus);

		rdbtnNewRadioButton = new JRadioButton("True");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnNewRadioButton.setBounds(115, 310, 103, 32);
		getContentPane().add(rdbtnNewRadioButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = taskNameText.getText().toString();
					Date start = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDateText.getText());
					Timestamp startDate = new Timestamp(start.getTime());
					Date end = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDateText.getText());
					Timestamp endDate = new Timestamp(end.getTime());
					String address = addressText.getText().toString();
					Boolean status = rdbtnNewRadioButton.isSelected();
					if (newTask == null) {
						newTask = new Task(name, startDate, endDate, address, status);
						if (taskManager.saveTask(newTask, taskManager.getUser())) {
							
							while (taskManager.getTableModel().getRowCount() > 0) {
								taskManager.getTableModel().removeRow(0);
							}
							taskManager.initData(taskManager.getUser());
						}
					} else {
						newTask.setTaskname(name);
						newTask.setStartDate(startDate);
						newTask.setEndDate(endDate);
						newTask.setAddress(address);
						newTask.setStatus(status);
						taskManager.getTaskService().editTask(newTask, taskManager.getUser());
						
						taskManager.updateData();
					}
					
					newTask = null;
					setVisible(false);
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}

			}
		});
	}

	public Task getNewTask() {
		return newTask;
	}

	public void setNewTask(Task newTask) {
		this.newTask = newTask;
	}

	public void setText(Task task) {
		taskNameText.setText(task.getTaskname());
		startDateText.setText(task.getStartDate().toString());
		endDateText.setText(task.getEndDate().toString());
		addressText.setText(task.getAddress().toString());
	}
}
