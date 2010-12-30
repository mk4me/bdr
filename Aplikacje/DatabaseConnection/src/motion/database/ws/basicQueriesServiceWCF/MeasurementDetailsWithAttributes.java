
package motion.database.ws.basicQueriesServiceWCF;

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
 *         &lt;element name="MeasurementID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MeasurementConfID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TrialID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}Attributes" minOccurs="0"/>
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
    "measurementID",
    "measurementConfID",
    "trialID",
    "attributes"
})
@XmlRootElement(name = "MeasurementDetailsWithAttributes")
public class MeasurementDetailsWithAttributes {

    @XmlElement(name = "MeasurementID")
    protected int measurementID;
    @XmlElement(name = "MeasurementConfID")
    protected int measurementConfID;
    @XmlElement(name = "TrialID")
    protected int trialID;
    @XmlElement(name = "Attributes")
    protected Attributes attributes;

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
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link Attributes }
     *     
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributes }
     *     
     */
    public void setAttributes(Attributes value) {
        this.attributes = value;
    }

}
