package motion.database;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.XMLGregorianCalendar;

import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.GenericResult;
import motion.database.model.Measurement;
import motion.database.model.MeasurementConfiguration;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.PerformerConfiguration;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.SessionPrivileges;
import motion.database.model.SessionValidationInfo;
import motion.database.model.Trial;
import motion.database.model.User;
import motion.database.model.UserBasket;
import motion.database.model.UserPrivileges;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;
import motion.database.ws.userPersonalSpaceWCF.ArrayOfFilterPredicate;

/**
 * This interface represents a Facade between clients and a motion server. 
 * Different implementations may deliver different communication paths as well as 
 * protocols and transmission interfaces. Intention of this interface is to hide all 
 * these details from clients.
 * 
 * The only limitation a client has got here is authentication procedure. Due to current 
 * server implementation a client must login to a Web Services server and to a FTPS server.
 * In case of future changes the two methods: setWSCredentials and setFTPSCredentials should 
 * be exchanged with a more generic design pattern with an authenticator object used.     
 *
 * The Facade interface is responsible for
 * <ul>
 * <li> executing operations on the server side (and receiving results) </li>
 * <li> uploading files </li>
 * <li> downloading files </li>
 * </ul>
 * In case of the remote operations execution all parameters and returned values are transformed
 * from connection and server specific structures to native facade objects. This feature realizes
 * total separation of the client and server and lets the clients to see data in pure object 
 * oriented model. For example facade defines its own representation for Performer, Session, Trial, 
 * etc. 
 * 
 * Files uploading and downloading may be additionally supported by observers in order to inform 
 * a user about progress. 
 * 
 * Currently the facade defines operations for the following services on the server side:
 *  <ul>
 *   	<li> Administration </li>
 *   	<li> Authorization </li>
 *  	<li> Basic queries</li>
 *      <li> Basic updates </li>
 *  	<li> File upload/download </li>
 *      <li> User personal space </li>
 *  </ul>
 *
 */
public interface DatabaseProxy {

	/**
	 * This method sets user authentication data when logging into Web Services server. 
	 * 
	 * @param userName
	 * @param password
	 * @param domainName
	 */
	public abstract void setWSCredentials(String userName, String password,
			String domainName);

	/**
	 * This method sets user authentication data when logging into FTPS server.
	 * 
	 * @param address
	 * @param userName
	 * @param password
	 */
	public abstract void setFTPSCredentials(String address, String userName,
			String password);

	/**
	 * This method should return an information about the server connection.
	 * 
	 * @return String describing the connection (user name and server used).
	 */
	public abstract String getConnectionInfo();
	
	public void removeBasket(String basketName) throws Exception;

	public void createBasket(String basketName) throws Exception;
	
	public void removeEntityFromBasket(String basketName, int resourceID, String entityName) throws Exception;
	
	public void addEntityToBasket(String basketName, int resourceID, String entity) throws Exception;
	
	public SessionPrivileges listSessionPrivileges(int sessionID) throws Exception;

	public DbElementsList<User> listUsers() throws Exception;

	
	public abstract void registerFileUploadListener(
			FileTransferListener listener);

	public abstract void cancelCurrentFileTransfer();

	public abstract List<GenericDescription> execGenericQuery(ArrayList<FilterPredicate> filterPredicates,
			String[] p_entitiesToInclude) throws Exception;

	public abstract DbElementsList<Performer> listPerformersWithAttributes()
			throws Exception;

	public abstract DbElementsList<Performer> listLabPerformersWithAttributes(
			int labID) throws Exception;

	public  String getSessionLabel(int sessionID) throws Exception;

	
//	@Deprecated
//	public abstract DbElementsList<Session> listPerformerSessions(
//			int PerformerID) throws Exception;

	public List<String> listEnumValues(String attributeName, String entityKind) throws Exception;

	public abstract HashMap<String, String> listAttributesDefined(String group,
			String entityKind) throws Exception;

	public abstract HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(
			String entityKind) throws Exception;

	public abstract Vector<String> listAttributeGroupsDefined(String entityKind)
			throws Exception;

	public abstract Vector<MotionKind> listMotionKindsDefined()
			throws Exception;

	public DbElementsList<SessionGroup> listSessionGroupsDefined() throws Exception;

	public abstract DbElementsList<Session> listPerformerSessionsWithAttributes(
			int performerID) throws Exception;

	public abstract DbElementsList<Session> listLabSessionsWithAttributes(
			int labID) throws Exception;

	public abstract DbElementsList<Trial> listSessionTrialsWithAttributes(
			int sessionID) throws Exception;

	public abstract int createPerformer()
			throws Exception;

	public abstract int createSession(int [] sessionGroupID, String sessionName, String sessionTags, String sessionDescription, int labID, XMLGregorianCalendar sessionDate, String motionKindName ) throws Exception;

	public abstract int createTrial(int sessionID, String trialName, String trialDescription) throws Exception;

	/**
	 * This is a new method for setting generic attribute value of any entity which supports generic values.
	 * Be careful since not all EntityKind objects can store generic attributes. It only applies to:
	 * <ul>
	 * 	<li> Performer
	 *  <li> Session
	 *  <li> Trial
	 *  <li> Segment
	 *  <li> File
	 * </ul>
	 * 
	 * @param entityID Unique database identification of an entity
	 * @param attributeValue value of an attribute. If set to null then an attribute value is removed.
	 * @param update indicated updating action
	 * @throws Exception
	 */
	public abstract void setEntityAttribute(int entityID,
			EntityAttribute attributeValue, boolean update)
			throws Exception;
	
	@Deprecated
	public abstract void setSessionAttribute(int sessionID,
			String attributeName, String attributeValue, boolean update)
			throws Exception;

	@Deprecated
	public void setSessionAttribute(int sessionID, EntityAttribute a, boolean update) throws Exception;
	
	@Deprecated
	public abstract void setTrialAttribute(int trialID, String attributeName,
			String attributeValue, boolean update) throws Exception;

	@Deprecated
	public abstract void setPerformerAttribute(int performerID,
			String attributeName, String attributeValue, boolean update)
			throws Exception;

	public abstract void setFileAttribute(int fileID, String attributeName,
			String attributeValue, boolean update) throws Exception;
	
	public abstract DbElementsList<DatabaseFile> listFiles(int trialID, EntityKind kind)
			throws Exception;

	public abstract String downloadFile(int fileID, String destLocalFolder,
			FileTransferListener transferListener, boolean recreateFolder) throws Exception;

	public abstract GenericDescription<?> getById(int id, EntityKind kind) throws Exception;

	public abstract void registerStateMessageListener(TextMessageListener listener);

	public void removeSessionPrivileges(String grantedUserLogin, int sessionID, boolean writePrivilege) throws Exception;

	public void grantSessionPrivileges(String grantedUserLogin, int sessionID, boolean writePrivilege) throws Exception;

	public void createUserAccount(String firstName, String lastName) throws Exception;
	
	public boolean checkUserAccount() throws Exception;
	
	public void setSessionPrivileges(int sessionID, boolean readPrivilege, boolean writePrivilege) throws Exception;

	public DbElementsList<UserBasket>  listUserBaskets() throws Exception;
	// 16.07.2011
	/*
	public  DbElementsList<Measurement> listTrialMeasurementsWithAttributes(int trialID) throws Exception;
	*/
	public int createMeasurement(int trialID, int measurementConfigurationID ) throws Exception;
	
	public int createMeasurementConfiguration(String name, String kind, String description ) throws Exception;
	
	public void  defineAttributeGroup(String groupName, String unit) throws Exception;
	
	public String [] listMeasurementConfKinds();

	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(EntityKind kind) throws Exception;

	DbElementsList<MeasurementConfiguration> listMeasurementConfigurationsWithAttributes() throws Exception;

	void uploadFile(int resourceId, EntityKind kind, String description,
			String localFilePath, FileTransferListener listener)
			throws Exception;

	public void setFileTypedAttribute(int resourceId, EntityAttribute attribute, int fileID, boolean update) throws Exception;

	void updateStoredFilters(ArrayOfFilterPredicate filter) throws Exception;

	List<motion.database.ws.userPersonalSpaceWCF.FilterList.FilterPredicate> listStoredFilters()
			throws Exception;

	DbElementsList<? extends GenericDescription<?>> listBasketEntitiesWithAttributes(String basketName, EntityKind kind) throws Exception;

	void addAttributeEnumValue(EntityAttribute a, String value,	boolean clearExisting) throws Exception;

	void defineAttribute(EntityAttribute a, String pluginDescriptor) throws Exception;

	void clearEntityAttribute(int ID, EntityAttribute a) throws Exception;

	void removeAttribute(EntityAttribute a, String pluginDescriptor)
			throws Exception;

	void removeAttributeGroup(String groupName, String unit) throws Exception;

	void uploadDirectory(int resourceId, EntityKind kind, String description, String filesPath,	FileTransferListener listener) throws Exception;

	DbElementsList<Performer> listSessionPerformersWithAttributes(int sessionID) throws Exception;

	int assignPerformerToSession(int sessionID, int performerID) throws Exception;
	// 16.07.2011
	/*
	boolean addPerformerToMeasurement(int performerID, int measurementID) throws Exception;
	*/
	void uploadFilesDirectories(int resourceId, EntityKind kind,
			String description, File[] files,
			FileTransferListener listener) throws Exception;

	DbElementsList<Session> listGroupSessions(int sessionGroupID) throws Exception;

	DbElementsList<SessionGroup> listSessionSessionGroups(int sessionID) throws Exception;

	void saveAttributeViewConfiguration() throws Exception;

	void readAttributeViewConfiguration() throws Exception;

	DbElementsList<PerformerConfiguration> listSessionPerformerConfigurations(
			int sessionID) throws Exception;

	Session getSessionContent(int sessionID) throws Exception;

	SessionValidationInfo validateSessionFileSet(File[] paths) throws Exception;

	int uploadSessionFileSet(File[] paths, FileTransferListener listener)
			throws Exception;

	void replaceFile(int resourceId, String localFilePath,
			FileTransferListener listener) throws Exception;
	// 16.07.2011
	/*
	DbElementsList<Measurement> listMeasurementConfMeasurementsWithAttributes(
			int measurementConfID) throws Exception;
	*/
	boolean assignSessionToGroup(int sessionID, int groupID) throws Exception;


}