
package motion.database.ws.fileStoremanService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetrieveFileResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="RetrieveFileResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="RetrieveFileResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "retrieveFileResult"
})
@XmlRootElement(name = "RetrieveFileResponse")
public class RetrieveFileResponse {

    @XmlElement(name = "RetrieveFileResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    protected String retrieveFileResult;

    /**
     * Gets the value of the retrieveFileResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieveFileResult() {
        return retrieveFileResult;
    }

    /**
     * Sets the value of the retrieveFileResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieveFileResult(String value) {
        this.retrieveFileResult = value;
    }

}
