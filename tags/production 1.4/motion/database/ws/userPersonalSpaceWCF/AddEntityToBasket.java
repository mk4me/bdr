
package motion.database.ws.userPersonalSpaceWCF;

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
 *         &lt;element name="basketName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resourceID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "basketName",
    "resourceID",
    "entity"
})
@XmlRootElement(name = "AddEntityToBasket")
public class AddEntityToBasket {

    protected String basketName;
    protected int resourceID;
    protected String entity;

    /**
     * Gets the value of the basketName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasketName() {
        return basketName;
    }

    /**
     * Sets the value of the basketName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasketName(String value) {
        this.basketName = value;
    }

    /**
     * Gets the value of the resourceID property.
     * 
     */
    public int getResourceID() {
        return resourceID;
    }

    /**
     * Sets the value of the resourceID property.
     * 
     */
    public void setResourceID(int value) {
        this.resourceID = value;
    }

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity(String value) {
        this.entity = value;
    }

}
