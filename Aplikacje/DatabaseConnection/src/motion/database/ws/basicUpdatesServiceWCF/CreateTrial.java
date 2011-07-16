
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
 *         &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="trialName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trialDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "sessionID",
    "trialName",
    "trialDescription"
})
@XmlRootElement(name = "CreateTrial")
public class CreateTrial {

    protected int sessionID;
    protected String trialName;
    protected String trialDescription;

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
     * Gets the value of the trialName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrialName() {
        return trialName;
    }

    /**
     * Sets the value of the trialName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrialName(String value) {
        this.trialName = value;
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

}
