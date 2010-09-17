package motion.database.model;

import java.util.GregorianCalendar;
import java.util.List;

public class EntityAttribute {

	public static final String INTEGER_TYPE_SHORT = "int";
	public static final String INTEGER_TYPE = "integer";
	public static final String STRING_TYPE = "string";
	public static final String DATE_TYPE = "DATE";	// Server uses 'string' for dates.
	public static final String UNKNOWN_TYPE = "Unknown";
	
	public static final String SUBTYPE_SHORT_STRING = "shortString";
	public static final String SUBTYPE_LONG_STRING = "longString";
	public static final String SUBTYPE_DATE = "date";
	public static final String SUBTYPE_DATE_TIME = "dateTime";
	
	public String type;
	public String groupName;
	public Object value;
	public String name;
	public String unit;
	public String subtype;
	public List<String> enumValues;
	public EntityKind kind;
	public boolean isEnum;
	
	public EntityAttribute(String attribute, String type, String subType, String unit, List<String> enumValues, String groupName) 
	{
		this( attribute, null, null, groupName, type, subType, unit, enumValues!=null );
		this.enumValues = enumValues;
	}

	
	public EntityAttribute(String name, EntityKind kind, Object value, String groupName, String type, String subType, String unit, boolean isEnum)
	{
		this.name = name;
		this.value = value;
		this.groupName = groupName;
		this.type = type;
		this.kind = kind;
		this.subtype = subType;
		this.unit = unit;
		this.isEnum = isEnum;
	}
	
	public EntityAttribute(String name, EntityKind kind, Object value, String groupName, String type)
	{
		this(name, kind, value, groupName, type, null, null, false);
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
			append( groupName ).append( ", " ).append( type ).append( ", " ).append( unit ).append( ", " ).append( subtype ).
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
	
	public String getSubtype() {
		
		return subtype;
	}
	
	public String getUnit() {
		
		return unit;
	}
	
	public List<String> getEnumValues() {
		
		return enumValues;
	}
	
	public String[] getOperators() {
		if (type.equals(INTEGER_TYPE) || type.equals(INTEGER_TYPE_SHORT)) {
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
		if (type.equals(INTEGER_TYPE) || type.equals(INTEGER_TYPE_SHORT)) {
			return Integer.class;
		} else if (type.equals(STRING_TYPE)) {
			return String.class;
		} else if (type.equals(DATE_TYPE)) {
			return String.class;
		} else { // Unknown type.
			return Object.class;
		}
	}

	private Class<?> getTypeClass()
	{
		String typeL = type.toLowerCase();
		if (typeL.contains( "string" ))
			return String.class;
		else if (typeL.contains( "float" ))
			return Float.class;
		else if (typeL.contains( "double" ))
			return Double.class;
		else if (typeL.contains( "integer" ) || typeL.contains( "int" ))
			return Integer.class;
		else if (typeL.contains( "calendar" ))
			return GregorianCalendar.class;
		else return null;
	}
	
	public void setValueFromString(Object newValue) {
		
		if ( getTypeClass() == String.class)
			this.value = newValue;
		else if ( getTypeClass() == Integer.class) {
			if (newValue instanceof String) {
				this.value = Integer.parseInt( (String) newValue );
			} else {
				this.value = newValue;
			}
		} else if ( getTypeClass() == Float.class) {
			if (newValue instanceof String) {
				this.value = Float.parseFloat( (String) newValue );
			} else {
				this.value = newValue;
			}
		} else if ( getTypeClass() == Double.class) {
			if (newValue instanceof String) {
				this.value = Double.parseDouble( (String) newValue );
			} else {
				this.value = newValue;
			}
		} else if ( getTypeClass() == GregorianCalendar.class)
			this.value = GregorianCalendar.getInstance();
		else	
			throw new RuntimeException("TODO: Unknown value type." + this.value.getClass() + " new value:" + newValue );
	}

	public void emptyValue() {
		if ( getTypeClass() == String.class)
			this.value = "";
		else if ( getTypeClass() == Integer.class)
			this.value = 0;
		else if ( getTypeClass() == Float.class)
			this.value = 0;
		else if ( getTypeClass() == Double.class)
			this.value = 0;
		else if ( getTypeClass() == GregorianCalendar.class)
			this.value = GregorianCalendar.getInstance();
		else	
			throw new RuntimeException("TODO: Unknown value type." + this.value.getClass() );
	}
}
