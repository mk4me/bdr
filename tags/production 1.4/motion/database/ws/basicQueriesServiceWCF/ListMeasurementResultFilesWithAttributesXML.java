
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
 *         &lt;element MeasurementConfName="MeasurementID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "MeasurementID"
})
@XmlRootElement(name = "ListMeasurementResultFilesWithAttributesXML")
public class ListMeasurementResultFilesWithAttributesXML {

    protected int measurementID;

    /**
     * Gets the value of the MeasurementID property.
     * 
     */
    public int getMeasurementID() {
        return measurementID;
    }

    /**
     * Sets the value of the MeasurementID property.
     * 
     */
    public void setMeasurementID(int value) {
        this.measurementID = value;
    }

}
