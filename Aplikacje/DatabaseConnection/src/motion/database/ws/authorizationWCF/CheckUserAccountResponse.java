
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
 *         &lt;element name="CheckUserAccountResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "checkUserAccountResult"
})
@XmlRootElement(name = "CheckUserAccountResponse")
public class CheckUserAccountResponse {

    @XmlElement(name = "CheckUserAccountResult")
    protected boolean checkUserAccountResult;

    /**
     * Gets the value of the checkUserAccountResult property.
     * 
     */
    public boolean isCheckUserAccountResult() {
        return checkUserAccountResult;
    }

    /**
     * Sets the value of the checkUserAccountResult property.
     * 
     */
    public void setCheckUserAccountResult(boolean value) {
        this.checkUserAccountResult = value;
    }

}
