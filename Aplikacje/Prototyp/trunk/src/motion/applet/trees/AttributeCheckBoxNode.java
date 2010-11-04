package motion.applet.trees;

import motion.database.model.EntityAttribute;

public class AttributeCheckBoxNode extends CheckBoxNode {
	private EntityAttribute attribute;
	
	public AttributeCheckBoxNode(String text, boolean selected, EntityAttribute attribute) {
		super(text, selected);
		this.attribute = attribute;
	}
	
	@Override
	public void setSelected(boolean newValue) {
		super.setSelected(newValue);
		attribute.isVisible = newValue;
	}
}