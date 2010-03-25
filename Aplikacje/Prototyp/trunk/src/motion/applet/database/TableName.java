package motion.applet.database;

public class TableName {
	private final String table;
	private final String label;
	
	public TableName(String table, String label) {
		this.table = table;
		this.label = label;
	}
	
	public String toString() {
		
		return this.label;
	}
	
	public String getTableName() {
		
		return this.table;
	}
}
