package UI;

import java.io.File;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
/*
 * This class is used to creat File button drawed in the left of application
 */
public class RenderFile extends JButton{
	private File file;
	public RenderFile(File file) {
		this.file = file;
		setHorizontalAlignment(SwingConstants.LEFT);
		setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));

		if(file.getName().length() > 9){
			setText(file.getName().substring(0, 5)+"...");
		}else{
			setText(file.getName());
		}
		// setSize(40,40);
		setVerticalTextPosition(SwingConstants.BOTTOM);
	    setHorizontalTextPosition(SwingConstants.CENTER);
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return this.file;
	}

}