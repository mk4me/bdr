
package motion.database.ws.userPersonalSpaceWCF;

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
 * generated in the motion.database.ws.userPersonalSpaceWCF package. 
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
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _QueryException_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "QueryException");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UPSException_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", "UPSException");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _UPSExceptionDetails_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", "Details");
    private final static QName _UPSExceptionIssueKind_QNAME = new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", "IssueKind");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: motion.database.ws.userPersonalSpaceWCF
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateStoredFiltersResponse }
     * 
     */
    public UpdateStoredFiltersResponse createUpdateStoredFiltersResponse() {
        return new UpdateStoredFiltersResponse();
    }

    /**
     * Create an instance of {@link BasketPerformerWithAttributesList }
     * 
     */
    public BasketPerformerWithAttributesList createBasketPerformerWithAttributesList() {
        return new BasketPerformerWithAttributesList();
    }

    /**
     * Create an instance of {@link UpdateStoredFilters }
     * 
     */
    public UpdateStoredFilters createUpdateStoredFilters() {
        return new UpdateStoredFilters();
    }

    /**
     * Create an instance of {@link ListStoredFiltersResponse }
     * 
     */
    public ListStoredFiltersResponse createListStoredFiltersResponse() {
        return new ListStoredFiltersResponse();
    }

    /**
     * Create an instance of {@link CreateBasket }
     * 
     */
    public CreateBasket createCreateBasket() {
        return new CreateBasket();
    }

    /**
     * Create an instance of {@link BasketDefinitionList.BasketDefinition }
     * 
     */
    public BasketDefinitionList.BasketDefinition createBasketDefinitionListBasketDefinition() {
        return new BasketDefinitionList.BasketDefinition();
    }

    /**
     * Create an instance of {@link FilterList.FilterPredicate }
     * 
     */
    public FilterList.FilterPredicate createFilterListFilterPredicate() {
        return new FilterList.FilterPredicate();
    }

    /**
     * Create an instance of {@link ListBasketTrialsWithAttributesXMLResponse }
     * 
     */
    public ListBasketTrialsWithAttributesXMLResponse createListBasketTrialsWithAttributesXMLResponse() {
        return new ListBasketTrialsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult }
     * 
     */
    public ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult createListBasketSessionsWithAttributesXMLResponseListBasketSessionsWithAttributesXMLResult() {
        return new ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link BasketSessionWithAttributesList }
     * 
     */
    public BasketSessionWithAttributesList createBasketSessionWithAttributesList() {
        return new BasketSessionWithAttributesList();
    }

    /**
     * Create an instance of {@link PerformerDetailsWithAttributes }
     * 
     */
    public PerformerDetailsWithAttributes createPerformerDetailsWithAttributes() {
        return new PerformerDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link BasketTrialWithAttributesList }
     * 
     */
    public BasketTrialWithAttributesList createBasketTrialWithAttributesList() {
        return new BasketTrialWithAttributesList();
    }

    /**
     * Create an instance of {@link UPSException }
     * 
     */
    public UPSException createUPSException() {
        return new UPSException();
    }

    /**
     * Create an instance of {@link BasketDefinitionList }
     * 
     */
    public BasketDefinitionList createBasketDefinitionList() {
        return new BasketDefinitionList();
    }

    /**
     * Create an instance of {@link ListUserBasketsResponse.ListUserBasketsResult }
     * 
     */
    public ListUserBasketsResponse.ListUserBasketsResult createListUserBasketsResponseListUserBasketsResult() {
        return new ListUserBasketsResponse.ListUserBasketsResult();
    }

    /**
     * Create an instance of {@link RemoveEntityFromBasket }
     * 
     */
    public RemoveEntityFromBasket createRemoveEntityFromBasket() {
        return new RemoveEntityFromBasket();
    }

    /**
     * Create an instance of {@link RemoveBasketResponse }
     * 
     */
    public RemoveBasketResponse createRemoveBasketResponse() {
        return new RemoveBasketResponse();
    }

    /**
     * Create an instance of {@link ArrayOfFilterPredicate }
     * 
     */
    public ArrayOfFilterPredicate createArrayOfFilterPredicate() {
        return new ArrayOfFilterPredicate();
    }

    /**
     * Create an instance of {@link ListBasketSessionsWithAttributesXML }
     * 
     */
    public ListBasketSessionsWithAttributesXML createListBasketSessionsWithAttributesXML() {
        return new ListBasketSessionsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListStoredFiltersResponse.ListStoredFiltersResult }
     * 
     */
    public ListStoredFiltersResponse.ListStoredFiltersResult createListStoredFiltersResponseListStoredFiltersResult() {
        return new ListStoredFiltersResponse.ListStoredFiltersResult();
    }

    /**
     * Create an instance of {@link ListBasketPerformersWithAttributesXMLResponse }
     * 
     */
    public ListBasketPerformersWithAttributesXMLResponse createListBasketPerformersWithAttributesXMLResponse() {
        return new ListBasketPerformersWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link FilterList }
     * 
     */
    public FilterList createFilterList() {
        return new FilterList();
    }

    /**
     * Create an instance of {@link AddEntityToBasket }
     * 
     */
    public AddEntityToBasket createAddEntityToBasket() {
        return new AddEntityToBasket();
    }

    /**
     * Create an instance of {@link Attributes.Attribute }
     * 
     */
    public Attributes.Attribute createAttributesAttribute() {
        return new Attributes.Attribute();
    }

    /**
     * Create an instance of {@link ListBasketTrialsWithAttributesXML }
     * 
     */
    public ListBasketTrialsWithAttributesXML createListBasketTrialsWithAttributesXML() {
        return new ListBasketTrialsWithAttributesXML();
    }

    /**
     * Create an instance of {@link ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult }
     * 
     */
    public ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult createListBasketPerformersWithAttributesXMLResponseListBasketPerformersWithAttributesXMLResult() {
        return new ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link TrialDetailsWithAttributes }
     * 
     */
    public TrialDetailsWithAttributes createTrialDetailsWithAttributes() {
        return new TrialDetailsWithAttributes();
    }

    /**
     * Create an instance of {@link ListStoredFilters }
     * 
     */
    public ListStoredFilters createListStoredFilters() {
        return new ListStoredFilters();
    }

    /**
     * Create an instance of {@link ListUserBaskets }
     * 
     */
    public ListUserBaskets createListUserBaskets() {
        return new ListUserBaskets();
    }

    /**
     * Create an instance of {@link ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult }
     * 
     */
    public ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult createListBasketTrialsWithAttributesXMLResponseListBasketTrialsWithAttributesXMLResult() {
        return new ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult();
    }

    /**
     * Create an instance of {@link CreateBasketResponse }
     * 
     */
    public CreateBasketResponse createCreateBasketResponse() {
        return new CreateBasketResponse();
    }

    /**
     * Create an instance of {@link QueryException }
     * 
     */
    public QueryException createQueryException() {
        return new QueryException();
    }

    /**
     * Create an instance of {@link RemoveBasket }
     * 
     */
    public RemoveBasket createRemoveBasket() {
        return new RemoveBasket();
    }

    /**
     * Create an instance of {@link ListUserBasketsResponse }
     * 
     */
    public ListUserBasketsResponse createListUserBasketsResponse() {
        return new ListUserBasketsResponse();
    }

    /**
     * Create an instance of {@link AddEntityToBasketResponse }
     * 
     */
    public AddEntityToBasketResponse createAddEntityToBasketResponse() {
        return new AddEntityToBasketResponse();
    }

    /**
     * Create an instance of {@link ListBasketPerformersWithAttributesXML }
     * 
     */
    public ListBasketPerformersWithAttributesXML createListBasketPerformersWithAttributesXML() {
        return new ListBasketPerformersWithAttributesXML();
    }

    /**
     * Create an instance of {@link motion.database.ws.userPersonalSpaceWCF.FilterPredicate }
     * 
     */
    public motion.database.ws.userPersonalSpaceWCF.FilterPredicate createFilterPredicate() {
        return new motion.database.ws.userPersonalSpaceWCF.FilterPredicate();
    }

    /**
     * Create an instance of {@link RemoveEntityFromBasketResponse }
     * 
     */
    public RemoveEntityFromBasketResponse createRemoveEntityFromBasketResponse() {
        return new RemoveEntityFromBasketResponse();
    }

    /**
     * Create an instance of {@link ListBasketSessionsWithAttributesXMLResponse }
     * 
     */
    public ListBasketSessionsWithAttributesXMLResponse createListBasketSessionsWithAttributesXMLResponse() {
        return new ListBasketSessionsWithAttributesXMLResponse();
    }

    /**
     * Create an instance of {@link Attributes }
     * 
     */
    public Attributes createAttributes() {
        return new Attributes();
    }

    /**
     * Create an instance of {@link SessionDetailsWithAttributes }
     * 
     */
    public SessionDetailsWithAttributes createSessionDetailsWithAttributes() {
        return new SessionDetailsWithAttributes();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", name = "QueryException")
    public JAXBElement<QueryException> createQueryException(QueryException value) {
        return new JAXBElement<QueryException>(_QueryException_QNAME, QueryException.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link UPSException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", name = "UPSException")
    public JAXBElement<UPSException> createUPSException(UPSException value) {
        return new JAXBElement<UPSException>(_UPSException_QNAME, UPSException.class, null, value);
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
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", name = "Details", scope = UPSException.class)
    public JAXBElement<String> createUPSExceptionDetails(String value) {
        return new JAXBElement<String>(_UPSExceptionDetails_QNAME, String.class, UPSException.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService", name = "IssueKind", scope = UPSException.class)
    public JAXBElement<String> createUPSExceptionIssueKind(String value) {
        return new JAXBElement<String>(_UPSExceptionIssueKind_QNAME, String.class, UPSException.class, value);
    }

}
