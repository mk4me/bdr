package motion.database.model;

@SuppressWarnings("serial")
public class SessionGroup extends GenericDescription<SessionGroupStaticAttributes>{

	public SessionGroup() {
		super(SessionGroupStaticAttributes.SessionGroupID.name(), EntityKind.sessionGroup);
		put(SessionGroupStaticAttributes.SessionGroupID, -1);
	}
	
	public String toString() {
		if (super.get(SessionStaticAttributes.SessionLabel.toString()) != null)
			return super.get(SessionStaticAttributes.SessionLabel.toString()).value.toString();
		else
			return "Session " + super.getId() + " (no label)";
	}

	public SessionGroup(int sessionGroupID, String name) {
		super(SessionGroupStaticAttributes.SessionGroupID.name(), EntityKind.sessionGroup);
		put(SessionGroupStaticAttributes.SessionGroupID, sessionGroupID);
		put(SessionGroupStaticAttributes.Name, name);
	}

}
