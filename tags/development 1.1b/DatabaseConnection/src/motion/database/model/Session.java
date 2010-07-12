package motion.database.model;


@SuppressWarnings("serial")
public class Session extends GenericDescription<SessionStaticAttributes>{

	public Session() {
		super(SessionStaticAttributes.sessionID.name(), EntityKind.session);
	}
	
	public String toString() {
		if (super.get(SessionStaticAttributes.sessionLabel.toString()) != null)
			return super.get(SessionStaticAttributes.sessionLabel.toString()).value.toString();
		else
			return "Session " + super.getId() + " (no label)";
	}
}