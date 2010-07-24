package motion.database.ws;

import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.model.EntityAttribute;
import motion.database.model.Privileges;
import motion.database.model.UserPrivileges;
import motion.database.model.UserPrivilegesStaticAttributes;

public class SessionPrivilegesSetter {

	private int sessionID;
	private Privileges privileges;
	private DbElementsList<UserPrivileges> userPrivileges;

	
	public DbElementsList<UserPrivileges> getUserPrivileges() {
		return userPrivileges;
	}


	public void setUserPrivileges(DbElementsList<UserPrivileges> userPrivileges) {
		this.userPrivileges = userPrivileges;
	}


	public SessionPrivilegesSetter(int sessionID, Privileges privileges) {
		super();
		this.sessionID = sessionID;
		this.privileges = privileges;
	}

	
	public int getSessionID() {
		return sessionID;
	}


	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}


	public Privileges getPrivileges() {
		return privileges;
	}


	public void setPrivileges(Privileges privileges) {
		this.privileges = privileges;
	}



	public void performDatabaseUpdate() throws Exception
	{
		DatabaseProxy db = DatabaseConnection.getInstance();
		
		switch(privileges)
		{
			case PRIVATE: 
				db.setSessionPrivileges(this.sessionID, false, false); break;
			case PUBLIC_READ: 
				db.setSessionPrivileges( this.sessionID, true, false); break;
			case PUBLIC_WRITE: 
				db.setSessionPrivileges( this.sessionID, true, true); break;
			case CUSTOM: 
			{
				DbElementsList<UserPrivileges> actualPrivileges = DatabaseConnection.getInstance().listSessionPrivileges(sessionID);
				UserPrivileges wanted;
				for (UserPrivileges actual : actualPrivileges)
				{
					if ( (wanted = userPrivileges.findById( actual.getId() )) == null )
						db.removeSessionPrivileges(
								(String)((EntityAttribute)actual.get( UserPrivilegesStaticAttributes.login)).value, 
								(String)((EntityAttribute)actual.get( UserPrivilegesStaticAttributes.login)).value, 
								sessionID, 
								(Boolean)((EntityAttribute)actual.get( UserPrivilegesStaticAttributes.canWrite)).value );
					else
						db.grantSessionPrivileges( 
								(String)((EntityAttribute)wanted.get( UserPrivilegesStaticAttributes.login)).value, 
								(String)((EntityAttribute)wanted.get( UserPrivilegesStaticAttributes.login)).value, 
								sessionID, 
								(Boolean)((EntityAttribute)wanted.get( UserPrivilegesStaticAttributes.canWrite)).value );
				}
			
			}
		}
	}
}
