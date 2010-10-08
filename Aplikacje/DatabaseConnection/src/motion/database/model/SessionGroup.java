package motion.database.model;

@SuppressWarnings("serial")
public class SessionGroup extends GenericDescription<SessionGroupStaticAttributes>{

	public SessionGroup() {
		super(SessionGroupStaticAttributes.SessionGroupID.name(), EntityKind.sessionGroup);
		put(SessionGroupStaticAttributes.SessionGroupID, -1);
	}
	
	public String toString() {
			return "Session Group " + ((EntityAttribute)get( SessionGroupStaticAttributes.SessionGroupID ) ).value + " " + ((EntityAttribute)get( SessionGroupStaticAttributes.Name)).value;
	}

	public SessionGroup(int sessionGroupID, String name) {
		super(SessionGroupStaticAttributes.SessionGroupID.name(), EntityKind.sessionGroup);
		put(SessionGroupStaticAttributes.SessionGroupID, sessionGroupID);
		put(SessionGroupStaticAttributes.Name, name);
	}

}
