package motion.database.ws;

import motion.database.ws.DatabaseConnectionOld.ConnectionState;
import motion.database.ws.basicQueriesServiceWCF.BasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;

public class ToolsWCF {

	private static void prepareCall(IBasicQueriesWS port) {
		// TODO Auto-generated method stub
		
	}

	
	public static IBasicQueriesWS getBasicQueriesPort( String callerName, DatabaseConnectionWCF db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		
		db.log.entering( "DatabaseConnectionWCF", callerName );

		BasicQueriesWS service = new BasicQueriesWS();
		IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
		
		prepareCall(port);

		return port;
	}
}
