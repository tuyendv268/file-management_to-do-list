package Application;
/*
 * Classname
 * 
 * Version info
 *
 * Copyright notice 
*/
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import HandleEvent.Forward_BackwardEvent;
import HandleEvent.HandleMenuOptionEvent;
import HandleEvent.HandleMenuPopupEvent;
import HandleEvent.HandleMouseEvent;
import UI.ListFile;
import UI.MenuOption;
import UI.MenuPopup;
import UI.RenderNameOfNode;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

/*
 * Launch app
 */
public class App extends JFrame{
	private App app_temp;
	public static HandleMouseEvent mEvent;
	public static HandleMenuPopupEvent mActionEvent;
	public static Forward_BackwardEvent mForward_BackwardEvent;
	public static HandleMenuOptionEvent menuOptionEvent;
	public static DefaultTreeModel model;
	private static File currentFile,copyFile;
	private static ArrayList<DefaultMutableTreeNode> forw_backw;
	private static int currentPos;
	private static MenuOption menuOption;
	private JScrollPane treeScrollPane;
	private JTree tree;
	private JPanel panel;
	private JButton backward, forward;
	private File fileRoot;
	private ListFile listFile;
	private DefaultMutableTreeNode root,currentNode;
	private JTextField path;
	
	private MenuPopup menuPopup;
/*
 * 
 */
	public App() {
		mActionEvent = new HandleMenuPopupEvent(this);
		mEvent = new HandleMouseEvent(this);
		mForward_BackwardEvent = new Forward_BackwardEvent(this);
		menuOptionEvent = new HandleMenuOptionEvent(this);
		listFile = new ListFile(this);
		init();
		handleEvent();
	}
/*
 * Initial components drawed in panel
 */
	public void init() {
		forw_backw = new ArrayList<>();
		currentPos = -1;
		fileRoot = new File("C:\\Users\\tuyen.dv\\Documents");
		currentFile = fileRoot;
		root = new DefaultMutableTreeNode(fileRoot);
		root = creatTree(root, fileRoot);
		app_temp = this;
		getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setLayout(null);
		setContentPane(panel);
		
		tree = new JTree(root);
		tree.setCellRenderer(new RenderNameOfNode());
		model = (DefaultTreeModel)tree.getModel();
		treeScrollPane = new JScrollPane(tree);
		treeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		treeScrollPane.setSize(350, 460);
		treeScrollPane.setLocation(2, 50);
		getContentPane().add(treeScrollPane);
		
		menuOption = new MenuOption();
		panel.add(menuOption);
		
		listFile = new ListFile(this);
		if(listFile != null){
			listFile.RenderAllFileInFolder(fileRoot);
		}
	 	panel.add(listFile);
	 	
	 	path = new JTextField();
	 	path.setBounds(95, 23, 740, 26);
	 	panel.add(path);
	 	path.setColumns(10);
	 	
		forward = new JButton(">");
		forward.setBounds(47,23,45,25);
		forward.setEnabled(false);
		forward.addActionListener(mForward_BackwardEvent);
		panel.add(forward);

		backward = new JButton("<");
		backward.addActionListener(mForward_BackwardEvent);
		backward.setEnabled(false);
		backward.setBounds(0, 23, 45, 25);
		panel.add(backward);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(480, 100, 850, 550);
		setResizable(false);
		this.setVisible(true);
	}
/*
 * This function is used to handle event from user
 */
	public void handleEvent() {
		tree.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					menuPopup = new MenuPopup(app_temp);
					panel.add(menuPopup);
					menuPopup.show(treeScrollPane, e.getX(), e.getY());
				}
			}
			
		});
		
		
		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				try {
					DefaultMutableTreeNode temp = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
					File temp_1 = (File) temp.getUserObject();
					currentNode = temp;
					//  If user switch to another action branch, reset previous action sequence
					if(currentPos > 0 && currentPos < forw_backw.size()-1){
						forw_backw.clear();
						currentPos = -1;
					}
					//  Add current action to forward_backward array, and increase currentPos
					forw_backw.add(temp);
					currentPos++;
					//  If Current file is last action, forward Button is unclickable
					//  If Current File is first action, backward Button is unclickable
					if(currentPos > 0){
						backward.setEnabled(true);
					}else if(currentPos < (forw_backw.size() - 1)){
						forward.setEnabled(false);
					}
					 // repaint
					listFile.removeAll();// delete current folder or file in panel
					listFile.revalidate();
					listFile.repaint();
					// If temp_1 is a Folder, Render all files in this folder on the panel
					if(temp_1.isDirectory()) {
						listFile.RenderAllFileInFolder(temp_1);
					}else if(temp_1.isFile()){
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode)temp.getParent();
						File folder_temp = (File) parent.getUserObject();
						listFile.RenderAllFileInFolder(folder_temp);
					}
					currentFile = temp_1;
					path.setText(temp_1.getPath());
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
		});
		
	 	path.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					File temp = new File(path.getText().toString());
					if(temp.isFile()) {
						currentFile = temp;
						openFile();
					}
				}
			}
		});
	}
	
/*
 * creat folder tree
 * root node is root containing file
 */
	public DefaultMutableTreeNode creatTree(DefaultMutableTreeNode root,File file) {
		root = new DefaultMutableTreeNode(file);
		File[] listFile = file.listFiles();
		
		for (File temp : listFile) {
			if(temp.isDirectory()) {
				root.add(creatTree(root, temp));
			}else if(temp.isFile()){
				root.add(new DefaultMutableTreeNode(temp));
			}
		}
		return root;
	}
	public static void main(String[] args){
		new App();
	}

	public void openFile() {
		if(currentFile.isFile()) {
			try {
				Desktop.getDesktop().open(currentFile);
				System.out.println(currentFile.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void copyFile() {
		copyFile = currentFile;
	}
	
	public void pasteFile() {
		File des = new File(currentFile,copyFile.getName());
		try {
			Files.copy(copyFile.toPath(),des.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(des);
		currentNode.add(newNode);
		App.model.reload(newNode.getParent());
	}
	
	public void deleteFile() {
		currentFile.delete();
		App.model.removeNodeFromParent(currentNode);
		update((DefaultMutableTreeNode)currentNode.getParent());
	}
	
	public void update(DefaultMutableTreeNode node) {
		model.reload(node);
	}

	public void newFile() {
		new Input(this);
	}
	
	// Getter/Setter

	public static ArrayList<DefaultMutableTreeNode> getForw_backw() {
		return forw_backw;
	}
	public static int getCurrentPos() {
		return currentPos;
	}
	public static void setCurrentPos(int data){
		currentPos = data;
	}
	
	public MenuPopup getMenuPopup() {
		return menuPopup;
	}

	public static MenuOption getMenuOption(){
		return App.menuOption;
	}

	public File getCurrentFile() {
		return currentFile;
	}
	public void setCurrentFile(File file){
		App.currentFile = file;
	}
	public ListFile getListFile() {
		return this.listFile;
	}

	public DefaultMutableTreeNode getCurrentNode(){
		return this.currentNode;
	}

	public void setEnableForward(Boolean b){
		this.forward.setEnabled(b);
	}

	public void setEnableBackward(Boolean b){
		this.backward.setEnabled(b);
	}

	public JTextField getPath(){
		return this.path;
	}

	public JButton getForWard(){
		return this.forward;
	}
	public JButton getBackWard(){
		return this.backward;
	}
}