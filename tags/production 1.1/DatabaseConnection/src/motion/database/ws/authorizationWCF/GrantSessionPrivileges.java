
package motion.database.ws.authorizationWCF;

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
 *         &lt;element name="grantedUserLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grantedUserDomain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="write" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "grantedUserLogin",
    "grantedUserDomain",
    "sessionID",
    "write"
})
@XmlRootElement(name = "GrantSessionPrivileges")
public class GrantSessionPrivileges {

    protected String grantedUserLogin;
    protected String grantedUserDomain;
    protected int sessionID;
    protected boolean write;

    /**
     * Gets the value of the grantedUserLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrantedUserLogin() {
        return grantedUserLogin;
    }

    /**
     * Sets the value of the grantedUserLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrantedUserLogin(String value) {
        this.grantedUserLogin = value;
    }

    /**
     * Gets the value of the grantedUserDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrantedUserDomain() {
        return grantedUserDomain;
    }

    /**
     * Sets the value of the grantedUserDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrantedUserDomain(String value) {
        this.grantedUserDomain = value;
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
     * Gets the value of the write property.
     * 
     */
    public boolean isWrite() {
        return write;
    }

    /**
     * Sets the value of the write property.
     * 
     */
    public void setWrite(boolean value) {
        this.write = value;
    }

}
