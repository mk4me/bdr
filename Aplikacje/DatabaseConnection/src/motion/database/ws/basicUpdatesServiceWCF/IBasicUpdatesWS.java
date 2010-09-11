
package motion.database.ws.basicUpdatesServiceWCF;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "IBasicUpdatesWS", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IBasicUpdatesWS {


    /**
     * 
     * @param performerData
     * @return
     *     returns int
     * @throws IBasicUpdatesWSCreatePerformerUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreatePerformer", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/CreatePerformer")
    @WebResult(name = "CreatePerformerResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreatePerformer", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreatePerformer")
    @ResponseWrapper(localName = "CreatePerformerResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreatePerformerResponse")
    public int createPerformer(
        @WebParam(name = "performerData", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        PerformerData performerData)
        throws IBasicUpdatesWSCreatePerformerUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionDate
     * @param motionKindName
     * @param labID
     * @param sessionGroupIDs
     * @param sessionDescription
     * @return
     *     returns int
     * @throws IBasicUpdatesWSCreateSessionUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreateSession", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/CreateSession")
    @WebResult(name = "CreateSessionResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreateSession", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateSession")
    @ResponseWrapper(localName = "CreateSessionResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateSessionResponse")
    public int createSession(
        @WebParam(name = "labID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int labID,
        @WebParam(name = "motionKindName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String motionKindName,
        @WebParam(name = "sessionDate", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        XMLGregorianCalendar sessionDate,
        @WebParam(name = "sessionDescription", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String sessionDescription,
        @WebParam(name = "sessionGroupIDs", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        ArrayOfInt sessionGroupIDs)
        throws IBasicUpdatesWSCreateSessionUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param mcDescription
     * @param mcName
     * @param mcKind
     * @return
     *     returns int
     * @throws IBasicUpdatesWSCreateMeasurementConfigurationUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreateMeasurementConfiguration", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/CreateMeasurementConfiguration")
    @WebResult(name = "CreateMeasurementConfigurationResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreateMeasurementConfiguration", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateMeasurementConfiguration")
    @ResponseWrapper(localName = "CreateMeasurementConfigurationResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateMeasurementConfigurationResponse")
    public int createMeasurementConfiguration(
        @WebParam(name = "mcName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String mcName,
        @WebParam(name = "mcKind", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String mcKind,
        @WebParam(name = "mcDescription", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String mcDescription)
        throws IBasicUpdatesWSCreateMeasurementConfigurationUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param trialDescription
     * @param sessionID
     * @param trialDuration
     * @return
     *     returns int
     * @throws IBasicUpdatesWSCreateTrialUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreateTrial", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/CreateTrial")
    @WebResult(name = "CreateTrialResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreateTrial", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateTrial")
    @ResponseWrapper(localName = "CreateTrialResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateTrialResponse")
    public int createTrial(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int sessionID,
        @WebParam(name = "trialDescription", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String trialDescription,
        @WebParam(name = "trialDuration", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int trialDuration)
        throws IBasicUpdatesWSCreateTrialUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param mcID
     * @param trialID
     * @return
     *     returns int
     * @throws IBasicUpdatesWSCreateMeasurementUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "CreateMeasurement", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/CreateMeasurement")
    @WebResult(name = "CreateMeasurementResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "CreateMeasurement", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateMeasurement")
    @ResponseWrapper(localName = "CreateMeasurementResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.CreateMeasurementResponse")
    public int createMeasurement(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int trialID,
        @WebParam(name = "mcID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int mcID)
        throws IBasicUpdatesWSCreateMeasurementUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param groupID
     * @param sessionID
     * @return
     *     returns boolean
     * @throws IBasicUpdatesWSAssignSessionToGroupUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "AssignSessionToGroup", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/AssignSessionToGroup")
    @WebResult(name = "AssignSessionToGroupResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "AssignSessionToGroup", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.AssignSessionToGroup")
    @ResponseWrapper(localName = "AssignSessionToGroupResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.AssignSessionToGroupResponse")
    public boolean assignSessionToGroup(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int sessionID,
        @WebParam(name = "groupID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int groupID)
        throws IBasicUpdatesWSAssignSessionToGroupUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param sessionID
     * @param performerID
     * @return
     *     returns int
     * @throws IBasicUpdatesWSAssignPerformerToSessionUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "AssignPerformerToSession", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/AssignPerformerToSession")
    @WebResult(name = "AssignPerformerToSessionResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "AssignPerformerToSession", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.AssignPerformerToSession")
    @ResponseWrapper(localName = "AssignPerformerToSessionResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.AssignPerformerToSessionResponse")
    public int assignPerformerToSession(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int sessionID,
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int performerID)
        throws IBasicUpdatesWSAssignPerformerToSessionUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param MeasurementID
     * @param performerID
     * @return
     *     returns boolean
     * @throws IBasicUpdatesWSAddPerformerToMeasurementUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "AddPerformerToMeasurement", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/AddPerformerToMeasurement")
    @WebResult(name = "AddPerformerToMeasurementResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    @RequestWrapper(localName = "AddPerformerToMeasurement", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.AddPerformerToMeasurement")
    @ResponseWrapper(localName = "AddPerformerToMeasurementResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.AddPerformerToMeasurementResponse")
    public boolean addPerformerToMeasurement(
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int performerID,
        @WebParam(name = "MeasurementID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int measurementID)
        throws IBasicUpdatesWSAddPerformerToMeasurementUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param update
     * @param attributeName
     * @param attributeValue
     * @param performerID
     * @throws IBasicUpdatesWSSetPerformerAttributeUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "SetPerformerAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/SetPerformerAttribute")
    @RequestWrapper(localName = "SetPerformerAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetPerformerAttribute")
    @ResponseWrapper(localName = "SetPerformerAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetPerformerAttributeResponse")
    public void setPerformerAttribute(
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int performerID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update)
        throws IBasicUpdatesWSSetPerformerAttributeUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param update
     * @param attributeName
     * @param sessionID
     * @param attributeValue
     * @throws IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "SetSessionAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/SetSessionAttribute")
    @RequestWrapper(localName = "SetSessionAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetSessionAttribute")
    @ResponseWrapper(localName = "SetSessionAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetSessionAttributeResponse")
    public void setSessionAttribute(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int sessionID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update)
        throws IBasicUpdatesWSSetSessionAttributeUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param update
     * @param attributeName
     * @param attributeValue
     * @param trialID
     * @throws IBasicUpdatesWSSetTrialAttributeUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "SetTrialAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/SetTrialAttribute")
    @RequestWrapper(localName = "SetTrialAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetTrialAttribute")
    @ResponseWrapper(localName = "SetTrialAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetTrialAttributeResponse")
    public void setTrialAttribute(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int trialID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update)
        throws IBasicUpdatesWSSetTrialAttributeUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param update
     * @param attributeName
     * @param MeasurementID
     * @param attributeValue
     * @throws IBasicUpdatesWSSetMeasurementAttributeUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "SetMeasurementAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/SetMeasurementAttribute")
    @RequestWrapper(localName = "SetMeasurementAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetMeasurementAttribute")
    @ResponseWrapper(localName = "SetMeasurementAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetMeasurementAttributeResponse")
    public void setMeasurementAttribute(
        @WebParam(name = "MeasurementID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int measurementID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update)
        throws IBasicUpdatesWSSetMeasurementAttributeUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param update
     * @param attributeName
     * @param attributeValue
     * @param measurementConfID
     * @throws IBasicUpdatesWSSetMeasurementConfAttributeUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "SetMeasurementConfAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/SetMeasurementConfAttribute")
    @RequestWrapper(localName = "SetMeasurementConfAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetMeasurementConfAttribute")
    @ResponseWrapper(localName = "SetMeasurementConfAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetMeasurementConfAttributeResponse")
    public void setMeasurementConfAttribute(
        @WebParam(name = "measurementConfID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int measurementConfID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update)
        throws IBasicUpdatesWSSetMeasurementConfAttributeUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param update
     * @param attributeName
     * @param fileID
     * @param attributeValue
     * @throws IBasicUpdatesWSSetFileAttributeUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "SetFileAttribute", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/SetFileAttribute")
    @RequestWrapper(localName = "SetFileAttribute", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetFileAttribute")
    @ResponseWrapper(localName = "SetFileAttributeResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetFileAttributeResponse")
    public void setFileAttribute(
        @WebParam(name = "fileID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int fileID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "attributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeValue,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update)
        throws IBasicUpdatesWSSetFileAttributeUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param attributeName
     * @param resourceID
     * @param entity
     * @throws IBasicUpdatesWSClearAttributeValueUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "ClearAttributeValue", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/ClearAttributeValue")
    @RequestWrapper(localName = "ClearAttributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.ClearAttributeValue")
    @ResponseWrapper(localName = "ClearAttributeValueResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.ClearAttributeValueResponse")
    public void clearAttributeValue(
        @WebParam(name = "resourceID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int resourceID,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "entity", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String entity)
        throws IBasicUpdatesWSClearAttributeValueUpdateExceptionFaultFaultMessage
    ;

    /**
     * 
     * @param update
     * @param attributeName
     * @param fileID
     * @param resourceID
     * @param entity
     * @throws IBasicUpdatesWSSetFileTypedAttributeValueUpdateExceptionFaultFaultMessage
     */
    @WebMethod(operationName = "SetFileTypedAttributeValue", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService/IBasicUpdatesWS/SetFileTypedAttributeValue")
    @RequestWrapper(localName = "SetFileTypedAttributeValue", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetFileTypedAttributeValue")
    @ResponseWrapper(localName = "SetFileTypedAttributeValueResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", className = "motion.database.ws.basicUpdatesServiceWCF.SetFileTypedAttributeValueResponse")
    public void setFileTypedAttributeValue(
        @WebParam(name = "resourceID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int resourceID,
        @WebParam(name = "entity", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String entity,
        @WebParam(name = "attributeName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        String attributeName,
        @WebParam(name = "fileID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        int fileID,
        @WebParam(name = "update", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
        boolean update)
        throws IBasicUpdatesWSSetFileTypedAttributeValueUpdateExceptionFaultFaultMessage
    ;

}
