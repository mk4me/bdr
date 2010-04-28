package motion.database.model;


@SuppressWarnings("serial")
public class Performer extends GenericDescription<PerformerStaticAttributes>{

	public Performer() {
		super(PerformerStaticAttributes.performerID.name(), EntityKind.performer);
	}
}