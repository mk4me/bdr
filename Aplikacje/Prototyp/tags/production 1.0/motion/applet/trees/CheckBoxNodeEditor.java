package motion.applet.trees;

import java.awt.Component;
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

import motion.database.model.Filter;

public class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {
	private CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
	private ChangeEvent changeEvent = null;
	private JTree tree;
	
	public CheckBoxNodeEditor(JTree tree) {
		this.tree = tree;
	}
	
	public Object getCellEditorValue() {
		JCheckBox checkbox = renderer.getLeafRenderer();
		//Filter checkBoxNode = new Filter(checkbox.getText(), checkbox.isSelected());
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
		Filter filter = (Filter) treeNode.getUserObject();
		filter.setSelected(checkbox.isSelected());
		//return checkBoxNode;
		return filter;
	}
	
	public boolean isCellEditable(EventObject event) {
		boolean returnValue = false;
		if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
			if (path != null) {
				Object node = path.getLastPathComponent();
				if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
					Object userObject = treeNode.getUserObject();
					// Check boxes only for leaf nodes
					//returnValue = ((treeNode.isLeaf()) && (userObject instanceof Filter));
					returnValue = (userObject instanceof Filter);
				}
			}
		}
		
		return returnValue;
	}
	
	public Component getTreeCellEditorComponent(JTree tree, Object value, 
			boolean selected, boolean expanded, boolean leaf, int row) {
		Component editor = renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, true);
		
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				if (stopCellEditing()) {
					fireEditingStopped();
				}
			}
		};
		
		if (editor instanceof JCheckBox) {
			((JCheckBox) editor).addItemListener(itemListener);
		}
		
		return editor;
	}
}
