package motion.database.model;

import java.util.List;


public class AttributeName {
	// Attribute types (defined only here for the applet).
	public static final String INTEGER_TYPE = "Integer";
	public static final String STRING_TYPE = "String";
	public static final String DATE_TYPE = "DATE";
	public static final String UNKNOWN_TYPE = "Unknown";
	
	private String attribute;
	private String type;
	private String subType;
	private String unit;
	private List<String> enumValues;
	
	private String groupName;
	
	public AttributeName(String attribute, String type) {
		this.attribute = attribute;
		this.type = type;
	}
	
	public AttributeName(String attribute, String type, String subType, String unit, List<String> enumValues, String groupName) {
		this.attribute = attribute;
		this.type = type;
		this.subType = subType;
		this.unit = unit;
		this.enumValues = enumValues;
		this.groupName = groupName;
	}
	
	public String toString() {
		
		return attribute;
	}
	
	public EntityAttribute toEntityAttribute(Object value) {
		EntityAttribute entityAttribute = new EntityAttribute(
				this.attribute,
				value,
				this.groupName,
				this.type);
		
		return entityAttribute;
	}
	
	public String getType() {
		
		return type;
	}
	
	public String getSubType() {
		
		return subType;
	}
	
	public String getUnit() {
		
		return unit;
	}
	
	public List<String> getEnumValues() {
		
		return enumValues;
	}
	
	public String[] getOperators() {
		if (type.equals(INTEGER_TYPE)) {
			return Predicate.integerOperators;
		} else if (type.equals(STRING_TYPE)) {
			return Predicate.stringOperators;
		} else if (type.equals(DATE_TYPE)) {
			return Predicate.dateOperators;
		} else { // Unknown type.
			return new String[]{UNKNOWN_TYPE};
		}
	}
	
	public Class getAttributeClass() {
		if (type.equals(INTEGER_TYPE)) {
			return Integer.class;
		} else if (type.equals(STRING_TYPE)) {
			return String.class;
		} else if (type.equals(DATE_TYPE)) {
			return String.class;
		} else { // Unknown type.
			return Object.class;
		}
	}
}
