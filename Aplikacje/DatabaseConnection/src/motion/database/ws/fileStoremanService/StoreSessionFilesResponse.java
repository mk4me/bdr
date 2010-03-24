
package motion.database.ws.fileStoremanService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StoreSessionFilesResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="StoreSessionFilesResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="StoreSessionFilesResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "storeSessionFilesResult"
})
@XmlRootElement(name = "StoreSessionFilesResponse")
public class StoreSessionFilesResponse {

    @XmlElement(name = "StoreSessionFilesResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    protected int storeSessionFilesResult;

    /**
     * Gets the value of the storeSessionFilesResult property.
     * 
     */
    public int getStoreSessionFilesResult() {
        return storeSessionFilesResult;
    }

    /**
     * Sets the value of the storeSessionFilesResult property.
     * 
     */
    public void setStoreSessionFilesResult(int value) {
        this.storeSessionFilesResult = value;
    }

}
