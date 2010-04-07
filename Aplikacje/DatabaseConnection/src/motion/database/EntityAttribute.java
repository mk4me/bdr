package motion.database;

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
		StringBuffer result = new StringBuffer();
		result.append( name ).append("[").append( value ).append( ", " ).
			append( groupName ).append( ", " ).append( type ).append("]");
		return result.toString();
	}
}
