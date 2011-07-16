
package motion.database.ws.userPersonalSpaceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttributeViewSetting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttributeViewSetting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescribedEntity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Show" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeViewSetting", propOrder = {
    "attributeName",
    "describedEntity",
    "show"
})
public class AttributeViewSetting {

    @XmlElement(name = "AttributeName")
    protected String attributeName;
    @XmlElement(name = "DescribedEntity")
    protected String describedEntity;
    @XmlElement(name = "Show")
    protected boolean show;

    /**
     * Gets the value of the attributeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Sets the value of the attributeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeName(String value) {
        this.attributeName = value;
    }

    /**
     * Gets the value of the describedEntity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescribedEntity() {
        return describedEntity;
    }

    /**
     * Sets the value of the describedEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescribedEntity(String value) {
        this.describedEntity = value;
    }

    /**
     * Gets the value of the show property.
     * 
     */
    public boolean isShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     * 
     */
    public void setShow(boolean value) {
        this.show = value;
    }

}
