
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
 *         &lt;element name="CreateMeasurementConfigurationResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "createMeasurementConfigurationResult"
})
@XmlRootElement(name = "CreateMeasurementConfigurationResponse")
public class CreateMeasurementConfigurationResponse {

    @XmlElement(name = "CreateMeasurementConfigurationResult")
    protected int createMeasurementConfigurationResult;

    /**
     * Gets the value of the createMeasurementConfigurationResult property.
     * 
     */
    public int getCreateMeasurementConfigurationResult() {
        return createMeasurementConfigurationResult;
    }

    /**
     * Sets the value of the createMeasurementConfigurationResult property.
     * 
     */
    public void setCreateMeasurementConfigurationResult(int value) {
        this.createMeasurementConfigurationResult = value;
    }

}
