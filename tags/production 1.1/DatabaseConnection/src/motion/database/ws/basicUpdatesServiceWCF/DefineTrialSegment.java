
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
 *         &lt;element name="trialID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="segmentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "trialID",
    "segmentName",
    "startTime",
    "endTime"
})
@XmlRootElement(name = "DefineTrialSegment")
public class DefineTrialSegment {

    protected int trialID;
    protected String segmentName;
    protected int startTime;
    protected int endTime;

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

    /**
     * Gets the value of the segmentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentName() {
        return segmentName;
    }

    /**
     * Sets the value of the segmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentName(String value) {
        this.segmentName = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     */
    public void setStartTime(int value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     */
    public void setEndTime(int value) {
        this.endTime = value;
    }

}
