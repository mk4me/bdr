package motion.applet.trees;

import motion.database.model.EntityAttributeGroup;

public class AttributeGroupCheckBoxNode extends CheckBoxNode {
	private EntityAttributeGroup group;
	
	public AttributeGroupCheckBoxNode(String text, boolean selected, EntityAttributeGroup group) {
		super(text, selected);
		this.group = group;
	}
	
	@Override
	public void setSelected(boolean newValue) {
		super.setSelected(newValue);
		group.isVisible = newValue;
	}
}