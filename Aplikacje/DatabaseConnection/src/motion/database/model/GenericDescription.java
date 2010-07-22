package motion.database.model;

import java.util.HashMap;

//TODO: co z metod� equals() ?
@SuppressWarnings("serial")
public abstract class GenericDescription<T extends Enum<T>> extends HashMap<String, EntityAttribute>
{
	String idAttributeName;
	public EntityKind entityKind;
	public HashMap<String, EntityAttributeGroup> groups;
	public Class clazz;
	
	public GenericDescription(String name, EntityKind entityKind)
	{
		this.idAttributeName = name;
		this.groups = new HashMap<String, EntityAttributeGroup>();
		this.entityKind = entityKind;
	}
	
	
	public Object get(T key) {
		return super.get(key.name());
	}

	public Object getValue(T key) {
		return ((EntityAttribute)super.get(key.name())).value;
	}
	
	public EntityAttribute put(T key, EntityAttribute arg) {
		return put(key.name(), arg);
	}
	
	public EntityAttribute put(String key, EntityAttribute arg) {
		if (arg!=null)
		{
			EntityAttributeGroup group = groups.get( arg.groupName );
			if (group == null)
			{
				group = new EntityAttributeGroup( arg.groupName, entityKind.name() );
				groups.put( group.name, group );
			}
			group.add( arg );
		}
		
		return super.put( key, arg );
	}

	public String[] getStaticAttributesArray()
	{
		return this.entityKind.getKeys();
	}
	
	public Object put(T key, Object arg) {
		
		if (arg != null && key != null)
			return this.put( key.name(), new EntityAttribute( key.name(), arg, "static", arg.getClass().getName() ) );
		else
			return null;
	}

	public int getId()
	{
		if ( get(idAttributeName) != null )
			return (Integer)get( idAttributeName ).value;
		else
			return -1;
	}
	
	public void addEmptyGenericAttributes( HashMap<String, EntityAttributeGroup> newGroups )
	{
		for( EntityAttributeGroup g: newGroups.values())
			for ( EntityAttribute a : g )
				if ( get( a.name ) == null )
				{	
					a.emptyValue();
					this.put( a.name, a );
				}
	}
	
	public void removeEmptyAttributes()
	{
		for( EntityAttribute a : this.values() )
			if (a.value == null)
				removeAttribute( a );
	}
	
	private void removeAttribute(EntityAttribute a) 
	{
		EntityAttributeGroup g = groups.get( a.groupName );
		if (g != null)
			g.remove( a );
		this.remove( a );
	}


	public String toString()
	{
		StringBuffer output = new StringBuffer();
		
		output.append( this.getClass().getName() );
		if ( this.getId()!=-1)
			output.append( '(' ).append( this.getId() ).append(')').append(System.getProperty( "line.separator" ) );
		for ( String key : this.keySet() )
				output.append( key ).append('=').append( this.get(key).value ).append(System.getProperty( "line.separator" ) );
		return output.toString();
	}
}


