
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
 *         &lt;element name="MeasurementConfID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MeasurementConfName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MeasurementConfDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "measurementConfID",
    "measurementConfName",
    "measurementConfDescription",
    "attributes"
})
@XmlRootElement(name = "MeasurementConfDetailsWithAttributes")
public class MeasurementConfDetailsWithAttributes {

    @XmlElement(name = "MeasurementConfID")
    protected int measurementConfID;
    @XmlElement(name = "MeasurementConfName", required = true)
    protected String measurementConfName;
    @XmlElement(name = "MeasurementConfDescription", required = true)
    protected String measurementConfDescription;
    @XmlElement(name = "Attributes")
    protected Attributes attributes;

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
     * Gets the value of the measurementConfName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurementConfName() {
        return measurementConfName;
    }

    /**
     * Sets the value of the measurementConfName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurementConfName(String value) {
        this.measurementConfName = value;
    }

    /**
     * Gets the value of the measurementConfDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasurementConfDescription() {
        return measurementConfDescription;
    }

    /**
     * Sets the value of the measurementConfDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasurementConfDescription(String value) {
        this.measurementConfDescription = value;
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
