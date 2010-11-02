
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

    private final static QName _QueryExceptionIssueKind_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "IssueKind");
    private final static QName _QueryExceptionDetails_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "Details");
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

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: motion.database.ws.basicQueriesServiceWCF
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MeasurementPerformerWithAttributesList }
     * 
     */
    public MeasurementPerformerWithAttributesList createMeasurementPerformerWithAttributesList() {
        return new MeasurementPerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXML }
     * 
     */
    public ListPerformersWithAttributesXML createListPerformersWithAttributesXML() {
        return new ListPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link MeasurementConfWithAttributesList }
     * 
     */
    public MeasurementConfWithAttributesList createMeasurementConfWithAttributesList() {
        return new MeasurementConfWithAttributesList();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResponse }
     * 
     */
    public GenericQueryXMLResponse createGenericQueryXMLResponse() {
        return new GenericQueryXMLResponse();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXML }
     * 
     */
    public ListFilesWithAttributesXML createListFilesWithAttributesXML() {
        return new ListFilesWithAttributesXML();
    }

    /**
     * Create an instance of {@link MotionKindDefinitionList.MotionKindDefinition }
     * 
     */
    public MotionKindDefinitionList.MotionKindDefinition createMotionKindDefinitionListMotionKindDefinition() {
        return new MotionKindDefinitionList.MotionKindDefinition();
    }

    /**
     * Create an instance of {@link ListMeasurementConfMeasurementsWithAttributesXML }
     * 
     */
    public ListMeasurementConfMeasurementsWithAttributesXML createListMeasurementConfMeasurementsWithAttributesXML() {
        return new ListMeasurementConfMeasurementsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListGroupSessionsWithAttributesXML }
     * 
     */
    public ListGroupSessionsWithAttributesXML createListGroupSessionsWithAttributesXML() {
        return new ListGroupSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListFileAttributeDataXML }
     * 
     */
    public ListFileAttributeDataXML createListFileAttributeDataXML() {
        return new ListFileAttributeDataXML();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXMLResponse }
     * 
     */
    public ListLabSessionsWithAttributesXMLResponse createListLabSessionsWithAttributesXMLResponse() {
        return new ListLabSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult }
     * 
     */
    public GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult createGetPerformerConfigurationByIdXMLResponseGetPerformerConfigurationByIdXMLResult() {
        return new GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult();
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
     * Create an instance of {@link MeasurementConfSessionWithAttributesList }
     * 
     */
    public MeasurementConfSessionWithAttributesList createMeasurementConfSessionWithAttributesList() {
        return new MeasurementConfSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult }
     * 
     */
    public ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult createListSessionPerformersWithAttributesXMLResponseListSessionPerformersWithAttributesXMLResult() {
        return new ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link PerformerSessionList }
     * 
     */
    public PerformerSessionList createPerformerSessionList() {
        return new PerformerSessionList();
    }

    /**
     * Create an instance of {@link ListGroupSessionsWithAttributesXMLResponse }
     * 
     */
    public ListGroupSessionsWithAttributesXMLResponse createListGroupSessionsWithAttributesXMLResponse() {
        return new ListGroupSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link EnumValueList }
     * 
     */
    public EnumValueList createEnumValueList() {
        return new EnumValueList();
    }

    /**
     * Create an instance of {@link ListMeasurementPerformersWithAttributesXML }
     * 
     */
    public ListMeasurementPerformersWithAttributesXML createListMeasurementPerformersWithAttributesXML() {
        return new ListMeasurementPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link GetSessionByIdXMLResponse }
     * 
     */
    public GetSessionByIdXMLResponse createGetSessionByIdXMLResponse() {
        return new GetSessionByIdXMLResponse();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult }
     * 
     */
    public GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult createGetPerformerByIdXMLResponseGetPerformerByIdXMLResult() {
        return new GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList.AttributeDefinition }
     * 
     */
    public AttributeDefinitionList.AttributeDefinition createAttributeDefinitionListAttributeDefinition() {
        return new AttributeDefinitionList.AttributeDefinition();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResponse }
     * 
     */
    public ListSessionTrialsXMLResponse createListSessionTrialsXMLResponse() {
        return new ListSessionTrialsXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult }
     * 
     */
    public ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult createListSessionGroupsDefinedResponseListSessionGroupsDefinedResult() {
        return new ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult();
    }

    /**
     * Create an instance of {@link GetMeasurementConfigurationByIdXMLResponse }
     * 
     */
    public GetMeasurementConfigurationByIdXMLResponse createGetMeasurementConfigurationByIdXMLResponse() {
        return new GetMeasurementConfigurationByIdXMLResponse();
    }

    /**
     * Create an instance of {@link FileList.FileDetails }
     * 
     */
    public FileList.FileDetails createFileListFileDetails() {
        return new FileList.FileDetails();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult }
     * 
     */
    public ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult createListLabPerformersWithAttributesXMLResponseListLabPerformersWithAttributesXMLResult() {
        return new ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link GroupSessionWithAttributesList }
     * 
     */
    public GroupSessionWithAttributesList createGroupSessionWithAttributesList() {
        return new GroupSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXML }
     * 
     */
    public ListSessionTrialsXML createListSessionTrialsXML() {
        return new ListSessionTrialsXML();
    }

    /**
     * Create an instance of {@link ArrayOfFilterPredicate }
     * 
     */
    public ArrayOfFilterPredicate createArrayOfFilterPredicate() {
        return new ArrayOfFilterPredicate();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult }
     * 
     */
    public ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult createListAttributeGroupsDefinedResponseListAttributeGroupsDefinedResult() {
        return new ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList.AttributeDefinition.EnumValues }
     * 
     */
    public AttributeDefinitionList.AttributeDefinition.EnumValues createAttributeDefinitionListAttributeDefinitionEnumValues() {
        return new AttributeDefinitionList.AttributeDefinition.EnumValues();
    }

    /**
     * Create an instance of {@link PerformerDetailsWithAttributes }
     * 
     */
    public PerformerDetailsWithAttributes createPerformerDetailsWithAttributes() {
        return new PerformerDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListSessionSessionGroups }
     * 
     */
    public ListSessionSessionGroups createListSessionSessionGroups() {
        return new ListSessionSessionGroups();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXML }
     * 
     */
    public ListSessionTrialsWithAttributesXML createListSessionTrialsWithAttributesXML() {
        return new ListSessionTrialsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult }
     * 
     */
    public ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult createListSessionPerformerConfsWithAttributesXMLResponseListSessionPerformerConfsWithAttributesXMLResult() {
        return new ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListFilesXMLResponse }
     * 
     */
    public ListFilesXMLResponse createListFilesXMLResponse() {
        return new ListFilesXMLResponse();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefined }
     * 
     */
    public ListAttributeGroupsDefined createListAttributeGroupsDefined() {
        return new ListAttributeGroupsDefined();
    }

    /**
     * Create an instance of {@link ListFilesXML }
     * 
     */
    public ListFilesXML createListFilesXML() {
        return new ListFilesXML();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult }
     * 
     */
    public ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult createListFilesWithAttributesXMLResponseListFilesWithAttributesXMLResult() {
        return new ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link TrialMeasurementWithAttributesList }
     * 
     */
    public TrialMeasurementWithAttributesList createTrialMeasurementWithAttributesList() {
        return new TrialMeasurementWithAttributesList();
    }

    /**
     * Create an instance of {@link GenericQueryUniformXMLResponse }
     * 
     */
    public GenericQueryUniformXMLResponse createGenericQueryUniformXMLResponse() {
        return new GenericQueryUniformXMLResponse();
    }

    /**
     * Create an instance of {@link SessionDetailsWithAttributes }
     * 
     */
    public SessionDetailsWithAttributes createSessionDetailsWithAttributes() {
        return new SessionDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListAttributesDefined }
     * 
     */
    public ListAttributesDefined createListAttributesDefined() {
        return new ListAttributesDefined();
    }

    /**
     * Create an instance of {@link GetSessionLabelResponse }
     * 
     */
    public GetSessionLabelResponse createGetSessionLabelResponse() {
        return new GetSessionLabelResponse();
    }

    /**
     * Create an instance of {@link ListEnumValuesResponse }
     * 
     */
    public ListEnumValuesResponse createListEnumValuesResponse() {
        return new ListEnumValuesResponse();
    }

    /**
     * Create an instance of {@link GetSessionByIdXML }
     * 
     */
    public GetSessionByIdXML createGetSessionByIdXML() {
        return new GetSessionByIdXML();
    }

    /**
     * Create an instance of {@link ListMeasurementConfigurationsWithAttributesXMLResponse }
     * 
     */
    public ListMeasurementConfigurationsWithAttributesXMLResponse createListMeasurementConfigurationsWithAttributesXMLResponse() {
        return new ListMeasurementConfigurationsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link GenericQueryXMLResponse.GenericQueryXMLResult }
     * 
     */
    public GenericQueryXMLResponse.GenericQueryXMLResult createGenericQueryXMLResponseGenericQueryXMLResult() {
        return new GenericQueryXMLResponse.GenericQueryXMLResult();
    }

    /**
     * Create an instance of {@link ListMeasurementPerformersWithAttributesXMLResponse }
     * 
     */
    public ListMeasurementPerformersWithAttributesXMLResponse createListMeasurementPerformersWithAttributesXMLResponse() {
        return new ListMeasurementPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResponse.ListPerformersXMLResult }
     * 
     */
    public ListPerformersXMLResponse.ListPerformersXMLResult createListPerformersXMLResponseListPerformersXMLResult() {
        return new ListPerformersXMLResponse.ListPerformersXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionPerformersWithAttributesXMLResponse }
     * 
     */
    public ListSessionPerformersWithAttributesXMLResponse createListSessionPerformersWithAttributesXMLResponse() {
        return new ListSessionPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link SessionSessionGroupList }
     * 
     */
    public SessionSessionGroupList createSessionSessionGroupList() {
        return new SessionSessionGroupList();
    }

    /**
     * Create an instance of {@link ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult }
     * 
     */
    public ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult createListMeasurementPerformersWithAttributesXMLResponseListMeasurementPerformersWithAttributesXMLResult() {
        return new ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListTrialMeasurementsWithAttributesXMLResponse }
     * 
     */
    public ListTrialMeasurementsWithAttributesXMLResponse createListTrialMeasurementsWithAttributesXMLResponse() {
        return new ListTrialMeasurementsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult }
     * 
     */
    public ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult createListMotionKindsDefinedResponseListMotionKindsDefinedResult() {
        return new ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult();
    }

    /**
     * Create an instance of {@link PerformerSessionList.SessionDetails }
     * 
     */
    public PerformerSessionList.SessionDetails createPerformerSessionListSessionDetails() {
        return new PerformerSessionList.SessionDetails();
    }

    /**
     * Create an instance of {@link GetMeasurementByIdXML }
     * 
     */
    public GetMeasurementByIdXML createGetMeasurementByIdXML() {
        return new GetMeasurementByIdXML();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult createListPerformerSessionsWithAttributesXMLResponseListPerformerSessionsWithAttributesXMLResult() {
        return new ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link GetPerformerConfigurationByIdXML }
     * 
     */
    public GetPerformerConfigurationByIdXML createGetPerformerConfigurationByIdXML() {
        return new GetPerformerConfigurationByIdXML();
    }

    /**
     * Create an instance of {@link GetTrialByIdXML }
     * 
     */
    public GetTrialByIdXML createGetTrialByIdXML() {
        return new GetTrialByIdXML();
    }

    /**
     * Create an instance of {@link ListEnumValues }
     * 
     */
    public ListEnumValues createListEnumValues() {
        return new ListEnumValues();
    }

    /**
     * Create an instance of {@link PerformerList }
     * 
     */
    public PerformerList createPerformerList() {
        return new PerformerList();
    }

    /**
     * Create an instance of {@link PerformerConfDetailsWithAttributes }
     * 
     */
    public PerformerConfDetailsWithAttributes createPerformerConfDetailsWithAttributes() {
        return new PerformerConfDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListFileAttributeDataWithAttributesXMLResponse }
     * 
     */
    public ListFileAttributeDataWithAttributesXMLResponse createListFileAttributeDataWithAttributesXMLResponse() {
        return new ListFileAttributeDataWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link GenericUniformAttributesQueryResult }
     * 
     */
    public GenericUniformAttributesQueryResult createGenericUniformAttributesQueryResult() {
        return new GenericUniformAttributesQueryResult();
    }

    /**
     * Create an instance of {@link GetTrialByIdXMLResponse.GetTrialByIdXMLResult }
     * 
     */
    public GetTrialByIdXMLResponse.GetTrialByIdXMLResult createGetTrialByIdXMLResponseGetTrialByIdXMLResult() {
        return new GetTrialByIdXMLResponse.GetTrialByIdXMLResult();
    }

    /**
     * Create an instance of {@link ListAttributeGroupsDefinedResponse }
     * 
     */
    public ListAttributeGroupsDefinedResponse createListAttributeGroupsDefinedResponse() {
        return new ListAttributeGroupsDefinedResponse();
    }

    /**
     * Create an instance of {@link GenericQueryUniformXML }
     * 
     */
    public GenericQueryUniformXML createGenericQueryUniformXML() {
        return new GenericQueryUniformXML();
    }

    /**
     * Create an instance of {@link FileList }
     * 
     */
    public FileList createFileList() {
        return new FileList();
    }

    /**
     * Create an instance of {@link FilterPredicate }
     * 
     */
    public FilterPredicate createFilterPredicate() {
        return new FilterPredicate();
    }

    /**
     * Create an instance of {@link ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult }
     * 
     */
    public ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult createListGroupSessionsWithAttributesXMLResponseListGroupSessionsWithAttributesXMLResult() {
        return new ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListFileAttributeDataWithAttributesXMLResponse.ListFileAttributeDataWithAttributesXMLResult }
     * 
     */
    public ListFileAttributeDataWithAttributesXMLResponse.ListFileAttributeDataWithAttributesXMLResult createListFileAttributeDataWithAttributesXMLResponseListFileAttributeDataWithAttributesXMLResult() {
        return new ListFileAttributeDataWithAttributesXMLResponse.ListFileAttributeDataWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link GenericQueryXML }
     * 
     */
    public GenericQueryXML createGenericQueryXML() {
        return new GenericQueryXML();
    }

    /**
     * Create an instance of {@link GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult }
     * 
     */
    public GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult createGetMeasurementByIdXMLResponseGetMeasurementByIdXMLResult() {
        return new GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult }
     * 
     */
    public ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult createListPerformersWithAttributesXMLResponseListPerformersWithAttributesXMLResult() {
        return new ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult }
     * 
     */
    public ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult createListMeasurementConfMeasurementsWithAttributesXMLResponseListMeasurementConfMeasurementsWithAttributesXMLResult() {
        return new ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformersXMLResponse }
     * 
     */
    public ListPerformersXMLResponse createListPerformersXMLResponse() {
        return new ListPerformersXMLResponse();
    }

    /**
     * Create an instance of {@link MeasurementConfDetailsWithAttributes }
     * 
     */
    public MeasurementConfDetailsWithAttributes createMeasurementConfDetailsWithAttributes() {
        return new MeasurementConfDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link SessionPerformerConfWithAttributesList }
     * 
     */
    public SessionPerformerConfWithAttributesList createSessionPerformerConfWithAttributesList() {
        return new SessionPerformerConfWithAttributesList();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult createListSessionTrialsWithAttributesXMLResponseListSessionTrialsWithAttributesXMLResult() {
        return new ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult }
     * 
     */
    public ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult createListSessionSessionGroupsResponseListSessionSessionGroupsResult() {
        return new ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult }
     * 
     */
    public ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult createListPerformerSessionsXMLResponseListPerformerSessionsXMLResult() {
        return new ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefined }
     * 
     */
    public ListMotionKindsDefined createListMotionKindsDefined() {
        return new ListMotionKindsDefined();
    }

    /**
     * Create an instance of {@link GetTrialByIdXMLResponse }
     * 
     */
    public GetTrialByIdXMLResponse createGetTrialByIdXMLResponse() {
        return new GetTrialByIdXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefinedResponse }
     * 
     */
    public ListSessionGroupsDefinedResponse createListSessionGroupsDefinedResponse() {
        return new ListSessionGroupsDefinedResponse();
    }

    /**
     * Create an instance of {@link SessionGroupDefinitionList }
     * 
     */
    public SessionGroupDefinitionList createSessionGroupDefinitionList() {
        return new SessionGroupDefinitionList();
    }

    /**
     * Create an instance of {@link ListSessionPerformersWithAttributesXML }
     * 
     */
    public ListSessionPerformersWithAttributesXML createListSessionPerformersWithAttributesXML() {
        return new ListSessionPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link GetMeasurementByIdXMLResponse }
     * 
     */
    public GetMeasurementByIdXMLResponse createGetMeasurementByIdXMLResponse() {
        return new GetMeasurementByIdXMLResponse();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXMLResponse }
     * 
     */
    public ListPerformerSessionsXMLResponse createListPerformerSessionsXMLResponse() {
        return new ListPerformerSessionsXMLResponse();
    }

    /**
     * Create an instance of {@link ListMeasurementConfMeasurementsWithAttributesXMLResponse }
     * 
     */
    public ListMeasurementConfMeasurementsWithAttributesXMLResponse createListMeasurementConfMeasurementsWithAttributesXMLResponse() {
        return new ListMeasurementConfMeasurementsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionPerformerConfsWithAttributesXMLResponse }
     * 
     */
    public ListSessionPerformerConfsWithAttributesXMLResponse createListSessionPerformerConfsWithAttributesXMLResponse() {
        return new ListSessionPerformerConfsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link PerformerList.PerformerDetails }
     * 
     */
    public PerformerList.PerformerDetails createPerformerListPerformerDetails() {
        return new PerformerList.PerformerDetails();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResponse.ListAttributesDefinedResult }
     * 
     */
    public ListAttributesDefinedResponse.ListAttributesDefinedResult createListAttributesDefinedResponseListAttributesDefinedResult() {
        return new ListAttributesDefinedResponse.ListAttributesDefinedResult();
    }

    /**
     * Create an instance of {@link FileWithAttributesList.FileDetailsWithAttributes }
     * 
     */
    public FileWithAttributesList.FileDetailsWithAttributes createFileWithAttributesListFileDetailsWithAttributes() {
        return new FileWithAttributesList.FileDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link SessionTrialList.TrialDetails }
     * 
     */
    public SessionTrialList.TrialDetails createSessionTrialListTrialDetails() {
        return new SessionTrialList.TrialDetails();
    }

    /**
     * Create an instance of {@link ListFileAttributeDataWithAttributesXML }
     * 
     */
    public ListFileAttributeDataWithAttributesXML createListFileAttributeDataWithAttributesXML() {
        return new ListFileAttributeDataWithAttributesXML();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXML }
     * 
     */
    public ListLabPerformersWithAttributesXML createListLabPerformersWithAttributesXML() {
        return new ListLabPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link SessionTrialList }
     * 
     */
    public SessionTrialList createSessionTrialList() {
        return new SessionTrialList();
    }

    /**
     * Create an instance of {@link ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult }
     * 
     */
    public ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult createListTrialMeasurementsWithAttributesXMLResponseListTrialMeasurementsWithAttributesXMLResult() {
        return new ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link GetSessionByIdXMLResponse.GetSessionByIdXMLResult }
     * 
     */
    public GetSessionByIdXMLResponse.GetSessionByIdXMLResult createGetSessionByIdXMLResponseGetSessionByIdXMLResult() {
        return new GetSessionByIdXMLResponse.GetSessionByIdXMLResult();
    }

    /**
     * Create an instance of {@link ListMeasurementConfigurationsWithAttributesXML }
     * 
     */
    public ListMeasurementConfigurationsWithAttributesXML createListMeasurementConfigurationsWithAttributesXML() {
        return new ListMeasurementConfigurationsWithAttributesXML();
    }

    /**
     * Create an instance of {@link GetPerformerConfigurationByIdXMLResponse }
     * 
     */
    public GetPerformerConfigurationByIdXMLResponse createGetPerformerConfigurationByIdXMLResponse() {
        return new GetPerformerConfigurationByIdXMLResponse();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXMLResponse }
     * 
     */
    public GetPerformerByIdXMLResponse createGetPerformerByIdXMLResponse() {
        return new GetPerformerByIdXMLResponse();
    }

    /**
     * Create an instance of {@link ListFilesXMLResponse.ListFilesXMLResult }
     * 
     */
    public ListFilesXMLResponse.ListFilesXMLResult createListFilesXMLResponseListFilesXMLResult() {
        return new ListFilesXMLResponse.ListFilesXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionGroupsDefined }
     * 
     */
    public ListSessionGroupsDefined createListSessionGroupsDefined() {
        return new ListSessionGroupsDefined();
    }

    /**
     * Create an instance of {@link ListMotionKindsDefinedResponse }
     * 
     */
    public ListMotionKindsDefinedResponse createListMotionKindsDefinedResponse() {
        return new ListMotionKindsDefinedResponse();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXMLResponse }
     * 
     */
    public ListPerformerSessionsWithAttributesXMLResponse createListPerformerSessionsWithAttributesXMLResponse() {
        return new ListPerformerSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link LabPerformerWithAttributesList }
     * 
     */
    public LabPerformerWithAttributesList createLabPerformerWithAttributesList() {
        return new LabPerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link AttributeGroupDefinitionList }
     * 
     */
    public AttributeGroupDefinitionList createAttributeGroupDefinitionList() {
        return new AttributeGroupDefinitionList();
    }

    /**
     * Create an instance of {@link MeasurementConfMeasurementWithAttributesList }
     * 
     */
    public MeasurementConfMeasurementWithAttributesList createMeasurementConfMeasurementWithAttributesList() {
        return new MeasurementConfMeasurementWithAttributesList();
    }

    /**
     * Create an instance of {@link GetMeasurementConfigurationByIdXML }
     * 
     */
    public GetMeasurementConfigurationByIdXML createGetMeasurementConfigurationByIdXML() {
        return new GetMeasurementConfigurationByIdXML();
    }

    /**
     * Create an instance of {@link SessionPerformerWithAttributesList }
     * 
     */
    public SessionPerformerWithAttributesList createSessionPerformerWithAttributesList() {
        return new SessionPerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link GenericQueryResult.GenericResultRow }
     * 
     */
    public GenericQueryResult.GenericResultRow createGenericQueryResultGenericResultRow() {
        return new GenericQueryResult.GenericResultRow();
    }

    /**
     * Create an instance of {@link MeasurementDetailsWithAttributes }
     * 
     */
    public MeasurementDetailsWithAttributes createMeasurementDetailsWithAttributes() {
        return new MeasurementDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsXML }
     * 
     */
    public ListPerformerSessionsXML createListPerformerSessionsXML() {
        return new ListPerformerSessionsXML();
    }

    /**
     * Create an instance of {@link ListLabPerformersWithAttributesXMLResponse }
     * 
     */
    public ListLabPerformersWithAttributesXMLResponse createListLabPerformersWithAttributesXMLResponse() {
        return new ListLabPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link GetPerformerByIdXML }
     * 
     */
    public GetPerformerByIdXML createGetPerformerByIdXML() {
        return new GetPerformerByIdXML();
    }

    /**
     * Create an instance of {@link GetSessionLabel }
     * 
     */
    public GetSessionLabel createGetSessionLabel() {
        return new GetSessionLabel();
    }

    /**
     * Create an instance of {@link TrialDetailsWithAttributes }
     * 
     */
    public TrialDetailsWithAttributes createTrialDetailsWithAttributes() {
        return new TrialDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult }
     * 
     */
    public ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult createListMeasurementConfigurationsWithAttributesXMLResponseListMeasurementConfigurationsWithAttributesXMLResult() {
        return new ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link QueryException }
     * 
     */
    public QueryException createQueryException() {
        return new QueryException();
    }

    /**
     * Create an instance of {@link PerformerSessionWithAttributesList }
     * 
     */
    public PerformerSessionWithAttributesList createPerformerSessionWithAttributesList() {
        return new PerformerSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link ListFileAttributeDataXMLResponse }
     * 
     */
    public ListFileAttributeDataXMLResponse createListFileAttributeDataXMLResponse() {
        return new ListFileAttributeDataXMLResponse();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsWithAttributesXML }
     * 
     */
    public ListPerformerSessionsWithAttributesXML createListPerformerSessionsWithAttributesXML() {
        return new ListPerformerSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListTrialMeasurementsWithAttributesXML }
     * 
     */
    public ListTrialMeasurementsWithAttributesXML createListTrialMeasurementsWithAttributesXML() {
        return new ListTrialMeasurementsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListAttributesDefinedResponse }
     * 
     */
    public ListAttributesDefinedResponse createListAttributesDefinedResponse() {
        return new ListAttributesDefinedResponse();
    }

    /**
     * Create an instance of {@link MotionKindDefinitionList }
     * 
     */
    public MotionKindDefinitionList createMotionKindDefinitionList() {
        return new MotionKindDefinitionList();
    }

    /**
     * Create an instance of {@link AttributeGroupDefinitionList.AttributeGroupDefinition }
     * 
     */
    public AttributeGroupDefinitionList.AttributeGroupDefinition createAttributeGroupDefinitionListAttributeGroupDefinition() {
        return new AttributeGroupDefinitionList.AttributeGroupDefinition();
    }

    /**
     * Create an instance of {@link Attributes.Attribute }
     * 
     */
    public Attributes.Attribute createAttributesAttribute() {
        return new Attributes.Attribute();
    }

    /**
     * Create an instance of {@link ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult }
     * 
     */
    public ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult createListFileAttributeDataXMLResponseListFileAttributeDataXMLResult() {
        return new ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult();
    }

    /**
     * Create an instance of {@link ListPerformersXML }
     * 
     */
    public ListPerformersXML createListPerformersXML() {
        return new ListPerformersXML();
    }

    /**
     * Create an instance of {@link AttributeDefinitionList }
     * 
     */
    public AttributeDefinitionList createAttributeDefinitionList() {
        return new AttributeDefinitionList();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXML }
     * 
     */
    public ListLabSessionsWithAttributesXML createListLabSessionsWithAttributesXML() {
        return new ListLabSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link PerformerWithAttributesList }
     * 
     */
    public PerformerWithAttributesList createPerformerWithAttributesList() {
        return new PerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link GenericQueryResult }
     * 
     */
    public GenericQueryResult createGenericQueryResult() {
        return new GenericQueryResult();
    }

    /**
     * Create an instance of {@link LabSessionWithAttributesList }
     * 
     */
    public LabSessionWithAttributesList createLabSessionWithAttributesList() {
        return new LabSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult }
     * 
     */
    public ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult createListLabSessionsWithAttributesXMLResponseListLabSessionsWithAttributesXMLResult() {
        return new ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult }
     * 
     */
    public ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult createListSessionTrialsXMLResponseListSessionTrialsXMLResult() {
        return new ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult();
    }

    /**
     * Create an instance of {@link SessionGroupDefinition }
     * 
     */
    public SessionGroupDefinition createSessionGroupDefinition() {
        return new SessionGroupDefinition();
    }

    /**
     * Create an instance of {@link ListFilesWithAttributesXMLResponse }
     * 
     */
    public ListFilesWithAttributesXMLResponse createListFilesWithAttributesXMLResponse() {
        return new ListFilesWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListSessionPerformerConfsWithAttributesXML }
     * 
     */
    public ListSessionPerformerConfsWithAttributesXML createListSessionPerformerConfsWithAttributesXML() {
        return new ListSessionPerformerConfsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListMeasurementConfSessionsWithAttributesXMLResponse }
     * 
     */
    public ListMeasurementConfSessionsWithAttributesXMLResponse createListMeasurementConfSessionsWithAttributesXMLResponse() {
        return new ListMeasurementConfSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListEnumValuesResponse.ListEnumValuesResult }
     * 
     */
    public ListEnumValuesResponse.ListEnumValuesResult createListEnumValuesResponseListEnumValuesResult() {
        return new ListEnumValuesResponse.ListEnumValuesResult();
    }

    /**
     * Create an instance of {@link ListSessionSessionGroupsResponse }
     * 
     */
    public ListSessionSessionGroupsResponse createListSessionSessionGroupsResponse() {
        return new ListSessionSessionGroupsResponse();
    }

    /**
     * Create an instance of {@link ListMeasurementConfSessionsWithAttributesXML }
     * 
     */
    public ListMeasurementConfSessionsWithAttributesXML createListMeasurementConfSessionsWithAttributesXML() {
        return new ListMeasurementConfSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult }
     * 
     */
    public GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult createGetMeasurementConfigurationByIdXMLResponseGetMeasurementConfigurationByIdXMLResult() {
        return new GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult();
    }

    /**
     * Create an instance of {@link ListSessionTrialsWithAttributesXMLResponse }
     * 
     */
    public ListSessionTrialsWithAttributesXMLResponse createListSessionTrialsWithAttributesXMLResponse() {
        return new ListSessionTrialsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult }
     * 
     */
    public ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult createListMeasurementConfSessionsWithAttributesXMLResponseListMeasurementConfSessionsWithAttributesXMLResult() {
        return new ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link SessionTrialWithAttributesList }
     * 
     */
    public SessionTrialWithAttributesList createSessionTrialWithAttributesList() {
        return new SessionTrialWithAttributesList();
    }

    /**
     * Create an instance of {@link GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult }
     * 
     */
    public GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult createGenericQueryUniformXMLResponseGenericQueryUniformXMLResult() {
        return new GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult();
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

}
