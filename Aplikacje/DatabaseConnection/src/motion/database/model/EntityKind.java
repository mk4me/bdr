package motion.database.model;

import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;

public enum EntityKind {

	performer(PerformerStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setPerformerAttribute(ID, a.name, a.value.toString(), update);			
		}
	}, 
	session(SessionStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setSessionAttribute(ID, a.name, a.value.toString(), update);			
		}
	}, 
	trial(TrialStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setTrialAttribute(ID, a.name, a.value.toString(), update);			
		}
	}, 
	segment(SegmentStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setSegmentAttribute(ID, a.name, a.value.toString(), update);			
		}
	},
	
	file(DatabaseFileStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setFileAttribute(ID, a.name, a.value.toString(), update);			
		}
	}, 
	user(UserStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("User entity does not support generic attributes!");
		}
	}, 
	userPrivileges(UserPrivilegesStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("UserPrivileges entity does not support generic attributes!");
		}
	},
	result(null) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			throw new Exception("Result entity does not support generic attributes!");
		}
	}, 
	userBasket (UserBasketStaticAttributes.class) {
		@Override
		public void setEntityAttribute(IBasicUpdatesWS port, int ID,
				EntityAttribute a, boolean update) throws Exception {
			port.setSegmentAttribute(ID, a.name, a.value.toString(), update);			
		}
	},;
	
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
	
	abstract public void setEntityAttribute(IBasicUpdatesWS port, int ID, EntityAttribute a, boolean update) throws Exception;
}
