package motion.database.model;


@SuppressWarnings("serial")
public class PerformerConfiguration extends GenericDescription<PerformerConfigurationStaticAttributes>{

	public PerformerConfiguration() {
		super(PerformerConfigurationStaticAttributes.PerformerConfigurationID.name(), EntityKind.performer_conf);
	}
	
	public String toString() {
		
		return super.getValue(PerformerConfigurationStaticAttributes.PerformerConfigurationID).toString() + " Session:" +
			super.getValue(PerformerConfigurationStaticAttributes.SessionID).toString() + " Performer:" + 
			super.getValue(PerformerConfigurationStaticAttributes.PerformerID).toString();
	}
}