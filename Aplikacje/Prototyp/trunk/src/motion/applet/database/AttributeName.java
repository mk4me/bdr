package motion.applet.database;

import motion.applet.filter.model.Predicate;

public class AttributeName {
	protected static final String INTEGER_TYPE = "Integer";
	protected static final String STRING_TYPE = "String";
	protected static final String DATE_TYPE = "DATE";
	protected static final String UNKNOWN_TYPE = "Unknown";
	
	private final String attribute;
	private final String type;
	
	public AttributeName(String attribute, String type) {
		this.attribute = attribute;
		this.type = type;
	}
	
	public String toString() {
		
		return attribute;
	}
	
	public String getType() {
		
		return type;
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
