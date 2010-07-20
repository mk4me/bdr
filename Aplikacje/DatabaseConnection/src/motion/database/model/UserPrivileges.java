package motion.database.model;


@SuppressWarnings("serial")
public class UserPrivileges extends GenericDescription<UserPrivilegesStaticAttributes>{

	public UserPrivileges() {
		super(UserPrivilegesStaticAttributes.login.name(), EntityKind.userPrivileges);
	}
}