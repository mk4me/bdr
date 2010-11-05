
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
 *         &lt;element name="PerformerConfID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PerformerID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "performerConfID",
    "sessionID",
    "performerID",
    "attributes"
})
@XmlRootElement(name = "PerformerConfDetailsWithAttributes")
public class PerformerConfDetailsWithAttributes {

    @XmlElement(name = "PerformerConfID")
    protected int performerConfID;
    @XmlElement(name = "SessionID")
    protected int sessionID;
    @XmlElement(name = "PerformerID")
    protected int performerID;
    @XmlElement(name = "Attributes")
    protected Attributes attributes;

    /**
     * Gets the value of the performerConfID property.
     * 
     */
    public int getPerformerConfID() {
        return performerConfID;
    }

    /**
     * Sets the value of the performerConfID property.
     * 
     */
    public void setPerformerConfID(int value) {
        this.performerConfID = value;
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
     * Gets the value of the performerID property.
     * 
     */
    public int getPerformerID() {
        return performerID;
    }

    /**
     * Sets the value of the performerID property.
     * 
     */
    public void setPerformerID(int value) {
        this.performerID = value;
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
