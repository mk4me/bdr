
package motion.database.ws.basicQueriesService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import motion.database.ws.basicQueriesService.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesService.Attributes.Attribute;
import motion.database.ws.basicQueriesService.FileListType.FileDetails;
import motion.database.ws.basicQueriesService.FileWithAttributesList.FileDetailsWithAttributes;
import motion.database.ws.basicQueriesService.GenericQueryResult.GenericResultRow;
import motion.database.ws.basicQueriesService.GenericQueryXMLResponse.GenericQueryXMLResult;
import motion.database.ws.basicQueriesService.ListAttributesDefinedResponse.ListAttributesDefinedResult;
import motion.database.ws.basicQueriesService.ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListFilesXMLResponse.ListFilesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersXMLResponse.ListPerformersXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult;
import motion.database.ws.basicQueriesService.PerformQueryResponse.PerformQueryResult;
import motion.database.ws.basicQueriesService.PerformerList.PerformerDetails;
import motion.database.ws.basicQueriesService.PerformerSessionList.SessionDetails;
import motion.database.ws.basicQueriesService.PerformerSessionWithAttributesList.SessionDetailsWithAttributes;
import motion.database.ws.basicQueriesService.PerformerWithAttributesList.PerformerDetailsWithAttributes;
import motion.database.ws.basicQueriesService.SessionTrialList.TrialDetails;
import motion.database.ws.basicQueriesService.SessionTrialWithAttributesList.TrialDetailsWithAttributes;
import motion.database.ws.basicQueriesService.TrailSegmentList.SegmentDetails;
import motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList.SegmentDetailsWithAttributes;


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

    private final static QName _FileList_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "FileList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: motion.database.ws.basicQueriesService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResponse }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResponse createListSessionTrialsWithAttributesXMLResponse() {
        return new ListSessionTrialsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResult }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResult createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResult() {
        return new ListPerformerSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXML }
     * 
     */
    public ListTrialSegmentsXML createListTrialSegmentsXML() {
        return new ListTrialSegmentsXML();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXMLResponse }
     * 
     */
    public ListTrialSegmentsWithAttributesXMLResponse createListTrialSegmentsWithAttributesXMLResponse() {
        return new ListTrialSegmentsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResponse }
     * 
     */
    public GenericQueryXMLResponse createGenericQueryXMLResponse() {
        return new GenericQueryXMLResponse();
    }

    /**
     * Create an instance of {@link SessionDetailsWithAttributes }
     * 
     */
    public SessionDetailsWithAttributes createPerformerSessionWithAttributesListSessionDetailsWithAttributes() {
        return new SessionDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResult }
     * 
     */
    public ListSessionTrialsXMLResult createListSessionTrialsXMLResponseListSessionTrialsXMLResult() {
        return new ListSessionTrialsXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionFilesResponse }
     * 
     */
    public ListSessionFilesResponse createListSessionFilesResponse() {
        return new ListSessionFilesResponse();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXML }
     * 
     */
    public ListPerformerSessionsXML createListPerformerSessionsXML() {
        return new ListPerformerSessionsXML();
    }

    /**
     * Create an instance of {@link GenericQueryResult }
     * 
     */
    public GenericQueryResult createGenericQueryResult() {
        return new GenericQueryResult();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXMLResult }
     * 
     */
    public ListPerformersWithAttributesXMLResult createListPerformersWithAttributesXMLResponseListPerformersWithAttributesXMLResult() {
        return new ListPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXMLResponse }
     * 
     */
    public ListTrialSegmentsXMLResponse createListTrialSegmentsXMLResponse() {
        return new ListTrialSegmentsXMLResponse();
    }

    /**
     * Create an instance of {@link SessionTrialWithAttributesList }
     * 
     */
    public SessionTrialWithAttributesList createSessionTrialWithAttributesList() {
        return new SessionTrialWithAttributesList();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXML }
     * 
     */
    public ListPerformersWithAttributesXML createListPerformersWithAttributesXML() {
        return new ListPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link ArrayOfPlainFileDetails }
     * 
     */
    public ArrayOfPlainFileDetails createArrayOfPlainFileDetails() {
        return new ArrayOfPlainFileDetails();
    }

    /**
     * Create an instance of {@link PerformerList }
     * 
     */
    public PerformerList createPerformerList() {
        return new PerformerList();
    }

    /**
     * Create an instance of {@link PerformQueryResult }
     * 
     */
    public PerformQueryResult createPerformQueryResponsePerformQueryResult() {
        return new PerformQueryResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResult }
     * 
     */
    public ListPerformerSessionsXMLResult createListPerformerSessionsXMLResponseListPerformerSessionsXMLResult() {
        return new ListPerformerSessionsXMLResult();
    }

    /**
     * Create an instance of {@link SegmentDetails }
     * 
     */
    public SegmentDetails createTrailSegmentListSegmentDetails() {
        return new SegmentDetails();
    }

    /**
     * Create an instance of {@link FileListType }
     * 
     */
    public FileListType createFileListType() {
        return new FileListType();
    }

    /**
     * Create an instance of {@link ArrayOfFilterPredicate }
     * 
     */
    public ArrayOfFilterPredicate createArrayOfFilterPredicate() {
        return new ArrayOfFilterPredicate();
    }

    /**
     * Create an instance of {@link ListFilesXMLResult }
     * 
     */
    public ListFilesXMLResult createListFilesXMLResponseListFilesXMLResult() {
        return new ListFilesXMLResult();
    }

    /**
     * Create an instance of {@link FileDetails }
     * 
     */
    public FileDetails createFileListTypeFileDetails() {
        return new FileDetails();
    }

    /**
     * Create an instance of {@link PerformerSessionList }
     * 
     */
    public PerformerSessionList createPerformerSessionList() {
        return new PerformerSessionList();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXML }
     * 
     */
    public ListPerformerSessionsWithAttributesXML createListPerformerSessionsWithAttributesXML() {
        return new ListPerformerSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListSessionFiles }
     * 
     */
    public ListSessionFiles createListSessionFiles() {
        return new ListSessionFiles();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResult }
     * 
     */
    public ListAttributesDefinedResult createListAttributesDefinedResponseListAttributesDefinedResult() {
        return new ListAttributesDefinedResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResponse }
     * 
     */
    public ListPerformerSessionsXMLResponse createListPerformerSessionsXMLResponse() {
        return new ListPerformerSessionsXMLResponse();
    }

    /**
     * Create an instance of {@link GenericResultRow }
     * 
     */
    public GenericResultRow createGenericQueryResultGenericResultRow() {
        return new GenericResultRow();
    }

    /**
     * Create an instance of {@link PerformerSessionWithAttributesList }
     * 
     */
    public PerformerSessionWithAttributesList createPerformerSessionWithAttributesList() {
        return new PerformerSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link PlainFileDetails }
     * 
     */
    public PlainFileDetails createPlainFileDetails() {
        return new PlainFileDetails();
    }

    /**
     * Create an instance of {@link AttributeDefinition }
     * 
     */
    public AttributeDefinition createAttributeDefinitionListAttributeDefinition() {
        return new AttributeDefinition();
    }

    /**
     * Create an instance of {@link ListFilesXMLResponse }
     * 
     */
    public ListFilesXMLResponse createListFilesXMLResponse() {
        return new ListFilesXMLResponse();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttributesAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXML }
     * 
     */
    public ListSessionTrialsWithAttributesXML createListSessionTrialsWithAttributesXML() {
        return new ListSessionTrialsWithAttributesXML();
    }

    /**
     * Create an instance of {@link PlainSessionDetails }
     * 
     */
    public PlainSessionDetails createPlainSessionDetails() {
        return new PlainSessionDetails();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResponse }
     * 
     */
    public ListAttributesDefinedResponse createListAttributesDefinedResponse() {
        return new ListAttributesDefinedResponse();
    }

    /**
     * Create an instance of {@link GenericQueryXML }
     * 
     */
    public GenericQueryXML createGenericQueryXML() {
        return new GenericQueryXML();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXMLResult }
     * 
     */
    public ListTrialSegmentsXMLResult createListTrialSegmentsXMLResponseListTrialSegmentsXMLResult() {
        return new ListTrialSegmentsXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResult }
     * 
     */
    public ListPerformersXMLResult createListPerformersXMLResponseListPerformersXMLResult() {
        return new ListPerformersXMLResult();
    }

    /**
     * Create an instance of {@link TrialDetailsWithAttributes }
     * 
     */
    public TrialDetailsWithAttributes createSessionTrialWithAttributesListTrialDetailsWithAttributes() {
        return new TrialDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link FileDetailsWithAttributes }
     * 
     */
    public FileDetailsWithAttributes createFileWithAttributesListFileDetailsWithAttributes() {
        return new FileDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResponse }
     * 
     */
    public ListPerformersXMLResponse createListPerformersXMLResponse() {
        return new ListPerformersXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXML }
     * 
     */
    public ListSessionTrialsXML createListSessionTrialsXML() {
        return new ListSessionTrialsXML();
    }

    /**
     * Create an instance of {@link PerformerDetails }
     * 
     */
    public PerformerDetails createPerformerListPerformerDetails() {
        return new PerformerDetails();
    }

    /**
     * Create an instance of {@link PerformQueryResponse }
     * 
     */
    public PerformQueryResponse createPerformQueryResponse() {
        return new PerformQueryResponse();
    }

    /**
     * Create an instance of {@link ListPerformersXML }
     * 
     */
    public ListPerformersXML createListPerformersXML() {
        return new ListPerformersXML();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResponse }
     * 
     */
    public ListSessionTrialsXMLResponse createListSessionTrialsXMLResponse() {
        return new ListSessionTrialsXMLResponse();
    }

    /**
     * Create an instance of {@link PerformerDetailsWithAttributes }
     * 
     */
    public PerformerDetailsWithAttributes createPerformerWithAttributesListPerformerDetailsWithAttributes() {
        return new PerformerDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXMLResult }
     * 
     */
    public ListTrialSegmentsWithAttributesXMLResult createListTrialSegmentsWithAttributesXMLResponseListTrialSegmentsWithAttributesXMLResult() {
        return new ListTrialSegmentsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link TrailSegmentWithAttributesList }
     * 
     */
    public TrailSegmentWithAttributesList createTrailSegmentWithAttributesList() {
        return new TrailSegmentWithAttributesList();
    }

    /**
     * Create an instance of {@link SessionTrialList }
     * 
     */
    public SessionTrialList createSessionTrialList() {
        return new SessionTrialList();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResult }
     * 
     */
    public GenericQueryXMLResult createGenericQueryXMLResponseGenericQueryXMLResult() {
        return new GenericQueryXMLResult();
    }

    /**
     * Create an instance of {@link FileWithAttributesList }
     * 
     */
    public FileWithAttributesList createFileWithAttributesList() {
        return new FileWithAttributesList();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXMLResponse }
     * 
     */
    public ListPerformersWithAttributesXMLResponse createListPerformersWithAttributesXMLResponse() {
        return new ListPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListAttributesDefined }
     * 
     */
    public ListAttributesDefined createListAttributesDefined() {
        return new ListAttributesDefined();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResult }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResult createListSessionTrialsWithAttributesXMLResponseListSessionTrialsWithAttributesXMLResult() {
        return new ListSessionTrialsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link SessionDetails }
     * 
     */
    public SessionDetails createPerformerSessionListSessionDetails() {
        return new SessionDetails();
    }

    /**
     * Create an instance of {@link FilterPredicate }
     * 
     */
    public FilterPredicate createFilterPredicate() {
        return new FilterPredicate();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList }
     * 
     */
    public AttributeDefinitionList createAttributeDefinitionList() {
        return new AttributeDefinitionList();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXML }
     * 
     */
    public ListFilesWithAttributesXML createListFilesWithAttributesXML() {
        return new ListFilesWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListPerformerSessions }
     * 
     */
    public ListPerformerSessions createListPerformerSessions() {
        return new ListPerformerSessions();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResponse }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResponse createListPerformerSessionsWithAttributesXMLResponse() {
        return new ListPerformerSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXML }
     * 
     */
    public ListTrialSegmentsWithAttributesXML createListTrialSegmentsWithAttributesXML() {
        return new ListTrialSegmentsWithAttributesXML();
    }

    /**
     * Create an instance of {@link TrailSegmentList }
     * 
     */
    public TrailSegmentList createTrailSegmentList() {
        return new TrailSegmentList();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXMLResponse }
     * 
     */
    public ListFilesWithAttributesXMLResponse createListFilesWithAttributesXMLResponse() {
        return new ListFilesWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link PerformerWithAttributesList }
     * 
     */
    public PerformerWithAttributesList createPerformerWithAttributesList() {
        return new PerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link SegmentDetailsWithAttributes }
     * 
     */
    public SegmentDetailsWithAttributes createTrailSegmentWithAttributesListSegmentDetailsWithAttributes() {
        return new SegmentDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsResponse }
     * 
     */
    public ListPerformerSessionsResponse createListPerformerSessionsResponse() {
        return new ListPerformerSessionsResponse();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link ArrayOfPlainSessionDetails }
     * 
     */
    public ArrayOfPlainSessionDetails createArrayOfPlainSessionDetails() {
        return new ArrayOfPlainSessionDetails();
    }

    /**
     * Create an instance of {@link ListFilesXML }
     * 
     */
    public ListFilesXML createListFilesXML() {
        return new ListFilesXML();
    }

    /**
     * Create an instance of {@link PerformQuery }
     * 
     */
    public PerformQuery createPerformQuery() {
        return new PerformQuery();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXMLResult }
     * 
     */
    public ListFilesWithAttributesXMLResult createListFilesWithAttributesXMLResponseListFilesWithAttributesXMLResult() {
        return new ListFilesWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link TrialDetails }
     * 
     */
    public TrialDetails createSessionTrialListTrialDetails() {
        return new TrialDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "FileList")
    public JAXBElement<FileListType> createFileList(FileListType value) {
        return new JAXBElement<FileListType>(_FileList_QNAME, FileListType.class, null, value);
    }

}
