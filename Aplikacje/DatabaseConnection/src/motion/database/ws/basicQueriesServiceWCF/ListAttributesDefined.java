
package motion.database.ws.basicQueriesServiceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entityKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attributeGroupName",
    "entityKind"
})
@XmlRootElement(name = "ListAttributesDefined")
public class ListAttributesDefined {

    protected String attributeGroupName;
    protected String entityKind;

    /**
     * Gets the value of the attributeGroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeGroupName() {
        return attributeGroupName;
    }

    /**
     * Sets the value of the attributeGroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeGroupName(String value) {
        this.attributeGroupName = value;
    }

    /**
     * Gets the value of the entityKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityKind() {
        return entityKind;
    }

    /**
     * Sets the value of the entityKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityKind(String value) {
        this.entityKind = value;
    }

}
