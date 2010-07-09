package motion.database.model;


@SuppressWarnings("serial")
public class Session extends GenericDescription<SessionStaticAttributes>{

	public Session() {
		super(SessionStaticAttributes.sessionID.name(), EntityKind.session);
	}
	
	public String toString() {
		
		return super.get(SessionStaticAttributes.sessionLabel.toString()).value.toString();
	}
}