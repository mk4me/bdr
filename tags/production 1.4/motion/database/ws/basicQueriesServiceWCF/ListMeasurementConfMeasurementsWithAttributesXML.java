
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
 *         &lt;element name="trialID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "trialID"
})
@XmlRootElement(name = "ListMeasurementConfMeasurementsWithAttributesXML")
public class ListMeasurementConfMeasurementsWithAttributesXML {

    protected int trialID;

    /**
     * Gets the value of the trialID property.
     * 
     */
    public int getTrialID() {
        return trialID;
    }

    /**
     * Sets the value of the trialID property.
     * 
     */
    public void setTrialID(int value) {
        this.trialID = value;
    }

}
