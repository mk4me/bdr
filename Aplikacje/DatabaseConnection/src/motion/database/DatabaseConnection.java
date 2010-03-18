package motion.database;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.ws.BindingProvider;

import motion.database.ws.basicQueriesService.ArrayOfPlainFileDetails;
import motion.database.ws.basicQueriesService.ArrayOfPlainSessionDetails;
import motion.database.ws.basicQueriesService.BasicQueriesService;
import motion.database.ws.basicQueriesService.BasicQueriesServiceSoap;
import motion.database.ws.basicQueriesService.PlainFileDetails;
import motion.database.ws.basicQueriesService.PlainSessionDetails;
import motion.database.ws.basicQueriesService.SessionTrialWithAttributesList;
import motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList;
import motion.database.ws.basicQueriesService.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialFilesXMLResponse.ListTrialFilesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.PerformerSessionWithAttributesList.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesService.SessionTrialWithAttributesList.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList.SegmentDetailsWithAttributes;
import motion.database.ws.basicUpdateService.BasicUpdatesService;
import motion.database.ws.basicUpdateService.BasicUpdatesServiceSoap;
import motion.database.ws.basicUpdateService.PerformerData;
import motion.database.ws.fileStoremanService.FileStoremanService;
import motion.database.ws.fileStoremanService.FileStoremanServiceSoap;
import motion.database.ws.test.SqlResultStream;
import motion.database.ws.test.TestWs;
import motion.database.ws.test.TestWsSoap;

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

	// TODO: Modify to call trial upload file service 
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

			port.storeSessionFile(trialId, "", description, destRemoteFolder+new File(localFilePath).getName() );
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	
	// TODO: testing 
	public void uploadSessionFiles(int sessionId, String filesPath, FileTransferListener listener) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";

			File dir = new File(filesPath);
			if ( dir.isDirectory() )
			{
				createRemoteFolder( dir.getName(), destRemoteFolder );
				destRemoteFolder += dir.getName();
				for( String singleFile : dir.list() )
				{
					putFile( singleFile, destRemoteFolder, listener );			
					FileStoremanService service = new FileStoremanService();
					FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
					prepareCall( (BindingProvider)port );
	
					port.storeSessionFiles( sessionId, destRemoteFolder+new File(singleFile).getName() );
				}
			}
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}
	
	
	@Deprecated
	public  List<Session> listPerformerSessions(int performerID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessions" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ArrayOfPlainSessionDetails result = port.listPerformerSessions(performerID);
		
			ArrayList<Session> output = new ArrayList<Session>();
			
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
			
			
			log.exiting( "DatabaseConnection", "listPerformerSessions", result.getPlainSessionDetails() );
			
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
	
	
	public  List<Session> listPerformerSessionsWithAttributes(int performerID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessionsWithAttributes" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListPerformerSessionsWithAttributesXMLResult result = port.listPerformerSessionsWithAttributesXML(performerID);
			ArrayList<Session> output = new ArrayList<Session>();
			
			for (Object o : result.getContent())
			{
				Session session = new Session();
				motion.database.ws.basicQueriesService.PerformerSessionWithAttributesList ss = (motion.database.ws.basicQueriesService.PerformerSessionWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
				{
					session.put( SessionStaticAttributes.labID, s.getLabID() );
					session.put( SessionStaticAttributes.motionKindID, s.getMotionKindID() );
					session.put( SessionStaticAttributes.performerID, s.getPerformerID() );
					session.put( SessionStaticAttributes.sessionDate, s.getSessionDate() );
					session.put( SessionStaticAttributes.sessionDescription, s.getSessionDescription() );
					session.put( SessionStaticAttributes.sessionID, s.getSessionID() );
					session.put( SessionStaticAttributes.userID, s.getUserID() );
					if (s.getAttributes() != null)
						if (s.getAttributes().getAttribute() != null)
							for (  motion.database.ws.basicQueriesService.Attributes.Attribute att : s.getAttributes().getAttribute() )
							{
								Object value = att.getValue();
								session.put(att.getName(), value );
							}
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


	public  List<Trial> listSessionTrialsWithAttributes(int sessionID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionTrialsWithAttributes" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListSessionTrialsWithAttributesXMLResult result = port.listSessionTrialsWithAttributesXML(sessionID);
			ArrayList<Trial> output = new ArrayList<Trial>();
			
			for (Object o : result.getContent())
			{
				Trial trial = new Trial();
				SessionTrialWithAttributesList ss = (motion.database.ws.basicQueriesService.SessionTrialWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( TrialDetailsWithAttributes s : ss.getTrialDetailsWithAttributes() )
				{
					trial.put( TrialStaticAttributes.trialID, s.getTrialID() );
					trial.put( TrialStaticAttributes.duration, s.getDuration() );
					trial.put( TrialStaticAttributes.sessionID, s.getSessionID() );
					trial.put( TrialStaticAttributes.trialDescription, s.getTrialDescription() );
					if (s.getAttributes() != null)
						if (s.getAttributes().getAttribute() != null)
							for (  motion.database.ws.basicQueriesService.Attributes.Attribute att : s.getAttributes().getAttribute() )
							{
								Object value = att.getValue();
								trial.put(att.getName(), value );
							}
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

	
	public  List<Segment> listTrialSegmentsWithAttributes(int trialID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listTrialSegmentsWithAttributes" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListTrialSegmentsWithAttributesXMLResult result = port.listTrialSegmentsWithAttributesXML(trialID);
			ArrayList<Segment> output = new ArrayList<Segment>();
			
			for (Object o : result.getContent())
			{
				Segment segment = new Segment();
				TrailSegmentWithAttributesList ss = (motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList)o;//(((JAXBElement<?>)o).getValue());
				for ( SegmentDetailsWithAttributes s : ss.getSegmentDetailsWithAttributes() )
				{
					segment.put( SegmentStaticAttributes.endTime, s.getEndTime() );
					segment.put( SegmentStaticAttributes.segmentID, s.getSegmentID() );
					segment.put( SegmentStaticAttributes.segmentName, s.getSegmentName() );
					segment.put( SegmentStaticAttributes.startTime, s.getStartTime() );
					segment.put( SegmentStaticAttributes.trialID, s.getTrialID() );
					if (s.getAttributes() != null)
						if (s.getAttributes().getAttribute() != null)
							for (  motion.database.ws.basicQueriesService.Attributes.Attribute att : s.getAttributes().getAttribute() )
							{
								Object value = att.getValue();
								segment.put(att.getName(), value );
							}
					output.add( segment );
				}
			}
			
			log.exiting( "DatabaseConnection", "listPerformerSessionsWithAttributes", result );
			
			//System.out.println( result );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	// TODO: Czy mo¿emy uznaæ, ¿ê PlainFileDetails to jest typ ogólno dostêpny -- poza fasad¹?
	public  List<PlainFileDetails> listSessionFiles(int sessionID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionFiles" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ArrayOfPlainFileDetails result = port.listSessionFiles(sessionID);
		
			log.exiting( "DatabaseConnection", "listSessionFiles", result.getPlainFileDetails() );
			
			return result.getPlainFileDetails();
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	// TODO: Czy ka¿da us³uga informuj¹ca o plikach nie mo¿e zwracaæ tego samego typu?
	// TODO: Czy to nie mo¿e byæ ta sama us³uga rozró¿niajaca rodzaj pliku poprzez parametr: session, trial, performer ? 
	public  List<PlainFileDetails> listTrialFiles(int trialID) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listTrialFiles" );
	
			BasicQueriesService service = new BasicQueriesService();
			BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
			
			prepareCall( (BindingProvider)port);
	
			ListTrialFilesXMLResult result = port.listTrialFilesXML(trialID);
		
			return null;//(List<PlainFileDetails>)result.getContent();
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
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
