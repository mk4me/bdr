//package motion.database.ws;
//
//import java.net.PasswordAuthentication;
//import java.util.ArrayList;
//import java.util.logging.Logger;
//
//
//import org.apache.axis2.client.Options;
//import org.apache.axis2.context.MessageContextConstants;
//import org.apache.axis2.transport.http.HttpTransportProperties;
//import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;
//import org.apache.commons.httpclient.HostConfiguration;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.Credentials;
//import org.apache.commons.httpclient.NTCredentials;
//import org.apache.commons.httpclient.auth.AuthScheme;
//import org.apache.commons.httpclient.auth.CredentialsNotAvailableException;
//import org.apache.commons.httpclient.auth.CredentialsProvider;
//import org.apache.commons.httpclient.auth.NTLMScheme;
//import org.apache.commons.httpclient.params.DefaultHttpParams;
//import org.apache.commons.httpclient.params.HttpParams;
//
//import motion.database.DbElementsList;
//import motion.database.model.Session;
//import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
//import motion.database.ws.basicQueriesServiceWCF.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult;
//import motion.database.ws.basicUpdatesServiceAxis.BasicQueriesWSStub;
//import motion.database.ws.basicUpdatesServiceAxis.BasicQueriesWSStub.ListLabSessionsWithAttributesXML;
//import motion.database.ws.basicUpdatesServiceAxis.BasicQueriesWSStub.ListLabSessionsWithAttributesXMLResponse;
//
//public class DatabaseConnectionAxis extends DatabaseConnectionWCF {
//
//	public DatabaseConnectionAxis(Logger log) {
//		super(log);
//	}
//	
//
//	public  DbElementsList<Session> listLabSessionsWithAttributes(int labID) throws Exception
//	{
//		BasicQueriesWSStub port = new BasicQueriesWSStub();
//
//		//Options options = new Options();
//		 
//		HttpClient client = new HttpClient();
//		
//		HttpTransportProperties.Authenticator
//		   auth = new HttpTransportProperties.Authenticator();
//		auth.setUsername("applet_user");
//		auth.setPassword("aplet4Motion");
//		auth.setHost("db-bdr.pjwstk.edu.pl");
//		auth.setDomain("db-bdr");
//		auth.setRealm("db-bdr.pjwstk.edu.pl");
//		ArrayList l = new ArrayList();
//		l.add( Authenticator.NTLM );
//		auth.setAuthSchemes(l);
//		//auth.setPort(443);
//		
//		CredentialsProvider myCredentialsProvider = new CredentialsProvider() {
//			public org.apache.commons.httpclient.Credentials getCredentials(final AuthScheme scheme, final String host, int port, boolean proxy) throws CredentialsNotAvailableException {
//				NTCredentials nt = new NTCredentials( "applet_user", "aplet4Motion", "db-bdr.pjwstk.edu.pl", "db-bdr" );
//				return nt; 
//			}
//		};
//
//		//org.apache.commons.httpclient.params.HttpConnectionParams.
//		
//		DefaultHttpParams.getDefaultParams().setParameter("http.authentication.credential-provider", myCredentialsProvider);
//
//		
//		// set if realm or domain is known
//
//		port._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
//		//port._getServiceClient().setOptions(options);
//
//		BasicQueriesWSStub.ListLabSessionsWithAttributesXML p = new ListLabSessionsWithAttributesXML();
//		p.setLabID( 1 );
//		//port._getServiceClient().se
//		ListLabSessionsWithAttributesXMLResponse result = port.listLabSessionsWithAttributesXML(p);
//		DbElementsList<Session> output = new DbElementsList<Session>();
//		
//		
//		System.err.println("oK");
//		System.out.println( "JEEEST" + result );
//		 
//		return output;
//	}
//
////	public  DbElementsList<Session> listLabSessionsWithAttributes(int labID) throws Exception
////	{
//////		pl.edu.pjwstk.bytom.ruch.MotionDB.BasicQueriesService.BasicHttpBinding_IBasicQueriesWSStub service = new pl.edu.pjwstk.bytom.ruch.MotionDB.BasicQueriesService.BasicHttpBinding_IBasicQueriesWSStub();
//////		HttpClient client = new HttpClient();
////
////		HttpTransportProperties.ProxyProperties proxyProperties = 
////			new HttpTransportProperties.ProxyProperties();
////
//////		proxyProperties.setProxyName("axis2");
//////		proxyProperties.setProxyPort(9762);
////		proxyProperties.setProxyName("db-bdr.pjwstk.edu.pl");
////		proxyProperties.setDomain("db-bdr");
////		proxyProperties.setPassWord("aplet4Motion");
////		proxyProperties.setUserName("applet_user");
////		
////		CredentialsProvider myCredentialsProvider = new CredentialsProvider() {
////            public org.apache.commons.httpclient.Credentials getCredentials(final AuthScheme scheme, final String host, int port, boolean proxy) throws CredentialsNotAvailableException {
////        		NTCredentials nt = new NTCredentials( "applet_user", "aplet4Motion", "db-bdr.pjwstk.edu.pl", "db-bdr" );
////                return nt; 
////            }
////        };
////
////        DefaultHttpParams.getDefaultParams().setParameter("http.authentication.credential-provider", myCredentialsProvider);
//////        DefaultHttpParams.getDefaultParams().setParameter( Call.USERNAME_PROPERTY, "applet_user");
//////        DefaultHttpParams.getDefaultParams().setParameter( Call.PASSWORD_PROPERTY, "aplet4Motion");
////	
////		BasicQueriesWSStub port = new BasicQueriesWSStub();
////		Options options = port._getServiceClient().getOptions();
////
////		//in order to makesure that we use HTTP 1.0
////		//options.setProperty(MessageContextConstants.HTTP_PROTOCOL_VERSION,
////		//HTTPConstants.HEADER_PROTOCOL_10);
////
////		options.setProperty(org.apache.axis2.transport.http.HTTPConstants.PROXY,
////		proxyProperties);
////		options.setProperty("http.authentication.credential-provider", myCredentialsProvider );
////	       // DefaultHttpParams.getDefaultParams().setParameter("http.authentication.credential-provider", myCredentialsProvider);
////
////		//		AxisEngine engine = service.getEngine();
////		
////		
//////		pl.edu.pjwstk.bytom.ruch.MotionDB.BasicQueriesService.IBasicQueriesWS port = service.getBasicHttpBinding_IBasicQueriesWS();
////
////	
//////		((Stub)port)._setProperty( Call.USERNAME_PROPERTY, "applet_user" );
//////		((Stub)port)._setProperty( Call.PASSWORD_PROPERTY, "aplet4Motion" );
////		
//// 
//////        DefaultHttpParams.getDefaultParams().setParameter("http.authentication.credential-provider", myCredentialsProvider);
////
////		
////		//HttpClient client = new HttpClient();
////		
////		BasicQueriesWSStub.ListLabSessionsWithAttributesXML p = new ListLabSessionsWithAttributesXML();
////		p.setLabID( 1 );
////		ListLabSessionsWithAttributesXMLResponse result = port.listLabSessionsWithAttributesXML(p);
////		DbElementsList<Session> output = new DbElementsList<Session>();
////		
/////*		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listLabSessionsWithAttributes", this );
////	
////		ListLabSessionsWithAttributesXMLResult result = port.listLabSessionsWithAttributesXML(labID);
////		
////		for ( motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes s : result.getLabSessionWithAttributesList().getSessionDetailsWithAttributes() )
////			output.add( ToolsWCF.transformSessionDetails(s) );
////		
////		ToolsWCF.finalizeCall();
////*/
////		
////		System.err.println("oK");
////		System.out.println( "JEEEST" + result );
////		 
////		return output;
////	}
//
//}
