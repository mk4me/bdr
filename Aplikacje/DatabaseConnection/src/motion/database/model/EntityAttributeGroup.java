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

}
