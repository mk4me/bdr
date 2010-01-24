
package motion.database.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SqlParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SqlParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="clrTypeName" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="direction" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlParameter}ParameterDirection" default="Input" />
 *       &lt;attribute name="localeId" type="{http://www.w3.org/2001/XMLSchema}int" default="-1" />
 *       &lt;attribute name="maxLength" type="{http://www.w3.org/2001/XMLSchema}long" default="1" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="precision" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="18" />
 *       &lt;attribute name="scale" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="0" />
 *       &lt;attribute name="sqlCollationVersion" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *       &lt;attribute name="sqlCompareOptions" type="{http://schemas.microsoft.com/sqlserver/2004/sqltypes}sqlCompareOptionsList" default="Default" />
 *       &lt;attribute name="sqlDbType" type="{http://schemas.microsoft.com/sqlserver/2004/sqltypes}sqlDbTypeEnum" default="NVarChar" />
 *       &lt;attribute name="sqlSortId" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *       &lt;attribute name="typeName" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="xmlSchemaCollection" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SqlParameter", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlParameter", propOrder = {
    "value"
})
public class SqlParameter {

    @XmlElement(name = "Value", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlParameter", required = true, nillable = true)
    protected Object value;
    @XmlAttribute
    protected String clrTypeName;
    @XmlAttribute
    protected ParameterDirection direction;
    @XmlAttribute
    protected Integer localeId;
    @XmlAttribute
    protected Long maxLength;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected Short precision;
    @XmlAttribute
    protected Short scale;
    @XmlAttribute
    protected Integer sqlCollationVersion;
    @XmlAttribute
    protected List<SqlCompareOptionsEnum> sqlCompareOptions;
    @XmlAttribute
    protected SqlDbTypeEnum sqlDbType;
    @XmlAttribute
    protected Integer sqlSortId;
    @XmlAttribute
    protected String typeName;
    @XmlAttribute
    protected String xmlSchemaCollection;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Gets the value of the clrTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClrTypeName() {
        if (clrTypeName == null) {
            return "";
        } else {
            return clrTypeName;
        }
    }

    /**
     * Sets the value of the clrTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClrTypeName(String value) {
        this.clrTypeName = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterDirection }
     *     
     */
    public ParameterDirection getDirection() {
        if (direction == null) {
            return ParameterDirection.INPUT;
        } else {
            return direction;
        }
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterDirection }
     *     
     */
    public void setDirection(ParameterDirection value) {
        this.direction = value;
    }

    /**
     * Gets the value of the localeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getLocaleId() {
        if (localeId == null) {
            return -1;
        } else {
            return localeId;
        }
    }

    /**
     * Sets the value of the localeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLocaleId(Integer value) {
        this.localeId = value;
    }

    /**
     * Gets the value of the maxLength property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getMaxLength() {
        if (maxLength == null) {
            return  1L;
        } else {
            return maxLength;
        }
    }

    /**
     * Sets the value of the maxLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxLength(Long value) {
        this.maxLength = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the precision property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getPrecision() {
        if (precision == null) {
            return ((short) 18);
        } else {
            return precision;
        }
    }

    /**
     * Sets the value of the precision property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPrecision(Short value) {
        this.precision = value;
    }

    /**
     * Gets the value of the scale property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getScale() {
        if (scale == null) {
            return ((short) 0);
        } else {
            return scale;
        }
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setScale(Short value) {
        this.scale = value;
    }

    /**
     * Gets the value of the sqlCollationVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSqlCollationVersion() {
        if (sqlCollationVersion == null) {
            return  0;
        } else {
            return sqlCollationVersion;
        }
    }

    /**
     * Sets the value of the sqlCollationVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSqlCollationVersion(Integer value) {
        this.sqlCollationVersion = value;
    }

    /**
     * Gets the value of the sqlCompareOptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sqlCompareOptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSqlCompareOptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SqlCompareOptionsEnum }
     * 
     * 
     */
    public List<SqlCompareOptionsEnum> getSqlCompareOptions() {
        if (sqlCompareOptions == null) {
            sqlCompareOptions = new ArrayList<SqlCompareOptionsEnum>();
        }
        return this.sqlCompareOptions;
    }

    /**
     * Gets the value of the sqlDbType property.
     * 
     * @return
     *     possible object is
     *     {@link SqlDbTypeEnum }
     *     
     */
    public SqlDbTypeEnum getSqlDbType() {
        if (sqlDbType == null) {
            return SqlDbTypeEnum.N_VAR_CHAR;
        } else {
            return sqlDbType;
        }
    }

    /**
     * Sets the value of the sqlDbType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SqlDbTypeEnum }
     *     
     */
    public void setSqlDbType(SqlDbTypeEnum value) {
        this.sqlDbType = value;
    }

    /**
     * Gets the value of the sqlSortId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSqlSortId() {
        if (sqlSortId == null) {
            return  0;
        } else {
            return sqlSortId;
        }
    }

    /**
     * Sets the value of the sqlSortId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSqlSortId(Integer value) {
        this.sqlSortId = value;
    }

    /**
     * Gets the value of the typeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeName() {
        if (typeName == null) {
            return "";
        } else {
            return typeName;
        }
    }

    /**
     * Sets the value of the typeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeName(String value) {
        this.typeName = value;
    }

    /**
     * Gets the value of the xmlSchemaCollection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlSchemaCollection() {
        if (xmlSchemaCollection == null) {
            return "";
        } else {
            return xmlSchemaCollection;
        }
    }

    /**
     * Sets the value of the xmlSchemaCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlSchemaCollection(String value) {
        this.xmlSchemaCollection = value;
    }

}
