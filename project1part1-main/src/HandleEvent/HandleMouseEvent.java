package HandleEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import Application.App;
import UI.ListFile;
import UI.RenderFile;

public class HandleMouseEvent implements MouseListener{
    public static App app;

    public HandleMouseEvent(App app){
        HandleMouseEvent.app = app;
    }
    @Override
	public void mouseClicked(MouseEvent e) {
		RenderFile temp = (RenderFile) e.getSource();
		File file = temp.getFile();
		if(file.isFile()) {
			app.setCurrentFile(temp.getFile());
			app.openFile();
		}else if(file.isDirectory()) {
            if(app.getListFile() != null){
                ListFile listFile =  app.getListFile();
                listFile.removeAll();
                listFile.revalidate();
                listFile.repaint();
                listFile.RenderAllFileInFolder(file);
            }
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}