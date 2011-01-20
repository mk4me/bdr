package motion.database.model;

import java.util.HashMap;

import javax.xml.datatype.XMLGregorianCalendar;

import motion.database.DatabaseConnection;

/**
 * A generic class which models common behavior for all entities which may contain generic attributes.
 * All attributes are kept in a HashMap for which names of attributes are keys and EntityAttribute objects are values.
 * 
 * This class and all its subclasses are designed in such a way that a programmer gets support from the 
 * compiler in accessing generic and static attributes. Therefore String keys from the hash map are exchanged for enumeration.
 * 
 * For an example usage have a look at Performer and PerformerStaticAttributes classes.
 * 
 * @author kk
 *
 * @param <T> Enumeration containing set of static attributes for given entity.
 */
@SuppressWarnings("serial")
public abstract class GenericDescription<T extends Enum<T>> extends HashMap<String, EntityAttribute>
{
	/**
	 * Most of the entities have one attribute which describes ID of an entity instance. 
	 * Since in each entity this attribute may have different name, this field defines the name of this attribute.
	 */
	String idAttributeName;
	/**
	 * EntityKind enumeration value connected to this entity. 
	 */
	public EntityKind entityKind;
	/**
	 * HashMap of attribute groups. Name of the group is the key, while EntityAttributeGroup is the value. 
	 */
	public HashMap<String, EntityAttributeGroup> groups;

	
	public GenericDescription(String name, EntityKind entityKind)
	{
		this.idAttributeName = name;
		this.groups = new HashMap<String, EntityAttributeGroup>();
		this.entityKind = entityKind;
	}
	
	public String getIdAttributeName()
	{
		return idAttributeName;
	}
	
	/**
	 * Returns an attribute for given static attribute name. The key should be taken from
	 * the enumeration of available static attributes for this entity. 
	 * 
	 * @param key of static attribute (from T enumeration)
	 * @return attribute object (may be null if there is no attribute for given key)
	 */
	public Object get(T key) {
		return super.get(key.name());
	}

	/**
	 * Returns an attribute value for given static attribute name. The key should be taken from
	 * the enumeration of available static attributes for this entity. 
	 * 
	 * @param key of static attribute (from T enumeration)
	 * @return value of an attribute (may be null)
	 */
	public Object getValue(T key) {
		return ((EntityAttribute)super.get(key.name())).value;
	}
	
	/**
	 * Stores an attribute under given key. 
	 *  
	 * @param key must be taken from the enumeration of available static attributes (T)
	 * @param arg entity attribute to be stored 
	 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.) 
	 */
	public EntityAttribute put(T key, EntityAttribute arg) {
		return put(key.name(), arg);
	}
	
	/**
	 * Stores an attribute under given name. This method should be used for generic attributes.
	 * Attribute is automatically added to appropriate
	 * group. Name of this group should be set inside arg correctly. 
	 *  
	 * @param key string with name of a generic attribute
	 * @param arg entity attribute to be stored 
	 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
	 */
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

	/**
	 * An array of static attributes names getter.
	 * 
	 * @return array with names of all static attributes for this entity.
	 */
	public String[] getStaticAttributesArray()
	{
		return this.entityKind.getKeys();
	}
	
	/**
	 * Stores a value of attribute under given key (static attribute). An attribute is
	 * created automatically as a container for this value.
	 * 
	 * @param key static attribute key (from T enumeration)
	 * @param arg value
	 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
	 */
	public Object put(T key, Object arg) {
		
		if (arg != null && key != null)
			return this.put( key.name(), new EntityAttribute( key.name(), this.entityKind, arg, "_static", EntityAttribute.getTypeName(arg) ) );
		else
			return null;
	}

	/**
	 * Returns ID value for this entity. 
	 * 
	 * @return ID value, -1 in case of error or no ID set
	 */
	public int getId()
	{
		if ( get(idAttributeName) != null )
		{
			if ( get( idAttributeName ).value instanceof Integer )
				return (Integer)get( idAttributeName ).value;
			else if (get( idAttributeName ).type.equals( EntityAttribute.DB_TYPE_INTEGER ) 
					&& get( idAttributeName ).value instanceof String)
				return Integer.parseInt( (String)get( idAttributeName ).value );
			else if (get( idAttributeName ).type.equals( EntityAttribute.DB_TYPE_STRING ))
				return ((String)get( idAttributeName ).value).hashCode();
			else
			{
				DatabaseConnection.log.severe("Cannot return ID value of unconvertable type: " + 
						get( idAttributeName ).type);
				return -1;
			}
		}
		else
		{
			DatabaseConnection.log.severe("No ID attribute for this entity!");
			return -1;
		}
	}
	
	/**
	 * This method adds given generic attributes but with empty values. 
	 * 
	 * @param newGroups group of attributes to be added to this entity
	 */
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
	
	/**
	 * Removes all attributes which have empty values.
	 * 
	 */
	public void removeEmptyAttributes()
	{
		for( EntityAttribute a : this.values() )
			if (a.value == null)
				removeAttribute( a );
	}
	
	/**
	 * Removes and attribute.
	 * 
	 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
	 */
	private void removeAttribute(EntityAttribute a) 
	{
		EntityAttributeGroup g = groups.get( a.groupName );
		if (g != null)
			g.remove( a );
		this.remove( a );
	}

	/**
	 * Informative method. Converts all content of this entity to a string with all attributes.
	 * 
	 *  @return string of all attributes
	 */
	public String toStringAllAttributes()
	{
		StringBuffer output = new StringBuffer();
		
		output.append( this.getClass().getName() );
		try {
			if ( this.getId()!=-1)
				output.append( '(' ).append( this.getId() ).append(')').append(System.getProperty( "line.separator" ) );
			for ( String key : this.keySet() )
					output.append( key ).append('=').append( this.get(key).value ).append(System.getProperty( "line.separator" ) );
		} catch (Exception e) {
			DatabaseConnection.log.severe( e.getMessage() );
		}
		return output.toString();
	}

	/**
	 * Standard to string methods. In this case it returns all attributes (including values) enumerated in a string.
	 */
	public String toString()
	{
		return toStringAllAttributes();
	}

	/**
	 * Static version converting an entity to a string containing all attributes.
	 * 
	 * @param d an entity
	 * @return string with attributes
	 */
	public static final String toStringAllAttributes( GenericDescription<?> d )
	{
		return d.toString();
	}
}


