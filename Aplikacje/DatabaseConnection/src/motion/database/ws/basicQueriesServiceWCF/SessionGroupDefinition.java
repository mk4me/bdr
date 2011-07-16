
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
 *         &lt;element name="SessionGroupID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SessionGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "sessionGroupID",
    "sessionGroupName"
})
@XmlRootElement(name = "SessionGroupDefinition")
public class SessionGroupDefinition {

    @XmlElement(name = "SessionGroupID")
    protected int sessionGroupID;
    @XmlElement(name = "SessionGroupName", required = true)
    protected String sessionGroupName;

    /**
     * Gets the value of the sessionGroupID property.
     * 
     */
    public int getSessionGroupID() {
        return sessionGroupID;
    }

    /**
     * Sets the value of the sessionGroupID property.
     * 
     */
    public void setSessionGroupID(int value) {
        this.sessionGroupID = value;
    }

    /**
     * Gets the value of the sessionGroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionGroupName() {
        return sessionGroupName;
    }

    /**
     * Sets the value of the sessionGroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionGroupName(String value) {
        this.sessionGroupName = value;
    }

}
