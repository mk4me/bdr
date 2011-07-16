package motion.database.model;


@SuppressWarnings("serial")
public class Performer extends GenericDescription<PerformerStaticAttributes>{

	public Performer() {
		super(PerformerStaticAttributes.PerformerID.name(), EntityKind.performer);
	}
	
	public String toString() {
		
		return "Performer ID: " + super.getId();
	}
	
}