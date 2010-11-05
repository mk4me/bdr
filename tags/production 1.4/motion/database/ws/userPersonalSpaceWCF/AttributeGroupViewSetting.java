
package motion.database.ws.userPersonalSpaceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttributeGroupViewSetting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttributeGroupViewSetting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AttributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "AttributeGroupViewSetting", propOrder = {
    "attributeGroupName",
    "describedEntity",
    "show"
})
public class AttributeGroupViewSetting {

    @XmlElement(name = "AttributeGroupName")
    protected String attributeGroupName;
    @XmlElement(name = "DescribedEntity")
    protected String describedEntity;
    @XmlElement(name = "Show")
    protected boolean show;

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
