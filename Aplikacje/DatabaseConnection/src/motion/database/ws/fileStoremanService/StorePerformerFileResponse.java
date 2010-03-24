
package motion.database.ws.fileStoremanService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StorePerformerFileResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="StorePerformerFileResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="StorePerformerFileResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "storePerformerFileResult"
})
@XmlRootElement(name = "StorePerformerFileResponse")
public class StorePerformerFileResponse {

    @XmlElement(name = "StorePerformerFileResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
    protected int storePerformerFileResult;

    /**
     * Gets the value of the storePerformerFileResult property.
     * 
     */
    public int getStorePerformerFileResult() {
        return storePerformerFileResult;
    }

    /**
     * Sets the value of the storePerformerFileResult property.
     * 
     */
    public void setStorePerformerFileResult(int value) {
        this.storePerformerFileResult = value;
    }

}
