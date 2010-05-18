package motion.database.ws;

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

import motion.database.DbElementsList;
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
import motion.database.ws.basicQueriesServiceWCF.*;
import motion.database.ws.basicQueriesServiceWCF.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesServiceWCF.AttributeGroupDefinitionList.AttributeGroupDefinition;
import motion.database.ws.basicQueriesServiceWCF.Attributes.Attribute;
import motion.database.ws.basicQueriesServiceWCF.FileWithAttributesList.FileDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetSessionByIdXMLResponse.GetSessionByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.GetTrialByIdXMLResponse.GetTrialByIdXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult;
import motion.database.ws.basicQueriesServiceWCF.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesServiceWCF.MotionKindDefinitionList.MotionKindDefinition;
import motion.database.ws.basicQueriesServiceWCF.SessionGroupDefinitionList.SessionGroupDefinition;
import motion.database.ws.fileStoremanService.FileStoremanService;
import motion.database.ws.fileStoremanService.FileStoremanServiceSoap;
import motion.database.ws.test.SqlResultStream;
import motion.database.ws.test.TestWs;
import motion.database.ws.test.TestWsSoap;

import com.zehon.BatchTransferProgress;
import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.ftps.FTPs;


public class DatabaseConnectionWCF extends DatabaseConnectionOld {

	
	public DatabaseConnectionWCF(Logger log)
	{
		super(log);
		this.state = ConnectionState.UNINITIALIZED;
	};
	

//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#uploadPerformerFile(int, java.lang.String, java.lang.String, motion.database.FileTransferListener)
//	 */
//	public void uploadPerformerFile(int performerId, String description, String localFilePath, FileTransferListener listener) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			fileTransferCancelled = false;
//			String destRemoteFolder = getUniqueFolderName();
//			putFile(localFilePath, destRemoteFolder, listener);			
//		    
//			FileStoremanService service = new FileStoremanService();
//			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
//			prepareCall( (BindingProvider)port );
//
//			if (!fileTransferCancelled)
//				port.storePerformerFile( performerId, "", description, destRemoteFolder+new File(localFilePath).getName() );
//		}
//		else
//			throw new Exception("Not Initialized. Cannot perform file uploading.");
//	}
//
//	class BatchTransferProgressObserver implements BatchTransferProgress
//	{
//
//		@Override
//		public void transferComplete(String arg0) {
//			System.out.println( "Transfer zako�czono: " + arg0);
//		}
//
//		@Override
//		public void transferError(String arg0, Throwable arg1) {
//			System.out.println( "Transfer error: " + arg0);
//		}
//
//		@Override
//		public void transferStart(String arg0) {
//			System.out.println( "Transfer rozpocz�to: " + arg0);
//		}
//	}
//	
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#uploadSessionFiles(int, java.lang.String, motion.database.FileTransferListener)
//	 */
//	public void uploadSessionFiles(int sessionId, String filesPath, FileTransferListener listener) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			String destRemoteFolder = getUniqueFolderName();
//
//			File dir = new File(filesPath);
//			if ( dir.isDirectory() )
//			{
//				createRemoteFolder( dir.getName(), destRemoteFolder );
//				FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
///*				destRemoteFolder += dir.getName();
//				for( String singleFile : dir.list() )
//				{
//					putFile( singleFile, destRemoteFolder, listener );			
//	
//				}
//*/
//				FileStoremanService service = new FileStoremanService();
//				FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
//				prepareCall( (BindingProvider)port );
//				port.storeSessionFiles( sessionId, destRemoteFolder+dir.getName(), "TODO: add description" );
//			}
//		}
//		else
//			throw new Exception("Not Initialized. Cannot perform file uploading.");
//	}
//	
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#uploadPerformerFiles(int, java.lang.String, motion.database.FileTransferListener)
//	 */
//	public void uploadPerformerFiles(int performerId, String filesPath, FileTransferListener listener) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			String destRemoteFolder = getUniqueFolderName();
//
//			File dir = new File(filesPath);
//			if ( dir.isDirectory() )
//			{
//				createRemoteFolder( dir.getName(), destRemoteFolder );
//				FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
//				FileStoremanService service = new FileStoremanService();
//				FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
//				prepareCall( (BindingProvider)port );
//				port.storePerformerFiles( performerId, destRemoteFolder+dir.getName() );
//			}
//		}
//		else
//			throw new Exception("Not Initialized. Cannot perform file uploading.");
//	}
//
//	
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#uploadTrialFiles(int, java.lang.String, motion.database.FileTransferListener)
//	 */
//	public void uploadTrialFiles(int trialId, String filesPath, FileTransferListener listener) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			String destRemoteFolder = getUniqueFolderName();
//
//			File dir = new File(filesPath);
//			if ( dir.isDirectory() )
//			{
//				createRemoteFolder( dir.getName(), destRemoteFolder );
//				FTPs.sendFolder( filesPath, destRemoteFolder+dir.getName(), new BatchTransferProgressObserver(), ftpsCredentials.address, ftpsCredentials.userName, ftpsCredentials.password);
//				FileStoremanService service = new FileStoremanService();
//				FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
//				prepareCall( (BindingProvider)port );
//				port.storeTrialFiles( trialId, destRemoteFolder+dir.getName() );
//			}
//		}
//		else
//			throw new Exception("Not Initialized. Cannot perform file uploading.");
//	}
//
//	
//

	public  List<GenericResult> execGenericQuery(Filter filter, String[] p_entitiesToInclude) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "execGenericQuery", this );
		
		ArrayOfString entitiesToInclude = new ArrayOfString();
		for( String s : p_entitiesToInclude )
			entitiesToInclude.getString().add( s );
		
		ArrayOfFilterPredicate arrayOfFilterPredicate = new ArrayOfFilterPredicate();
		for ( FilterPredicate f : filter.toFilterPredicateWCF() )
			arrayOfFilterPredicate.getFilterPredicate().add( f );
		
		GenericQueryUniformXMLResult result = port.genericQueryUniformXML( 
				arrayOfFilterPredicate,	entitiesToInclude );

		
		DbElementsList<GenericResult> output = new DbElementsList<GenericResult>();
		GenericUniformAttributesQueryResult ss = result.getGenericUniformAttributesQueryResult();
		for (Attributes aa : ss.getAttributes() )
			output.add( transformGenericAttributes( aa, new GenericResult() ) );
		
		return output;
	}

	
	public  String getSessionLabel(int sessionID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getSessionLabel", this );
	
		return port.getSessionLabel( sessionID );
	}

	
	public  DbElementsList<Performer> listLabPerformersWithAttributes(int labID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listLabPerformersWithAttributes", this );

		ListLabPerformersWithAttributesXMLResult result = port.listLabPerformersWithAttributesXML(labID);

		DbElementsList<Performer> output = new DbElementsList<Performer>();
		
		if (result.getLabPerformerWithAttributesList() != null) {
			for ( PerformerDetailsWithAttributes s : result.getLabPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
					output.add( transformPerformerDetails(s) );
		}
		
		return output;
	}

	
	/**
	 * @param s
	 * @return
	 */
	private Performer transformPerformerDetails(PerformerDetailsWithAttributes s) 
	{
		if (s==null)
			return null;
		
		Performer performer = new Performer();
		performer.put( PerformerStaticAttributes.performerID, s.getPerformerID() );
		performer.put( PerformerStaticAttributes.firstName, s.getFirstName() );
		performer.put( PerformerStaticAttributes.lastName, s.getLastName() );
		transformGenericAttributes( s.getAttributes(), performer );
		return performer;
	}

	
	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listAttributesDefined(java.lang.String, java.lang.String)
	 */
	public HashMap<String, String> listAttributesDefined(String group, String entityKind) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listAttributesDefined", this );

		ListAttributesDefinedResult result = port.listAttributesDefined( group, entityKind );
		
		HashMap<String, String> output = new HashMap<String, String>();
		for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
				output.put( a.getAttributeName(), a.getAttributeType() );
		
		return output;
	}

	
	
	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listGrouppedAttributesDefined(java.lang.String)
	 */
	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(String entityKind) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listGrouppedAttributesDefined" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
		
			ListAttributesDefinedResult result = port.listAttributesDefined( "_ALL", entityKind);
			
			HashMap<String, EntityAttributeGroup> output = new HashMap<String, EntityAttributeGroup>();

			for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
			{	
				EntityAttributeGroup group = output.get( a.getAttributeGroupName() );
				if (group == null)
				{
					group = new EntityAttributeGroup( a.getAttributeGroupName(), entityKind );
					output.put( a.getAttributeGroupName(), group );
				}
				group.add( new EntityAttribute( a.getAttributeName(), null, a.getAttributeGroupName(), a.getAttributeType() ) );
			}
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list attributes.");
	}

	
	
	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listAttributeGroupsDefined(java.lang.String)
	 */
	public Vector<String> listAttributeGroupsDefined(String entityKind) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listAttributesDefined" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
		
			ListAttributeGroupsDefinedResult result = port.listAttributeGroupsDefined(entityKind);
			
			Vector<String> output = new Vector<String>();
			for (AttributeGroupDefinition a : result.getAttributeGroupDefinitionList().getAttributeGroupDefinition() )
				output.add( a.getAttributeGroupName() );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list attributes.");
	}


	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listMotionKindsDefined()
	 */
	public Vector<MotionKind> listMotionKindsDefined() throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listAttributesDefined" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
		
			ListMotionKindsDefinedResult result = port.listMotionKindsDefined();
			
			Vector<MotionKind> output = new Vector<MotionKind>();
			for (MotionKindDefinition a : result.getMotionKindDefinitionList().getMotionKindDefinition() )
				output.add( new MotionKind( a.getMotionKindID(), a.getMotionKindName() ) );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list motion kinds.");
	}

	
	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listSessionGroupsDefined()
	 */
	public Vector<SessionGroup> listSessionGroupsDefined() throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listAttributesDefined" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
		
			ListSessionGroupsDefinedResult result = port.listSessionGroupsDefined();
			
			Vector<SessionGroup> output = new Vector<SessionGroup>();

			for (SessionGroupDefinition a : result.getSessionGroupDefinitionList().getSessionGroupDefinition() )
				output.add( new SessionGroup( a.getSessionGroupID(), a.getSessionGroupName() ) );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list motion kinds.");
	}

	
	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#getSessionById(int)
	 */
	public Session getSessionById(int id) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			GetSessionByIdXMLResult result = port.getSessionByIdXML(id);
			SessionDetailsWithAttributes s = result.getSessionDetailsWithAttributes();
			return transformSessionDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
		
	}


	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#getTrialById(int)
	 */
	public Trial getTrialById(int id) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			GetTrialByIdXMLResult result = port.getTrialByIdXML(id);
			TrialDetailsWithAttributes s = result.getTrialDetailsWithAttributes();
			return transformTrialDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
	}


	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#getSegmentById(int)
	 */
	public Segment getSegmentById(int id) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			GetSegmentByIdXMLResult result = port.getSegmentByIdXML(id);
			SegmentDetailsWithAttributes s = result.getSegmentDetailsWithAttributes();
			return transformSegmentDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
	}

	
	
	private Session transformSessionDetails(SessionDetailsWithAttributes s) {
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
		session.put( SessionStaticAttributes.sessionLabel, s.getSessionLabel() );
		
		transformGenericAttributes( s.getAttributes(), session );
		return session;
	}

// ------------ PERFORMER ----------------------------------------------------------------	
	
	public Performer getPerformerById(int id) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "getSessionById" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			GetPerformerByIdXMLResult result = port.getPerformerByIdXML(id);
			PerformerDetailsWithAttributes s = result.getPerformerDetailsWithAttributes();
			return transformPerformerDetails(s);
		}
		else
			throw new Exception("Not Initialized. Cannot perform data retrival.");
	}


	public  DbElementsList<Performer> listPerformersWithAttributes() throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessions" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			ListPerformersWithAttributesXMLResult result = port.listPerformersWithAttributesXML();

			DbElementsList<Performer> output = new DbElementsList<Performer>();
			
			for ( PerformerDetailsWithAttributes s : result.getPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
				output.add( transformPerformerDetails(s) );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot list performers.");
	}

	

	public  DbElementsList<Session> listPerformerSessionsWithAttributes(int performerID) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessionsWithAttributes" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			ListPerformerSessionsWithAttributesXMLResult result = port.listPerformerSessionsWithAttributesXML(performerID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			PerformerSessionWithAttributesList ss = result.getPerformerSessionWithAttributesList();
			for ( SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
					output.add( transformSessionDetails(s) );

			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	

	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listLabSessionsWithAttributes(int)
	 */
	public  DbElementsList<Session> listLabSessionsWithAttributes(int labID) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerSessionsWithAttributes" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			ListLabSessionsWithAttributesXMLResult result = port.listLabSessionsWithAttributesXML(labID);
			DbElementsList<Session> output = new DbElementsList<Session>();
			
			for ( motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes s : result.getLabSessionWithAttributesList().getSessionDetailsWithAttributes() )
				output.add( transformSessionDetails(s) );
			
			log.exiting( "DatabaseConnection", "listPerformerSessionsWithAttributes", result );
			
			return output;
		}
		else
			throw new Exception("Not Initialized. Cannot perform file uploading.");
	}

	

	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listSessionTrialsWithAttributes(int)
	 */
	public  DbElementsList<Trial> listSessionTrialsWithAttributes(int sessionID) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionTrialsWithAttributes" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			ListSessionTrialsWithAttributesXMLResult result = port.listSessionTrialsWithAttributesXML(sessionID);
			DbElementsList<Trial> output = new DbElementsList<Trial>();
			
			for ( TrialDetailsWithAttributes s : result.getSessionTrialWithAttributesList().getTrialDetailsWithAttributes() )
				output.add( transformTrialDetails(s) );
			
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
			motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes s) {
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
//
//
//	
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#createPerformer(java.lang.String, java.lang.String)
//	 */
//	public int createPerformer(String name, String surname) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "createPerformer" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			PerformerData performerData = new PerformerData();
//			performerData.setName( name );
//			performerData.setSurname( surname );
//
//			int result = port.createPerformer(performerData);
//			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot create performer.");
//	}
//
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#createSession(int, int[], java.lang.String, int, int, javax.xml.datatype.XMLGregorianCalendar, java.lang.String)
//	 */
//	public int createSession(int performerID, int [] sessionGroupID, String sessionDescription, int labID, int userID, XMLGregorianCalendar sessionDate, String motionKindName ) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "createSession" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			ArrayOfInt sessionGroupIDs = new DatabaseArrayOfInteger( sessionGroupID );
//			
//			int result = port.createSession(userID, labID, motionKindName, performerID, sessionDate, sessionDescription, sessionGroupIDs);
//			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot create session.");
//	}
//
//		
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#createTrial(int, java.lang.String, int)
//	 */
//	public int createTrial(int sessionID, String trialDescription, int trialDuration ) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "createTrial" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			int result = port.createTrial(sessionID, trialDescription, trialDuration);
//			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot create performer.");
//	}
//
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#defineTrialSegment(int, java.lang.String, int, int)
//	 */
//	public int defineTrialSegment(int trialID, String segmentName, int startTime, int endTime ) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "defineTrialSegment" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			int result = port.defineTrialSegment(trialID, segmentName, startTime, endTime);
//			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot create performer.");
//	}
//
//	
//	
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#setSessionAttribute(int, java.lang.String, java.lang.String, boolean)
//	 */
//	public int setSessionAttribute(int sessionID, String attributeName, String attributeValue, boolean update) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "setSessionAttribute" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			int result = port.setSessionAttribute(sessionID, attributeName, attributeValue, update);			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot set attribute.");
//	}
//
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#setTrialAttribute(int, java.lang.String, java.lang.String, boolean)
//	 */
//	public int setTrialAttribute(int trialID, String attributeName, String attributeValue, boolean update) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "setTrialAttribute" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			int result = port.setTrialAttribute(trialID, attributeName, attributeValue, update);			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot set attribute.");
//	}
//
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#setPerformerAttribute(int, java.lang.String, java.lang.String, boolean)
//	 */
//	public int setPerformerAttribute(int performerID, String attributeName, String attributeValue, boolean update) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "setPerformerAttribute" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			int result = port.setPerformerAttribute(performerID, attributeName, attributeValue, update);			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot set attribute.");
//	}
//
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#setSegmentAttribute(int, java.lang.String, java.lang.String, boolean)
//	 */
//	public int setSegmentAttribute(int segmentID, String attributeName, String attributeValue, boolean update) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "setSegmentAttribute" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			int result = port.setSegmentAttribute(segmentID, attributeName, attributeValue, update);			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot set attribute.");
//	}
//
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#setFileAttribute(int, java.lang.String, java.lang.String, boolean)
//	 */
//	public int setFileAttribute(int fileID, String attributeName, String attributeValue, boolean update) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "setFileAttribute" );
//	
//			BasicUpdatesService service = new BasicUpdatesService();
//			BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//			
//			prepareCall(port);
//	
//			int result = port.setFileAttribute(fileID, attributeName, attributeValue, update);			
//			return result;
//		}
//		else
//			throw new Exception("Not Initialized. Cannot set file attribute.");
//	}
//
			
	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listTrialSegmentsWithAttributes(int)
	 */
	public  DbElementsList<Segment> listTrialSegmentsWithAttributes(int trialID) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listTrialSegmentsWithAttributes" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
			ListTrialSegmentsWithAttributesXMLResult result = port.listTrialSegmentsWithAttributesXML(trialID);
			DbElementsList<Segment> output = new DbElementsList<Segment>();
			
			for ( SegmentDetailsWithAttributes s : result.getTrailSegmentWithAttributesList().getSegmentDetailsWithAttributes() )
				output.add( transformSegmentDetails(s) );

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
			motion.database.ws.basicQueriesServiceWCF.SegmentDetailsWithAttributes s) {
		
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

	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listSessionFiles(int)
	 */
	public  DbElementsList<DatabaseFile> listSessionFiles(int sessionID) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listSessionFiles" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall(port);
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(sessionID, EntityKind.session.name());

			return transformListOfFiles(result);
		}
		else
			throw new Exception("Not Initialized. Cannot perform file listing.");
	}

	
	private DbElementsList<DatabaseFile> transformListOfFiles(Object result) {

		DbElementsList<DatabaseFile> list = new DbElementsList<DatabaseFile>();
		ListFilesWithAttributesXMLResult r = (ListFilesWithAttributesXMLResult) result;
		for (FileDetailsWithAttributes d: r.getFileWithAttributesList().getFileDetailsWithAttributes() )
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
	private <T extends GenericDescription<?>> T transformGenericAttributes(Attributes attributes, T destinationObject) {
		if (attributes != null)
			if (attributes.getAttribute() != null)
				for (  motion.database.ws.basicQueriesServiceWCF.Attributes.Attribute att : attributes.getAttribute() )
				{
					EntityAttribute attribute = new EntityAttribute(att.getName(), att.getValue(), att.getAttributeGroup(), att.getType() );
					destinationObject.put(att.getName(), attribute );
				}
		return destinationObject;
	}


	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listTrialFiles(int)
	 */
	public  DbElementsList<DatabaseFile> listTrialFiles(int trialID) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listTrialFiles" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();

			prepareCall(port);
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(trialID, EntityKind.trial.name());
		
			return transformListOfFiles(result);
		}
		else
			throw new Exception("Not Initialized. Cannot perform file listing.");
	}

	/* (non-Javadoc)
	 * @see motion.database.DatabaseProxy#listPerformerFiles(int)
	 */
	public  DbElementsList<DatabaseFile> listPerformerFiles(int performerID) throws Exception
	{
		if (this.state == ConnectionState.INITIALIZED)
		{
			log.entering( "DatabaseConnection", "listPerformerFiles" );
	
			BasicQueriesWS service = new BasicQueriesWS();
			IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
			
			prepareCall( port );
	
			ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(performerID, EntityKind.performer.name());
		
			return transformListOfFiles(result);
		}
		else
			throw new Exception("Not Initialized. Cannot perform file listing.");
	}
//
//	/* (non-Javadoc)
//	 * @see motion.database.DatabaseProxy#downloadFile(int, java.lang.String, motion.database.FileTransferListener)
//	 */
//	public  String downloadFile(int fileID, String destLocalFolder, FileTransferListener transferListener) throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "listSessionFiles" );
//	
//		    FileStoremanService service = new FileStoremanService();
//			FileStoremanServiceSoap port = service.getFileStoremanServiceSoap();
//			prepareCall(port);
//	
//			String file = port.retrieveFile(fileID);
//			
//			File remoteFile = new File ( file );
//
//			getFile( remoteFile.getName(), remoteFile.getParent(), 
//					this.ftpsCredentials.address, this.ftpsCredentials.userName, this.ftpsCredentials.password,
//					destLocalFolder, transferListener );
//
//			port.downloadComplete(fileID, file);
//			
//			return destLocalFolder + remoteFile.getName();
//		}
//		else
//			throw new Exception("Not Initialized. Cannot perform file uploading.");
//	}
//
//	private void getFile(String remoteFileName, String remoteFilePath, String address,
//			String userName, String password, String destLocalFolder,
//			FileTransferListener transferListener) {
//
//		fileTransferSupport.resetDownloadListeners();
//		fileTransferSupport.registerDownloadListener(transferListener);
//		
//		try {
//			fileTransferSupport.getFile(remoteFileName, remoteFilePath, remoteFileName, destLocalFolder, address, userName, password );
//		} catch (Exception e) {
//			log.severe( e.getMessage() );
//			e.printStackTrace();
//		}
//	}
//	
///*
// * 
//	public  void testNewService() throws Exception
//	{
//		if (this.state == ConnectionState.INITIALIZED)
//		{
//			log.entering( "DatabaseConnection", "testNewService" );
//	
//			motion.database.ws.basicQueriesServiceWCF.BasicQueriesWS service = new motion.database.ws.basicQueriesServiceWCF.BasicQueriesWS();
//			
//			motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
//
//			Proxy pp =  (Proxy)port;
//			
////			((BindingProvider)port).getRequestContext().put( BindingProvider.USERNAME_PROPERTY, this.wsCredentials.domainName+"\\"+this.wsCredentials.userName);
////	        ((BindingProvider)port).getRequestContext().put( BindingProvider.PASSWORD_PROPERTY, this.wsCredentials.password );
//
//			motion.database.ws.basicQueriesServiceWCF.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult result = port.getPerformerByIdXML(2);
//
//			System.out.println( transformPerformerDetails( result.getPerformerDetailsWithAttributes() ) );
//		}
//		else
//			throw new Exception("Not Initialized. Cannot perform file listing.");
//	}
//*/


	private void prepareCall(IBasicQueriesWS port) {
		// TODO Auto-generated method stub
		
	}
}
