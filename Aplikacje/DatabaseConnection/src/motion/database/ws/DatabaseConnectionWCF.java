package motion.database.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import motion.database.DbElementsList;
import motion.database.TextMessageListener;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Filter;
import motion.database.model.GenericResult;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Segment;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.Trial;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfFilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.ArrayOfString;
import motion.database.ws.basicQueriesServiceWCF.Attributes;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;
import motion.database.ws.basicQueriesServiceWCF.GenericUniformAttributesQueryResult;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerSessionWithAttributesList;
import motion.database.ws.basicQueriesServiceWCF.SegmentDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesServiceWCF.AttributeGroupDefinitionList.AttributeGroupDefinition;
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


public class DatabaseConnectionWCF extends DatabaseConnectionOld {

	
	public DatabaseConnectionWCF(Logger log)
	{
		super(log);
		this.state = ConnectionState.UNINITIALIZED;
	};
	
	public void registerStateMessageListener(TextMessageListener listener) {
	
		ToolsWCF.registerActionListener(listener);
	}
	
////////////////////////////////////////////////////////////////////////////
//	Generic Result 
	
	
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
			output.add( ToolsWCF.transformGenericAttributes( aa, new GenericResult() ) );
		
		return output;
	}
	

////////////////////////////////////////////////////////////////////////////
//	Attributes 

	
	public HashMap<String, String> listAttributesDefined(String group, String entityKind) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listAttributesDefined", this );

		ListAttributesDefinedResult result = port.listAttributesDefined( group, entityKind );
		
		HashMap<String, String> output = new HashMap<String, String>();
		for (AttributeDefinition a : result.getAttributeDefinitionList().getAttributeDefinition() )
				output.put( a.getAttributeName(), a.getAttributeType() );
		
		return output;
	}

	
	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(String entityKind) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listGrouppedAttributesDefined", this );

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

	
	
////////////////////////////////////////////////////////////////////////////
//	Attribute Groups
	
	
	public Vector<String> listAttributeGroupsDefined(String entityKind) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listAttributeGroupsDefined", this );
		
		ListAttributeGroupsDefinedResult result = port.listAttributeGroupsDefined(entityKind);
		
		Vector<String> output = new Vector<String>();
		for (AttributeGroupDefinition a : result.getAttributeGroupDefinitionList().getAttributeGroupDefinition() )
			output.add( a.getAttributeGroupName() );
		
		return output;
	}

	
////////////////////////////////////////////////////////////////////////////
//	Motion Kinds

	
	public Vector<MotionKind> listMotionKindsDefined() throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listMotionKindsDefined", this );
		
		ListMotionKindsDefinedResult result = port.listMotionKindsDefined();
		
		Vector<MotionKind> output = new Vector<MotionKind>();
		for (MotionKindDefinition a : result.getMotionKindDefinitionList().getMotionKindDefinition() )
			output.add( new MotionKind( a.getMotionKindID(), a.getMotionKindName() ) );
		
		return output;
	}

	
	



	
////////////////////////////////////////////////////////////////////////////
//	Performer

	
	public Performer getPerformerById(int id) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getPerformerById", this );
	
		GetPerformerByIdXMLResult result = port.getPerformerByIdXML(id);
		PerformerDetailsWithAttributes s = result.getPerformerDetailsWithAttributes();

		return ToolsWCF.transformPerformerDetails(s);
	}

	
	public  DbElementsList<Performer> listLabPerformersWithAttributes(int labID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listLabPerformersWithAttributes", this );

		ListLabPerformersWithAttributesXMLResult result = port.listLabPerformersWithAttributesXML(labID);

		DbElementsList<Performer> output = new DbElementsList<Performer>();
		
		for ( PerformerDetailsWithAttributes s : result.getLabPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
				output.add( ToolsWCF.transformPerformerDetails(s) );
		
		return output;
	}

	public  DbElementsList<Performer> listPerformersWithAttributes() throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listPerformersWithAttributes", this );

		ListPerformersWithAttributesXMLResult result = port.listPerformersWithAttributesXML();

		DbElementsList<Performer> output = new DbElementsList<Performer>();
		
		for ( PerformerDetailsWithAttributes s : result.getPerformerWithAttributesList().getPerformerDetailsWithAttributes() )
			output.add( ToolsWCF.transformPerformerDetails(s) );
		
		return output;
	}

	
////////////////////////////////////////////////////////////////////////////
//	Session
	
	
	public Session getSessionById(int id) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getSessionById", this );
	
		GetSessionByIdXMLResult result = port.getSessionByIdXML(id);
		SessionDetailsWithAttributes s = result.getSessionDetailsWithAttributes();

		return ToolsWCF.transformSessionDetails(s);
	}


	public  String getSessionLabel(int sessionID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getSessionLabel", this );
	
		return port.getSessionLabel( sessionID );
	}

	
	public  DbElementsList<Session> listPerformerSessionsWithAttributes(int performerID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listPerformerSessionsWithAttributes", this );
	
		ListPerformerSessionsWithAttributesXMLResult result = port.listPerformerSessionsWithAttributesXML(performerID);
		DbElementsList<Session> output = new DbElementsList<Session>();
		
		PerformerSessionWithAttributesList ss = result.getPerformerSessionWithAttributesList();
		for ( SessionDetailsWithAttributes s : ss.getSessionDetailsWithAttributes() )
				output.add( ToolsWCF.transformSessionDetails(s) );

		return output;
	}

	
	public  DbElementsList<Session> listLabSessionsWithAttributes(int labID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listLabSessionsWithAttributes", this );
	
		ListLabSessionsWithAttributesXMLResult result = port.listLabSessionsWithAttributesXML(labID);
		DbElementsList<Session> output = new DbElementsList<Session>();
		
		for ( motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes s : result.getLabSessionWithAttributesList().getSessionDetailsWithAttributes() )
			output.add( ToolsWCF.transformSessionDetails(s) );
		
		return output;
	}

	
////////////////////////////////////////////////////////////////////////////
//	Session Groups
	
	public Vector<SessionGroup> listSessionGroupsDefined() throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listSessionGroupsDefined", this );
		
		ListSessionGroupsDefinedResult result = port.listSessionGroupsDefined();
		
		Vector<SessionGroup> output = new Vector<SessionGroup>();

		for (SessionGroupDefinition a : result.getSessionGroupDefinitionList().getSessionGroupDefinition() )
			output.add( new SessionGroup( a.getSessionGroupID(), a.getSessionGroupName() ) );
		
		return output;
	}


	

////////////////////////////////////////////////////////////////////////////
//	Trial

	
	public Trial getTrialById(int id) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getTrialById", this );
	
		GetTrialByIdXMLResult result = port.getTrialByIdXML(id);
		TrialDetailsWithAttributes s = result.getTrialDetailsWithAttributes();

		return ToolsWCF.transformTrialDetails(s);
	}


	public  DbElementsList<Trial> listSessionTrialsWithAttributes(int sessionID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listSessionTrialsWithAttributes", this );
	
		ListSessionTrialsWithAttributesXMLResult result = port.listSessionTrialsWithAttributesXML(sessionID);
		DbElementsList<Trial> output = new DbElementsList<Trial>();
		
		for ( TrialDetailsWithAttributes s : result.getSessionTrialWithAttributesList().getTrialDetailsWithAttributes() )
			output.add( ToolsWCF.transformTrialDetails(s) );
			
		return output;
	}


	
////////////////////////////////////////////////////////////////////////////
//	Segment

	
	public Segment getSegmentById(int id) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "getSegmentById", this );
	
		GetSegmentByIdXMLResult result = port.getSegmentByIdXML(id);
		SegmentDetailsWithAttributes s = result.getSegmentDetailsWithAttributes();
		return ToolsWCF.transformSegmentDetails(s);
	}

	
	public  DbElementsList<Segment> listTrialSegmentsWithAttributes(int trialID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listTrialSegmentsWithAttributes", this );
		
		ListTrialSegmentsWithAttributesXMLResult result = port.listTrialSegmentsWithAttributesXML(trialID);
		DbElementsList<Segment> output = new DbElementsList<Segment>();
		
		for ( SegmentDetailsWithAttributes s : result.getTrailSegmentWithAttributesList().getSegmentDetailsWithAttributes() )
			output.add( ToolsWCF.transformSegmentDetails(s) );

		return output;
	}

	
////////////////////////////////////////////////////////////////////////////
//	File
	
	
	public  DbElementsList<DatabaseFile> listSessionFiles(int sessionID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listSessionFiles", this );
	
		ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(sessionID, EntityKind.session.name());

		return ToolsWCF.transformListOfFiles(result);
	}

	

	public  DbElementsList<DatabaseFile> listTrialFiles(int trialID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listTrialFiles", this );

		ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(trialID, EntityKind.trial.name());
		
		return ToolsWCF.transformListOfFiles(result);
	}

	

	public  DbElementsList<DatabaseFile> listPerformerFiles(int performerID) throws Exception
	{
		IBasicQueriesWS port = ToolsWCF.getBasicQueriesPort( "listPerformerFiles", this );

		ListFilesWithAttributesXMLResult result = port.listFilesWithAttributesXML(performerID, EntityKind.performer.name());
		
		return ToolsWCF.transformListOfFiles(result);
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
//			System.out.println( ToolsWCF.transformPerformerDetails( result.getPerformerDetailsWithAttributes() ) );
//		}
//		else
//			throw new Exception("Not Initialized. Cannot perform file listing.");
//	}
//*/
	//
	//
	//	
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#createPerformer(java.lang.String, java.lang.String)
//		 */
//		public int createPerformer(String name, String surname) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "createPerformer" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				PerformerData performerData = new PerformerData();
//				performerData.setName( name );
//				performerData.setSurname( surname );
	//
//				int result = port.createPerformer(performerData);
//				
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot create performer.");
//		}
	//
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#createSession(int, int[], java.lang.String, int, int, javax.xml.datatype.XMLGregorianCalendar, java.lang.String)
//		 */
//		public int createSession(int performerID, int [] sessionGroupID, String sessionDescription, int labID, int userID, XMLGregorianCalendar sessionDate, String motionKindName ) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "createSession" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				ArrayOfInt sessionGroupIDs = new DatabaseArrayOfInteger( sessionGroupID );
//				
//				int result = port.createSession(userID, labID, motionKindName, performerID, sessionDate, sessionDescription, sessionGroupIDs);
//				
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot create session.");
//		}
	//
//			
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#createTrial(int, java.lang.String, int)
//		 */
//		public int createTrial(int sessionID, String trialDescription, int trialDuration ) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "createTrial" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				int result = port.createTrial(sessionID, trialDescription, trialDuration);
//				
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot create performer.");
//		}
	//
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#defineTrialSegment(int, java.lang.String, int, int)
//		 */
//		public int defineTrialSegment(int trialID, String segmentName, int startTime, int endTime ) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "defineTrialSegment" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				int result = port.defineTrialSegment(trialID, segmentName, startTime, endTime);
//				
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot create performer.");
//		}
	//
	//	
	//	
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#setSessionAttribute(int, java.lang.String, java.lang.String, boolean)
//		 */
//		public int setSessionAttribute(int sessionID, String attributeName, String attributeValue, boolean update) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "setSessionAttribute" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				int result = port.setSessionAttribute(sessionID, attributeName, attributeValue, update);			
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot set attribute.");
//		}
	//
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#setTrialAttribute(int, java.lang.String, java.lang.String, boolean)
//		 */
//		public int setTrialAttribute(int trialID, String attributeName, String attributeValue, boolean update) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "setTrialAttribute" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				int result = port.setTrialAttribute(trialID, attributeName, attributeValue, update);			
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot set attribute.");
//		}
	//
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#setPerformerAttribute(int, java.lang.String, java.lang.String, boolean)
//		 */
//		public int setPerformerAttribute(int performerID, String attributeName, String attributeValue, boolean update) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "setPerformerAttribute" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				int result = port.setPerformerAttribute(performerID, attributeName, attributeValue, update);			
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot set attribute.");
//		}
	//
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#setSegmentAttribute(int, java.lang.String, java.lang.String, boolean)
//		 */
//		public int setSegmentAttribute(int segmentID, String attributeName, String attributeValue, boolean update) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "setSegmentAttribute" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				int result = port.setSegmentAttribute(segmentID, attributeName, attributeValue, update);			
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot set attribute.");
//		}
	//
//		/* (non-Javadoc)
//		 * @see motion.database.DatabaseProxy#setFileAttribute(int, java.lang.String, java.lang.String, boolean)
//		 */
//		public int setFileAttribute(int fileID, String attributeName, String attributeValue, boolean update) throws Exception
//		{
//			if (this.state == ConnectionState.INITIALIZED)
//			{
//				log.entering( "DatabaseConnection", "setFileAttribute" );
	//	
//				BasicUpdatesService service = new BasicUpdatesService();
//				BasicUpdatesServiceSoap port = service.getBasicUpdatesServiceSoap();
//				
//				prepareCall(port);
	//	
//				int result = port.setFileAttribute(fileID, attributeName, attributeValue, update);			
//				return result;
//			}
//			else
//				throw new Exception("Not Initialized. Cannot set file attribute.");
//		}
	//
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

}
