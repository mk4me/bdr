
package motion.database.ws.fileStoremanService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StoreSessionFileResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="StoreSessionFileResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="StoreSessionFileResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "storeSessionFileResult"
})
@XmlRootElement(name = "StoreSessionFileResponse")
public class StoreSessionFileResponse {

    @XmlElement(name = "StoreSessionFileResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    protected int storeSessionFileResult;

    /**
     * Gets the value of the storeSessionFileResult property.
     * 
     */
    public int getStoreSessionFileResult() {
        return storeSessionFileResult;
    }

    /**
     * Sets the value of the storeSessionFileResult property.
     * 
     */
    public void setStoreSessionFileResult(int value) {
        this.storeSessionFileResult = value;
    }

}
