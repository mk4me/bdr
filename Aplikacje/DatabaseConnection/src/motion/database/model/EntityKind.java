package motion.database.model;

public enum EntityKind {

	performer(PerformerStaticAttributes.class), 
	session(SessionStaticAttributes.class), 
	trial(TrialStaticAttributes.class), 
	segment(SegmentStaticAttributes.class), 
	file(DatabaseFileStaticAttributes.class), 
	user(UserStaticAttributes.class), 
	userPrivileges(UserPrivilegesStaticAttributes.class),
	result(null);
	
	private Class<?> staticAttributes;

	EntityKind(Class<?> staticAttributes)
	{
		this.staticAttributes = staticAttributes;
	}
	
	public String[] getKeys()
	{
		Object[] k = (staticAttributes).getEnumConstants();
		String [] result = new String[k.length];
		int i = 0;
		for( Object o : k )
			result[i++] = o.toString();
		return result;
	}
}
