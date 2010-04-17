
package motion.database.ws.basicQueriesService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import motion.database.ws.basicQueriesService.AttributeDefinitionList.AttributeDefinition;
import motion.database.ws.basicQueriesService.AttributeGroupDefinitionList.AttributeGroupDefinition;
import motion.database.ws.basicQueriesService.Attributes.Attribute;
import motion.database.ws.basicQueriesService.FileListType.FileDetails;
import motion.database.ws.basicQueriesService.FileWithAttributesList.FileDetailsWithAttributes;
import motion.database.ws.basicQueriesService.GenericQueryResult.GenericResultRow;
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
import motion.database.ws.basicQueriesService.ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListPerformersXMLResponse.ListPerformersXMLResult;
import motion.database.ws.basicQueriesService.ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;
import motion.database.ws.basicQueriesService.ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult;
import motion.database.ws.basicQueriesService.MotionKindDefinitionList.MotionKindDefinition;
import motion.database.ws.basicQueriesService.PerformQueryResponse.PerformQueryResult;
import motion.database.ws.basicQueriesService.PerformerList.PerformerDetails;
import motion.database.ws.basicQueriesService.PerformerSessionList.SessionDetails;
import motion.database.ws.basicQueriesService.SessionGroupDefinitionList.SessionGroupDefinition;
import motion.database.ws.basicQueriesService.SessionTrialList.TrialDetails;
import motion.database.ws.basicQueriesService.TrailSegmentList.SegmentDetails;


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
     * Create an instance of {@link ListFilesWithAttributesXMLResult }
     * 
     */
    public ListFilesWithAttributesXMLResult createListFilesWithAttributesXMLResponseListFilesWithAttributesXMLResult() {
        return new ListFilesWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link GetTrialByIdXMLResponse }
     * 
     */
    public GetTrialByIdXMLResponse createGetTrialByIdXMLResponse() {
        return new GetTrialByIdXMLResponse();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link TrailSegmentWithAttributesList }
     * 
     */
    public TrailSegmentWithAttributesList createTrailSegmentWithAttributesList() {
        return new TrailSegmentWithAttributesList();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefinedResult }
     * 
     */
    public ListAttributeGroupsDefinedResult createListAttributeGroupsDefinedResponseListAttributeGroupsDefinedResult() {
        return new ListAttributeGroupsDefinedResult();
    }

    /**
     * Create an instance of {@link SessionTrialWithAttributesList }
     * 
     */
    public SessionTrialWithAttributesList createSessionTrialWithAttributesList() {
        return new SessionTrialWithAttributesList();
    }

    /**
     * Create an instance of {@link PerformerWithAttributesList }
     * 
     */
    public PerformerWithAttributesList createPerformerWithAttributesList() {
        return new PerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link GetSegmentByIdXMLResult }
     * 
     */
    public GetSegmentByIdXMLResult createGetSegmentByIdXMLResponseGetSegmentByIdXMLResult() {
        return new GetSegmentByIdXMLResult();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefinedResponse }
     * 
     */
    public ListMotionKindsDefinedResponse createListMotionKindsDefinedResponse() {
        return new ListMotionKindsDefinedResponse();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXMLResponse }
     * 
     */
    public ListTrialSegmentsXMLResponse createListTrialSegmentsXMLResponse() {
        return new ListTrialSegmentsXMLResponse();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefinedResponse }
     * 
     */
    public ListAttributeGroupsDefinedResponse createListAttributeGroupsDefinedResponse() {
        return new ListAttributeGroupsDefinedResponse();
    }

    /**
     * Create an instance of {@link ListSessionFiles }
     * 
     */
    public ListSessionFiles createListSessionFiles() {
        return new ListSessionFiles();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsResponse }
     * 
     */
    public ListPerformerSessionsResponse createListPerformerSessionsResponse() {
        return new ListPerformerSessionsResponse();
    }

    /**
     * Create an instance of {@link ListFilesXML }
     * 
     */
    public ListFilesXML createListFilesXML() {
        return new ListFilesXML();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXML }
     * 
     */
    public GetPerformerByIdXML createGetPerformerByIdXML() {
        return new GetPerformerByIdXML();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefined }
     * 
     */
    public ListAttributeGroupsDefined createListAttributeGroupsDefined() {
        return new ListAttributeGroupsDefined();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResponse }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResponse createListPerformerSessionsWithAttributesXMLResponse() {
        return new ListPerformerSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXML }
     * 
     */
    public ListTrialSegmentsXML createListTrialSegmentsXML() {
        return new ListTrialSegmentsXML();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefinedResult }
     * 
     */
    public ListMotionKindsDefinedResult createListMotionKindsDefinedResponseListMotionKindsDefinedResult() {
        return new ListMotionKindsDefinedResult();
    }

    /**
     * Create an instance of {@link GetSessionByIdXMLResult }
     * 
     */
    public GetSessionByIdXMLResult createGetSessionByIdXMLResponseGetSessionByIdXMLResult() {
        return new GetSessionByIdXMLResult();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXMLResult }
     * 
     */
    public ListTrialSegmentsWithAttributesXMLResult createListTrialSegmentsWithAttributesXMLResponseListTrialSegmentsWithAttributesXMLResult() {
        return new ListTrialSegmentsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link LabSessionWithAttributesList }
     * 
     */
    public LabSessionWithAttributesList createLabSessionWithAttributesList() {
        return new LabSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link GetSessionByIdXMLResponse }
     * 
     */
    public GetSessionByIdXMLResponse createGetSessionByIdXMLResponse() {
        return new GetSessionByIdXMLResponse();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXMLResult }
     * 
     */
    public GetPerformerByIdXMLResult createGetPerformerByIdXMLResponseGetPerformerByIdXMLResult() {
        return new GetPerformerByIdXMLResult();
    }

    /**
     * Create an instance of {@link PlainSessionDetails }
     * 
     */
    public PlainSessionDetails createPlainSessionDetails() {
        return new PlainSessionDetails();
    }

    /**
     * Create an instance of {@link PerformerDetailsWithAttributes }
     * 
     */
    public PerformerDetailsWithAttributes createPerformerDetailsWithAttributes() {
        return new PerformerDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link PerformerSessionWithAttributesList }
     * 
     */
    public PerformerSessionWithAttributesList createPerformerSessionWithAttributesList() {
        return new PerformerSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXML }
     * 
     */
    public ListLabSessionsWithAttributesXML createListLabSessionsWithAttributesXML() {
        return new ListLabSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link TrialDetails }
     * 
     */
    public TrialDetails createSessionTrialListTrialDetails() {
        return new TrialDetails();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefinedResult }
     * 
     */
    public ListSessionGroupsDefinedResult createListSessionGroupsDefinedResponseListSessionGroupsDefinedResult() {
        return new ListSessionGroupsDefinedResult();
    }

    /**
     * Create an instance of {@link PerformerSessionList }
     * 
     */
    public PerformerSessionList createPerformerSessionList() {
        return new PerformerSessionList();
    }

    /**
     * Create an instance of {@link TrailSegmentList }
     * 
     */
    public TrailSegmentList createTrailSegmentList() {
        return new TrailSegmentList();
    }

    /**
     * Create an instance of {@link GetSegmentByIdXML }
     * 
     */
    public GetSegmentByIdXML createGetSegmentByIdXML() {
        return new GetSegmentByIdXML();
    }

    /**
     * Create an instance of {@link ListAttributesDefined }
     * 
     */
    public ListAttributesDefined createListAttributesDefined() {
        return new ListAttributesDefined();
    }

    /**
     * Create an instance of {@link LabPerformerWithAttributesList }
     * 
     */
    public LabPerformerWithAttributesList createLabPerformerWithAttributesList() {
        return new LabPerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link PerformQueryResponse }
     * 
     */
    public PerformQueryResponse createPerformQueryResponse() {
        return new PerformQueryResponse();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXML }
     * 
     */
    public ListLabPerformersWithAttributesXML createListLabPerformersWithAttributesXML() {
        return new ListLabPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResponse }
     * 
     */
    public ListAttributesDefinedResponse createListAttributesDefinedResponse() {
        return new ListAttributesDefinedResponse();
    }

    /**
     * Create an instance of {@link SessionTrialList }
     * 
     */
    public SessionTrialList createSessionTrialList() {
        return new SessionTrialList();
    }

    /**
     * Create an instance of {@link GetTrialByIdXMLResult }
     * 
     */
    public GetTrialByIdXMLResult createGetTrialByIdXMLResponseGetTrialByIdXMLResult() {
        return new GetTrialByIdXMLResult();
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
     * Create an instance of {@link ListMotionKindsDefined }
     * 
     */
    public ListMotionKindsDefined createListMotionKindsDefined() {
        return new ListMotionKindsDefined();
    }

    /**
     * Create an instance of {@link SegmentDetailsWithAttributes }
     * 
     */
    public SegmentDetailsWithAttributes createSegmentDetailsWithAttributes() {
        return new SegmentDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link SegmentDetails }
     * 
     */
    public SegmentDetails createTrailSegmentListSegmentDetails() {
        return new SegmentDetails();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXML }
     * 
     */
    public ListPerformersWithAttributesXML createListPerformersWithAttributesXML() {
        return new ListPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link GenericQueryXML }
     * 
     */
    public GenericQueryXML createGenericQueryXML() {
        return new GenericQueryXML();
    }

    /**
     * Create an instance of {@link ArrayOfFilterPredicate }
     * 
     */
    public ArrayOfFilterPredicate createArrayOfFilterPredicate() {
        return new ArrayOfFilterPredicate();
    }

    /**
     * Create an instance of {@link PerformerDetails }
     * 
     */
    public PerformerDetails createPerformerListPerformerDetails() {
        return new PerformerDetails();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResult }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResult createListSessionTrialsWithAttributesXMLResponseListSessionTrialsWithAttributesXMLResult() {
        return new ListSessionTrialsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXML }
     * 
     */
    public ListPerformerSessionsXML createListPerformerSessionsXML() {
        return new ListPerformerSessionsXML();
    }

    /**
     * Create an instance of {@link Attribute }
     * 
     */
    public Attribute createAttributesAttribute() {
        return new Attribute();
    }

    /**
     * Create an instance of {@link GetSessionByIdXML }
     * 
     */
    public GetSessionByIdXML createGetSessionByIdXML() {
        return new GetSessionByIdXML();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefined }
     * 
     */
    public ListSessionGroupsDefined createListSessionGroupsDefined() {
        return new ListSessionGroupsDefined();
    }

    /**
     * Create an instance of {@link SessionDetails }
     * 
     */
    public SessionDetails createPerformerSessionListSessionDetails() {
        return new SessionDetails();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResponse }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResponse createListSessionTrialsWithAttributesXMLResponse() {
        return new ListSessionTrialsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXMLResponse }
     * 
     */
    public ListLabSessionsWithAttributesXMLResponse createListLabSessionsWithAttributesXMLResponse() {
        return new ListLabSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXMLResponse }
     * 
     */
    public GetPerformerByIdXMLResponse createGetPerformerByIdXMLResponse() {
        return new GetPerformerByIdXMLResponse();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXMLResult }
     * 
     */
    public ListLabPerformersWithAttributesXMLResult createListLabPerformersWithAttributesXMLResponseListLabPerformersWithAttributesXMLResult() {
        return new ListLabPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link PerformQuery }
     * 
     */
    public PerformQuery createPerformQuery() {
        return new PerformQuery();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefinedResponse }
     * 
     */
    public ListSessionGroupsDefinedResponse createListSessionGroupsDefinedResponse() {
        return new ListSessionGroupsDefinedResponse();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResponse }
     * 
     */
    public ListPerformersXMLResponse createListPerformersXMLResponse() {
        return new ListPerformersXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionFilesResponse }
     * 
     */
    public ListSessionFilesResponse createListSessionFilesResponse() {
        return new ListSessionFilesResponse();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXMLResponse }
     * 
     */
    public ListLabPerformersWithAttributesXMLResponse createListLabPerformersWithAttributesXMLResponse() {
        return new ListLabPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListPerformerSessions }
     * 
     */
    public ListPerformerSessions createListPerformerSessions() {
        return new ListPerformerSessions();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResult }
     * 
     */
    public ListPerformerSessionsXMLResult createListPerformerSessionsXMLResponseListPerformerSessionsXMLResult() {
        return new ListPerformerSessionsXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXMLResponse }
     * 
     */
    public ListPerformersWithAttributesXMLResponse createListPerformersWithAttributesXMLResponse() {
        return new ListPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXMLResponse }
     * 
     */
    public ListFilesWithAttributesXMLResponse createListFilesWithAttributesXMLResponse() {
        return new ListFilesWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXML }
     * 
     */
    public ListSessionTrialsXML createListSessionTrialsXML() {
        return new ListSessionTrialsXML();
    }

    /**
     * Create an instance of {@link GetTrialByIdXML }
     * 
     */
    public GetTrialByIdXML createGetTrialByIdXML() {
        return new GetTrialByIdXML();
    }

    /**
     * Create an instance of {@link TrialDetailsWithAttributes }
     * 
     */
    public TrialDetailsWithAttributes createTrialDetailsWithAttributes() {
        return new TrialDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link PerformerList }
     * 
     */
    public PerformerList createPerformerList() {
        return new PerformerList();
    }

    /**
     * Create an instance of {@link FileDetails }
     * 
     */
    public FileDetails createFileListTypeFileDetails() {
        return new FileDetails();
    }

    /**
     * Create an instance of {@link ListFilesXMLResponse }
     * 
     */
    public ListFilesXMLResponse createListFilesXMLResponse() {
        return new ListFilesXMLResponse();
    }

    /**
     * Create an instance of {@link SessionGroupDefinition }
     * 
     */
    public SessionGroupDefinition createSessionGroupDefinitionListSessionGroupDefinition() {
        return new SessionGroupDefinition();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResult }
     * 
     */
    public GenericQueryXMLResult createGenericQueryXMLResponseGenericQueryXMLResult() {
        return new GenericQueryXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResponse }
     * 
     */
    public ListSessionTrialsXMLResponse createListSessionTrialsXMLResponse() {
        return new ListSessionTrialsXMLResponse();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXMLResult }
     * 
     */
    public ListTrialSegmentsXMLResult createListTrialSegmentsXMLResponseListTrialSegmentsXMLResult() {
        return new ListTrialSegmentsXMLResult();
    }

    /**
     * Create an instance of {@link AttributeGroupDefinition }
     * 
     */
    public AttributeGroupDefinition createAttributeGroupDefinitionListAttributeGroupDefinition() {
        return new AttributeGroupDefinition();
    }

    /**
     * Create an instance of {@link ArrayOfPlainFileDetails }
     * 
     */
    public ArrayOfPlainFileDetails createArrayOfPlainFileDetails() {
        return new ArrayOfPlainFileDetails();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResponse }
     * 
     */
    public GenericQueryXMLResponse createGenericQueryXMLResponse() {
        return new GenericQueryXMLResponse();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXML }
     * 
     */
    public ListTrialSegmentsWithAttributesXML createListTrialSegmentsWithAttributesXML() {
        return new ListTrialSegmentsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResult }
     * 
     */
    public ListPerformersXMLResult createListPerformersXMLResponseListPerformersXMLResult() {
        return new ListPerformersXMLResult();
    }

    /**
     * Create an instance of {@link ListFilesXMLResult }
     * 
     */
    public ListFilesXMLResult createListFilesXMLResponseListFilesXMLResult() {
        return new ListFilesXMLResult();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXMLResult }
     * 
     */
    public ListLabSessionsWithAttributesXMLResult createListLabSessionsWithAttributesXMLResponseListLabSessionsWithAttributesXMLResult() {
        return new ListLabSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXML }
     * 
     */
    public ListPerformerSessionsWithAttributesXML createListPerformerSessionsWithAttributesXML() {
        return new ListPerformerSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResult }
     * 
     */
    public ListSessionTrialsXMLResult createListSessionTrialsXMLResponseListSessionTrialsXMLResult() {
        return new ListSessionTrialsXMLResult();
    }

    /**
     * Create an instance of {@link SessionDetailsWithAttributes }
     * 
     */
    public SessionDetailsWithAttributes createSessionDetailsWithAttributes() {
        return new SessionDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXML }
     * 
     */
    public ListSessionTrialsWithAttributesXML createListSessionTrialsWithAttributesXML() {
        return new ListSessionTrialsWithAttributesXML();
    }

    /**
     * Create an instance of {@link FileListType }
     * 
     */
    public FileListType createFileListType() {
        return new FileListType();
    }

    /**
     * Create an instance of {@link MotionKindDefinition }
     * 
     */
    public MotionKindDefinition createMotionKindDefinitionListMotionKindDefinition() {
        return new MotionKindDefinition();
    }

    /**
     * Create an instance of {@link AttributeDefinition }
     * 
     */
    public AttributeDefinition createAttributeDefinitionListAttributeDefinition() {
        return new AttributeDefinition();
    }

    /**
     * Create an instance of {@link FilterPredicate }
     * 
     */
    public FilterPredicate createFilterPredicate() {
        return new FilterPredicate();
    }

    /**
     * Create an instance of {@link PlainFileDetails }
     * 
     */
    public PlainFileDetails createPlainFileDetails() {
        return new PlainFileDetails();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXMLResult }
     * 
     */
    public ListPerformersWithAttributesXMLResult createListPerformersWithAttributesXMLResponseListPerformersWithAttributesXMLResult() {
        return new ListPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link GenericQueryResult }
     * 
     */
    public GenericQueryResult createGenericQueryResult() {
        return new GenericQueryResult();
    }

    /**
     * Create an instance of {@link GetSegmentByIdXMLResponse }
     * 
     */
    public GetSegmentByIdXMLResponse createGetSegmentByIdXMLResponse() {
        return new GetSegmentByIdXMLResponse();
    }

    /**
     * Create an instance of {@link ListPerformersXML }
     * 
     */
    public ListPerformersXML createListPerformersXML() {
        return new ListPerformersXML();
    }

    /**
     * Create an instance of {@link ArrayOfPlainSessionDetails }
     * 
     */
    public ArrayOfPlainSessionDetails createArrayOfPlainSessionDetails() {
        return new ArrayOfPlainSessionDetails();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXML }
     * 
     */
    public ListFilesWithAttributesXML createListFilesWithAttributesXML() {
        return new ListFilesWithAttributesXML();
    }

    /**
     * Create an instance of {@link MotionKindDefinitionList }
     * 
     */
    public MotionKindDefinitionList createMotionKindDefinitionList() {
        return new MotionKindDefinitionList();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXMLResponse }
     * 
     */
    public ListTrialSegmentsWithAttributesXMLResponse createListTrialSegmentsWithAttributesXMLResponse() {
        return new ListTrialSegmentsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link FileDetailsWithAttributes }
     * 
     */
    public FileDetailsWithAttributes createFileWithAttributesListFileDetailsWithAttributes() {
        return new FileDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link PerformQueryResult }
     * 
     */
    public PerformQueryResult createPerformQueryResponsePerformQueryResult() {
        return new PerformQueryResult();
    }

    /**
     * Create an instance of {@link GenericResultRow }
     * 
     */
    public GenericResultRow createGenericQueryResultGenericResultRow() {
        return new GenericResultRow();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResult }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResult createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResult() {
        return new ListPerformerSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link FileWithAttributesList }
     * 
     */
    public FileWithAttributesList createFileWithAttributesList() {
        return new FileWithAttributesList();
    }

    /**
     * Create an instance of {@link SessionGroupDefinitionList }
     * 
     */
    public SessionGroupDefinitionList createSessionGroupDefinitionList() {
        return new SessionGroupDefinitionList();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList }
     * 
     */
    public AttributeDefinitionList createAttributeDefinitionList() {
        return new AttributeDefinitionList();
    }

    /**
     * Create an instance of {@link AttributeGroupDefinitionList }
     * 
     */
    public AttributeGroupDefinitionList createAttributeGroupDefinitionList() {
        return new AttributeGroupDefinitionList();
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
