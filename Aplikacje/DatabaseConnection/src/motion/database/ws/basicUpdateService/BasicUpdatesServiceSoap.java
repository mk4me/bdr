
package motion.database.ws.basicUpdateService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b59-fcs
 * Generated source version: 2.0
 * 
 */
@WebService(name = "BasicUpdatesServiceSoap", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
public interface BasicUpdatesServiceSoap {


    /**
     * 
     * @param performerData
     * @return
     *     returns int
     */
    @WebMethod(operationName = "CreatePerformer", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/CreatePerformer")
    @WebResult(name = "CreatePerformerResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreatePerformer", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.CreatePerformer")
    @ResponseWrapper(localName = "CreatePerformerResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.CreatePerformerResponse")
    public int createPerformer(
        @WebParam(name = "performerData", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        PerformerData performerData);

    /**
     * 
     * @param sessionData
     * @param sessionGroupIDs
     * @param performerID
     * @return
     *     returns int
     */
    @WebMethod(operationName = "CreateSession", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/CreateSession")
    @WebResult(name = "CreateSessionResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreateSession", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.CreateSession")
    @ResponseWrapper(localName = "CreateSessionResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.CreateSessionResponse")
    public int createSession(
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int performerID,
        @WebParam(name = "sessionGroupIDs", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        ArrayOfInt sessionGroupIDs,
        @WebParam(name = "sessionData", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        SessionData sessionData);

    /**
     * 
     * @param sessionID
     * @param trialData
     * @return
     *     returns int
     */
    @WebMethod(operationName = "CreateTrial", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/CreateTrial")
    @WebResult(name = "CreateTrialResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreateTrial", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.CreateTrial")
    @ResponseWrapper(localName = "CreateTrialResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.CreateTrialResponse")
    public int createTrial(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int sessionID,
        @WebParam(name = "trialData", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        TrialData trialData);

    /**
     * 
     * @param groupID
     * @param sessionID
     * @return
     *     returns boolean
     */
    @WebMethod(operationName = "AssignSessionToGroup", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/AssignSessionToGroup")
    @WebResult(name = "AssignSessionToGroupResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "AssignSessionToGroup", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.AssignSessionToGroup")
    @ResponseWrapper(localName = "AssignSessionToGroupResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.AssignSessionToGroupResponse")
    public boolean assignSessionToGroup(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int sessionID,
        @WebParam(name = "groupID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int groupID);

    /**
     * 
     * @param update
     * @param attributeName
     * @param attributeValue
     * @param performerID
     * @return
     *     returns int
     */
    @WebMethod(operationName = "SetPerformerAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/SetPerformerAttribute")
    @WebResult(name = "SetPerformerAttributeResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "SetPerformerAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetPerformerAttribute")
    @ResponseWrapper(localName = "SetPerformerAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetPerformerAttributeResponse")
    public int setPerformerAttribute(
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int performerID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update);

    /**
     * 
     * @param update
     * @param attributeName
     * @param sessionID
     * @param attributeValue
     * @return
     *     returns int
     */
    @WebMethod(operationName = "SetSessionAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/SetSessionAttribute")
    @WebResult(name = "SetSessionAttributeResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "SetSessionAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetSessionAttribute")
    @ResponseWrapper(localName = "SetSessionAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetSessionAttributeResponse")
    public int setSessionAttribute(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int sessionID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update);

    /**
     * 
     * @param attributeValue
     * @param trialID
     * @param attributeID
     * @return
     *     returns boolean
     */
    @WebMethod(operationName = "SetTrialAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/SetTrialAttribute")
    @WebResult(name = "SetTrialAttributeResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "SetTrialAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetTrialAttribute")
    @ResponseWrapper(localName = "SetTrialAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetTrialAttributeResponse")
    public boolean setTrialAttribute(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int trialID,
        @WebParam(name = "attributeID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int attributeID,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue);

    /**
     * 
     * @param fileID
     * @param attributeValue
     * @param attributeId
     * @return
     *     returns boolean
     */
    @WebMethod(operationName = "SetFileAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/SetFileAttribute")
    @WebResult(name = "SetFileAttributeResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "SetFileAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetFileAttribute")
    @ResponseWrapper(localName = "SetFileAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdateService.SetFileAttributeResponse")
    public boolean setFileAttribute(
        @WebParam(name = "fileID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int fileID,
        @WebParam(name = "attributeId", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int attributeId,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue);

}