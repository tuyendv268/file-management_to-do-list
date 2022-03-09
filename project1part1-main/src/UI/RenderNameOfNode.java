package UI;

import java.awt.Component;
import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
/*
 * This class is use to draw the name of node in jtree
 * Instead of path of the file
 */
public class RenderNameOfNode extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,boolean sel
    ,boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof DefaultMutableTreeNode) {
            value = ((DefaultMutableTreeNode)value).getUserObject();
            if (value instanceof File) {
                value = ((File) value).getName();
            }
        }
        return super.getTreeCellRendererComponent(tree, value, sel, expanded
                    , leaf, row, hasFocus);
    }

}