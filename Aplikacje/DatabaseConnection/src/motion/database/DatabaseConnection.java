package motion.database;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import motion.database.ws.DatabaseConnection2;


public class DatabaseConnection {

/*	public static class Credentials {
		public String userName;
		public String password;
		public String domainName;
		public String address;

		public void setCredentials(String userName, String password, String domainName)
		{
			this.userName = userName;
			this.password = password;
			this.domainName = domainName;
		}
		
		public void setAddress(String address)
		{
			this.address = address;
		}
	}
*/
	enum ConnectionState{ INITIALIZED, CONNECTED, ABORTED, CLOSED, UNINITIALIZED };
	
	private static DatabaseProxy instance;
	public static Logger log;
	
	public static final String LOG_ID = "DatabaseConnection";
	public static final String LOG_FILE_NAME = "DatabaseConnection.log";
	
	static{
		FileHandler hand;
		ConsoleHandler cons;
		log = Logger.getLogger( LOG_ID );
		try {
			hand = new FileHandler( LOG_FILE_NAME );
			hand.setFormatter( new SimpleFormatter() );
		    log.addHandler(hand);
		    hand.setLevel( Level.ALL);
		    log.setLevel( Level.ALL );
		    //log.setFilter(null);
		    log.finer( "Database Connection File Log created" );
		} catch (Exception e) {
		}
		try {
			cons = new ConsoleHandler();
		    log.addHandler( cons );
		    cons.setLevel( Level.INFO );
		    log.setLevel( Level.ALL );
		    //log.setFilter(null);
		    log.finer( "Database Connection Console Log created" );
		} catch (Exception e) {
		}
	}

	/**
	 * Same as getInstanceWCF.
	 * 
	 * @return returns connection facade to the new BDR WCF Web Services 
	 */
	public static DatabaseProxy getInstance()
	{
		return getInstanceWCF();
	}

	public static DatabaseProxy getInstanceWCF()
	{
		if (instance==null)
			instance = new DatabaseConnection2(log);
		return instance;
	}
	
	public static Logger getLogger()
	{
		return log;
	}
}
