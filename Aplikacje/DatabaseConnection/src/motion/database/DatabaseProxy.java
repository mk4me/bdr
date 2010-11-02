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
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.Trial;
import motion.database.model.User;
import motion.database.model.UserBasket;
import motion.database.model.UserPrivileges;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;
import motion.database.ws.userPersonalSpaceWCF.ArrayOfFilterPredicate;

public interface DatabaseProxy {

	public abstract void setWSCredentials(String userName, String password,
			String domainName);

	public abstract void setFTPSCredentials(String address, String userName,
			String password);

	public abstract String getConnectionInfo();
	
	
	//public abstract boolean testConnection() throws Exception;

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

	public abstract int createPerformer(String name, String surname)
			throws Exception;

	public abstract int createSession(int[] sessionGroupID,
			String sessionDescription, int labID, XMLGregorianCalendar sessionDate, String motionKindName)
			throws Exception;

	public abstract int createTrial(int sessionID, String trialDescription) throws Exception;

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

	public  DbElementsList<Measurement> listTrialMeasurementsWithAttributes(int trialID) throws Exception;

	public int createMeasurement(int trialID, int measurementConfigurationID ) throws Exception;
	
	public int createMeasurementConfiguration(String name, String kind, String description ) throws Exception;
	
	public void  defineAttributeGroup(String groupName, String unit) throws Exception;
	
	public String [] listMeasurementConfKinds();

	public HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(EntityKind kind) throws Exception;

	DbElementsList<MeasurementConfiguration> listMeasurementConfigurationsWithAttributes(
			int trialID) throws Exception;

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

	boolean addPerformerToMeasurement(int performerID, int measurementID) throws Exception;

	void uploadFilesDirectories(int resourceId, EntityKind kind,
			String description, File[] files,
			FileTransferListener listener) throws Exception;

	DbElementsList<Session> listGroupSessions(int sessionGroupID) throws Exception;

	DbElementsList<SessionGroup> listSessionSessionGroups(int sessionID) throws Exception;


}