package motion.database.model;

import java.util.Vector;

/**
 * This class models group of attributes as they exist on the server. 
 * A group may be defined for one entity kind. 
 * A group may have also GUI visibility set for users (in the same way as generic attributes).
 * 
 */
@SuppressWarnings("serial")
public class EntityAttributeGroup extends Vector<EntityAttribute>{

	
	public EntityKind kind;
	public String name;
	public boolean isVisible;
	
	public EntityAttributeGroup(String name, String entityKind)
	{
		super();
		this.name = name;
		this.kind = EntityKind.valueOf( entityKind );
		this.isVisible = true;
	}
	
	public EntityAttributeGroup(String name, EntityKind kind)
	{
		super();
		this.name = name;
		this.kind = kind;
		this.isVisible = true;
	}
		
	public String toString()
	{
		return name;
	}
	
	/**
	 * This methods has linear complexity since group in an extension of a normal vector. In case of insufficient 
	 * performance implementation of attribute group should be changed to use a hash table. 
	 * 
	 * @param name of an attribute to search for
	 * @return attribute found or null
	 */
	public EntityAttribute findAttributeByName(String name)
	{
		for (EntityAttribute a : this)
			if (a.name.equals( name ))
				return a;
		return null;
	}
	
	public boolean equals(Object o)
	{
		if( o instanceof EntityAttributeGroup)
		{
			EntityAttributeGroup g = (EntityAttributeGroup) o;
			return ( this.name.equals(g.name) && this.kind.equals(g.kind) );
		}
		else
			return false;
	}
}
