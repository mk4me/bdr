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
	
	public AttributeName(String attribute, String type) {
		this.attribute = attribute;
		this.type = type;
	}
	
	public AttributeName(String attribute, String type, String subType, String unit, List<String> enumValues) {
		this.attribute = attribute;
		this.type = type;
		this.subType = subType;
		this.unit = unit;
		this.enumValues = enumValues;
	}
	
	public String toString() {
		
		return attribute;
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
}
