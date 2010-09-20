package motion.database.ws;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

import motion.database.DatabaseConnection;
import motion.database.DbElementsList;
import motion.database.TextMessageListener;
import motion.database.model.DatabaseFile;
import motion.database.model.DatabaseFileStaticAttributes;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.Measurement;
import motion.database.model.MeasurementConfiguration;
import motion.database.model.MeasurementConfigurationStaticAttributes;
import motion.database.model.MeasurementStaticAttributes;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;
import motion.database.model.User;
import motion.database.model.UserBasket;
import motion.database.model.UserBasketStaticAttributes;
import motion.database.model.UserPrivileges;
import motion.database.model.UserPrivilegesStaticAttributes;
import motion.database.model.UserStaticAttributes;
import motion.database.ws.DatabaseConnection2.ConnectionState;
import motion.database.ws.administrationWCF.AdministrationWS;
import motion.database.ws.administrationWCF.IAdministrationWS;
import motion.database.ws.authorizationWCF.AuthorizationWS;
import motion.database.ws.authorizationWCF.IAuthorizationWS;
import motion.database.ws.authorizationWCF.ListSessionPrivilegesResponse.ListSessionPrivilegesResult;
import motion.database.ws.authorizationWCF.ListUsersResponse.ListUsersResult;
import motion.database.ws.authorizationWCF.SessionPrivilegeList.SessionPrivilege;
import motion.database.ws.authorizationWCF.UserList.UserDetails;
import motion.database.ws.basicQueriesServiceWCF.Attributes;
import motion.database.ws.basicQueriesServiceWCF.BasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.IBasicQueriesWS;
import motion.database.ws.basicQueriesServiceWCF.MeasurementConfDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.MeasurementDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.FileList.FileDetails;
import motion.database.ws.basicQueriesServiceWCF.FileWithAttributesList.FileDetailsWithAttributes;
import motion.database.ws.basicQueriesServiceWCF.ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult;
import motion.database.ws.basicQueriesServiceWCF.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicUpdatesServiceWCF.BasicUpdatesWS;
import motion.database.ws.basicUpdatesServiceWCF.IBasicUpdatesWS;
import motion.database.ws.fileStoremanServiceWCF.FileStoremanWS;
import motion.database.ws.fileStoremanServiceWCF.IFileStoremanWS;
import motion.database.ws.userPersonalSpaceWCF.IUserPersonalSpaceWS;
import motion.database.ws.userPersonalSpaceWCF.TrialDetailsWithAttributes;
import motion.database.ws.userPersonalSpaceWCF.UserPersonalSpace;
import motion.database.ws.userPersonalSpaceWCF.Attributes.Attribute;
import motion.database.ws.userPersonalSpaceWCF.BasketDefinitionList.BasketDefinition;

public class ConnectionTools2 {

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

	private static void prepareAdministationServiceCall(IAdministrationWS port) {
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
	
	public static IBasicQueriesWS getBasicQueriesPort( String callerName, DatabaseConnection2 db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnection2", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		BasicQueriesWS service = new BasicQueriesWS();
		IBasicQueriesWS port = service.getBasicHttpBindingIBasicQueriesWS();
		
		prepareBasicQueriesServiceCall(port);

		return port;
	}

	public static IFileStoremanWS getFileStoremanServicePort( String callerName, DatabaseConnection2 db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnection2", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		FileStoremanWS service = new FileStoremanWS();
		IFileStoremanWS port = service.getBasicHttpBindingIFileStoremanWS();
		
		prepareFileStoremanServiceCall(port);

		return port;
	}


	public static IBasicUpdatesWS getBasicUpdateServicePort(String callerName, DatabaseConnection2 db) throws Exception 
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnection2", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		BasicUpdatesWS service = new BasicUpdatesWS();
		IBasicUpdatesWS port = service.getBasicHttpBindingIBasicUpdatesWS();
		
		prepareBasicUpdatesServicesCall(port);

		return port;
	}

	public static IAuthorizationWS getAuthorizationServicePort( String callerName, DatabaseConnection2 db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnection2", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		AuthorizationWS service = new AuthorizationWS();
		IAuthorizationWS port = service.getBasicHttpBindingIAuthorizationWS();
		
		prepareAuthorizationServiceCall(port);

		return port;
	}

	public static IAdministrationWS getAdministrationServicePort(String callerName,
			DatabaseConnection2 db) throws Exception {

		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnection2", callerName );
		if (textMessageListener!=null)
			textMessageListener.setMessage("Performing: " + callerName);
		
		AdministrationWS service = new AdministrationWS();
		IAdministrationWS port = service.getBasicHttpBindingIAdministrationWS();
		
		prepareAdministationServiceCall(port);

		return port;
	}
	
	
	public static IUserPersonalSpaceWS getUserPersonalSpaceServicePort( String callerName, DatabaseConnection2 db ) throws Exception
	{
		if (db.state != ConnectionState.INITIALIZED)
		{
			db.log.severe("Trying to use WS without initialization! Called:" + callerName);
			throw new Exception("Not Initialized. Cannot do: " + callerName );
		}
		db.log.entering( "DatabaseConnection2", callerName );
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
		performer.put( PerformerStaticAttributes.PerformerID, s.getPerformerID() );
		performer.put( PerformerStaticAttributes.FirstName, s.getFirstName() );
		performer.put( PerformerStaticAttributes.LastName, s.getLastName() );
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
					EntityAttribute attribute = new EntityAttribute(att.getName(), destinationObject.entityKind, att.getValue(), att.getAttributeGroup(), att.getType() );
					destinationObject.put(att.getName(), attribute );
				}
		return destinationObject;
	}

	public static EntityKind inferEntityType(Attributes attributes) {
		
		if (attributes != null)
			if (attributes.getAttribute() != null)
				for (  motion.database.ws.basicQueriesServiceWCF.Attributes.Attribute att : attributes.getAttribute() )
				{
					return EntityKind.valueOf( att.getEntity() );
				}

		return null;
	}
	
	public static DbElementsList<DatabaseFile> transformListOfFiles(Object result) {

		DbElementsList<DatabaseFile> list = new DbElementsList<DatabaseFile>();
		ListFilesWithAttributesXMLResult r = (ListFilesWithAttributesXMLResult) result;
		for (FileDetailsWithAttributes d: r.getFileWithAttributesList().getFileDetailsWithAttributes() )
		{
			DatabaseFile df = new DatabaseFile();
			df.put( DatabaseFileStaticAttributes.FileID, d.getFileID() );
			df.put( DatabaseFileStaticAttributes.FileName, d.getFileName() );
			df.put( DatabaseFileStaticAttributes.FileDescription, d.getFileDescription() );
			df.put( DatabaseFileStaticAttributes.SubdirPath, d.getSubdirPath() );
			ConnectionTools2.transformGenericAttributes( d.getAttributes(), df );
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

	
	
	public static Session transformSessionDetails(SessionDetailsWithAttributes s) {
		if(s==null)
			return null;
		
		Session session = new Session();
		session.put( SessionStaticAttributes.LabID, s.getLabID() );
		session.put( SessionStaticAttributes.MotionKindID, s.getMotionKindID() );
		session.put( SessionStaticAttributes.SessionDate, s.getSessionDate() );
		session.put( SessionStaticAttributes.SessionDescription, s.getSessionDescription() );
		session.put( SessionStaticAttributes.SessionID, s.getSessionID() );
		session.put( SessionStaticAttributes.UserID, s.getUserID() );
		session.put( SessionStaticAttributes.SessionLabel, s.getSessionLabel() );
		
		ConnectionTools2.transformGenericAttributes( s.getAttributes(), session );
		return session;
	}
	

	public static Trial transformTrialDetails(
			motion.database.ws.basicQueriesServiceWCF.TrialDetailsWithAttributes s) {
		if (s==null)
			return null;

		Trial trial = new Trial();
		trial.put( TrialStaticAttributes.TrialID, s.getTrialID() );
		trial.put( TrialStaticAttributes.Duration, s.getDuration() );
		trial.put( TrialStaticAttributes.TrialDescription, s.getTrialDescription() );
		trial.put( TrialStaticAttributes.SessionID, s.getTrialDescription() );
		ConnectionTools2.transformGenericAttributes( s.getAttributes(), trial );
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
		if (listSessionPrivileges!=null && listSessionPrivileges.getSessionPrivilegeList() != null)
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

	public static Performer transformPerformerDetailsUPS(
			motion.database.ws.userPersonalSpaceWCF.PerformerDetailsWithAttributes s) {
			if (s==null)
				return null;
			
			Performer performer = new Performer();
			performer.put( PerformerStaticAttributes.PerformerID, s.getPerformerID() );
			performer.put( PerformerStaticAttributes.FirstName, s.getFirstName() );
			performer.put( PerformerStaticAttributes.LastName, s.getLastName() );
			transformGenericAttributes( s.getAttributes(), performer );

			return performer;
	}

	public static <T extends GenericDescription<?>> T transformGenericAttributes(
			motion.database.ws.userPersonalSpaceWCF.Attributes attributes,
			T destinationObject ) {
			if (attributes != null)
				if (attributes.getAttribute() != null)
					for (  Attribute att : attributes.getAttribute() )
					{
						EntityAttribute attribute = new EntityAttribute(att.getName(), destinationObject.entityKind, att.getValue(), att.getAttributeGroup(), att.getType() );
						destinationObject.put(att.getName(), attribute );
					}
			return destinationObject;
		}

	public static Session transformSessionDetailsUPS(
			motion.database.ws.userPersonalSpaceWCF.SessionDetailsWithAttributes s) {
		if(s==null)
			return null;
		
		Session session = new Session();
		session.put( SessionStaticAttributes.LabID, s.getLabID() );
		session.put( SessionStaticAttributes.MotionKindID, s.getMotionKindID() );
		session.put( SessionStaticAttributes.SessionDate, s.getSessionDate() );
		session.put( SessionStaticAttributes.SessionDescription, s.getSessionDescription() );
		session.put( SessionStaticAttributes.SessionID, s.getSessionID() );
		session.put( SessionStaticAttributes.UserID, s.getUserID() );
		session.put( SessionStaticAttributes.SessionLabel, s.getSessionLabel() );
		
		ConnectionTools2.transformGenericAttributes( s.getAttributes(), session );
		return session;
	}

	public static Trial transformTrialDetailsUPS(TrialDetailsWithAttributes s) {

		Trial trial = new Trial();
		trial.put( TrialStaticAttributes.TrialID, s.getTrialID() );
		trial.put( TrialStaticAttributes.Duration, s.getDuration() );
		trial.put( TrialStaticAttributes.TrialDescription, s.getTrialDescription() );
		trial.put( TrialStaticAttributes.SessionID, s.getTrialDescription() );

		ConnectionTools2.transformGenericAttributes( s.getAttributes(), trial );
		return trial;
	}

	public static UserBasket transformBasketDefinitionUPS(BasketDefinition s) {
		UserBasket basket = new UserBasket();
		basket.put( UserBasketStaticAttributes.basketName, s.getBasketName() );
		return basket;
	}

	public static Measurement transformMeasurementDetails(
			MeasurementDetailsWithAttributes s) {
		Measurement m = new Measurement();
		m.put( MeasurementStaticAttributes.MeasurementID, s.getMeasurementID() );
		ConnectionTools2.transformGenericAttributes( s.getAttributes(), m );
		return m;
	}

	public static MeasurementConfiguration transformMeasurementConfigurationDetails(
			MeasurementConfDetailsWithAttributes s) {
		MeasurementConfiguration m = new MeasurementConfiguration();
		m.put( MeasurementConfigurationStaticAttributes.MeasurementConfID, s.getMeasurementConfID() );
		m.put( MeasurementConfigurationStaticAttributes.MeasurementConfDescription, s.getMeasurementConfDescription() );
		m.put( MeasurementConfigurationStaticAttributes.MeasurementConfName, s.getMeasurementConfName() );
		ConnectionTools2.transformGenericAttributes( s.getAttributes(), m );
		return m;
	}

	public static Exception transformWSFaultMessage( Exception e )
	{
		String s = e.getLocalizedMessage();
		try{
			Class<?> ec = e.getClass();
			Method method = ec.getMethod( "getFaultInfo" );
			Object info = null;
			if (method != null)
				info = method.invoke( e );
			if (info != null)
				ec = info.getClass();
			method = ec.getMethod( "getDetails" );
			if (method != null)
				info = method.invoke( e );
			if (info != null)
				ec = info.getClass();
			method = ec.getMethod( "getValue" );
			if (method != null)
				info = method.invoke( e );
			if (info instanceof String)
				s = (String) info;
		}
		catch(Exception ex)
		{
			
		}
		
		DatabaseConnection.log.log( Level.SEVERE, s, e );
		return new Exception( s, e ); 
	}

}
