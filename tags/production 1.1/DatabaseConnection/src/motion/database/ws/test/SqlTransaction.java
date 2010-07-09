
package motion.database.ws.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SqlTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SqlTransaction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Descriptor" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="Type">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Begin"/>
 *               &lt;enumeration value="Commit"/>
 *               &lt;enumeration value="Rollback"/>
 *               &lt;enumeration value="EnlistDTC"/>
 *               &lt;enumeration value="Defect"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SqlTransaction", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlTransaction", propOrder = {
    "descriptor",
    "type"
})
public class SqlTransaction {

    @XmlElement(name = "Descriptor", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlTransaction", required = true)
    protected byte[] descriptor;
    @XmlElement(name = "Type", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlTransaction", required = true)
    protected String type;

    /**
     * Gets the value of the descriptor property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDescriptor() {
        return descriptor;
    }

    /**
     * Sets the value of the descriptor property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDescriptor(byte[] value) {
        this.descriptor = ((byte[]) value);
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
