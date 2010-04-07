package motion.database;


@SuppressWarnings("serial")
public class Session extends GenericDescription<SessionStaticAttributes>{

	public Session() {
		super(SessionStaticAttributes.sessionID.name(), EntityKind.session);
	}
}