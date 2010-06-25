
package motion.database.ws.fileStoremanService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StoreTrialFiles element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="StoreTrialFiles">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="trialId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "trialId",
    "path"
})
@XmlRootElement(name = "StoreTrialFiles")
public class StoreTrialFiles {

    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    protected int trialId;
    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    protected String path;

    /**
     * Gets the value of the trialId property.
     * 
     */
    public int getTrialId() {
        return trialId;
    }

    /**
     * Sets the value of the trialId property.
     * 
     */
    public void setTrialId(int value) {
        this.trialId = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

}