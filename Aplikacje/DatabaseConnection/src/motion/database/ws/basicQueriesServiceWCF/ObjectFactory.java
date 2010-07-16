
package motion.database.ws.basicQueriesServiceWCF;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the motion.database.ws.basicQueriesServiceWCF package. 
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

    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _QueryException_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "QueryException");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _QueryExceptionIssueKind_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "IssueKind");
    private final static QName _QueryExceptionDetails_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "Details");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: motion.database.ws.basicQueriesServiceWCF
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult }
     * 
     */
    public ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult createListAttributeGroupsDefinedResponseListAttributeGroupsDefinedResult() {
        return new ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult();
    }

    /**
     * Create an instance of {@link LabSessionWithAttributesList }
     * 
     */
    public LabSessionWithAttributesList createLabSessionWithAttributesList() {
        return new LabSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXMLResponse }
     * 
     */
    public ListFilesWithAttributesXMLResponse createListFilesWithAttributesXMLResponse() {
        return new ListFilesWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXMLResponse }
     * 
     */
    public ListLabSessionsWithAttributesXMLResponse createListLabSessionsWithAttributesXMLResponse() {
        return new ListLabSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult }
     * 
     */
    public ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult createListSessionTrialsXMLResponseListSessionTrialsXMLResult() {
        return new ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult }
     * 
     */
    public ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult createListPerformerSessionsXMLResponseListPerformerSessionsXMLResult() {
        return new ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResponse }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResponse createListPerformerSessionsWithAttributesXMLResponse() {
        return new ListPerformerSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link SessionTrialList }
     * 
     */
    public SessionTrialList createSessionTrialList() {
        return new SessionTrialList();
    }

    /**
     * Create an instance of {@link TrialDetailsWithAttributes }
     * 
     */
    public TrialDetailsWithAttributes createTrialDetailsWithAttributes() {
        return new TrialDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefinedResponse }
     * 
     */
    public ListSessionGroupsDefinedResponse createListSessionGroupsDefinedResponse() {
        return new ListSessionGroupsDefinedResponse();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXMLResponse }
     * 
     */
    public ListLabPerformersWithAttributesXMLResponse createListLabPerformersWithAttributesXMLResponse() {
        return new ListLabPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link PerformerWithAttributesList }
     * 
     */
    public PerformerWithAttributesList createPerformerWithAttributesList() {
        return new PerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link PerformerSessionList }
     * 
     */
    public PerformerSessionList createPerformerSessionList() {
        return new PerformerSessionList();
    }

    /**
     * Create an instance of {@link AttributeGroupDefinitionList.AttributeGroupDefinition }
     * 
     */
    public AttributeGroupDefinitionList.AttributeGroupDefinition createAttributeGroupDefinitionListAttributeGroupDefinition() {
        return new AttributeGroupDefinitionList.AttributeGroupDefinition();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXML }
     * 
     */
    public ListFilesWithAttributesXML createListFilesWithAttributesXML() {
        return new ListFilesWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefined }
     * 
     */
    public ListSessionGroupsDefined createListSessionGroupsDefined() {
        return new ListSessionGroupsDefined();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXML }
     * 
     */
    public ListLabPerformersWithAttributesXML createListLabPerformersWithAttributesXML() {
        return new ListLabPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResponse.ListAttributesDefinedResult }
     * 
     */
    public ListAttributesDefinedResponse.ListAttributesDefinedResult createListAttributesDefinedResponseListAttributesDefinedResult() {
        return new ListAttributesDefinedResponse.ListAttributesDefinedResult();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXML }
     * 
     */
    public ListPerformersWithAttributesXML createListPerformersWithAttributesXML() {
        return new ListPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefinedResponse }
     * 
     */
    public ListAttributeGroupsDefinedResponse createListAttributeGroupsDefinedResponse() {
        return new ListAttributeGroupsDefinedResponse();
    }

    /**
     * Create an instance of {@link GenericQueryResult }
     * 
     */
    public GenericQueryResult createGenericQueryResult() {
        return new GenericQueryResult();
    }

    /**
     * Create an instance of {@link GetTrialByIdXML }
     * 
     */
    public GetTrialByIdXML createGetTrialByIdXML() {
        return new GetTrialByIdXML();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResponse.ListPerformersXMLResult }
     * 
     */
    public ListPerformersXMLResponse.ListPerformersXMLResult createListPerformersXMLResponseListPerformersXMLResult() {
        return new ListPerformersXMLResponse.ListPerformersXMLResult();
    }

    /**
     * Create an instance of {@link ListFilesXMLResponse }
     * 
     */
    public ListFilesXMLResponse createListFilesXMLResponse() {
        return new ListFilesXMLResponse();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult }
     * 
     */
    public ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult createListLabPerformersWithAttributesXMLResponseListLabPerformersWithAttributesXMLResult() {
        return new ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link SessionTrialList.TrialDetails }
     * 
     */
    public SessionTrialList.TrialDetails createSessionTrialListTrialDetails() {
        return new SessionTrialList.TrialDetails();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXMLResponse }
     * 
     */
    public ListTrialSegmentsWithAttributesXMLResponse createListTrialSegmentsWithAttributesXMLResponse() {
        return new ListTrialSegmentsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefined }
     * 
     */
    public ListMotionKindsDefined createListMotionKindsDefined() {
        return new ListMotionKindsDefined();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult }
     * 
     */
    public GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult createGetPerformerByIdXMLResponseGetPerformerByIdXMLResult() {
        return new GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult();
    }

    /**
     * Create an instance of {@link GetSegmentByIdXML }
     * 
     */
    public GetSegmentByIdXML createGetSegmentByIdXML() {
        return new GetSegmentByIdXML();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResponse.GenericQueryXMLResult }
     * 
     */
    public GenericQueryXMLResponse.GenericQueryXMLResult createGenericQueryXMLResponseGenericQueryXMLResult() {
        return new GenericQueryXMLResponse.GenericQueryXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXML }
     * 
     */
    public ListSessionTrialsWithAttributesXML createListSessionTrialsWithAttributesXML() {
        return new ListSessionTrialsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXMLResponse }
     * 
     */
    public ListPerformersWithAttributesXMLResponse createListPerformersWithAttributesXMLResponse() {
        return new ListPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link GetSessionLabelResponse }
     * 
     */
    public GetSessionLabelResponse createGetSessionLabelResponse() {
        return new GetSessionLabelResponse();
    }

    /**
     * Create an instance of {@link PerformerSessionList.SessionDetails }
     * 
     */
    public PerformerSessionList.SessionDetails createPerformerSessionListSessionDetails() {
        return new PerformerSessionList.SessionDetails();
    }

    /**
     * Create an instance of {@link SessionTrialWithAttributesList }
     * 
     */
    public SessionTrialWithAttributesList createSessionTrialWithAttributesList() {
        return new SessionTrialWithAttributesList();
    }

    /**
     * Create an instance of {@link SessionDetailsWithAttributes }
     * 
     */
    public SessionDetailsWithAttributes createSessionDetailsWithAttributes() {
        return new SessionDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult }
     * 
     */
    public GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult createGetSegmentByIdXMLResponseGetSegmentByIdXMLResult() {
        return new GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult();
    }

    /**
     * Create an instance of {@link SessionGroupDefinitionList.SessionGroupDefinition }
     * 
     */
    public SessionGroupDefinitionList.SessionGroupDefinition createSessionGroupDefinitionListSessionGroupDefinition() {
        return new SessionGroupDefinitionList.SessionGroupDefinition();
    }

    /**
     * Create an instance of {@link Attributes.Attribute }
     * 
     */
    public Attributes.Attribute createAttributesAttribute() {
        return new Attributes.Attribute();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList.AttributeDefinition }
     * 
     */
    public AttributeDefinitionList.AttributeDefinition createAttributeDefinitionListAttributeDefinition() {
        return new AttributeDefinitionList.AttributeDefinition();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResponse }
     * 
     */
    public ListAttributesDefinedResponse createListAttributesDefinedResponse() {
        return new ListAttributesDefinedResponse();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult }
     * 
     */
    public ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult createListTrialSegmentsXMLResponseListTrialSegmentsXMLResult() {
        return new ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult();
    }

    /**
     * Create an instance of {@link GenericQueryResult.GenericResultRow }
     * 
     */
    public GenericQueryResult.GenericResultRow createGenericQueryResultGenericResultRow() {
        return new GenericQueryResult.GenericResultRow();
    }

    /**
     * Create an instance of {@link MotionKindDefinitionList }
     * 
     */
    public MotionKindDefinitionList createMotionKindDefinitionList() {
        return new MotionKindDefinitionList();
    }

    /**
     * Create an instance of {@link SessionGroupDefinitionList }
     * 
     */
    public SessionGroupDefinitionList createSessionGroupDefinitionList() {
        return new SessionGroupDefinitionList();
    }

    /**
     * Create an instance of {@link FileList.FileDetails }
     * 
     */
    public FileList.FileDetails createFileListFileDetails() {
        return new FileList.FileDetails();
    }

    /**
     * Create an instance of {@link MotionKindDefinitionList.MotionKindDefinition }
     * 
     */
    public MotionKindDefinitionList.MotionKindDefinition createMotionKindDefinitionListMotionKindDefinition() {
        return new MotionKindDefinitionList.MotionKindDefinition();
    }

    /**
     * Create an instance of {@link GenericQueryUniformXML }
     * 
     */
    public GenericQueryUniformXML createGenericQueryUniformXML() {
        return new GenericQueryUniformXML();
    }

    /**
     * Create an instance of {@link PerformerList.PerformerDetails }
     * 
     */
    public PerformerList.PerformerDetails createPerformerListPerformerDetails() {
        return new PerformerList.PerformerDetails();
    }

    /**
     * Create an instance of {@link ListPerformersXML }
     * 
     */
    public ListPerformersXML createListPerformersXML() {
        return new ListPerformersXML();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXML }
     * 
     */
    public ListPerformerSessionsWithAttributesXML createListPerformerSessionsWithAttributesXML() {
        return new ListPerformerSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link TrailSegmentList.SegmentDetails }
     * 
     */
    public TrailSegmentList.SegmentDetails createTrailSegmentListSegmentDetails() {
        return new TrailSegmentList.SegmentDetails();
    }

    /**
     * Create an instance of {@link FilterPredicate }
     * 
     */
    public FilterPredicate createFilterPredicate() {
        return new FilterPredicate();
    }

    /**
     * Create an instance of {@link ArrayOfFilterPredicate }
     * 
     */
    public ArrayOfFilterPredicate createArrayOfFilterPredicate() {
        return new ArrayOfFilterPredicate();
    }

    /**
     * Create an instance of {@link ListAttributesDefined }
     * 
     */
    public ListAttributesDefined createListAttributesDefined() {
        return new ListAttributesDefined();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult }
     * 
     */
    public ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult createListPerformersWithAttributesXMLResponseListPerformersWithAttributesXMLResult() {
        return new ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link LabPerformerWithAttributesList }
     * 
     */
    public LabPerformerWithAttributesList createLabPerformerWithAttributesList() {
        return new LabPerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link GetSessionLabel }
     * 
     */
    public GetSessionLabel createGetSessionLabel() {
        return new GetSessionLabel();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXMLResponse }
     * 
     */
    public ListTrialSegmentsXMLResponse createListTrialSegmentsXMLResponse() {
        return new ListTrialSegmentsXMLResponse();
    }

    /**
     * Create an instance of {@link GenericQueryUniformXMLResponse }
     * 
     */
    public GenericQueryUniformXMLResponse createGenericQueryUniformXMLResponse() {
        return new GenericQueryUniformXMLResponse();
    }

    /**
     * Create an instance of {@link GenericQueryXML }
     * 
     */
    public GenericQueryXML createGenericQueryXML() {
        return new GenericQueryXML();
    }

    /**
     * Create an instance of {@link GetSessionByIdXML }
     * 
     */
    public GetSessionByIdXML createGetSessionByIdXML() {
        return new GetSessionByIdXML();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult }
     * 
     */
    public ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult createListTrialSegmentsWithAttributesXMLResponseListTrialSegmentsWithAttributesXMLResult() {
        return new ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link GetTrialByIdXMLResponse }
     * 
     */
    public GetTrialByIdXMLResponse createGetTrialByIdXMLResponse() {
        return new GetTrialByIdXMLResponse();
    }

    /**
     * Create an instance of {@link FileWithAttributesList }
     * 
     */
    public FileWithAttributesList createFileWithAttributesList() {
        return new FileWithAttributesList();
    }

    /**
     * Create an instance of {@link PerformerList }
     * 
     */
    public PerformerList createPerformerList() {
        return new PerformerList();
    }

    /**
     * Create an instance of {@link GetSessionByIdXMLResponse.GetSessionByIdXMLResult }
     * 
     */
    public GetSessionByIdXMLResponse.GetSessionByIdXMLResult createGetSessionByIdXMLResponseGetSessionByIdXMLResult() {
        return new GetSessionByIdXMLResponse.GetSessionByIdXMLResult();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXML }
     * 
     */
    public ListLabSessionsWithAttributesXML createListLabSessionsWithAttributesXML() {
        return new ListLabSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefined }
     * 
     */
    public ListAttributeGroupsDefined createListAttributeGroupsDefined() {
        return new ListAttributeGroupsDefined();
    }

    /**
     * Create an instance of {@link QueryException }
     * 
     */
    public QueryException createQueryException() {
        return new QueryException();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefinedResponse }
     * 
     */
    public ListMotionKindsDefinedResponse createListMotionKindsDefinedResponse() {
        return new ListMotionKindsDefinedResponse();
    }

    /**
     * Create an instance of {@link SegmentDetailsWithAttributes }
     * 
     */
    public SegmentDetailsWithAttributes createSegmentDetailsWithAttributes() {
        return new SegmentDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link AttributeGroupDefinitionList }
     * 
     */
    public AttributeGroupDefinitionList createAttributeGroupDefinitionList() {
        return new AttributeGroupDefinitionList();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResult() {
        return new ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXML }
     * 
     */
    public ListSessionTrialsXML createListSessionTrialsXML() {
        return new ListSessionTrialsXML();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsWithAttributesXML }
     * 
     */
    public ListTrialSegmentsWithAttributesXML createListTrialSegmentsWithAttributesXML() {
        return new ListTrialSegmentsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResponse }
     * 
     */
    public ListPerformersXMLResponse createListPerformersXMLResponse() {
        return new ListPerformersXMLResponse();
    }

    /**
     * Create an instance of {@link GenericUniformAttributesQueryResult }
     * 
     */
    public GenericUniformAttributesQueryResult createGenericUniformAttributesQueryResult() {
        return new GenericUniformAttributesQueryResult();
    }

    /**
     * Create an instance of {@link TrailSegmentWithAttributesList }
     * 
     */
    public TrailSegmentWithAttributesList createTrailSegmentWithAttributesList() {
        return new TrailSegmentWithAttributesList();
    }

    /**
     * Create an instance of {@link ListTrialSegmentsXML }
     * 
     */
    public ListTrialSegmentsXML createListTrialSegmentsXML() {
        return new ListTrialSegmentsXML();
    }

    /**
     * Create an instance of {@link GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult }
     * 
     */
    public GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult createGenericQueryUniformXMLResponseGenericQueryUniformXMLResult() {
        return new GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult }
     * 
     */
    public ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult createListSessionGroupsDefinedResponseListSessionGroupsDefinedResult() {
        return new ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult();
    }

    /**
     * Create an instance of {@link FileList }
     * 
     */
    public FileList createFileList() {
        return new FileList();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResponse }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResponse createListSessionTrialsWithAttributesXMLResponse() {
        return new ListSessionTrialsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListFilesXMLResponse.ListFilesXMLResult }
     * 
     */
    public ListFilesXMLResponse.ListFilesXMLResult createListFilesXMLResponseListFilesXMLResult() {
        return new ListFilesXMLResponse.ListFilesXMLResult();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList }
     * 
     */
    public AttributeDefinitionList createAttributeDefinitionList() {
        return new AttributeDefinitionList();
    }

    /**
     * Create an instance of {@link TrailSegmentList }
     * 
     */
    public TrailSegmentList createTrailSegmentList() {
        return new TrailSegmentList();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResponse }
     * 
     */
    public ListSessionTrialsXMLResponse createListSessionTrialsXMLResponse() {
        return new ListSessionTrialsXMLResponse();
    }

    /**
     * Create an instance of {@link GetSessionByIdXMLResponse }
     * 
     */
    public GetSessionByIdXMLResponse createGetSessionByIdXMLResponse() {
        return new GetSessionByIdXMLResponse();
    }

    /**
     * Create an instance of {@link PerformerDetailsWithAttributes }
     * 
     */
    public PerformerDetailsWithAttributes createPerformerDetailsWithAttributes() {
        return new PerformerDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult createListSessionTrialsWithAttributesXMLResponseListSessionTrialsWithAttributesXMLResult() {
        return new ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult }
     * 
     */
    public ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult createListMotionKindsDefinedResponseListMotionKindsDefinedResult() {
        return new ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXMLResponse }
     * 
     */
    public GetPerformerByIdXMLResponse createGetPerformerByIdXMLResponse() {
        return new GetPerformerByIdXMLResponse();
    }

    /**
     * Create an instance of {@link GetTrialByIdXMLResponse.GetTrialByIdXMLResult }
     * 
     */
    public GetTrialByIdXMLResponse.GetTrialByIdXMLResult createGetTrialByIdXMLResponseGetTrialByIdXMLResult() {
        return new GetTrialByIdXMLResponse.GetTrialByIdXMLResult();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResponse }
     * 
     */
    public GenericQueryXMLResponse createGenericQueryXMLResponse() {
        return new GenericQueryXMLResponse();
    }

    /**
     * Create an instance of {@link ListFilesXML }
     * 
     */
    public ListFilesXML createListFilesXML() {
        return new ListFilesXML();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXML }
     * 
     */
    public ListPerformerSessionsXML createListPerformerSessionsXML() {
        return new ListPerformerSessionsXML();
    }

    /**
     * Create an instance of {@link FileWithAttributesList.FileDetailsWithAttributes }
     * 
     */
    public FileWithAttributesList.FileDetailsWithAttributes createFileWithAttributesListFileDetailsWithAttributes() {
        return new FileWithAttributesList.FileDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXML }
     * 
     */
    public GetPerformerByIdXML createGetPerformerByIdXML() {
        return new GetPerformerByIdXML();
    }

    /**
     * Create an instance of {@link GetSegmentByIdXMLResponse }
     * 
     */
    public GetSegmentByIdXMLResponse createGetSegmentByIdXMLResponse() {
        return new GetSegmentByIdXMLResponse();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult }
     * 
     */
    public ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult createListLabSessionsWithAttributesXMLResponseListLabSessionsWithAttributesXMLResult() {
        return new ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link PerformerSessionWithAttributesList }
     * 
     */
    public PerformerSessionWithAttributesList createPerformerSessionWithAttributesList() {
        return new PerformerSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult }
     * 
     */
    public ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult createListFilesWithAttributesXMLResponseListFilesWithAttributesXMLResult() {
        return new ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResponse }
     * 
     */
    public ListPerformerSessionsXMLResponse createListPerformerSessionsXMLResponse() {
        return new ListPerformerSessionsXMLResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "QueryException")
    public JAXBElement<QueryException> createQueryException(QueryException value) {
        return new JAXBElement<QueryException>(_QueryException_QNAME, QueryException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "IssueKind", scope = QueryException.class)
    public JAXBElement<String> createQueryExceptionIssueKind(String value) {
        return new JAXBElement<String>(_QueryExceptionIssueKind_QNAME, String.class, QueryException.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "Details", scope = QueryException.class)
    public JAXBElement<String> createQueryExceptionDetails(String value) {
        return new JAXBElement<String>(_QueryExceptionDetails_QNAME, String.class, QueryException.class, value);
    }

}
