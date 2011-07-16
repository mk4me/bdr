
package motion.database.ws.fileStoremanServiceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FileData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FileData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FileLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubdirPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileData", propOrder = {
    "fileLocation",
    "subdirPath"
})
public class FileData {

    @XmlElement(name = "FileLocation")
    protected String fileLocation;
    @XmlElement(name = "SubdirPath")
    protected String subdirPath;

    /**
     * Gets the value of the fileLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * Sets the value of the fileLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileLocation(String value) {
        this.fileLocation = value;
    }

    /**
     * Gets the value of the subdirPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubdirPath() {
        return subdirPath;
    }

    /**
     * Sets the value of the subdirPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubdirPath(String value) {
        this.subdirPath = value;
    }

}
