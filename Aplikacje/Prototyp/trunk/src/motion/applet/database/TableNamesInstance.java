package motion.applet.database;

public class TableNamesInstance {
	public static final TableName PERFORMER = new TableName(TableName.PERFORMER_TABLE, TableName.PERFORMER_TABLE_ENG);
	public static final TableName SESSION = new TableName(TableName.SESSION_TABLE, TableName.SESSION_TABLE_ENG);
	public static final TableName TRIAL = new TableName(TableName.TRIAL_TABLE, TableName.TRIAL_TABLE_ENG);
	public static final TableName PATIENT = new TableName(TableName.PATIENT_TABLE, TableName.PATIENT_TABLE_ENG);
	public static final TableName FILE = new TableName(TableName.FILE_TABLE, TableName.FILE_TABLE_ENG);
	
	public static TableName toTableName(String table) {
		if (table.equals(TableName.PERFORMER_TABLE) || table.equals(TableName.PERFORMER_TABLE_ENG)) {
			return PERFORMER;
		} else if (table.equals(TableName.SESSION_TABLE) || table.equals(TableName.SESSION_TABLE_ENG)) {
			return SESSION;
		} else if (table.equals(TableName.TRIAL_TABLE) || table.equals(TableName.TRIAL_TABLE_ENG)) {
			return TRIAL;
		} else if (table.equals(TableName.PATIENT_TABLE) || table.equals(TableName.PATIENT_TABLE_ENG)) {
			return PATIENT;
		} else if (table.equals(TableName.FILE_TABLE) || table.equals(TableName.FILE_TABLE_ENG)) {
			return FILE;
		} else {
			return null;
		}
	}
}
