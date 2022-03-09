package UI;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

class FileListCellRenderer extends DefaultListCellRenderer {
    private FileSystemView fileSystemView;
    private JLabel label;
    FileListCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean selected, boolean expanded) {

        File file = (File)value;
        label.setHorizontalAlignment(LEFT);
        
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(Color.gray);
            label.setForeground( Color.BLACK);
        } else {
            label.setBackground(Color.white);
            label.setForeground(Color.BLACK);
        }

        return label;
    }
}