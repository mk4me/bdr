
package motion.database.ws.basicUpdatesServiceWCF;

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
 *         &lt;element name="resourceID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="update" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "resourceID",
    "entity",
    "attributeName",
    "fileID",
    "update"
})
@XmlRootElement(name = "SetFileTypedAttributeValue")
public class SetFileTypedAttributeValue {

    protected int resourceID;
    protected String entity;
    protected String attributeName;
    protected int fileID;
    protected boolean update;

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
     * Gets the value of the fileID property.
     * 
     */
    public int getFileID() {
        return fileID;
    }

    /**
     * Sets the value of the fileID property.
     * 
     */
    public void setFileID(int value) {
        this.fileID = value;
    }

    /**
     * Gets the value of the update property.
     * 
     */
    public boolean isUpdate() {
        return update;
    }

    /**
     * Sets the value of the update property.
     * 
     */
    public void setUpdate(boolean value) {
        this.update = value;
    }

}
