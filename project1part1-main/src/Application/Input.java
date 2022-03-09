/*
 * Classname
*
 * Version info
*
* Copyright notice 
*/

package Application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
/**
 * This class is used to get data (name of file, folder) when I want creat a new File
 */
public class Input extends JDialog{
	private JButton btnCLose,btnNew;
	private static App app;
	private JPanel contentPane;
	private JTextField tfName;

	public Input(App app) {
		Input.app = app;
		init();
	}
	public void init() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(600, 200, 300, 120);
		contentPane = new JPanel();
		
		JLabel name = new JLabel("Name : ");
		name.setBounds(20, 20, 50, 15);
		contentPane.add(name);
		
		tfName = new JTextField();
		tfName.setBounds(75, 20, 150, 20);
		contentPane.add(tfName);
		tfName.setColumns(10);
		
		btnNew = new JButton("New");
		btnNew.setBounds(100, 50, 80, 21);
		contentPane.add(btnNew);
		
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File newFile = new File(app.getCurrentFile(),tfName.getText());
				try {
					newFile.createNewFile();
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newFile);
					app.getCurrentNode().add(newNode);
					app.update(app.getCurrentNode());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(newFile.getPath());
				setVisible(false);
			}
		});
		
		
		btnCLose = new JButton("Close");
		btnCLose.setBounds(165, 180, 80, 21);
		contentPane.add(btnCLose);
		btnCLose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setVisible(true);
	}
}