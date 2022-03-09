package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


import Application.App;

import java.awt.FlowLayout;
import java.io.File;
/*
 * This class is used to list the file in particular Folder on the left part of
 * Application
 */
public class ListFile extends JPanel{
	private static App app;
	private static int mode = 1;

	public ListFile(App app) {
		ListFile.app = app;
		init();
	}
	
	public void init() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		setBounds(350, 45, 470, 440);
	}

	public static void setMode(int i){
		ListFile.mode = i;
	}
	
	public void RenderAllFileInFolder(File file) {
		RenderFile renderFile;
		if(file != null){
			File[] temp = file.listFiles();
			
			if(mode == 0){
				for (File file2 : temp) {
					renderFile = new RenderFile(file2);
					add(renderFile);
					renderFile.addMouseListener(App.mEvent);
				}
			}else if(mode == 1){
				DefaultListModel<File> l1 = new DefaultListModel<>();   
				for (File file2 : temp) {
					l1.addElement(file2); 
				}  
				JList<File> list = new JList<>(l1); 

				list.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						JList list = (JList)evt.getSource();
						if (evt.getClickCount() == 2) {
							File file = (File)list.getSelectedValue();
							app.setCurrentFile(file);
							if(file.isFile()){
								app.openFile();
							}else{
								app.getListFile().removeAll();// delete current folder or file in panel
								app.getListFile().revalidate();
								app.getListFile().repaint();
								app.getListFile().RenderAllFileInFolder(file);
							}
							System.out.println(file.getName());
						}
					}
				});
				JScrollPane scrollPane = new JScrollPane(list) ;
				scrollPane.setPreferredSize(getSize());
				list.setPreferredSize(scrollPane.getPreferredSize());
				list.setBorder(new EmptyBorder(10,10, 10, 10));
				list.setCellRenderer(new FileListCellRenderer());
				this.add(scrollPane);
				// this.add(list);
			}
		}
		
	}
	public App getApp() {
		return ListFile.app;
	}
}
