package motion.database;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.XMLGregorianCalendar;

import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.Filter;
import motion.database.model.GenericResult;
import motion.database.model.MotionKind;
import motion.database.model.Performer;
import motion.database.model.Segment;
import motion.database.model.Session;
import motion.database.model.SessionGroup;
import motion.database.model.Trial;

public interface DatabaseProxy {

	public abstract void setWSCredentials(String userName, String password,
			String domainName);

	public abstract void setFTPSCredentials(String address, String userName,
			String password);

	public abstract boolean testConnection() throws Exception;

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
			FileTransferListener listener) throws Exception;

	public abstract void uploadPerformerFiles(int performerId,
			String filesPath, FileTransferListener listener) throws Exception;

	public abstract void uploadTrialFiles(int trialId, String filesPath,
			FileTransferListener listener) throws Exception;

	public abstract List<GenericResult> execGenericQuery(Filter filter,
			String[] p_entitiesToInclude) throws Exception;

	public abstract DbElementsList<Performer> listPerformersWithAttributes()
			throws Exception;

	public abstract DbElementsList<Performer> listLabPerformersWithAttributes(
			int labID) throws Exception;

	public  String getSessionLabel(int sessionID) throws Exception;

	
	@Deprecated
	public abstract DbElementsList<Session> listPerformerSessions(
			int performerID) throws Exception;

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
			String sessionDescription, int labID, int userID,
			XMLGregorianCalendar sessionDate, String motionKindName)
			throws Exception;

	public abstract int createTrial(int sessionID, String trialDescription,
			int trialDuration) throws Exception;

	public abstract int defineTrialSegment(int trialID, String segmentName,
			int startTime, int endTime) throws Exception;

	public abstract int setSessionAttribute(int sessionID,
			String attributeName, String attributeValue, boolean update)
			throws Exception;

	public abstract int setTrialAttribute(int trialID, String attributeName,
			String attributeValue, boolean update) throws Exception;

	public abstract int setPerformerAttribute(int performerID,
			String attributeName, String attributeValue, boolean update)
			throws Exception;

	public abstract int setSegmentAttribute(int segmentID,
			String attributeName, String attributeValue, boolean update)
			throws Exception;

	public abstract int setFileAttribute(int fileID, String attributeName,
			String attributeValue, boolean update) throws Exception;

	public abstract DbElementsList<Segment> listTrialSegmentsWithAttributes(
			int trialID) throws Exception;

	public abstract DbElementsList<DatabaseFile> listSessionFiles(int sessionID)
			throws Exception;

	public abstract DbElementsList<DatabaseFile> listTrialFiles(int trialID)
			throws Exception;

	public abstract DbElementsList<DatabaseFile> listPerformerFiles(
			int performerID) throws Exception;

	/**
	 * 
	 * @param fileID
	 * @param destLocalFolder must end with "/"
	 * @param transferListener 
	 * @return
	 * @throws Exception
	 */
	public abstract String downloadFile(int fileID, String destLocalFolder,
			FileTransferListener transferListener) throws Exception;

	public abstract Performer getPerformerById(int id) throws Exception;

	public abstract void registerStateMessageListener(TextMessageListener listener);

}