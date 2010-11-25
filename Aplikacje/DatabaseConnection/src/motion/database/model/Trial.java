package motion.database.model;

import motion.database.DbElementsList;


@SuppressWarnings("serial")
public class Trial extends GenericDescription<TrialStaticAttributes>{

	/*
	 * Trial child elements  
	 */
	
	public DbElementsList<DatabaseFile>			files;	

	
	public Trial() {
		super(TrialStaticAttributes.TrialID.name(), EntityKind.trial);
	}
	
	public String toString() {
		
		return "Trial " + super.get(TrialStaticAttributes.TrialID.toString()).value.toString();
	}
}