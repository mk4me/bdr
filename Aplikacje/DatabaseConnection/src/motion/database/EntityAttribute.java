package motion.database;

import java.util.GregorianCalendar;

public class EntityAttribute {

	
	public String type;
	public String groupName;
	public Object value;
	public String name;

	public EntityAttribute(String name, Object value, String groupName, String type)
	{
		this.name = name;
		this.value = value;
		this.groupName = groupName;
		this.type = type;
	}

	public String toString()
	{
		return name;
	}

	public String toStringAllContent()
	{
		StringBuffer result = new StringBuffer();
		result.append( name ).append("[").append( value ).append( ", " ).
			append( groupName ).append( ", " ).append( type ).append("]");
		return result.toString();
	}

	private Class getTypeClass()
	{
		String typeL = type.toLowerCase();
		if (typeL.contains( "string" ))
			return String.class;
		else if (typeL.contains( "float" ))
			return Float.class;
		else if (typeL.contains( "double" ))
			return Double.class;
		else if (typeL.contains( "integer" ))
			return Integer.class;
		else if (typeL.contains( "calendar" ))
			return GregorianCalendar.class;
		else return null;
	}
	
	public void setValueFromString(Object newValue) {
		
		if ( getTypeClass() == String.class)
			this.value = newValue;
		else if ( getTypeClass() == Integer.class)
			this.value = Integer.parseInt( (String) newValue );
		else if ( getTypeClass() == Float.class)
			this.value = Float.parseFloat( (String) newValue );
		else if ( getTypeClass() == Double.class)
			this.value = Double.parseDouble( (String) newValue );
		else if ( getTypeClass() == GregorianCalendar.class)
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
