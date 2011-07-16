
package motion.database.ws.basicUpdatesServiceWCF;

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
 *         &lt;element name="CreateSessionResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "createSessionResult"
})
@XmlRootElement(name = "CreateSessionResponse")
public class CreateSessionResponse {

    @XmlElement(name = "CreateSessionResult")
    protected int createSessionResult;

    /**
     * Gets the value of the createSessionResult property.
     * 
     */
    public int getCreateSessionResult() {
        return createSessionResult;
    }

    /**
     * Sets the value of the createSessionResult property.
     * 
     */
    public void setCreateSessionResult(int value) {
        this.createSessionResult = value;
    }

}
