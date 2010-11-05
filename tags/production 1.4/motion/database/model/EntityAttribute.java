package motion.database.model;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

public class EntityAttribute {
/*
	public static final String INTEGER_TYPE_SHORT = "int";
	public static final String INTEGER_TYPE = "integer";
	public static final String STRING_TYPE = "string";
	public static final String FLOAT_TYPE = "float";
	public static final String DATE_TYPE = "DATE";	// Server uses 'string' for dates.
	public static final String UNKNOWN_TYPE = "Unknown";
	
	public static final String SUBTYPE_SHORT_STRING = "shortString";
	public static final String SUBTYPE_LONG_STRING = "longString";
	public static final String SUBTYPE_DATE = "date";
	public static final String SUBTYPE_DATE_TIME = "dateTime";
	*/
	
	public static final String TYPE_INT = "int";
	public static final String TYPE_DECIMAL = "decimal";
	public static final String TYPE_NON_NEGATIVE_INTEGER = "nonNegativeInteger";
	public static final String TYPE_NON_NEGATIVE_DECIMAL = "nonNegativeDecimal";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_SHORT_STRING = "shortString";
	public static final String TYPE_LONG_STRING = "longString";
	public static final String TYPE_DATE = "date";
	public static final String TYPE_DATE_TIME = "dateTime";
	public static final String TYPE_TIME_CODE = "TIMECODE";
	public static final String TYPE_UNKNOWN = "unknown";
	
	public static final String DB_TYPE_INTEGER = "integer";
	public static final String DB_TYPE_FLOAT = "float";
	public static final String DB_TYPE_STRING = "string";
	public static final String DB_TYPE_DATE = "DATE";
			
	public String type;
	public String groupName;
	public Object value;
	public String name;
	public String unit;
	//public String subtype;
	public List<String> enumValues;
	public EntityKind kind;
	public boolean isEnum;
	
	public boolean isVisible;
	
	public EntityAttribute(String attribute, String type, String unit, List<String> enumValues, String groupName) 
	{
		this( attribute, null, null, groupName, type, unit, enumValues!=null );
		this.enumValues = enumValues;
	}

	
	public EntityAttribute(String name, EntityKind kind, Object value, String groupName, String type, String unit, boolean isEnum)
	{
		this.name = name;
		this.value = value;
		this.groupName = groupName;
		this.type = type;
		this.kind = kind;
		this.unit = unit;
		this.isEnum = isEnum;
		this.isVisible = true;
	}
	
	public EntityAttribute(String name, EntityKind kind, Object value, String groupName, String type)
	{
		this(name, kind, value, groupName, type, null, false);
	}

	public EntityAttribute(String name, String kind)
	{
		this( name, EntityKind.valueOf( kind ), null, null, null );
	}

	public String toString()
	{
		return name;
	}

	public String toStringAllContent()
	{
		StringBuffer result = new StringBuffer();
		result.append( name ).append("(").append( kind.toString() ).append(")").append("[").append( value ).append( ", " ).
			append( groupName ).append( ", " ).append( type ).append( ", " ).append( unit ).
			append(", ").append( isEnum?getEnumInfo():"").append("]");
		return result.toString();
	}

	private String getEnumInfo() {
		StringBuffer result = new StringBuffer("enum[ ");
		for(String v:enumValues)
			result.append(v).append(",");
		result.setLength( result.length()-1 );
		result.append(" ]");
		return result.toString();
	}


	public String getType() {
		
		return type;
	}
	
	public String getUnit() {
		
		return unit;
	}
	
	public List<String> getEnumValues() {
		
		return enumValues;
	}
	
	// Used by applet for filters.
	public String[] getOperators() {
		if (type.equals(TYPE_INT) || type.equals(TYPE_NON_NEGATIVE_INTEGER) ||
				type.equals(TYPE_DECIMAL) || type.equals(TYPE_NON_NEGATIVE_DECIMAL) ||
				type.equals(TYPE_FLOAT)) {
			return Predicate.integerOperators;
		} else if (type.equals(TYPE_SHORT_STRING) || type.equals(TYPE_LONG_STRING)) {
			return Predicate.stringOperators;
		} else if (type.equals(TYPE_DATE) || type.equals(TYPE_DATE_TIME) || type.equals(TYPE_TIME_CODE)) {
			return Predicate.dateOperators;
		} else { // Unknown type.
			return new String[]{TYPE_UNKNOWN};
		}
	}
	
	// Used by applet for table cell format.
	public Class<?> getAttributeClass() {
		if (type == null) {
			return Object.class;
		} else if (type.equals(TYPE_INT) || type.equals(TYPE_NON_NEGATIVE_INTEGER)) {
			return Integer.class;
		} else if (type.equals(TYPE_DECIMAL) || type.equals(TYPE_NON_NEGATIVE_DECIMAL) || type.equals(TYPE_FLOAT)) {
			return Float.class;
		} else if (type.equals(TYPE_SHORT_STRING) || type.equals(TYPE_LONG_STRING)) {
			return String.class;
		} else if (type.equals(TYPE_DATE) || type.equals(TYPE_DATE_TIME) || type.equals(TYPE_TIME_CODE)) {
			return GregorianCalendar.class;
		} else { // Unknown type.
			return Object.class;
		}
	}
	
	public void setValueFromString(Object newValue) {
		Class<?> attributeClass = getAttributeClass();
		
		if (attributeClass == String.class) {
			this.value = newValue;
		} else if (attributeClass == Integer.class) {
			if (newValue instanceof String) {
				this.value = Integer.parseInt( (String) newValue );
			} else {
				this.value = newValue;
			}
		} else if (attributeClass == Float.class) {
			if (newValue instanceof String) {
				this.value = Float.parseFloat( (String) newValue );
			} else {
				this.value = newValue;
			}
		} else if (attributeClass == GregorianCalendar.class) {
			this.value = newValue;
		} else if (attributeClass == Object.class) {
			this.value = newValue.toString();
		} else {
			throw new RuntimeException("TODO: Unknown value type." + this.value.getClass() + " new value:" + newValue);
		}
	}

	public void emptyValue() {
		Class<?> attributeClass = getAttributeClass();
		
		if (attributeClass == String.class) {
			this.value = "";
		} else if (attributeClass == Integer.class) {
			this.value = 0;
		} else if (attributeClass == Float.class) {
			this.value = 0;
		} else if (attributeClass == XMLGregorianCalendar.class || getAttributeClass() == GregorianCalendar.class) {
			this.value = GregorianCalendar.getInstance();
		} else {
			throw new RuntimeException("TODO: Unknown value type." + this.value.getClass());
		}
	}

	//FIXME: subtype/type, is this needed?
	public static String getTypeName(Object arg) {
		if (arg instanceof String) {
			return DB_TYPE_STRING;//STRING_TYPE;
		} else if (arg instanceof Integer) {
			return DB_TYPE_INTEGER;//INTEGER_TYPE;
		} else if ( arg instanceof Float || arg instanceof Double) {
			return DB_TYPE_FLOAT;//FLOAT_TYPE;
		} else if ( arg instanceof XMLGregorianCalendar || arg instanceof GregorianCalendar) {
			return DB_TYPE_DATE;//DATE_TYPE;
		} else if ( arg instanceof Boolean) {
			return DB_TYPE_INTEGER;//DATE_TYPE;
		}	
		else {
			throw new RuntimeException("TODO: Unknown value type: " + arg);
		}
	}
}
