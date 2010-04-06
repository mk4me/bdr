
package motion.database.ws.basicQueriesService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import motion.database.ws.basicQueriesService.GenericQueryXMLResponse.GenericQueryXMLResult;
import motion.database.ws.basicQueriesService.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult;
import motion.database.ws.basicQueriesService.GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult;
import motion.database.ws.basicQueriesService.GetSessionByIdXMLResponse.GetSessionByIdXMLResult;
import motion.database.ws.basicQueriesService.GetTrialByIdXMLResponse.GetTrialByIdXMLResult;
import motion.database.ws.basicQueriesService.ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesService.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListFilesXMLResponse.ListFilesXMLResult;
import motion.database.ws.basicQueriesService.ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersXMLResponse.ListPerformersXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult;
import motion.database.ws.basicQueriesService.PerformQueryResponse.PerformQueryResult;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b59-fcs
 * Generated source version: 2.0
 * 
 */
@WebService(name = "BasicQueriesServiceSoap", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
public interface BasicQueriesServiceSoap {


    /**
     * 
     * @param performerID
     * @return
     *     returns motion.database.ws.basicQueriesService.ArrayOfPlainSessionDetails
     */
    @WebMethod(operationName = "ListPerformerSessions", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListPerformerSessions")
    @WebResult(name = "ListPerformerSessionsResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListPerformerSessions", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformerSessions")
    @ResponseWrapper(localName = "ListPerformerSessionsResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformerSessionsResponse")
    public ArrayOfPlainSessionDetails listPerformerSessions(
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int performerID);

    /**
     * 
     * @param sessionID
     * @return
     *     returns motion.database.ws.basicQueriesService.ArrayOfPlainFileDetails
     */
    @WebMethod(operationName = "ListSessionFiles", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListSessionFiles")
    @WebResult(name = "ListSessionFilesResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListSessionFiles", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionFiles")
    @ResponseWrapper(localName = "ListSessionFilesResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionFilesResponse")
    public ArrayOfPlainFileDetails listSessionFiles(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int sessionID);

    /**
     * 
     * @param entitiesToInclude
     * @param filter
     * @return
     *     returns motion.database.ws.basicQueriesService.GenericQueryXMLResponse.GenericQueryXMLResult
     */
    @WebMethod(operationName = "GenericQueryXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/GenericQueryXML")
    @WebResult(name = "GenericQueryXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "GenericQueryXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GenericQueryXML")
    @ResponseWrapper(localName = "GenericQueryXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GenericQueryXMLResponse")
    public GenericQueryXMLResult genericQueryXML(
        @WebParam(name = "filter", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        ArrayOfFilterPredicate filter,
        @WebParam(name = "entitiesToInclude", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        ArrayOfString entitiesToInclude);

    /**
     * 
     * @param id
     * @return
     *     returns motion.database.ws.basicQueriesService.GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult
     */
    @WebMethod(operationName = "GetPerformerByIdXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/GetPerformerByIdXML")
    @WebResult(name = "GetPerformerByIdXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "GetPerformerByIdXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetPerformerByIdXML")
    @ResponseWrapper(localName = "GetPerformerByIdXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetPerformerByIdXMLResponse")
    public GetPerformerByIdXMLResult getPerformerByIdXML(
        @WebParam(name = "id", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int id);

    /**
     * 
     * @param id
     * @return
     *     returns motion.database.ws.basicQueriesService.GetSessionByIdXMLResponse.GetSessionByIdXMLResult
     */
    @WebMethod(operationName = "GetSessionByIdXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/GetSessionByIdXML")
    @WebResult(name = "GetSessionByIdXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "GetSessionByIdXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetSessionByIdXML")
    @ResponseWrapper(localName = "GetSessionByIdXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetSessionByIdXMLResponse")
    public GetSessionByIdXMLResult getSessionByIdXML(
        @WebParam(name = "id", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int id);

    /**
     * 
     * @param id
     * @return
     *     returns motion.database.ws.basicQueriesService.GetTrialByIdXMLResponse.GetTrialByIdXMLResult
     */
    @WebMethod(operationName = "GetTrialByIdXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/GetTrialByIdXML")
    @WebResult(name = "GetTrialByIdXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "GetTrialByIdXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetTrialByIdXML")
    @ResponseWrapper(localName = "GetTrialByIdXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetTrialByIdXMLResponse")
    public GetTrialByIdXMLResult getTrialByIdXML(
        @WebParam(name = "id", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int id);

    /**
     * 
     * @param id
     * @return
     *     returns motion.database.ws.basicQueriesService.GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult
     */
    @WebMethod(operationName = "GetSegmentByIdXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/GetSegmentByIdXML")
    @WebResult(name = "GetSegmentByIdXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "GetSegmentByIdXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetSegmentByIdXML")
    @ResponseWrapper(localName = "GetSegmentByIdXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.GetSegmentByIdXMLResponse")
    public GetSegmentByIdXMLResult getSegmentByIdXML(
        @WebParam(name = "id", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int id);

    /**
     * 
     * @return
     *     returns motion.database.ws.basicQueriesService.ListPerformersXMLResponse.ListPerformersXMLResult
     */
    @WebMethod(operationName = "ListPerformersXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListPerformersXML")
    @WebResult(name = "ListPerformersXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListPerformersXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformersXML")
    @ResponseWrapper(localName = "ListPerformersXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformersXMLResponse")
    public ListPerformersXMLResult listPerformersXML();

    /**
     * 
     * @return
     *     returns motion.database.ws.basicQueriesService.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListPerformersWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListPerformersWithAttributesXML")
    @WebResult(name = "ListPerformersWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListPerformersWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformersWithAttributesXML")
    @ResponseWrapper(localName = "ListPerformersWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformersWithAttributesXMLResponse")
    public ListPerformersWithAttributesXMLResult listPerformersWithAttributesXML();

    /**
     * 
     * @param labID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListLabPerformersWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListLabPerformersWithAttributesXML")
    @WebResult(name = "ListLabPerformersWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListLabPerformersWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListLabPerformersWithAttributesXML")
    @ResponseWrapper(localName = "ListLabPerformersWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListLabPerformersWithAttributesXMLResponse")
    public ListLabPerformersWithAttributesXMLResult listLabPerformersWithAttributesXML(
        @WebParam(name = "labID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int labID);

    /**
     * 
     * @param performerID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult
     */
    @WebMethod(operationName = "ListPerformerSessionsXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListPerformerSessionsXML")
    @WebResult(name = "ListPerformerSessionsXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListPerformerSessionsXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformerSessionsXML")
    @ResponseWrapper(localName = "ListPerformerSessionsXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse")
    public ListPerformerSessionsXMLResult listPerformerSessionsXML(
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int performerID);

    /**
     * 
     * @param performerID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListPerformerSessionsWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListPerformerSessionsWithAttributesXML")
    @WebResult(name = "ListPerformerSessionsWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListPerformerSessionsWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXML")
    @ResponseWrapper(localName = "ListPerformerSessionsWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse")
    public ListPerformerSessionsWithAttributesXMLResult listPerformerSessionsWithAttributesXML(
        @WebParam(name = "performerID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int performerID);

    /**
     * 
     * @param labID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListLabSessionsWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListLabSessionsWithAttributesXML")
    @WebResult(name = "ListLabSessionsWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListLabSessionsWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListLabSessionsWithAttributesXML")
    @ResponseWrapper(localName = "ListLabSessionsWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListLabSessionsWithAttributesXMLResponse")
    public ListLabSessionsWithAttributesXMLResult listLabSessionsWithAttributesXML(
        @WebParam(name = "labID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int labID);

    /**
     * 
     * @param sessionID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult
     */
    @WebMethod(operationName = "ListSessionTrialsXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListSessionTrialsXML")
    @WebResult(name = "ListSessionTrialsXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListSessionTrialsXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionTrialsXML")
    @ResponseWrapper(localName = "ListSessionTrialsXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionTrialsXMLResponse")
    public ListSessionTrialsXMLResult listSessionTrialsXML(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int sessionID);

    /**
     * 
     * @param sessionID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListSessionTrialsWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListSessionTrialsWithAttributesXML")
    @WebResult(name = "ListSessionTrialsWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListSessionTrialsWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXML")
    @ResponseWrapper(localName = "ListSessionTrialsWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse")
    public ListSessionTrialsWithAttributesXMLResult listSessionTrialsWithAttributesXML(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int sessionID);

    /**
     * 
     * @param trialID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult
     */
    @WebMethod(operationName = "ListTrialSegmentsXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListTrialSegmentsXML")
    @WebResult(name = "ListTrialSegmentsXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListTrialSegmentsXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialSegmentsXML")
    @ResponseWrapper(localName = "ListTrialSegmentsXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialSegmentsXMLResponse")
    public ListTrialSegmentsXMLResult listTrialSegmentsXML(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int trialID);

    /**
     * 
     * @param trialID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListTrialSegmentsWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListTrialSegmentsWithAttributesXML")
    @WebResult(name = "ListTrialSegmentsWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListTrialSegmentsWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXML")
    @ResponseWrapper(localName = "ListTrialSegmentsWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse")
    public ListTrialSegmentsWithAttributesXMLResult listTrialSegmentsWithAttributesXML(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int trialID);

    /**
     * 
     * @param subjectID
     * @param subjectType
     * @return
     *     returns motion.database.ws.basicQueriesService.ListFilesXMLResponse.ListFilesXMLResult
     */
    @WebMethod(operationName = "ListFilesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListFilesXML")
    @WebResult(name = "ListFilesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListFilesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListFilesXML")
    @ResponseWrapper(localName = "ListFilesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListFilesXMLResponse")
    public ListFilesXMLResult listFilesXML(
        @WebParam(name = "subjectID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int subjectID,
        @WebParam(name = "subjectType", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        String subjectType);

    /**
     * 
     * @param subjectID
     * @param subjectType
     * @return
     *     returns motion.database.ws.basicQueriesService.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListFilesWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListFilesWithAttributesXML")
    @WebResult(name = "ListFilesWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListFilesWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListFilesWithAttributesXML")
    @ResponseWrapper(localName = "ListFilesWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListFilesWithAttributesXMLResponse")
    public ListFilesWithAttributesXMLResult listFilesWithAttributesXML(
        @WebParam(name = "subjectID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int subjectID,
        @WebParam(name = "subjectType", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        String subjectType);

    /**
     * 
     * @param query
     * @return
     *     returns motion.database.ws.basicQueriesService.PerformQueryResponse.PerformQueryResult
     */
    @WebMethod(operationName = "_PerformQuery", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/_PerformQuery")
    @WebResult(name = "_PerformQueryResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "_PerformQuery", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.PerformQuery")
    @ResponseWrapper(localName = "_PerformQueryResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.PerformQueryResponse")
    public PerformQueryResult performQuery(
        @WebParam(name = "query", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        String query);

    /**
     * 
     * @param entityKind
     * @param attributeGroupName
     * @return
     *     returns motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult
     */
    @WebMethod(operationName = "ListAttributesDefined", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListAttributesDefined")
    @WebResult(name = "ListAttributesDefinedResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListAttributesDefined", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListAttributesDefined")
    @ResponseWrapper(localName = "ListAttributesDefinedResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListAttributesDefinedResponse")
    public ListAttributesDefinedResult listAttributesDefined(
        @WebParam(name = "attributeGroupName", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        String attributeGroupName,
        @WebParam(name = "entityKind", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        String entityKind);

    /**
     * 
     * @param entityKind
     * @return
     *     returns motion.database.ws.basicQueriesService.ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult
     */
    @WebMethod(operationName = "ListAttributeGroupsDefined", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListAttributeGroupsDefined")
    @WebResult(name = "ListAttributeGroupsDefinedResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListAttributeGroupsDefined", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListAttributeGroupsDefined")
    @ResponseWrapper(localName = "ListAttributeGroupsDefinedResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListAttributeGroupsDefinedResponse")
    public ListAttributeGroupsDefinedResult listAttributeGroupsDefined(
        @WebParam(name = "entityKind", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        String entityKind);

}
