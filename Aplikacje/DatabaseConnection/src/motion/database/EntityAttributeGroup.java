package motion.database;

import java.util.Vector;

@SuppressWarnings("serial")
public class EntityAttributeGroup extends Vector<EntityAttribute>{

	
	public String entityKind;
	public String name;

	public EntityAttributeGroup(String name, String entityKind)
	{
		super();
		this.name = name;
		this.entityKind = entityKind;
	}
	
	public String toString()
	{
		return name;
	}

}
