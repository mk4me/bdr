package motion.database;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.bouncycastle.asn1.cms.Time;

import motion.database.ws.basicQueriesService.ArrayOfPlainSessionDetails;
import motion.database.ws.basicQueriesService.AttributeDefinitionList;
import motion.database.ws.basicQueriesService.AttributeGroupDefinitionList;
import motion.database.ws.basicQueriesService.Attributes;
import motion.database.ws.basicQueriesService.BasicQueriesService;
import motion.database.ws.basicQueriesService.BasicQueriesServiceSoap;
import motion.database.ws.basicQueriesService.FileWithAttributesList;
import motion.database.ws.basicQueriesService.LabPerformerWithAttributesList;
import motion.database.ws.basicQueriesService.LabSessionWithAttributesList;
import motion.database.ws.basicQueriesService.MotionKindDefinitionList;
import motion.database.ws.basicQueriesService.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesService.PerformerWithAttributesList;
import motion.database.ws.basicQueriesService.PlainSessionDetails;
import motion.database.ws.basicQueriesService.SegmentDetailsWithAttributes;
import motion.database.ws.basicQueriesService.SessionGroupDefinitionList;
import motion.database.ws.basicQueriesService.SessionTrialWithAttributesList;
import motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList;
import motion.database.ws.basicQueriesService.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesService.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesService.AttributeGroupDefinitionList.AttributeGroupDefinition;
import motion.database.ws.basicQueriesService.FileWithAttributesList.FileDetailsWithAttributes;
import motion.database.ws.basicQueriesService.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult;
import motion.database.ws.basicQueriesService.GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult;
import motion.database.ws.basicQueriesService.GetSessionByIdXMLResponse.GetSessionByIdXMLResult;
import motion.database.ws.basicQueriesService.GetTrialByIdXMLResponse.GetTrialByIdXMLResult;
import motion.database.ws.basicQueriesService.ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesService.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.MotionKindDefinitionList.MotionKindDefinition;
import motion.database.ws.basicQueriesService.SessionGroupDefinitionList.SessionGroupDefinition;
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
				
				// W niektórych przypadkach przed username trzeba podaæ domain oddzielone backslashem
				// my te¿ tak robimy na wszelki wypadek 
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
			createRemoteFolder( destRemoteFolder, "" );
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
			String destRemoteFolder = getUniqueFolderName();
			putFile(localFilePath, destRemoteFolder, listener);			
			
		    FileStoremanService service = new FileStoremanService();
			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
			prepareCall( (BindingProvider)port );

			port.storeSessionFile(sessionId, "", description, destRemoteFolder+new File(localFilePath).getName() );
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	private String getUniqueFolderName() {
		return wsCredentials.userName + System.currentTimeMillis()+"/";
	}

	// TODO: Kasowanie pliku po sobie na serwerze
	public void uploadTrialFile(int trialId, String description, String localFilePath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = getUniqueFolderName();
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
			String destRemoteFolder = getUniqueFolderName();
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
			System.out.println( "Transfer zakoñczono: " + arg0);
		}

		@Override
		public void transferError(String arg0, Throwable arg1) {
			System.out.println( "Transfer error: " + arg0);
		}

		@Override
		public void transferStart(String arg0) {
			System.out.println( "Transfer rozpoczêto: " + arg0);
		}
	}
	
	public void uploadSessionFiles(int sessionId, String filesPath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = getUniqueFolderName();

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
			String destRemoteFolder = getUniqueFolderName();

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
			String destRemoteFolder = getUniqueFolderName();

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
				for ( motion.database.ws.basicQueriesService.PerformerDetailsWithAttributes s : ss.getPerformerDetailsWithAttributes() )
					output.add( transformPerformerDetails(s) );
			}
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list performers.");
	}


	public  DbElementsList<Performer> listLabPerformersWithAttributes(int labID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessions" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListLabPerformersWithAttributesXMLResult result = port.listLabPerformersWithAttributesXML(labID);

			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			for (Object o : result.getContent())
			{
				LabPerformerWithAttributesList ss = (motion.database.ws.basicQueriesService.LabPerformerWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( motion.database.ws.basicQueriesService.PerformerDetailsWithAttributes s : ss.getPerformerDetailsWithAttributes() )
					output.add( transformPerformerDetails(s) );
			}
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list performers.");
	}

	
	/**
	 * @param s
	 * @return
	 */
	private Performer transformPerformerDetails(
			motion.database.ws.basicQueriesService.PerformerDetailsWithAttributes s) {
		if (s==null)
			return null;
		
		Performer performer = new Performer();
		performer.put( PerformerStaticAttributes.performerID, s.getPerformerID() );
		performer.put( PerformerStaticAttributes.firstName, s.getFirstName() );
		performer.put( PerformerStaticAttributes.lastName, s.getLastName() );
		transformGenericAttributes( s.getAttributes(), performer );
		return performer;
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
				AttributeDefinitionList attr = (AttributeDefinitionList)o;//(((JAXBElement<?>)o).getValue());
				for (AttributeDefinition a : attr.getAttributeDefinition() )
					output.put( a.getAttributeName(), a.getAttributeType() );
			}
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list attributes.");
	}

	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(String entityKind) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listGrouppedAttributesDefined" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
		
			ListAttributesDefinedResult result = port.listAttributesDefined( "_ALL", entityKind);
			
			HashMap<String, EntityAttributeGroup> output = new HashMap<String, EntityAttributeGroup>();
			for( Object o : result.getContent() )
			{
				AttributeDefinitionList attr = (AttributeDefinitionList)o;
				for (AttributeDefinition a : attr.getAttributeDefinition() )
				{	
					EntityAttributeGroup group = output.get( a.getAttributeGroupName() );
					if (group == null)
					{
						group = new EntityAttributeGroup( a.getAttributeGroupName(), entityKind );
						output.put( a.getAttributeGroupName(), group );
					}
					group.add( new EntityAttribute( a.getAttributeName(), null, a.getAttributeGroupName(), a.getAttributeType() ) );
				}
			}
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list attributes.");
	}

	
	
	public Vector<String> listAttributeGroupsDefined(String entityKind) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listAttributesDefined" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
		
			ListAttributeGroupsDefinedResult result = port.listAttributeGroupsDefined(entityKind);
			
			Vector<String> output = new Vector<String>();
			for( Object o : result.getContent() )
			{
				AttributeGroupDefinitionList attr = (motion.database.ws.basicQueriesService.AttributeGroupDefinitionList)o;//(((JAXBElement<?>)o).getValue());
				for (AttributeGroupDefinition a : attr.getAttributeGroupDefinition() )
					output.add( a.getAttributeGroupName() );
			}
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list attributes.");
	}


	public Vector<MotionKind> listMotionKindsDefined() throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listAttributesDefined" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
		
			ListMotionKindsDefinedResult result = port.listMotionKindsDefined();
			
			Vector<MotionKind> output = new Vector<MotionKind>();
			for( Object o : result.getContent() )
			{
				MotionKindDefinitionList attr = (motion.database.ws.basicQueriesService.MotionKindDefinitionList)o;//(((JAXBElement<?>)o).getValue());
				for (MotionKindDefinition a : attr.getMotionKindDefinition() )
					output.add( new MotionKind( a.getMotionKindID(), a.getMotionKindName() ) );
			}
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list motion kinds.");
	}

	
	public Vector<SessionGroup> listSessionGroupsDefined() throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listAttributesDefined" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
		
			ListSessionGroupsDefinedResult result = port.listSessionGroupsDefined();
			
			Vector<SessionGroup> output = new Vector<SessionGroup>();
			for( Object o : result.getContent() )
			{
				SessionGroupDefinitionList attr = (motion.database.ws.basicQueriesService.SessionGroupDefinitionList)o;//(((JAXBElement<?>)o).getValue());
				for (SessionGroupDefinition a : attr.getSessionGroupDefinition() )
					output.add( new SessionGroup( a.getSessionGroupID(), a.getSessionGroupName() ) );
			}
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list motion kinds.");
	}

	
	public Session getSessionById(int id) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			GetSessionByIdXMLResult result = port.getSessionByIdXML(id);
			motion.database.ws.basicQueriesService.SessionDetailsWithAttributes s = (motion.database.ws.basicQueriesService.SessionDetailsWithAttributes) result.getContent().get(0);
			return transformSessionDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
		
	}

	public Performer getPerformerById(int id) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			GetPerformerByIdXMLResult result = port.getPerformerByIdXML(id);
			PerformerDetailsWithAttributes s = (motion.database.ws.basicQueriesService.PerformerDetailsWithAttributes) result.getContent().get(0);
			return transformPerformerDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
	}

	public Trial getTrialById(int id) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			GetTrialByIdXMLResult result = port.getTrialByIdXML(id);
			TrialDetailsWithAttributes s = (motion.database.ws.basicQueriesService.TrialDetailsWithAttributes) result.getContent().get(0);
			return transformTrialDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
	}


	public Segment getSegmentById(int id) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			GetSegmentByIdXMLResult result = port.getSegmentByIdXML(id);
			SegmentDetailsWithAttributes s = (motion.database.ws.basicQueriesService.SegmentDetailsWithAttributes) result.getContent().get(0);
			return transformSegmentDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
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
				for ( motion.database.ws.basicQueriesService.SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
					output.add( transformSessionDetails(s) );
			}
			
			log.exiting( "DatabaseConnection", "listPerformerSessionsWithAttributes", result );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}


	public  DbElementsList<Session> listLabSessionsWithAttributes(int labID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessionsWithAttributes" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListLabSessionsWithAttributesXMLResult result = port.listLabSessionsWithAttributesXML(labID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			for (Object o : result.getContent())
			{
				LabSessionWithAttributesList ss = (motion.database.ws.basicQueriesService.LabSessionWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( motion.database.ws.basicQueriesService.SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
					output.add( transformSessionDetails(s) );
			}
			
			log.exiting( "DatabaseConnection", "listPerformerSessionsWithAttributes", result );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	
	/**
	 * @param s
	 * @return
	 */
	private Session transformSessionDetails(
			motion.database.ws.basicQueriesService.SessionDetailsWithAttributes s) {
		if(s==null)
			return null;
		
		Session session = new Session();
		session.put( SessionStaticAttributes.labID, s.getLabID() );
		session.put( SessionStaticAttributes.motionKindID, s.getMotionKindID() );
		session.put( SessionStaticAttributes.performerID, s.getPerformerID() );
		session.put( SessionStaticAttributes.sessionDate, s.getSessionDate() );
		session.put( SessionStaticAttributes.sessionDescription, s.getSessionDescription() );
		session.put( SessionStaticAttributes.sessionID, s.getSessionID() );
		session.put( SessionStaticAttributes.userID, s.getUserID() );
		transformGenericAttributes( s.getAttributes(), session );
		return session;
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
				for ( motion.database.ws.basicQueriesService.TrialDetailsWithAttributes s : ss.getTrialDetailsWithAttributes() )
					output.add( transformTrialDetails(s) );
			}
			
			log.exiting( "DatabaseConnection", "listPerformerSessionsWithAttributes", result );
			
			//System.out.println( result );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	/**
	 * @param s
	 * @return
	 */
	private Trial transformTrialDetails(
			motion.database.ws.basicQueriesService.TrialDetailsWithAttributes s) {
		if (s==null)
			return null;

		Trial trial = new Trial();
		trial.put( TrialStaticAttributes.trialID, s.getTrialID() );
		trial.put( TrialStaticAttributes.duration, s.getDuration() );
		trial.put( TrialStaticAttributes.sessionID, s.getSessionID() );
		trial.put( TrialStaticAttributes.trialDescription, s.getTrialDescription() );
		transformGenericAttributes( s.getAttributes(), trial );
		return trial;
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
			log.entering( "DatabaseConnection", "setSessionAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setSessionAttribute(sessionID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot set attribute.");
	}

	public int setTrialAttribute(int trialID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setTrialAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setTrialAttribute(trialID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot set attribute.");
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
			throw new Exception("Not Initialized. Cannot set attribute.");
	}

	public int setSegmentAttribute(int segmentID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setSegmentAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setSegmentAttribute(segmentID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot set attribute.");
	}

	public int setFileAttribute(int fileID, String attributeName, String attributeValue, boolean update) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "setFileAttribute" );
	
			BasicUpdatesService service = new BasicUpdatesService();
			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			int result = port.setFileAttribute(fileID, attributeName, attributeValue, update);			
			return result;
		}
		else
			throw new Exception("Not Initialized. Cannot set file attribute.");
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
				for ( motion.database.ws.basicQueriesService.SegmentDetailsWithAttributes s : ss.getSegmentDetailsWithAttributes() )
					output.add( transformSegmentDetails(s) );
			}
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	/**
	 * @param s
	 * @return
	 */
	private Segment transformSegmentDetails(
			motion.database.ws.basicQueriesService.SegmentDetailsWithAttributes s) {
		
		if (s==null)
			return null;

		Segment segment = new Segment();
		segment.put( SegmentStaticAttributes.endTime, s.getEndTime() );
		segment.put( SegmentStaticAttributes.segmentID, s.getSegmentID() );
		segment.put( SegmentStaticAttributes.segmentName, s.getSegmentName() );
		segment.put( SegmentStaticAttributes.startTime, s.getStartTime() );
		segment.put( SegmentStaticAttributes.trialID, s.getTrialID() );
		transformGenericAttributes( s.getAttributes(), segment );
		return segment;
	}

	public  DbElementsList<DatabaseFile> listSessionFiles(int sessionID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionFiles" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(sessionID, EntityKind.session.name());

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
					EntityAttribute attribute = new EntityAttribute(att.getName(), att.getValue(), att.getAttributeGroup(), att.getType() );
					destinationObject.put(att.getName(), attribute );
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
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(trialID, EntityKind.trial.name());
		
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
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(performerID, EntityKind.performer.name());
		
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

			port.downloadComplete(fileID, file);
			
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
