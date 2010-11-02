package motion.database;

import motion.database.model.UserPrivileges;

public class SessionPrivileges extends DbElementsList<UserPrivileges>
{
	public int sessionID;
	public boolean publicRead;
	public boolean publicWrite;
}
