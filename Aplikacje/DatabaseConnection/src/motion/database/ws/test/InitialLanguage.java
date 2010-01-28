
package motion.database.ws.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set initial language to set.
 * 
 * <p>Java class for initialLanguage element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="initialLanguage">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;attribute name="optional" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *         &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "initialLanguage")
public class InitialLanguage {

    @XmlAttribute
    protected Boolean optional;
    @XmlAttribute(required = true)
    protected String value;

    /**
     * Gets the value of the optional property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isOptional() {
        if (optional == null) {
            return false;
        } else {
            return optional;
        }
    }

    /**
     * Sets the value of the optional property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOptional(Boolean value) {
        this.optional = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
