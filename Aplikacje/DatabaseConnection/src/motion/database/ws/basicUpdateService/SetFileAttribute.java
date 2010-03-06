
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetFileAttribute element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SetFileAttribute">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="fileID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="attributeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="attributeValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fileID",
    "attributeId",
    "attributeValue"
})
@XmlRootElement(name = "SetFileAttribute")
public class SetFileAttribute {

    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int fileID;
    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int attributeId;
    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected String attributeValue;

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
     * Gets the value of the attributeId property.
     * 
     */
    public int getAttributeId() {
        return attributeId;
    }

    /**
     * Sets the value of the attributeId property.
     * 
     */
    public void setAttributeId(int value) {
        this.attributeId = value;
    }

    /**
     * Gets the value of the attributeValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     * Sets the value of the attributeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeValue(String value) {
        this.attributeValue = value;
    }

}