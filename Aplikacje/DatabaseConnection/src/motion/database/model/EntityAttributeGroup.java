package motion.database.model;

import java.util.Vector;

@SuppressWarnings("serial")
public class EntityAttributeGroup extends Vector<EntityAttribute>{

	
	public EntityKind kind;
	public String name;

	public EntityAttributeGroup(String name, String entityKind)
	{
		super();
		this.name = name;
		this.kind = EntityKind.valueOf( entityKind );
	}
	
	public EntityAttributeGroup(String name, EntityKind kind)
	{
		super();
		this.name = name;
		this.kind = kind;
	}
		
	public String toString()
	{
		return name;
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
