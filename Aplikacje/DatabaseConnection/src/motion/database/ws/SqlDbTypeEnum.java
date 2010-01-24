
package motion.database.ws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for sqlDbTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sqlDbTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BigInt"/>
 *     &lt;enumeration value="Binary"/>
 *     &lt;enumeration value="Bit"/>
 *     &lt;enumeration value="Char"/>
 *     &lt;enumeration value="DateTime"/>
 *     &lt;enumeration value="Decimal"/>
 *     &lt;enumeration value="Float"/>
 *     &lt;enumeration value="Image"/>
 *     &lt;enumeration value="Int"/>
 *     &lt;enumeration value="Money"/>
 *     &lt;enumeration value="NChar"/>
 *     &lt;enumeration value="NText"/>
 *     &lt;enumeration value="NVarChar"/>
 *     &lt;enumeration value="Real"/>
 *     &lt;enumeration value="SmallDateTime"/>
 *     &lt;enumeration value="SmallInt"/>
 *     &lt;enumeration value="SmallMoney"/>
 *     &lt;enumeration value="Structured"/>
 *     &lt;enumeration value="Text"/>
 *     &lt;enumeration value="Timestamp"/>
 *     &lt;enumeration value="TinyInt"/>
 *     &lt;enumeration value="Udt"/>
 *     &lt;enumeration value="UniqueIdentifier"/>
 *     &lt;enumeration value="VarBinary"/>
 *     &lt;enumeration value="VarChar"/>
 *     &lt;enumeration value="Variant"/>
 *     &lt;enumeration value="Xml"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum SqlDbTypeEnum {

    @XmlEnumValue("BigInt")
    BIG_INT("BigInt"),
    @XmlEnumValue("Binary")
    BINARY("Binary"),
    @XmlEnumValue("Bit")
    BIT("Bit"),
    @XmlEnumValue("Char")
    CHAR("Char"),
    @XmlEnumValue("DateTime")
    DATE_TIME("DateTime"),
    @XmlEnumValue("Decimal")
    DECIMAL("Decimal"),
    @XmlEnumValue("Float")
    FLOAT("Float"),
    @XmlEnumValue("Image")
    IMAGE("Image"),
    @XmlEnumValue("Int")
    INT("Int"),
    @XmlEnumValue("Money")
    MONEY("Money"),
    @XmlEnumValue("NChar")
    N_CHAR("NChar"),
    @XmlEnumValue("NText")
    N_TEXT("NText"),
    @XmlEnumValue("NVarChar")
    N_VAR_CHAR("NVarChar"),
    @XmlEnumValue("Real")
    REAL("Real"),
    @XmlEnumValue("SmallDateTime")
    SMALL_DATE_TIME("SmallDateTime"),
    @XmlEnumValue("SmallInt")
    SMALL_INT("SmallInt"),
    @XmlEnumValue("SmallMoney")
    SMALL_MONEY("SmallMoney"),
    @XmlEnumValue("Structured")
    STRUCTURED("Structured"),
    @XmlEnumValue("Text")
    TEXT("Text"),
    @XmlEnumValue("Timestamp")
    TIMESTAMP("Timestamp"),
    @XmlEnumValue("TinyInt")
    TINY_INT("TinyInt"),
    @XmlEnumValue("Udt")
    UDT("Udt"),
    @XmlEnumValue("UniqueIdentifier")
    UNIQUE_IDENTIFIER("UniqueIdentifier"),
    @XmlEnumValue("Variant")
    VARIANT("Variant"),
    @XmlEnumValue("VarBinary")
    VAR_BINARY("VarBinary"),
    @XmlEnumValue("VarChar")
    VAR_CHAR("VarChar"),
    @XmlEnumValue("Xml")
    XML("Xml");
    private final String value;

    SqlDbTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SqlDbTypeEnum fromValue(String v) {
        for (SqlDbTypeEnum c: SqlDbTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
