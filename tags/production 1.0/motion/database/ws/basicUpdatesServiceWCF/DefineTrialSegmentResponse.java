
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
 *         &lt;element name="DefineTrialSegmentResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "defineTrialSegmentResult"
})
@XmlRootElement(name = "DefineTrialSegmentResponse")
public class DefineTrialSegmentResponse {

    @XmlElement(name = "DefineTrialSegmentResult")
    protected int defineTrialSegmentResult;

    /**
     * Gets the value of the defineTrialSegmentResult property.
     * 
     */
    public int getDefineTrialSegmentResult() {
        return defineTrialSegmentResult;
    }

    /**
     * Sets the value of the defineTrialSegmentResult property.
     * 
     */
    public void setDefineTrialSegmentResult(int value) {
        this.defineTrialSegmentResult = value;
    }

}
