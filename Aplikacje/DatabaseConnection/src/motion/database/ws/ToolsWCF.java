package motion.database.ws;

import motion.database.DatabaseConnection;
import motion.database.DbElementsList;
import motion.database.TextMessageListener;
import motion.database.model.DatabaseFile;
import motion.database.model.DatabaseFileStaticAttributes;
import motion.database.model.EntityAttribute;
import motion.database.model.GenericDescription;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Segment;
import motion.database.model.SegmentStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;
import motion.database.model.User;
import motion.database.model.UserPrivileges;
import motion.database.model.UserPrivilegesStaticAttributes;
import motion.database.model.UserStaticAttributes;
import motion.database.ws.DatabaseConnectionWCF.ConnectionState;
import motion.database.ws.UserPersonalSpaceWCF.IUserPersonalSpaceWS;
import motion.database.ws.UserPersonalSpaceWCF.UserPersonalSpace;
import motion.database.ws.authorizationWCF.AuthorizationWS;
import motion.database.ws.authorizationWCF.IAuthorizationWS;
import motion.database.ws.authorizationWCF.ListSessionPrivilegesResponse.ListSessionPrivilegesResult;
import motion.database.ws.authorizationWCF.ListUsersResponse.ListUsersResult;
import motion.database.ws.authorizationWCF.SessionPrivilegeList.SessionPrivilege;
import motion.database.ws.authorizationWCF.UserList.UserDetails;
import motion.database.ws.basicQueriesServiceWCF.Attributes;
import motion.database.ws.basicQueriesServiceWCF.BasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.FileWithAttributesList.FileDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicUpdatesServiceWCF.BasicUpdatesWS;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;
import motion.database.ws.fileStoremanServiceWCF.FileStoremanWS;
import motion.database.ws.fileStoremanServiceWCF.IFileStoremanWS;

public class ToolsWCF {

	private static TextMessageListener textMessageListener;


	private static void prepareBasicQueriesServiceCall(IBasicQueriesWS port) {
		// TODO Auto-generated method stub
		
	}

	private static void prepareFileStoremanServiceCall(IFileStoremanWS port) {
		// TODO Auto-generated method stub
		
	}

	private static void prepareBasicUpdatesServicesCall(IBasicUpdatesWS port) {
		// TODO Auto-generated method stub
		
	}

	private static void prepareAuthorizationServiceCall(IAuthorizationWS port) {
		// TODO Auto-generated method stub
		
	}

	private static void prepareAuthorizationServiceCall(IUserPersonalSpaceWS port) {
		// TODO Auto-generated method stub
		
	}
	
	public static void setMessage(String message)
	{
		if (textMessageListener!=null)
			textMessageListener.setMessage(message);
	}
	
	public static IBasicQueriesWS getBasicQueriesPort( String callerName, DatabaseConnectionWCF db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnectionWCF", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		BasicQueriesWS service = new BasicQueriesWS();
		IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
		
		prepareBasicQueriesServiceCall(port);

		return port;
	}

	public static IFileStoremanWS getFileStoremanServicePort( String callerName, DatabaseConnectionWCF db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnectionWCF", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		FileStoremanWS service = new FileStoremanWS();
		IFileStoremanWS port = service.getBasicHttpBindingIFileStoremanWS();
		
		prepareFileStoremanServiceCall(port);

		return port;
	}


	public static IBasicUpdatesWS getBasicUpdateServicePort(String callerName, DatabaseConnectionWCF db) throws Exception 
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnectionWCF", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		BasicUpdatesWS service = new BasicUpdatesWS();
		IBasicUpdatesWS port = service.getBasicHttpBindingIBasicUpdatesWS();
		
		prepareBasicUpdatesServicesCall(port);

		return port;
	}

	public static IAuthorizationWS getAuthorizationServicePort( String callerName, DatabaseConnectionWCF db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnectionWCF", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		AuthorizationWS service = new AuthorizationWS();
		IAuthorizationWS port = service.getBasicHttpBindingIAuthorizationWS();
		
		prepareAuthorizationServiceCall(port);

		return port;
	}

	
	
	public static IUserPersonalSpaceWS getUserPersonalSpaceServicePort( String callerName, DatabaseConnectionWCF db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnectionWCF", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		UserPersonalSpace service = new UserPersonalSpace();
		IUserPersonalSpaceWS port = service.getBasicHttpBindingIUserPersonalSpaceWS();
		
		prepareAuthorizationServiceCall(port);

		return port;
	}

	
	public static void finalizeCall()
	{
		setMessage("Connected as '" + DatabaseConnection.getInstance().getConnectionInfo() + "'. Ready.");
	}
	
	
	public static Performer transformPerformerDetails(PerformerDetailsWithAttributes s) 
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
	
	/**
	 * @param attributes
	 * @param destinationObject
	 */
	public static <T extends GenericDescription<?>> T transformGenericAttributes(Attributes attributes, T destinationObject) {
		if (attributes != null)
			if (attributes.getAttribute() != null)
				for (  motion.database.ws.basicQueriesServiceWCF.Attributes.Attribute att : attributes.getAttribute() )
				{
					EntityAttribute attribute = new EntityAttribute(att.getName(), att.getValue(), att.getAttributeGroup(), att.getType() );
					destinationObject.put(att.getName(), attribute );
				}
		return destinationObject;
	}

	
	public static DbElementsList<DatabaseFile> transformListOfFiles(Object result) {

		DbElementsList<DatabaseFile> list = new DbElementsList<DatabaseFile>();
		ListFilesWithAttributesXMLResult r = (ListFilesWithAttributesXMLResult) result;
		for (FileDetailsWithAttributes d: r.getFileWithAttributesList().getFileDetailsWithAttributes() )
		{
			DatabaseFile df = new DatabaseFile();
			df.put( DatabaseFileStaticAttributes.fileID, d.getFileID() );
			df.put( DatabaseFileStaticAttributes.fileName, d.getFileName() );
			df.put( DatabaseFileStaticAttributes.fileDescription, d.getFileDescription() );
			ToolsWCF.transformGenericAttributes( d.getAttributes(), df );
			list.add( df );
		}

		return list;
	}

	
	public static DbElementsList<User> transformListOfUsers(Object result) {

		DbElementsList<User> list = new DbElementsList<User>();
		ListUsersResult r = (ListUsersResult) result;
		for (UserDetails d: r.getUserList().getUserDetails() )
		{
			User df = new User();
			df.put( UserStaticAttributes.firstName, d.getFirstName() );
			df.put( UserStaticAttributes.lastName, d.getLastName() );
			df.put( UserStaticAttributes.login, d.getLogin() );
			list.add( df );
		}
		
		return list;
	}

	
	/**
	 * @param s
	 * @return
	 */
	public static Segment transformSegmentDetails(
			motion.database.ws.basicQueriesServiceWCF.SegmentDetailsWithAttributes s) {
		
		if (s==null)
			return null;

		Segment segment = new Segment();
		segment.put( SegmentStaticAttributes.endTime, s.getEndTime() );
		segment.put( SegmentStaticAttributes.segmentID, s.getSegmentID() );
		segment.put( SegmentStaticAttributes.segmentName, s.getSegmentName() );
		segment.put( SegmentStaticAttributes.startTime, s.getStartTime() );
		segment.put( SegmentStaticAttributes.trialID, s.getTrialID() );
		ToolsWCF.transformGenericAttributes( s.getAttributes(), segment );
		return segment;
	}


	
	public static Session transformSessionDetails(SessionDetailsWithAttributes s) {
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
		
		ToolsWCF.transformGenericAttributes( s.getAttributes(), session );
		return session;
	}
	

	public static Trial transformTrialDetails(
			motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes s) {
		if (s==null)
			return null;

		Trial trial = new Trial();
		trial.put( TrialStaticAttributes.trialID, s.getTrialID() );
		trial.put( TrialStaticAttributes.duration, s.getDuration() );
		trial.put( TrialStaticAttributes.sessionID, s.getSessionID() );
		trial.put( TrialStaticAttributes.trialDescription, s.getTrialDescription() );
		ToolsWCF.transformGenericAttributes( s.getAttributes(), trial );
		return trial;
	}


	public static void registerActionListener(TextMessageListener listener) {
		textMessageListener = listener;
	}

	public static DbElementsList<UserPrivileges> transformListOfPrivileges(
			int sessionId,
			ListSessionPrivilegesResult listSessionPrivileges) 
	{
		DbElementsList<UserPrivileges> list = new DbElementsList<UserPrivileges>();
		for (SessionPrivilege d: listSessionPrivileges.getSessionPrivilegeList().getSessionPrivilege() )
		{
			UserPrivileges df = new UserPrivileges();
			df.put( UserPrivilegesStaticAttributes.login, d.getLogin() );
			df.put( UserPrivilegesStaticAttributes.canRead, true );
			df.put( UserPrivilegesStaticAttributes.canWrite, d.isCanWrite() );
			df.put( UserPrivilegesStaticAttributes.sessionId, sessionId );
			list.add( df );
		}

		return list;
	}



}
