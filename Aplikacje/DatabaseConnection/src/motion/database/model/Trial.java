package motion.database.model;


@SuppressWarnings("serial")
public class Trial extends GenericDescription<TrialStaticAttributes>{

	public Trial() {
		super(TrialStaticAttributes.trialID.name(), EntityKind.trial);
	}
	
	public String toString() {
		
		return "Trial " + super.get(TrialStaticAttributes.trialID.toString()).value.toString() + ", Session " +
			super.get(TrialStaticAttributes.sessionID.toString()).value.toString();
	}
}