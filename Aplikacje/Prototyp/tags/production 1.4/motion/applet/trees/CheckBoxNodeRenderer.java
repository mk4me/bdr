package motion.applet.trees;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxNodeRenderer implements TreeCellRenderer {
	private JCheckBox leafRenderer = new JCheckBox();
	private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();
	private Color selectionBorderColor;
	private Color selectionForeground;
	private Color selectionBackground;
	private Color textForeground;
	private Color textBackground;
	
	protected JCheckBox getLeafRenderer() {
		
		return leafRenderer;
	}
	
	public CheckBoxNodeRenderer() {
		Font fontValue;
		fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) {
			leafRenderer.setFont(fontValue);
		}
		Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
		leafRenderer.setFocusPainted((booleanValue != null) && (booleanValue.booleanValue()));
		selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Component returnValue;
		String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);
		leafRenderer.setText(stringValue);
		leafRenderer.setSelected(false);
		leafRenderer.setEnabled(tree.isEnabled());
		
		if (selected) {
			leafRenderer.setForeground(selectionForeground);
			leafRenderer.setBackground(selectionBackground);
		} else {
			leafRenderer.setForeground(textForeground);
			leafRenderer.setBackground(textBackground);
		}
		
		if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
			Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
			if (userObject instanceof CheckBoxNode) {
				CheckBoxNode node = (CheckBoxNode) userObject;
				leafRenderer.setText(node.getText());
				leafRenderer.setSelected(node.isSelected());
			}
		}
		returnValue = leafRenderer;
		
		return returnValue;
	}
}