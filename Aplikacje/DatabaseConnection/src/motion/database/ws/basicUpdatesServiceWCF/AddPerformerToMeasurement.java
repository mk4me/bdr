
package motion.database.ws.basicUpdatesServiceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="performerID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="measurementID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "performerID",
    "measurementID"
})
@XmlRootElement(name = "AddPerformerToMeasurement")
public class AddPerformerToMeasurement {

    protected int performerID;
    protected int measurementID;

    /**
     * Gets the value of the performerID property.
     * 
     */
    public int getPerformerID() {
        return performerID;
    }

    /**
     * Sets the value of the performerID property.
     * 
     */
    public void setPerformerID(int value) {
        this.performerID = value;
    }

    /**
     * Gets the value of the measurementID property.
     * 
     */
    public int getMeasurementID() {
        return measurementID;
    }

    /**
     * Sets the value of the measurementID property.
     * 
     */
    public void setMeasurementID(int value) {
        this.measurementID = value;
    }

}
