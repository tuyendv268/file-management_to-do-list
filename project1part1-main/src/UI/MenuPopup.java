package UI;

import java.awt.MenuItem;
import java.awt.PopupMenu;
/*
 * This class is used to creat Menu Popup 
 *  
 */

import Application.App;

public class MenuPopup extends PopupMenu{
	public static App app;
	public MenuItem open;
	public MenuItem newFile;
	public MenuItem newFolder;
	public MenuItem delete;
	public MenuItem copy;
	public MenuItem paste;
	public MenuItem move;
	public MenuItem properties;
	public MenuPopup(App app) {
		MenuPopup.app = app;
		open = new MenuItem("Open");
		newFile = new MenuItem("New File");
		newFolder = new MenuItem("New Folder");
		delete = new MenuItem("Delete");
		move = new MenuItem("Move");
		copy = new MenuItem("Copy");
		paste = new MenuItem("Paste");
		properties = new MenuItem("Properties");
		 
		open.addActionListener(App.mActionEvent);
		newFile.addActionListener(App.mActionEvent);
		newFolder.addActionListener(App.mActionEvent);
		move.addActionListener(App.mActionEvent);
		delete.addActionListener(App.mActionEvent);
		copy.addActionListener(App.mActionEvent);
		paste.addActionListener(App.mActionEvent);
		properties.addActionListener(App.mActionEvent);
		
		this.add(open);
		this.add(newFile);
		this.add(newFolder);
		this.add(move);
		this.add(delete);
		this.add(paste);
		this.add(copy);
		this.add(properties);
	}
}
