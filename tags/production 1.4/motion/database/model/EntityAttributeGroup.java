package motion.database.model;

import java.util.Vector;

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
