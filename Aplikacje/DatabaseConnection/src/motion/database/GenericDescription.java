package motion.database;

import java.util.HashMap;

@SuppressWarnings("serial")
abstract class GenericDescription<T extends Enum<T>> extends HashMap<String, Object>
{
	String idAttributeName;
	
	public GenericDescription(String name)
	{
		this.idAttributeName = name;
	}
	
	public Object get(T key) {
		return super.get(key.name());
	}
	
	public Object put(T key, Object arg) {
		return super.put( key.name(), arg );
	}
	
	public int getId()
	{
		return (Integer)get( idAttributeName );
	}
	
	public String toString()
	{
		StringBuffer output = new StringBuffer();
		
		output.append( this.getClass().getName() ).append( '(' ).append( this.getId() ).append(')').append(System.getProperty( "line.separator" ) );
		for ( String key : this.keySet() )
				output.append( key ).append('=').append( this.get(key) ).append(System.getProperty( "line.separator" ) );
		return output.toString();
	}
}


