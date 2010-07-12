package motion.applet.trees;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

public class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {
	private CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
	private ChangeEvent changeEvent = null;
	private JTree tree1;
	private DefaultMutableTreeNode editedNode;
	
	public CheckBoxNodeEditor(JTree tree) {
		this.tree1 = tree;
	}
	
	public Object getCellEditorValue() {
		JCheckBox checkbox = renderer.getLeafRenderer();
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree1.getSelectionPath().getLastPathComponent();
		((CheckBoxNode) treeNode.getUserObject()).setSelected(checkbox.isSelected());
		
		return treeNode.getUserObject();
	}
	
	public boolean isCellEditable(EventObject event) {
		boolean returnValue = false;
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree1.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
			if (path != null) {
				Object node = path.getLastPathComponent();
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					editedNode = (DefaultMutableTreeNode) node;
					Object userObject = editedNode.getUserObject();
					Rectangle r = tree1.getPathBounds(path);
					int x = mouseEvent.getX() - r.x;
					int y = mouseEvent.getY() - r.y;
					JCheckBox checkbox = renderer.getLeafRenderer();
					checkbox.setText("");
					returnValue = userObject instanceof CheckBoxNode && x > 0 && x < checkbox.getPreferredSize().width;
				}
			}
		}
		
		return returnValue;
	}
	
	public Component getTreeCellEditorComponent(final JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row) {
		Component editor = renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, true);
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				tree.repaint();
				fireEditingStopped();
			}
		};
		
		if (editor instanceof JCheckBox) {
			((JCheckBox) editor).addItemListener(itemListener);
		}
		
		return editor;
	}
}