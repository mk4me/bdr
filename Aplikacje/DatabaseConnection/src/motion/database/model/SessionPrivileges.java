package motion.database.model;

import motion.database.DbElementsList;

public class SessionPrivileges extends DbElementsList<UserPrivileges>
{
	public int sessionID;
	public boolean publicRead;
	public boolean publicWrite;
}
