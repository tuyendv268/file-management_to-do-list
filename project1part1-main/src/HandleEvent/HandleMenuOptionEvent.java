package HandleEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import Application.App;
import UI.ListFile;

public class HandleMenuOptionEvent implements ActionListener{
    public static App app;

    public HandleMenuOptionEvent(App app){
        HandleMenuOptionEvent.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((JMenuItem)e.getSource() == App.getMenuOption().getOnce_mode()){
            ListFile.setMode(0);
            System.out.println("Mode : 1");
        }else{
            ListFile.setMode(1);
            System.out.println("Mode : 2");
        }
        System.out.println(e.getClass());
        app.getListFile().removeAll();// delete current folder or file in panel
        app.getListFile().revalidate();
        app.getListFile().repaint();
        app.getListFile().RenderAllFileInFolder(app.getCurrentFile());
    }
    
}
