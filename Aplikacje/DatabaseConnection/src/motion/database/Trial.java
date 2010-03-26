package motion.database;


@SuppressWarnings("serial")
public class Trial extends GenericDescription<TrialStaticAttributes>{

	public Trial() {
		super(TrialStaticAttributes.trialID.name());
	}
}