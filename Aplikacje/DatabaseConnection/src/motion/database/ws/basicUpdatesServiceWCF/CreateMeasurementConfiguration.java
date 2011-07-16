
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
 *         &lt;element name="mcName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mcKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mcDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "mcName",
    "mcKind",
    "mcDescription"
})
@XmlRootElement(name = "CreateMeasurementConfiguration")
public class CreateMeasurementConfiguration {

    protected String mcName;
    protected String mcKind;
    protected String mcDescription;

    /**
     * Gets the value of the mcName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcName() {
        return mcName;
    }

    /**
     * Sets the value of the mcName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcName(String value) {
        this.mcName = value;
    }

    /**
     * Gets the value of the mcKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcKind() {
        return mcKind;
    }

    /**
     * Sets the value of the mcKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcKind(String value) {
        this.mcKind = value;
    }

    /**
     * Gets the value of the mcDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMcDescription() {
        return mcDescription;
    }

    /**
     * Sets the value of the mcDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMcDescription(String value) {
        this.mcDescription = value;
    }

}
