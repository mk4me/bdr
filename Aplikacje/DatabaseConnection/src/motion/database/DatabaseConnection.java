package motion.database;

import java.io.File;
import java.lang.reflect.Proxy;
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
import javax.xml.ws.Service;

import motion.database.model.DatabaseFile;
import motion.database.model.DatabaseFileStaticAttributes;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Filter;
import motion.database.model.GenericDescription;
import motion.database.model.GenericResult;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Segment;
import motion.database.model.SegmentStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;
import motion.database.ws.DatabaseArrayOfFilterPredicate;
import motion.database.ws.DatabaseArrayOfInteger;
import motion.database.ws.DatabaseArrayOfString;
import motion.database.ws.DatabaseConnectionOld;
import motion.database.ws.DatabaseConnectionWCF;
import motion.database.ws.basicQueriesService.ArrayOfPlainSessionDetails;
import motion.database.ws.basicQueriesService.ArrayOfString;
import motion.database.ws.basicQueriesService.AttributeDefinitionList;
import motion.database.ws.basicQueriesService.AttributeGroupDefinitionList;
import motion.database.ws.basicQueriesService.Attributes;
import motion.database.ws.basicQueriesService.BasicQueriesService;
import motion.database.ws.basicQueriesService.BasicQueriesServiceSoap;
import motion.database.ws.basicQueriesService.FileWithAttributesList;
import motion.database.ws.basicQueriesService.GenericUniformAttributesQueryResult;
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
import motion.database.ws.basicQueriesService.GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult;
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
	
	private static DatabaseProxy instance;
	private static Logger log;
	
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
	
	public static DatabaseProxy getInstance()
	{
		if (instance==null)
			instance = new DatabaseConnectionOld(log);
		return instance;
	}

	public static DatabaseProxy getInstanceWCF()
	{
		if (instance==null)
			instance = new DatabaseConnectionWCF(log);
		return instance;
	}
}
