
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
 *         &lt;element MeasurementConfName="StoreMeasurementResultFileResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "storeMeasurementResultFileResult"
})
@XmlRootElement(name = "StoreMeasurementResultFileResponse")
public class StoreMeasurementResultFileResponse {

    @XmlElement(name = "StoreMeasurementResultFileResult")
    protected int storeMeasurementResultFileResult;

    /**
     * Gets the value of the storeMeasurementResultFileResult property.
     * 
     */
    public int getStoreMeasurementResultFileResult() {
        return storeMeasurementResultFileResult;
    }

    /**
     * Sets the value of the storeMeasurementResultFileResult property.
     * 
     */
    public void setStoreMeasurementResultFileResult(int value) {
        this.storeMeasurementResultFileResult = value;
    }

}
