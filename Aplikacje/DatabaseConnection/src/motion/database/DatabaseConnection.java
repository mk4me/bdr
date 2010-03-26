package motion.database;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import motion.database.ws.basicQueriesService.ArrayOfPlainSessionDetails;
import motion.database.ws.basicQueriesService.Attributes;
import motion.database.ws.basicQueriesService.BasicQueriesService;
import motion.database.ws.basicQueriesService.BasicQueriesServiceSoap;
import motion.database.ws.basicQueriesService.FileWithAttributesList;
import motion.database.ws.basicQueriesService.PerformerWithAttributesList;
import motion.database.ws.basicQueriesService.PlainSessionDetails;
import motion.database.ws.basicQueriesService.SessionTrialWithAttributesList;
import motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList;
import motion.database.ws.basicQueriesService.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesService.FileWithAttributesList.FileDetailsWithAttributes;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesService.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.PerformerSessionWithAttributesList.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesService.PerformerWithAttributesList.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesService.SessionTrialWithAttributesList.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList.SegmentDetailsWithAttributes;
import motion.database.ws.basicUpdateService.ArrayOfInt;
import motion.database.ws.basicUpdateService.BasicUpdatesService;
import motion.database.ws.basicUpdateService.BasicUpdatesServiceSoap;
import motion.database.ws.basicUpdateService.PerformerData;
import motion.database.ws.fileStoremanService.FileStoremanService;
import motion.database.ws.fileStoremanService.FileStoremanServiceSoap;
import motion.database.ws.test.SqlResultStream;
import motion.database.ws.test.TestWs;
import motion.database.ws.test.TestWsSoap;

import com.zehon.BatchTransferProgress;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftps.FTPs;


public class DatabaseConnection {

	public static class Credentials {
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

	enum ConnectionState{ INITIALIZED, CONNECTED, ABORTED, CLOSED, UNINITIALIZED };
	
	private ConnectionState state;
	private Credentials wsCredentials = new Credentials();
	private Credentials ftpsCredentials = new Credentials();
	private Authenticator authenticator;
	private FileTransferSupport fileTransferSupport = new FileTransferSupport();

	private static DatabaseConnection instance;
	private static Logger log;
	
	public static final String LOG_ID = "DatabaseConnection";
	public static final String LOG_FILE_NAME = "DatabaseConnection.log";
	
	static{
		FileHandler hand;
		ConsoleHandler cons;
		try {
			hand = new FileHandler( LOG_FILE_NAME );
			cons = new ConsoleHandler();
			hand.setFormatter( new SimpleFormatter() );
			
			log = Logger.getLogger( LOG_ID );
		    log.addHandler(hand);
		    log.addHandler( cons );
		    hand.setLevel( Level.ALL);
		    cons.setLevel( Level.INFO );
		    log.setLevel( Level.ALL );
		    //log.setFilter(null);
		    log.finer( "Database Connection Log created" );
		} catch (Exception e) {
			System.out.println("Cannot create logger!");
			e.printStackTrace();
		}
	}
	
	public static DatabaseConnection getInstance()
	{
		if (instance==null)
			instance = new DatabaseConnection();
		return instance;
	}
	
	private DatabaseConnection()
	{
		this.state = ConnectionState.UNINITIALIZED;
	};
	
	public void setWSCredentials(String userName, String password, String domainName)
	{
		this.wsCredentials.userName = userName;
		this.wsCredentials.password = password;
		this.wsCredentials.domainName = domainName;
		this.state = ConnectionState.INITIALIZED;
	
		this.authenticator = new Authenticator() {
			
			protected PasswordAuthentication getPasswordAuthentication() {
				Logger log = Logger.getLogger( DatabaseConnection.LOG_ID );
				log.entering("Authenticator", "getPasswordAuthentication");
				log.fine( "Host: " + this.getRequestingHost() );
				log.fine( "Prompt: " + this.getRequestingPrompt() );
				log.fine( "Protocol: " + this.getRequestingProtocol() );
				log.fine( "Sheme: " + this.getRequestingScheme() );
				log.fine( "Site: " + this.getRequestingSite() );
				log.fine( "Requestor Type: " + this.getRequestorType() );
				
				// W niekt�rych przypadkach przed username trzeba poda� domain oddzielone backslashem
				// my te� tak robimy na wszelki wypadek 
				return new PasswordAuthentication(instance.wsCredentials.domainName+"\\"+instance.wsCredentials.userName, instance.wsCredentials.password.toCharArray() );
			  }
		};
		
        Authenticator.setDefault( this.authenticator );

        System.setProperty( "http.auth.preference", "ntlm");
    	System.setProperty( "java.security.krb5.conf", "krb5.conf");
    	System.setProperty( "java.security.auth.login.config", "login.conf");
    	System.setProperty( "javax.security.auth.useSubjectCredsOnly", "false");
    	System.setProperty( "http.auth.ntlm.domain", domainName);

	}
	
	public void setFTPSCredentials(String address, String userName, String password)
	{
		this.ftpsCredentials.setAddress(address);
		this.ftpsCredentials.setCredentials(userName, password, null);
	}
	
	private void prepareCall(BindingProvider port)
	{
		((BindingProvider)port).getRequestContext().put( BindingProvider.USERNAME_PROPERTY, this.wsCredentials.domainName+"\\"+this.wsCredentials.userName);
        ((BindingProvider)port).getRequestContext().put( BindingProvider.PASSWORD_PROPERTY, this.wsCredentials.password );
	}
	
	
	public boolean testConnection() throws Exception
	{
		log.entering( "DatabaseConnection", "testConnection" );

		TestWs service = new TestWs();
		TestWsSoap port = service.getTestWs();
		prepareCall( (BindingProvider)port );
		
		SqlResultStream response = port.testWm();
		
		 for ( Object r: response.getSqlRowSetOrSqlXmlOrSqlMessage() )
         	System.out.println(r.getClass() + "  " + r);
		 
		log.exiting( "DatabaseConnection", "testConnection", true );
		
		return true;
	}
	
	public void registerFileUploadListener( FileTransferListener listener )
	{
		this.fileTransferSupport.registerUploadListener(listener);
	}
	
	private void putFile(String localFilePath, String destRemoteFolder, FileTransferListener listener) throws FileTransferException
	{
		fileTransferSupport.resetUploadListeners();
		if (listener != null)
			fileTransferSupport.registerUploadListener(listener);
		
		try {
			fileTransferSupport.putFile(localFilePath, destRemoteFolder, ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
		} catch (Exception e) {
			log.severe( e.getMessage() );
			e.printStackTrace();
		}
	}
	
	private void createRemoteFolder( String newFolder, String destRemoteFolder ) throws FileTransferException
	{
		int status = FTPs.createFolder( newFolder, destRemoteFolder, 
				this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password);
		if(FileTransferStatus.SUCCESS == status){
			log.info( newFolder + " created in folder "+destRemoteFolder);
		}
		else if(FileTransferStatus.FAILURE == status){
			log.severe("Fail to ftps  to  folder "+destRemoteFolder);
		}
	}
	
	// TODO: Kasowanie pliku po sobie na serwerze
	public void uploadSessionFile(int sessionId, String description, String localFilePath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";
			putFile(localFilePath, destRemoteFolder, listener);			
			
		    FileStoremanService service = new FileStoremanService();
			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
			prepareCall( (BindingProvider)port );

			port.storeSessionFile(sessionId, "", description, destRemoteFolder+new File(localFilePath).getName() );
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	// TODO: Kasowanie pliku po sobie na serwerze
	public void uploadTrialFile(int trialId, String description, String localFilePath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";
			putFile(localFilePath, destRemoteFolder, listener);			
		    
			FileStoremanService service = new FileStoremanService();
			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
			prepareCall( (BindingProvider)port );

			port.storeTrialFile(trialId, "", description, destRemoteFolder+new File(localFilePath).getName() );
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	// TODO: Kasowanie pliku po sobie na serwerze
	public void uploadPerformerFile(int performerId, String description, String localFilePath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";
			putFile(localFilePath, destRemoteFolder, listener);			
		    
			FileStoremanService service = new FileStoremanService();
			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
			prepareCall( (BindingProvider)port );

			port.storePerformerFile( performerId, "", description, destRemoteFolder+new File(localFilePath).getName() );
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	class BatchTransferProgressObserver implements BatchTransferProgress
	{

		@Override
		public void transferComplete(String arg0) {
			System.out.println( "Transfer zako�czono: " + arg0);
		}

		@Override
		public void transferError(String arg0, Throwable arg1) {
			System.out.println( "Transfer error: " + arg0);
		}

		@Override
		public void transferStart(String arg0) {
			System.out.println( "Transfer rozpocz�to: " + arg0);
		}
	}
	
	public void uploadSessionFiles(int sessionId, String filesPath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";

			File dir = new File(filesPath);
			if ( dir.isDirectory() )
			{
				createRemoteFolder( dir.getName(), destRemoteFolder );
				FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
/*				destRemoteFolder += dir.getName();
				for( String singleFile : dir.list() )
				{
					putFile( singleFile, destRemoteFolder, listener );			
	
				}
*/
				FileStoremanService service = new FileStoremanService();
				FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
				prepareCall( (BindingProvider)port );
				port.storeSessionFiles( sessionId, destRemoteFolder+dir.getName(), "TODO: add description" );
			}
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}
	
	public void uploadPerformerFiles(int performerId, String filesPath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";

			File dir = new File(filesPath);
			if ( dir.isDirectory() )
			{
				createRemoteFolder( dir.getName(), destRemoteFolder );
				FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
				FileStoremanService service = new FileStoremanService();
				FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
				prepareCall( (BindingProvider)port );
				port.storePerformerFiles( performerId, destRemoteFolder+dir.getName() );
			}
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	
	public void uploadTrialFiles(int trialId, String filesPath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";

			File dir = new File(filesPath);
			if ( dir.isDirectory() )
			{
				createRemoteFolder( dir.getName(), destRemoteFolder );
				FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
				FileStoremanService service = new FileStoremanService();
				FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
				prepareCall( (BindingProvider)port );
				port.storeTrialFiles( trialId, destRemoteFolder+dir.getName() );
			}
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	
	public  DbElementsList<Performer> listPerformersWithAttributes() throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessions" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListPerformersWithAttributesXMLResult result = port.listPerformersWithAttributesXML();

			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			for (Object o : result.getContent())
			{
				PerformerWithAttributesList ss = (motion.database.ws.basicQueriesService.PerformerWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( PerformerDetailsWithAttributes s : ss.getPerformerDetailsWithAttributes() )
				{
					Performer performer = new Performer();
					performer.put( PerformerStaticAttributes.performerID, s.getPerformerID() );
					performer.put( PerformerStaticAttributes.firstName, s.getFirstName() );
					performer.put( PerformerStaticAttributes.lastName, s.getLastName() );
					
					transformGenericAttributes( s.getAttributes(), performer );
					output.add( performer );
				}
			}
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list performers.");
	}
	
	
	@Deprecated
	public  DbElementsList<Session> listPerformerSessions(int performerID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessions" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ArrayOfPlainSessionDetails result = port.listPerformerSessions(performerID);
		
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			for (PlainSessionDetails s : result.getPlainSessionDetails())
			{
				Session session = new Session();
				session.put( SessionStaticAttributes.labID, s.getLabID() );
				session.put( SessionStaticAttributes.motionKindID, s.getMotionKindID() );
				session.put( SessionStaticAttributes.performerID, s.getPerformerID() );
				session.put( SessionStaticAttributes.sessionDate, s.getSessionDate() );
				session.put( SessionStaticAttributes.sessionDescription, s.getSessionDescription() );
				session.put( SessionStaticAttributes.sessionID, s.getSessionID() );
				session.put( SessionStaticAttributes.userID, s.getUserID() );
				output.add( session );
			}
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	
	public HashMap<String, String> listAttributesDefined(String group, String entityKind) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listAttributesDefined" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
		
			ListAttributesDefinedResult result = port.listAttributesDefined( group, entityKind);
			
			HashMap<String, String> output = new HashMap<String, String>();
			for( Object o : result.getContent() )
			{
				motion.database.ws.basicQueriesService.AttributeDefinitionList attr = (motion.database.ws.basicQueriesService.AttributeDefinitionList)o;//(((JAXBElement<?>)o).getValue());
				for (AttributeDefinition a : attr.getAttributeDefinition() )
					output.put( a.getAttributeName(), a.getAttributeType() );
			}
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list attributes.");
	}
	
	
	public  DbElementsList<Session> listPerformerSessionsWithAttributes(int performerID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessionsWithAttributes" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListPerformerSessionsWithAttributesXMLResult result = port.listPerformerSessionsWithAttributesXML(performerID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			for (Object o : result.getContent())
			{
				motion.database.ws.basicQueriesService.PerformerSessionWithAttributesList ss = (motion.database.ws.basicQueriesService.PerformerSessionWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
				{
					Session session = new Session();
					session.put( SessionStaticAttributes.labID, s.getLabID() );
					session.put( SessionStaticAttributes.motionKindID, s.getMotionKindID() );
					session.put( SessionStaticAttributes.performerID, s.getPerformerID() );
					session.put( SessionStaticAttributes.sessionDate, s.getSessionDate() );
					session.put( SessionStaticAttributes.sessionDescription, s.getSessionDescription() );
					session.put( SessionStaticAttributes.sessionID, s.getSessionID() );
					session.put( SessionStaticAttributes.userID, s.getUserID() );
					transformGenericAttributes( s.getAttributes(), session );
					output.add( session );
				}
			}
			
			log.exiting( "DatabaseConnection", "listPerformerSessionsWithAttributes", result );
			
			//System.out.println( result );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}


	public  DbElementsList<Trial> listSessionTrialsWithAttributes(int sessionID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionTrialsWithAttributes" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListSessionTrialsWithAttributesXMLResult result = port.listSessionTrialsWithAttributesXML(sessionID);
			DbElementsList<Trial> output = new DbElementsList<Trial>();
			
			for (Object o : result.getContent())
			{
				SessionTrialWithAttributesList ss = (motion.database.ws.basicQueriesService.SessionTrialWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( TrialDetailsWithAttributes s : ss.getTrialDetailsWithAttributes() )
				{
					Trial trial = new Trial();
					trial.put( TrialStaticAttributes.trialID, s.getTrialID() );
					trial.put( TrialStaticAttributes.duration, s.getDuration() );
					trial.put( TrialStaticAttributes.sessionID, s.getSessionID() );
					trial.put( TrialStaticAttributes.trialDescription, s.getTrialDescription() );
					transformGenericAttributes( s.getAttributes(), trial );
					output.add( trial );
				}
			}
			
			log.exiting( "DatabaseConnection", "listPerformerSessionsWithAttributes", result );
			
			//System.out.println( result );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}


	
	public int createPerformer(String name, String surname) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "createPerformer" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			PerformerData performerData = new PerformerData();
			performerData.setName( name );
			performerData.setSurname( surname );

			int result = port.createPerformer(performerData);
			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

	// TODO: sessionGroupID -- nie jest przekazywane 
	public int createSession(int performerID, int [] sessionGroupID, String sessionDescription, int labID, int userID, XMLGregorianCalendar sessionDate, String motionKindName ) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "createSession" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ArrayOfInt sessionGroupIDs = new ArrayOfInt();
			
			int result = port.createSession(userID, labID, motionKindName, performerID, sessionDate, sessionDescription, sessionGroupIDs);
			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create session.");
	}

		
	public int createTrial(int sessionID, String trialDescription, int trialDuration ) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "createTrial" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.createTrial(sessionID, trialDescription, trialDuration);
			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

	public int defineTrialSegment(int trialID, String segmentName, int startTime, int endTime ) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "defineTrialSegment" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.defineTrialSegment(trialID, segmentName, startTime, endTime);
			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

	
	
	public int setSessionAttribute(int sessionID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setPerformerAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setSessionAttribute(sessionID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

	public int setTrialAttribute(int trialID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setPerformerAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setTrialAttribute(trialID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

	public int setPerformerAttribute(int performerID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setPerformerAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setPerformerAttribute(performerID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

	public int setSegmentAttribute(int segmentID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setPerformerAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setSegmentAttribute(segmentID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

	public int setFileAttribute(int fileID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setPerformerAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setFileAttribute(fileID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot create performer.");
	}

			
	public  DbElementsList<Segment> listTrialSegmentsWithAttributes(int trialID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listTrialSegmentsWithAttributes" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
			ListTrialSegmentsWithAttributesXMLResult result = port.listTrialSegmentsWithAttributesXML(trialID);
			DbElementsList<Segment> output = new DbElementsList<Segment>();
			
			for (Object o : result.getContent())
			{
				TrailSegmentWithAttributesList ss = (motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( SegmentDetailsWithAttributes s : ss.getSegmentDetailsWithAttributes() )
				{
					Segment segment = new Segment();
					segment.put( SegmentStaticAttributes.endTime, s.getEndTime() );
					segment.put( SegmentStaticAttributes.segmentID, s.getSegmentID() );
					segment.put( SegmentStaticAttributes.segmentName, s.getSegmentName() );
					segment.put( SegmentStaticAttributes.startTime, s.getStartTime() );
					segment.put( SegmentStaticAttributes.trialID, s.getTrialID() );
					transformGenericAttributes( s.getAttributes(), segment );
					output.add( segment );
				}
			}
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	public  DbElementsList<DatabaseFile> listSessionFiles(int sessionID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionFiles" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(sessionID, "session");

			return transformListOfFiles(result);
		}
		else
			throw new Exception("Not Initialized. Cannot perform file listing.");
	}

	
	private DbElementsList<DatabaseFile> transformListOfFiles(Object result) {

		DbElementsList<DatabaseFile> list = new DbElementsList<DatabaseFile>();
		ListFilesWithAttributesXMLResult r = (ListFilesWithAttributesXMLResult) result;
		List<Object> l = r.getContent();
		List<FileDetailsWithAttributes> f = ((FileWithAttributesList)l.get(0)).getFileDetailsWithAttributes();
		for (FileDetailsWithAttributes d: f)
		{
			DatabaseFile df = new DatabaseFile();
			df.put( DatabaseFileStaticAttributes.fileID, d.getFileID() );
			df.put( DatabaseFileStaticAttributes.fileName, d.getFileName() );
			df.put( DatabaseFileStaticAttributes.fileDescription, d.getFileDescription() );
			transformGenericAttributes( d.getAttributes(), df );
			list.add( df );
		}

		return list;
	}

	/**
	 * @param attributes
	 * @param destinationObject
	 */
	private void transformGenericAttributes(Attributes attributes, GenericDescription<?> destinationObject) {
		if (attributes != null)
			if (attributes.getAttribute() != null)
				for (  motion.database.ws.basicQueriesService.Attributes.Attribute att : attributes.getAttribute() )
				{
					Object value = att.getValue();
					destinationObject.put(att.getName(), value );
				}
	}


	public  DbElementsList<DatabaseFile> listTrialFiles(int trialID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listTrialFiles" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(trialID, "trial");
		
			return transformListOfFiles(result);
		}
		else
			throw new Exception("Not Initialized. Cannot perform file listing.");
	}

	public  DbElementsList<DatabaseFile> listPerformerFiles(int performerID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerFiles" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(performerID, "performer");
		
			return transformListOfFiles(result);
		}
		else
			throw new Exception("Not Initialized. Cannot perform file listing.");
	}

	/**
	 * 
	 * @param fileID
	 * @param destLocalFolder must end with "/"
	 * @param transferListener 
	 * @return
	 * @throws Exception
	 */
	public  String downloadFile(int fileID, String destLocalFolder, FileTransferListener transferListener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionFiles" );
	
		    FileStoremanService service = new FileStoremanService();
			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
			prepareCall( (BindingProvider)port);
	
			String file = port.retrieveFile(fileID);
			
			File remoteFile = new File ( file );

			getFile( remoteFile.getName(), remoteFile.getParent(), 
					this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password,
					destLocalFolder, transferListener );

			port.downloadComplete(fileID);
			
			return destLocalFolder + remoteFile.getName();
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	private void getFile(String remoteFileName, String remoteFilePath, String address,
			String userName, String password, String destLocalFolder,
			FileTransferListener transferListener) {

		fileTransferSupport.resetDownloadListeners();
		fileTransferSupport.registerDownloadListener(transferListener);
		
		try {
			fileTransferSupport.getFile(remoteFileName, remoteFilePath, remoteFileName, destLocalFolder, address, userName, password );
		} catch (Exception e) {
			log.severe( e.getMessage() );
			e.printStackTrace();
		}
	}
}
