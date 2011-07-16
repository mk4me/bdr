
package motion.database.ws.authorizationWCF;

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
 *         &lt;element name="IfCanUpdateResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "ifCanUpdateResult"
})
@XmlRootElement(name = "IfCanUpdateResponse")
public class IfCanUpdateResponse {

    @XmlElement(name = "IfCanUpdateResult")
    protected boolean ifCanUpdateResult;

    /**
     * Gets the value of the ifCanUpdateResult property.
     * 
     */
    public boolean isIfCanUpdateResult() {
        return ifCanUpdateResult;
    }

    /**
     * Sets the value of the ifCanUpdateResult property.
     * 
     */
    public void setIfCanUpdateResult(boolean value) {
        this.ifCanUpdateResult = value;
    }

}
