package motion.database.model;


@SuppressWarnings("serial")
public class Performer extends GenericDescription<PerformerStaticAttributes>{

	public Performer() {
		super(PerformerStaticAttributes.performerID.name(), EntityKind.performer);
	}
	
	public String toString() {
		
		return super.get(PerformerStaticAttributes.firstName.toString()).value.toString() + " " +
			super.get(PerformerStaticAttributes.lastName.toString()).value.toString();
	}
}