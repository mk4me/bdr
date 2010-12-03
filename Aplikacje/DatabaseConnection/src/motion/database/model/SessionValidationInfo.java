package motion.database.model;

import java.util.List;

public class SessionValidationInfo {

	public Session session;
	public List<String> errors;
	
	public SessionValidationInfo( Session session, List<String> errors)
	{
		this.session = session;
		this.errors = errors;
	}
}
