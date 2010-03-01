
package motion.database.ws.basicQueriesService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult.AttributeDefinitionList;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult.PerformerSessionWithAttributesList;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult.PerformerSessionWithAttributesList.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult.PerformerSessionWithAttributesList.SessionDetailsWithAttributes.Attributes;
import motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult.PerformerSessionList;
import motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse.ListSessionFilesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse.ListSessionFilesXMLResult.SessionFileList;
import motion.database.ws.basicQueriesService.PerformQueryResponse.PerformQueryResult;
import motion.database.ws.basicQueriesService.PerformQueryResponse.PerformQueryResult.Result;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the motion.database.ws.basicQueriesService package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListPerformerSessionsXMLResponseListPerformerSessionsXMLResultPerformerSessionList_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "PerformerSessionList");
    private final static QName _ListSessionFilesXMLResponseListSessionFilesXMLResultSessionFileList_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "SessionFileList");
    private final static QName _ListAttributesDefinedResponseListAttributesDefinedResultAttributeDefinitionList_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "AttributeDefinitionList");
    private final static QName _PerformQueryResponsePerformQueryResultResult_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "Result");
    private final static QName _ListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResultPerformerSessionWithAttributesList_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "PerformerSessionWithAttributesList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: motion.database.ws.basicQueriesService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult.AttributeDefinitionList.Attribute }
     * 
     */
    public motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult.AttributeDefinitionList.Attribute createListAttributesDefinedResponseListAttributesDefinedResultAttributeDefinitionListAttribute() {
        return new motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult.AttributeDefinitionList.Attribute();
    }

    /**
     * Create an instance of {@link ListPerformerSessions }
     * 
     */
    public ListPerformerSessions createListPerformerSessions() {
        return new ListPerformerSessions();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsResponse }
     * 
     */
    public ListPerformerSessionsResponse createListPerformerSessionsResponse() {
        return new ListPerformerSessionsResponse();
    }

    /**
     * Create an instance of {@link ListSessionFilesXML }
     * 
     */
    public ListSessionFilesXML createListSessionFilesXML() {
        return new ListSessionFilesXML();
    }

    /**
     * Create an instance of {@link PerformQuery }
     * 
     */
    public PerformQuery createPerformQuery() {
        return new PerformQuery();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXML }
     * 
     */
    public ListPerformerSessionsXML createListPerformerSessionsXML() {
        return new ListPerformerSessionsXML();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResult }
     * 
     */
    public ListAttributesDefinedResult createListAttributesDefinedResponseListAttributesDefinedResult() {
        return new ListAttributesDefinedResult();
    }

    /**
     * Create an instance of {@link PerformerSessionWithAttributesList }
     * 
     */
    public PerformerSessionWithAttributesList createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResultPerformerSessionWithAttributesList() {
        return new PerformerSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link motion.database.ws.basicQueriesService.SessionDetails }
     * 
     */
    public motion.database.ws.basicQueriesService.SessionDetails createSessionDetails() {
        return new motion.database.ws.basicQueriesService.SessionDetails();
    }

    /**
     * Create an instance of {@link PerformQueryResponse }
     * 
     */
    public PerformQueryResponse createPerformQueryResponse() {
        return new PerformQueryResponse();
    }

    /**
     * Create an instance of {@link motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult.PerformerSessionList.SessionDetails }
     * 
     */
    public motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult.PerformerSessionList.SessionDetails createListPerformerSessionsXMLResponseListPerformerSessionsXMLResultPerformerSessionListSessionDetails() {
        return new motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult.PerformerSessionList.SessionDetails();
    }

    /**
     * Create an instance of {@link ListSessionFiles }
     * 
     */
    public ListSessionFiles createListSessionFiles() {
        return new ListSessionFiles();
    }

    /**
     * Create an instance of {@link PerformerSessionList }
     * 
     */
    public PerformerSessionList createListPerformerSessionsXMLResponseListPerformerSessionsXMLResultPerformerSessionList() {
        return new PerformerSessionList();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResultPerformerSessionWithAttributesListSessionDetailsWithAttributesAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link ListSessionFilesXMLResponse }
     * 
     */
    public ListSessionFilesXMLResponse createListSessionFilesXMLResponse() {
        return new ListSessionFilesXMLResponse();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXML }
     * 
     */
    public ListPerformerSessionsWithAttributesXML createListPerformerSessionsWithAttributesXML() {
        return new ListPerformerSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResult }
     * 
     */
    public ListPerformerSessionsXMLResult createListPerformerSessionsXMLResponseListPerformerSessionsXMLResult() {
        return new ListPerformerSessionsXMLResult();
    }

    /**
     * Create an instance of {@link ArrayOfSessionDetails }
     * 
     */
    public ArrayOfSessionDetails createArrayOfSessionDetails() {
        return new ArrayOfSessionDetails();
    }

    /**
     * Create an instance of {@link ListSessionFilesXMLResult }
     * 
     */
    public ListSessionFilesXMLResult createListSessionFilesXMLResponseListSessionFilesXMLResult() {
        return new ListSessionFilesXMLResult();
    }

    /**
     * Create an instance of {@link SessionFileList }
     * 
     */
    public SessionFileList createListSessionFilesXMLResponseListSessionFilesXMLResultSessionFileList() {
        return new SessionFileList();
    }

    /**
     * Create an instance of {@link motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult.PerformerSessionWithAttributesList.SessionDetailsWithAttributes.Attributes.Attribute }
     * 
     */
    public motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult.PerformerSessionWithAttributesList.SessionDetailsWithAttributes.Attributes.Attribute createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResultPerformerSessionWithAttributesListSessionDetailsWithAttributesAttributesAttribute() {
        return new motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult.PerformerSessionWithAttributesList.SessionDetailsWithAttributes.Attributes.Attribute();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResponse }
     * 
     */
    public ListPerformerSessionsXMLResponse createListPerformerSessionsXMLResponse() {
        return new ListPerformerSessionsXMLResponse();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResponse }
     * 
     */
    public ListAttributesDefinedResponse createListAttributesDefinedResponse() {
        return new ListAttributesDefinedResponse();
    }

    /**
     * Create an instance of {@link PerformQueryResult }
     * 
     */
    public PerformQueryResult createPerformQueryResponsePerformQueryResult() {
        return new PerformQueryResult();
    }

    /**
     * Create an instance of {@link ListSessionFilesResponse }
     * 
     */
    public ListSessionFilesResponse createListSessionFilesResponse() {
        return new ListSessionFilesResponse();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList }
     * 
     */
    public AttributeDefinitionList createListAttributesDefinedResponseListAttributesDefinedResultAttributeDefinitionList() {
        return new AttributeDefinitionList();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResponse }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResponse createListPerformerSessionsWithAttributesXMLResponse() {
        return new ListPerformerSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse.ListSessionFilesXMLResult.SessionFileList.FileDetails }
     * 
     */
    public motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse.ListSessionFilesXMLResult.SessionFileList.FileDetails createListSessionFilesXMLResponseListSessionFilesXMLResultSessionFileListFileDetails() {
        return new motion.database.ws.basicQueriesService.ListSessionFilesXMLResponse.ListSessionFilesXMLResult.SessionFileList.FileDetails();
    }

    /**
     * Create an instance of {@link SessionDetailsWithAttributes }
     * 
     */
    public SessionDetailsWithAttributes createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResultPerformerSessionWithAttributesListSessionDetailsWithAttributes() {
        return new SessionDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createPerformQueryResponsePerformQueryResultResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link ArrayOfFileDetails }
     * 
     */
    public ArrayOfFileDetails createArrayOfFileDetails() {
        return new ArrayOfFileDetails();
    }

    /**
     * Create an instance of {@link ListAttributesDefined }
     * 
     */
    public ListAttributesDefined createListAttributesDefined() {
        return new ListAttributesDefined();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResult }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResult createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResult() {
        return new ListPerformerSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link motion.database.ws.basicQueriesService.FileDetails }
     * 
     */
    public motion.database.ws.basicQueriesService.FileDetails createFileDetails() {
        return new motion.database.ws.basicQueriesService.FileDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PerformerSessionList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "PerformerSessionList", scope = ListPerformerSessionsXMLResult.class)
    public JAXBElement<PerformerSessionList> createListPerformerSessionsXMLResponseListPerformerSessionsXMLResultPerformerSessionList(PerformerSessionList value) {
        return new JAXBElement<PerformerSessionList>(_ListPerformerSessionsXMLResponseListPerformerSessionsXMLResultPerformerSessionList_QNAME, PerformerSessionList.class, ListPerformerSessionsXMLResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SessionFileList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "SessionFileList", scope = ListSessionFilesXMLResult.class)
    public JAXBElement<SessionFileList> createListSessionFilesXMLResponseListSessionFilesXMLResultSessionFileList(SessionFileList value) {
        return new JAXBElement<SessionFileList>(_ListSessionFilesXMLResponseListSessionFilesXMLResultSessionFileList_QNAME, SessionFileList.class, ListSessionFilesXMLResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributeDefinitionList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "AttributeDefinitionList", scope = ListAttributesDefinedResult.class)
    public JAXBElement<AttributeDefinitionList> createListAttributesDefinedResponseListAttributesDefinedResultAttributeDefinitionList(AttributeDefinitionList value) {
        return new JAXBElement<AttributeDefinitionList>(_ListAttributesDefinedResponseListAttributesDefinedResultAttributeDefinitionList_QNAME, AttributeDefinitionList.class, ListAttributesDefinedResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Result }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "Result", scope = PerformQueryResult.class)
    public JAXBElement<Result> createPerformQueryResponsePerformQueryResultResult(Result value) {
        return new JAXBElement<Result>(_PerformQueryResponsePerformQueryResultResult_QNAME, Result.class, PerformQueryResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PerformerSessionWithAttributesList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "PerformerSessionWithAttributesList", scope = ListPerformerSessionsWithAttributesXMLResult.class)
    public JAXBElement<PerformerSessionWithAttributesList> createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResultPerformerSessionWithAttributesList(PerformerSessionWithAttributesList value) {
        return new JAXBElement<PerformerSessionWithAttributesList>(_ListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResultPerformerSessionWithAttributesList_QNAME, PerformerSessionWithAttributesList.class, ListPerformerSessionsWithAttributesXMLResult.class, value);
    }

}
