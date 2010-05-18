package motion.applet.webservice.client;

import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;

public class WebServiceInstance {
	private static DatabaseProxy databaseConnection;
	
	private WebServiceInstance() {
		databaseConnection = DatabaseConnection.getInstanceWCF();
//		databaseConnection.setWSCredentials("applet", "motion#motion2X", "pjwstk");
		databaseConnection.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");
	}
	
	public static DatabaseProxy getDatabaseConnection() {
		if (databaseConnection == null) {
			new WebServiceInstance();
		}
		
		return databaseConnection;
	}
}
