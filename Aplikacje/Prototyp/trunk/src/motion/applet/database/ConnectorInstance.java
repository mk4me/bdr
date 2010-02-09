package motion.applet.database;

public class ConnectorInstance {
	private static Connector connector;
	
	public ConnectorInstance() {
		connector = new Connector();
	}
	
	public static Connector getConnector() {
		
		return connector;
	}
}
