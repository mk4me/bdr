package motion.applet.database;

public class TableNamesInstance {
	private static final String PERFORMER_TABLE = "Performer";
	private static final String SESSION_TABLE = "Sesja";
	private static final String TRIAL_TABLE = "Obserwacja";
	private static final String PATIENT_TABLE = "Pacjent";
	
	public static final TableName PERFORMER = new TableName(PERFORMER_TABLE, "Performer");
	public static final TableName SESSION = new TableName(SESSION_TABLE, "Session");
	public static final TableName TRIAL = new TableName(TRIAL_TABLE, "Trial");
	public static final TableName PATIENT = new TableName(PATIENT_TABLE, "Patient");
	
	public static TableName toTableName(String table) {
		if (table.equals(PERFORMER_TABLE)) {
			return PERFORMER;
		} else if (table.equals(SESSION_TABLE)) {
			return SESSION;
		} else if (table.equals(TRIAL_TABLE)) {
			return TRIAL;
		} else if (table.equals(PATIENT_TABLE)) {
			return PATIENT;
		} else {
			return null;
		}
	}
}
