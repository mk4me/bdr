package motion.database.model;

import motion.database.DbElementsList;


@SuppressWarnings("serial")
public class Session extends GenericDescription<SessionStaticAttributes>{

	/*
	 * Session child elements  
	 */
	
	public DbElementsList<Trial> 			trials;
	public DbElementsList<DatabaseFile>			files;	
	
	public Session() {
		super(SessionStaticAttributes.SessionID.name(), EntityKind.session);
	}
	
	public String toString() {
		if (super.get(SessionStaticAttributes.SessionLabel.toString()) != null)
			return super.get(SessionStaticAttributes.SessionLabel.toString()).value.toString();
		else
			return "Session " + super.getId() + " (no label)";
	}
}