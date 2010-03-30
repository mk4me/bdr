package motion.applet.database;

public class TableNamesInstance {
	public static final TableName PERFORMER = new TableName(TableName.PERFORMER_TABLE, "Performer");
	public static final TableName SESSION = new TableName(TableName.SESSION_TABLE, "Session");
	public static final TableName TRIAL = new TableName(TableName.TRIAL_TABLE, "Trial");
	public static final TableName PATIENT = new TableName(TableName.PATIENT_TABLE, "Patient");
	
	public static TableName toTableName(String table) {
		if (table.equals(TableName.PERFORMER_TABLE)) {
			return PERFORMER;
		} else if (table.equals(TableName.SESSION_TABLE)) {
			return SESSION;
		} else if (table.equals(TableName.TRIAL_TABLE)) {
			return TRIAL;
		} else if (table.equals(TableName.PATIENT_TABLE)) {
			return PATIENT;
		} else {
			return null;
		}
	}
}
