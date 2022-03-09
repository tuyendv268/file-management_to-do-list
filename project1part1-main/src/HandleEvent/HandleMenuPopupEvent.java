package HandleEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Application.App;
import UI.MenuPopup;

public class HandleMenuPopupEvent implements ActionListener{
    public static App app;
    private MenuPopup menuPopup;

    public HandleMenuPopupEvent(App app){
        HandleMenuPopupEvent.app = app;
    }
    @Override
	public void actionPerformed(ActionEvent e) {
        menuPopup = app.getMenuPopup();
		if(e.getSource() == menuPopup.open) {
			app.openFile();
		}else if(e.getSource() == menuPopup.newFile) {
			app.newFile();
		}else if(e.getSource() == menuPopup.copy){
			app.copyFile();
		}else if(e.getSource() == menuPopup.paste){
			app.pasteFile();
		}else if(e.getSource() == menuPopup.delete) {
			app.deleteFile();
		}else if(e.getSource() == menuPopup.properties) {
			System.out.println("Properties");
		}
	}

}
