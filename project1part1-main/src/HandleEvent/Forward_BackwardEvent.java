package HandleEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import Application.App;
import UI.ListFile;

public class Forward_BackwardEvent implements ActionListener{
    private App app;
    private ListFile listFile;
    private DefaultMutableTreeNode current;
    private JTextField path;

    public Forward_BackwardEvent(App app){
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource(); File temp;
        ArrayList<DefaultMutableTreeNode> forward_backward = App.getForw_backw(); 
        int index = App.getCurrentPos();
        listFile = app.getListFile();
        path = app.getPath();

        if(source.getText().equals(">")){
            current = forward_backward.get(index+1);
            App.setCurrentPos(++ index);
        }else if(source.getText().equals("<")){
            current = forward_backward.get(index-1);
             App.setCurrentPos(-- index);
        }
        // Repaint
        listFile.removeAll();// delete current folder or file in panel
        listFile.revalidate();
        listFile.repaint(); // repaint its
        temp = (File)current.getUserObject();
        //  If this file is Directory , render all its file
        // If this file is File, render folder contain it 
        if(temp.isDirectory()){
            listFile.RenderAllFileInFolder(temp);
        }else if(temp.isFile()){
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode)current.getParent();
            File folder_temp = (File) parent.getUserObject();
            listFile.RenderAllFileInFolder(folder_temp);
        }
        path.setText(temp.getPath());

        // If current file is last/first action , forward /backward button is unclickable
        if(index >= forward_backward.size() - 1){
            app.setEnableForward(false);
            app.setEnableBackward(true);
        }else if(index <= 0){
            app.setEnableBackward(false);
            app.setEnableForward(true);
        }else{
            app.setEnableBackward(true);
            app.setEnableForward(true);
        }
        
    }
    
}
