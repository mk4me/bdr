
package motion.database.ws.basicUpdatesServiceWCF;

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
 *         &lt;element name="AddPerformerToMeasurementResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "addPerformerToMeasurementResult"
})
@XmlRootElement(name = "AddPerformerToMeasurementResponse")
public class AddPerformerToMeasurementResponse {

    @XmlElement(name = "AddPerformerToMeasurementResult")
    protected boolean addPerformerToMeasurementResult;

    /**
     * Gets the value of the addPerformerToMeasurementResult property.
     * 
     */
    public boolean isAddPerformerToMeasurementResult() {
        return addPerformerToMeasurementResult;
    }

    /**
     * Sets the value of the addPerformerToMeasurementResult property.
     * 
     */
    public void setAddPerformerToMeasurementResult(boolean value) {
        this.addPerformerToMeasurementResult = value;
    }

}
