package motion.database;

public class Connector {
	private java.sql.Connection connection = null;
	private final String url = "jdbc:sqlserver://";
	private final String serverName= "172.21.220.19";//"127.0.0.1";
	private final String portNumber = "1433";//"1434";
	private final String databaseName= "Motion";//"MotionDatabase";
	private final String userName = "applet";//"MotionApplet";
	private final String password = "motion#motion2X";//"xose2344";
	private final String selectMethod = "cursor"; 
	
	public Connector() {
		
	}
	
	private String getConnectionUrl() {
		return url +
	 		serverName + ":" + portNumber +
	 		";databaseName=" + databaseName +
	 		";selectMethod=" + selectMethod+";";
	}
	
	public java.sql.Connection openConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
			connection = java.sql.DriverManager.getConnection(getConnectionUrl(), userName, password);
			if (connection != null) {
				System.out.println("Connection Successful!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Trace in getConnection() : " + e.getMessage());
		}
		
		return connection;
	}
	
	public void displayDatabaseProperties() {
		java.sql.DatabaseMetaData databaseMetaData = null;
		java.sql.ResultSet resultSet = null;
		
		try {
			connection = this.openConnection();
			if (connection != null) {
				databaseMetaData = connection.getMetaData();
				System.out.println("Driver Information");
				System.out.println("\tDriver Name: "+ databaseMetaData.getDriverName());
				System.out.println("\tDriver Version: "+ databaseMetaData.getDriverVersion ());
				System.out.println("\nDatabase Information ");
				System.out.println("\tDatabase Name: "+ databaseMetaData.getDatabaseProductName());
				System.out.println("\tDatabase Version: "+ databaseMetaData.getDatabaseProductVersion());
				System.out.println("Avalilable Catalogs ");
				
				resultSet = databaseMetaData.getCatalogs();
				while (resultSet.next()) {
					System.out.println("\tcatalog: " + resultSet.getString(1));
				}
				resultSet.close();
				resultSet = null;
				this.closeConnection();
			} else {
				System.out.println("Error: No active Connection");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		databaseMetaData = null;
	}
	
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDatabaseName() {
		
		return this.databaseName;
	}
}
