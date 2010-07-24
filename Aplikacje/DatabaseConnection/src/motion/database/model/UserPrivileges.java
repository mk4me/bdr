package motion.database.model;


@SuppressWarnings("serial")
public class UserPrivileges extends GenericDescription<UserPrivilegesStaticAttributes>{

	public UserPrivileges() {
		super(UserPrivilegesStaticAttributes.login.name(), EntityKind.userPrivileges);
	}

	public String[] toStringArray()
	{
		return new String[]{ this.getValue(UserPrivilegesStaticAttributes.login).toString(), 
				this.getValue(UserPrivilegesStaticAttributes.canRead).toString(),
				this.getValue(UserPrivilegesStaticAttributes.canWrite).toString() };
	}

}