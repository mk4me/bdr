package motion.database;

import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.ws.BindingProvider;


import motion.database.ws.basicQueriesService.ArrayOfFileDetails;
import motion.database.ws.basicQueriesService.ArrayOfSessionDetails;
import motion.database.ws.basicQueriesService.BasicQueriesService;
import motion.database.ws.basicQueriesService.BasicQueriesServiceSoap;
import motion.database.ws.basicQueriesService.FileDetails;
import motion.database.ws.basicQueriesService.SessionDetails;
import motion.database.ws.fileStoremanService.FileStoremanService;
import motion.database.ws.fileStoremanService.FileStoremanServiceSoap;
import motion.database.ws.fileStoremanService.StoreSessionFile;
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
	
	public void uploadFile(int sessionId, String description, String localfilePath) throws Exception
	{
		if (this.state == DatabaseConnection.ConnectionState.INITIALIZED)
		{
			String destRemoteFolder = "";
			try {
				int status = FTPs.sendFile(localfilePath, destRemoteFolder, 
						this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password);
				if(FileTransferStatus.SUCCESS == status){
					log.info(localfilePath + " got ftps-ed successfully to  folder "+destRemoteFolder);
				}
				else if(FileTransferStatus.FAILURE == status){
					log.severe("Fail to ftps  to  folder "+destRemoteFolder);
				}
			} catch (FileTransferException e) {
				e.printStackTrace();
			}
			
		    FileStoremanService service = new FileStoremanService();
			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
			prepareCall( (BindingProvider)port);

			port.storeSessionFile(sessionId, "", description, destRemoteFolder+new File(localfilePath).getName() );
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}
	
	public  List<SessionDetails> listPerformerSessions(int performerID) throws Exception
	{
		log.entering( "DatabaseConnection", "listPerformerSessions" );

		BasicQueriesService service = new BasicQueriesService();
		BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
		
		prepareCall( (BindingProvider)port);

		ArrayOfSessionDetails result = port.listPerformerSessions(performerID);
	
		log.exiting( "DatabaseConnection", "listPerformerSessions", result.getSessionDetails() );
		
		return result.getSessionDetails();
	}

	public  List<FileDetails> listSessionFiles(int sessionID) throws Exception
	{
		log.entering( "DatabaseConnection", "listSessionFiles" );

		BasicQueriesService service = new BasicQueriesService();
		BasicQueriesServiceSoap port = service.getBasicQueriesServiceSoap();
		
		prepareCall( (BindingProvider)port);

		ArrayOfFileDetails result = port.listSessionFiles(sessionID);
	
		log.exiting( "DatabaseConnection", "listSessionFiles", result.getFileDetails() );
		
		return result.getFileDetails();
	}

	public  String downloadFile(int fileID, String destLocalFolder) throws Exception
	{
		log.entering( "DatabaseConnection", "listSessionFiles" );

	    FileStoremanService service = new FileStoremanService();
		FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
		prepareCall( (BindingProvider)port);

		String file = port.retrieveFile(fileID);
		
		file="sample_path/Trial01.c3d";
		
		File remoteFile = new File ( file );
		
		try {
			int status = FTPs.getFile( remoteFile.getName(), remoteFile.getParent(), 
					this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password,
					destLocalFolder );
			if(FileTransferStatus.SUCCESS == status){
				log.info(" got ftps-ed successfully to file"+ remoteFile );
			}
			else if(FileTransferStatus.FAILURE == status){
				log.severe("Fail to ftps  to  folder "+destLocalFolder);
			}
		} catch (FileTransferException e) {
			e.printStackTrace();
		}
		
		port.downloadComplete(fileID);
		
		return destLocalFolder + remoteFile.getName();
	}
}
