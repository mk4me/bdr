package motion.applet.database;

public class ConnectorInstance {
	private static Connector connector;
	
	private ConnectorInstance() {
		connector = new Connector();
	}
	
	public static Connector getConnector() {
		if (connector == null) {
			new ConnectorInstance();
		}
		
		return connector;
	}
}
