package motion.database.model;


@SuppressWarnings("serial")
public class Trial extends GenericDescription<TrialStaticAttributes>{

	public Trial() {
		super(TrialStaticAttributes.TrialID.name(), EntityKind.trial);
	}
	
	public String toString() {
		
		return "Trial " + super.get(TrialStaticAttributes.TrialID.toString()).value.toString();
	}
}