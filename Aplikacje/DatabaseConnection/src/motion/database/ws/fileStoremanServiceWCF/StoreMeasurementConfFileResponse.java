
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
 *         &lt;element name="StoreMeasurementConfFileResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "storeMeasurementConfFileResult"
})
@XmlRootElement(name = "StoreMeasurementConfFileResponse")
public class StoreMeasurementConfFileResponse {

    @XmlElement(name = "StoreMeasurementConfFileResult")
    protected int storeMeasurementConfFileResult;

    /**
     * Gets the value of the storeMeasurementConfFileResult property.
     * 
     */
    public int getStoreMeasurementConfFileResult() {
        return storeMeasurementConfFileResult;
    }

    /**
     * Sets the value of the storeMeasurementConfFileResult property.
     * 
     */
    public void setStoreMeasurementConfFileResult(int value) {
        this.storeMeasurementConfFileResult = value;
    }

}
