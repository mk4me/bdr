package motion.database.model;


@SuppressWarnings("serial")
public class User extends GenericDescription<UserStaticAttributes>{

	public User() {
		super(UserStaticAttributes.login.name(), EntityKind.user);
	}
}