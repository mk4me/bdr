
package motion.database.ws.basicQueriesServiceWCF;

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
 *         &lt;element name="measurementConfID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "measurementConfID"
})
@XmlRootElement(name = "ListMeasurementConfMeasurementsWithAttributesXML")
public class ListMeasurementConfMeasurementsWithAttributesXML {

    protected int measurementConfID;

    /**
     * Gets the value of the measurementConfID property.
     * 
     */
    public int getMeasurementConfID() {
        return measurementConfID;
    }

    /**
     * Sets the value of the measurementConfID property.
     * 
     */
    public void setMeasurementConfID(int value) {
        this.measurementConfID = value;
    }

}
