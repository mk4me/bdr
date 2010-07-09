package motion.applet.trees;

import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxNode extends DefaultMutableTreeNode {
	private String text;
	private boolean selected;
	
	public CheckBoxNode(String text, boolean selected) {
		this.text = text;
		this.selected = selected;
	}
	
	public boolean isSelected() {
		
		return selected;
	}
	
	public void setSelected(boolean newValue) {
		selected = newValue;
	}
	
	public String getText() {
		
		return text;
	}
	
	public void setText(String newValue) {
		text = newValue;
	}
	
	public String toString() {
	
		return getClass().getName() + "[" + text + "/" + selected + "]";
	}
}