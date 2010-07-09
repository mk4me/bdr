package motion.database.ws;

import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.model.Privileges;

public class SessionPrivilegesSetter {

	private int sessionID;
	private Privileges privileges;

	
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
			case CUSTOM: // /Milestone 8
		}
	}
}
