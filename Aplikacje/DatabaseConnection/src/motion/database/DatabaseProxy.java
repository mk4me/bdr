package motion.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.XMLGregorianCalendar;

import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericResult;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Segment;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.Trial;
import motion.database.model.User;
import motion.database.model.UserPrivileges;
import motion.database.ws.basicQueriesServiceWCF.FilterPredicate;

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
	
	public void addEntityToBasket(String basketName, int resourceID) throws Exception;
	
	public DbElementsList<UserPrivileges> listSessionPrivileges(int sessionID) throws Exception;

	public DbElementsList<User> listUsers() throws Exception;

	
	public abstract void registerFileUploadListener(
			FileTransferListener listener);

	public abstract void cancelCurrentFileTransfer();

	public abstract void uploadSessionFile(int sessionId, String description,
			String localFilePath, FileTransferListener listener)
			throws Exception;

	public abstract void uploadTrialFile(int trialId, String description,
			String localFilePath, FileTransferListener listener)
			throws Exception;

	public abstract void uploadPerformerFile(int performerId,
			String description, String localFilePath,
			FileTransferListener listener) throws Exception;

	public abstract void uploadSessionFiles(int sessionId, String filesPath,
			String description, FileTransferListener listener) throws Exception;

	public abstract void uploadPerformerFiles(int performerId,
			String filesPath, String description, FileTransferListener listener) throws Exception;

	public abstract void uploadTrialFiles(int trialId, String filesPath,
			String description, FileTransferListener listener) throws Exception;

	public abstract List<GenericResult> execGenericQuery(ArrayList<FilterPredicate> filterPredicates,
			String[] p_entitiesToInclude) throws Exception;

	public abstract DbElementsList<Performer> listPerformersWithAttributes()
			throws Exception;

	public abstract DbElementsList<Performer> listLabPerformersWithAttributes(
			int labID) throws Exception;

	public  String getSessionLabel(int sessionID) throws Exception;

	
//	@Deprecated
//	public abstract DbElementsList<Session> listPerformerSessions(
//			int performerID) throws Exception;

	public List<String> listEnumValues(String attributeName, String entityKind) throws Exception;

	public abstract HashMap<String, String> listAttributesDefined(String group,
			String entityKind) throws Exception;

	public abstract HashMap<String, EntityAttributeGroup> listGrouppedAttributesDefined(
			String entityKind) throws Exception;

	public abstract Vector<String> listAttributeGroupsDefined(String entityKind)
			throws Exception;

	public abstract Vector<MotionKind> listMotionKindsDefined()
			throws Exception;

	public abstract Vector<SessionGroup> listSessionGroupsDefined()
			throws Exception;

	public abstract Session getSessionById(int id) throws Exception;

	public abstract Trial getTrialById(int id) throws Exception;

	public abstract Segment getSegmentById(int id) throws Exception;

	public abstract DbElementsList<Session> listPerformerSessionsWithAttributes(
			int performerID) throws Exception;

	public abstract DbElementsList<Session> listLabSessionsWithAttributes(
			int labID) throws Exception;

	public abstract DbElementsList<Trial> listSessionTrialsWithAttributes(
			int sessionID) throws Exception;

	public abstract int createPerformer(String name, String surname)
			throws Exception;

	public abstract int createSession(int performerID, int[] sessionGroupID,
			String sessionDescription, int labID, XMLGregorianCalendar sessionDate, String motionKindName)
			throws Exception;

	public abstract int createTrial(int sessionID, String trialDescription,
			int trialDuration) throws Exception;

	public abstract int defineTrialSegment(int trialID, String segmentName,
			int startTime, int endTime) throws Exception;

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
	 * @param kind kind of an entity
	 * @param attributeValue value of an attribute. If set to null then an attribute value is removed.
	 * @param update indicated updating action
	 * @throws Exception
	 */
	public abstract void setEntityAttribute(int entityID,
			EntityKind kind, EntityAttribute attributeValue, boolean update)
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

	@Deprecated
	public abstract void setSegmentAttribute(int segmentID,
			String attributeName, String attributeValue, boolean update)
			throws Exception;

	@Deprecated
	public abstract void setFileAttribute(int fileID, String attributeName,
			String attributeValue, boolean update) throws Exception;
	
	public abstract DbElementsList<Segment> listTrialSegmentsWithAttributes(
			int trialID) throws Exception;

	public abstract DbElementsList<DatabaseFile> listSessionFiles(int sessionID)
			throws Exception;

	public abstract DbElementsList<DatabaseFile> listTrialFiles(int trialID)
			throws Exception;

	public abstract DbElementsList<DatabaseFile> listPerformerFiles(
			int performerID) throws Exception;

	public abstract String downloadFile(int fileID, String destLocalFolder,
			FileTransferListener transferListener, boolean recreateFolder) throws Exception;

	public abstract Performer getPerformerById(int id) throws Exception;

	public abstract void registerStateMessageListener(TextMessageListener listener);

	public void removeSessionPrivileges(String grantedUserLogin, int sessionID, boolean writePrivilege) throws Exception;

	public void grantSessionPrivileges(String grantedUserLogin, int sessionID, boolean writePrivilege) throws Exception;

	public void createUserAccount(String firstName, String lastName) throws Exception;
	
	public boolean checkUserAccount() throws Exception;
	
	public void setSessionPrivileges(int sessionID, boolean readPrivilege, boolean writePrivilege) throws Exception;

}