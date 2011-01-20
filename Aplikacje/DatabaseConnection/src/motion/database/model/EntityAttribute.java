package motion.database.model;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * This class describes a single attribute in an entity. It may represent just an
 * attribute or an attribute together with its value.
 * 
 * 
 * @author kk
 *
 */
public class EntityAttribute {
	public static final String TYPE_ID = "ID";
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
	public static final String TYPE_FILE = "file";
	
	public static final String DB_TYPE_INTEGER = "integer";
	public static final String DB_TYPE_FLOAT = "float";
	public static final String DB_TYPE_STRING = "string";
	public static final String DB_TYPE_DATE = "DATE";
			
	/**
	 * Attribute type
	 */
	public String type;
	
	/** 
	 * Attribute group name. It must be unique within an entity kind.
	 */
	public String groupName;
	/**
	 * Attribute value. May be null if this attribute was not returned as certain
	 * entity attribute value. In most cases methods from EntityKind enumeration like
	 * getAllAttributes() return this field null as in such cases it is not connected
	 * to any particular entity but to an EntityKind as a whole. 
	 */
	public Object value;
	/**
	 * Attribute name. Must be unique within a group of attributes.
	 */
	public String name;
	/**
	 * Attribute value unit. This information is stored in database to inform
	 * users about measures used in certain cases.
	 */
	public String unit;
	/**
	 * If an attribute is an enumeration then this field contains all enumeration values
	 * as strings.
	 */
	public List<String> enumValues;
	/**
	 * This field keeps information on kind of entity this attribute is stored in.
	 */
	public EntityKind kind;
	/**
	 * This field informs if the attribute is an enumeration.
	 */
	public boolean isEnum;
	/**
	 * This field is true if the actual (the one who logged in) user set this attribute
	 * to be visible in browser tables. Visibility may be changed for different attributes 
	 * and users. Each user may select to observ only the set of attributes he or she is 
	 * interested in. 
	 */
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

	/**
	 * Standard toString method returns only name of this attribute. Do not modify this behavior since many
	 * tables and other GUI components depend on it.
	 * 
	 */
	public String toString()
	{
		return name;
	}

	/**
	 * This method creates a string containing all content of this attribute.
	 * 
	 * @return
	 */
	public String toStringAllContent()
	{
		StringBuffer result = new StringBuffer();
		result.append( name ).append("(").append( kind.toString() ).append(")").append("[").append( value ).append( ", " ).
			append( groupName ).append( ", " ).append( type ).append( ", " ).append( unit ).
			append(", ").append( isEnum?getEnumInfo():"").append("]");
		return result.toString();
	}

	/**
	 * This method returs a string with enumeration information. All the possible enum values will be separated by commas.
	 * 
	 * @return
	 */
	private String getEnumInfo() {
		StringBuffer result = new StringBuffer("enum[ ");
		for(String v:enumValues)
			result.append(v).append(",");
		result.setLength( result.length()-1 );
		result.append(" ]");
		return result.toString();
	}


	/**
	 * Type getter.
	 * 
	 * @return
	 */
	public String getType() {
		
		return type;
	}
	
	/**
	 * Unit getter.
	 * @return
	 */
	public String getUnit() {
		
		return unit;
	}
	
	/**
	 * Enum values getter.
	 * 
	 * @return
	 */
	public List<String> getEnumValues() {
		
		return enumValues;
	}
	
	/**
	 * This method is used to get available operators for this attribute (upon its type). 
	 */  
	public String[] getOperators() {
		if (type.equals(TYPE_ID) || type.equals(TYPE_INT) || type.equals(TYPE_NON_NEGATIVE_INTEGER) ||
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
	
	/**
	 * This method is used to get Java-capable type information for an attribute. 
	 * 
	 * @return Java Class describing the type.
	 */
	public Class<?> getAttributeClass() {
		if (type == null) {
			return Object.class;
		} else if (type.equals(TYPE_ID) ||type.equals(TYPE_INT) || type.equals(TYPE_NON_NEGATIVE_INTEGER)) {
			return Integer.class;
		} else if (type.equals(TYPE_DECIMAL) || type.equals(TYPE_NON_NEGATIVE_DECIMAL) || type.equals(TYPE_FLOAT)) {
			return Float.class;
		} else if (type.equals(TYPE_SHORT_STRING) || type.equals(TYPE_LONG_STRING)) {
			return String.class;
		} else if (type.equals(TYPE_DATE) || type.equals(TYPE_DATE_TIME) || type.equals(TYPE_TIME_CODE)) {
			return GregorianCalendar.class;
		} else if (type.equals(TYPE_FILE)) {
			return Integer.class;
		}
		else
		{ // Unknown type.
			return String.class;
		}
	}
	
	/**
	 * This method sets the attribute value parsing a string. It is done according to the attribute type.
	 * 
	 * @param newValue as String
	 */
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

	/** 
	 * This method empties the value of this attribute. Appropriate 'zero' value is
	 * set according to attribute type. 
	 */
	public void emptyValue() {
		Class<?> attributeClass = getAttributeClass();
		
		if (attributeClass == String.class) {
			this.value = "";
		} else if (attributeClass == Integer.class) {
			this.value = 0;
		} else if (attributeClass == Float.class) {
			this.value = 0;
		} else if (attributeClass == XMLGregorianCalendar.class || attributeClass == GregorianCalendar.class) {
			this.value = GregorianCalendar.getInstance();
		} else {
			throw new RuntimeException("TODO: Unknown value type." + this.value.getClass());
		}
	}

	/**
	 * Returns name of type for given call parameter according to types defined for generic attributes.
	 * 
	 * @param arg
	 * @return
	 */
	public static String getTypeName(Object arg) {
		if (arg instanceof String) {
			return DB_TYPE_STRING;
		} else if (arg instanceof Integer) {
			return DB_TYPE_INTEGER;
		} else if (arg instanceof Float || arg instanceof Double) {
			return DB_TYPE_FLOAT;
		} else if (arg instanceof XMLGregorianCalendar || arg instanceof GregorianCalendar) {
			return DB_TYPE_DATE;
		} else if (arg instanceof Boolean) {
			return DB_TYPE_INTEGER;
		}	
		else {
			throw new RuntimeException("TODO: Unknown value type: " + arg);
		}
	}
}
