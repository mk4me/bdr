package motion.applet.database;

public class TableNamesInstance {
	public static final TableName PERFORMER = new TableName(TableName.PERFORMER_ENTITY, TableName.PERFORMER_ENTITY, TableName.PERFORMER_LABEL);
	public static final TableName SESSION = new TableName(TableName.SESSION_TABLE, TableName.SESSION_ENTITY, TableName.SESSION_LABEL);
	public static final TableName TRIAL = new TableName(TableName.TRIAL_TABLE, TableName.TRIAL_ENTITY, TableName.TRIAL_LABEL);
	public static final TableName PATIENT = new TableName(TableName.PATIENT_TABLE, TableName.PATIENT_ENTITY, TableName.PATIENT_LABEL);
	public static final TableName FILE = new TableName(TableName.FILE_TABLE, TableName.FILE_ENTITY, TableName.FILE_LABEL);
	
	public static final TableName allTableNames[] = { PERFORMER, SESSION, TRIAL, FILE };
	
	public static TableName toTableName(String table) {
		if (table.equals(TableName.PERFORMER_TABLE) || table.equals(TableName.PERFORMER_ENTITY)) {
			return PERFORMER;
		} else if (table.equals(TableName.SESSION_TABLE) || table.equals(TableName.SESSION_ENTITY)) {
			return SESSION;
		} else if (table.equals(TableName.TRIAL_TABLE) || table.equals(TableName.TRIAL_ENTITY)) {
			return TRIAL;
		} else if (table.equals(TableName.PATIENT_TABLE) || table.equals(TableName.PATIENT_ENTITY)) {
			return PATIENT;
		} else if (table.equals(TableName.FILE_TABLE) || table.equals(TableName.FILE_ENTITY)) {
			return FILE;
		} else {
			return null;
		}
	}
}
