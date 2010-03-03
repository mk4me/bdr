
package motion.database.ws.basicQueriesService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult;
import motion.database.ws.basicQueriesService.ListSessionFilesWithAttributesXMLResponse.ListSessionFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse.ListSessionFilesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult;
import motion.database.ws.basicQueriesService.ListTrialFilesWithAttributesXMLResponse.ListTrialFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialFilesXMLResponse.ListTrialFilesXMLResult;
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
     * @param sessionID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse.ListSessionFilesXMLResult
     */
    @WebMethod(operationName = "ListSessionFilesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListSessionFilesXML")
    @WebResult(name = "ListSessionFilesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListSessionFilesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionFilesXML")
    @ResponseWrapper(localName = "ListSessionFilesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse")
    public ListSessionFilesXMLResult listSessionFilesXML(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int sessionID);

    /**
     * 
     * @param sessionID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListSessionFilesWithAttributesXMLResponse.ListSessionFilesWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListSessionFilesWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListSessionFilesWithAttributesXML")
    @WebResult(name = "ListSessionFilesWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListSessionFilesWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionFilesWithAttributesXML")
    @ResponseWrapper(localName = "ListSessionFilesWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListSessionFilesWithAttributesXMLResponse")
    public ListSessionFilesWithAttributesXMLResult listSessionFilesWithAttributesXML(
        @WebParam(name = "sessionID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int sessionID);

    /**
     * 
     * @param trialID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListTrialFilesXMLResponse.ListTrialFilesXMLResult
     */
    @WebMethod(operationName = "ListTrialFilesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListTrialFilesXML")
    @WebResult(name = "ListTrialFilesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListTrialFilesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialFilesXML")
    @ResponseWrapper(localName = "ListTrialFilesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialFilesXMLResponse")
    public ListTrialFilesXMLResult listTrialFilesXML(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int trialID);

    /**
     * 
     * @param trialID
     * @return
     *     returns motion.database.ws.basicQueriesService.ListTrialFilesWithAttributesXMLResponse.ListTrialFilesWithAttributesXMLResult
     */
    @WebMethod(operationName = "ListTrialFilesWithAttributesXML", action = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService/ListTrialFilesWithAttributesXML")
    @WebResult(name = "ListTrialFilesWithAttributesXMLResult", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    @RequestWrapper(localName = "ListTrialFilesWithAttributesXML", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialFilesWithAttributesXML")
    @ResponseWrapper(localName = "ListTrialFilesWithAttributesXMLResponse", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", className = "motion.database.ws.basicQueriesService.ListTrialFilesWithAttributesXMLResponse")
    public ListTrialFilesWithAttributesXMLResult listTrialFilesWithAttributesXML(
        @WebParam(name = "trialID", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        int trialID);

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

}
