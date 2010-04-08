package motion.applet.database;

import java.util.ArrayList;

public class AttributeGroup {
	private String groupName;
	private ArrayList<AttributeName> attributes = new ArrayList<AttributeName>();
	
	public AttributeGroup(String groupName, ArrayList<AttributeName> attributes) {
		this.groupName = groupName;
		this.attributes = attributes;
	}
	
	public ArrayList<AttributeName> getAttributes() {
		
		return this.attributes;
	}
	
	public String getGroupName() {
		
		return this.groupName;
	}
}
