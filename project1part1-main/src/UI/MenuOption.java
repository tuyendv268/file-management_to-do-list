package UI;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import Application.App;
/**
 * This class is used to creat Menu Bar in the upper of application.
 */
public class MenuOption extends JMenuBar{
	private JMenu mnSetting;
	private JMenuItem newFileItem,once_mode, second_mode; 
	private JMenu helpMenu,aboutMenu, display;
	public MenuOption() {
		setAlignmentY(Component.CENTER_ALIGNMENT);
		setBounds(2, 2, 820, 20);
		
		mnSetting = new JMenu("Setting");
		mnSetting.setAlignmentX(Component.RIGHT_ALIGNMENT);
		mnSetting.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mnSetting.setHorizontalAlignment(SwingConstants.CENTER);
		add(mnSetting);
		
		display = new JMenu("Display");
		mnSetting.add(display);

		once_mode = new JMenuItem("First Mode");
		display.add(once_mode);

		second_mode = new JMenuItem ("Second Mode");
		display.add(second_mode);
		once_mode.addActionListener(App.menuOptionEvent);
		second_mode.addActionListener(App.menuOptionEvent);
		
		newFileItem = new JMenuItem("New File");
		mnSetting.add(newFileItem);
		
		helpMenu = new JMenu("Help");
		helpMenu.setAlignmentX(Component.RIGHT_ALIGNMENT);
		helpMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		helpMenu.setHorizontalAlignment(SwingConstants.CENTER);
		add(helpMenu);
		
		aboutMenu = new JMenu("About Me");
		aboutMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(aboutMenu);
	}


	public JMenuItem getOnce_mode() {
		return once_mode;
	}
	public JMenuItem getSecond_mode() {
		return second_mode;
	}
	
}
