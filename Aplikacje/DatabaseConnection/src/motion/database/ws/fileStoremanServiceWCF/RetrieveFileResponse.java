
package motion.database.ws.fileStoremanServiceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="RetrieveFileResult" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService}FileData"/>
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
    "retrieveFileResult"
})
@XmlRootElement(name = "RetrieveFileResponse")
public class RetrieveFileResponse {

    @XmlElement(name = "RetrieveFileResult", required = true)
    protected FileData retrieveFileResult;

    /**
     * Gets the value of the retrieveFileResult property.
     * 
     * @return
     *     possible object is
     *     {@link FileData }
     *     
     */
    public FileData getRetrieveFileResult() {
        return retrieveFileResult;
    }

    /**
     * Sets the value of the retrieveFileResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileData }
     *     
     */
    public void setRetrieveFileResult(FileData value) {
        this.retrieveFileResult = value;
    }

}
