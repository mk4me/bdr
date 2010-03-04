package motion.applet.webservice.client;

import motion.database.DatabaseConnection;

public class WebServiceInstance {
	private static DatabaseConnection databaseConnection;
	
	private WebServiceInstance() {
		databaseConnection = DatabaseConnection.getInstance();
		databaseConnection.setWSCredentials("applet", "motion#motion2X", "pjwstk");
		databaseConnection.setFTPSCredentials("dbpawell", "testUser", "testUser");
	}
	
	public static DatabaseConnection getDatabaseConnection() {
		if (databaseConnection == null) {
			new WebServiceInstance();
		}
		
		return databaseConnection;
	}
}
