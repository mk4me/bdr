
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
 *         &lt;element name="TrialID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TrialDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "trialID",
    "sessionID",
    "trialDescription",
    "attributes"
})
@XmlRootElement(name = "TrialDetailsWithAttributes")
public class TrialDetailsWithAttributes {

    @XmlElement(name = "TrialID")
    protected int trialID;
    @XmlElement(name = "SessionID")
    protected int sessionID;
    @XmlElement(name = "TrialDescription", required = true)
    protected String trialDescription;
    @XmlElement(name = "Attributes")
    protected Attributes attributes;

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
     * Gets the value of the sessionID property.
     * 
     */
    public int getSessionID() {
        return sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     * 
     */
    public void setSessionID(int value) {
        this.sessionID = value;
    }

    /**
     * Gets the value of the trialDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrialDescription() {
        return trialDescription;
    }

    /**
     * Sets the value of the trialDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrialDescription(String value) {
        this.trialDescription = value;
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
