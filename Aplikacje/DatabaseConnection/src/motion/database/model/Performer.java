package motion.database.model;


@SuppressWarnings("serial")
public class Performer extends GenericDescription<PerformerStaticAttributes>{

	public Performer() {
		super(PerformerStaticAttributes.PerformerID.name(), EntityKind.performer);
	}
	
	public String toString() {
		
		return super.get(PerformerStaticAttributes.FirstName.toString()).value.toString() + " " +
			super.get(PerformerStaticAttributes.LastName.toString()).value.toString();
	}
	
}