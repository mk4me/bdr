package motion.database.model;


@SuppressWarnings("serial")
public class PerformerConfiguration extends GenericDescription<PerformerConfigurationStaticAttributes>{

	public PerformerConfiguration() {
		super(PerformerConfigurationStaticAttributes.PerformerID.name(), EntityKind.performer);
	}
	
	public String toString() {
		
		return super.get(PerformerConfigurationStaticAttributes.FirstName.toString()).value.toString() + " " +
			super.get(PerformerConfigurationStaticAttributes.LastName.toString()).value.toString();
	}
	
}