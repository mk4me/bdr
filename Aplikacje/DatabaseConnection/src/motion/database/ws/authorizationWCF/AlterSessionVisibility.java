
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
 *         &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idPublic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isWritable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "idPublic",
    "isWritable"
})
@XmlRootElement(name = "AlterSessionVisibility")
public class AlterSessionVisibility {

    protected int sessionID;
    protected boolean idPublic;
    protected boolean isWritable;

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
     * Gets the value of the idPublic property.
     * 
     */
    public boolean isIdPublic() {
        return idPublic;
    }

    /**
     * Sets the value of the idPublic property.
     * 
     */
    public void setIdPublic(boolean value) {
        this.idPublic = value;
    }

    /**
     * Gets the value of the isWritable property.
     * 
     */
    public boolean isIsWritable() {
        return isWritable;
    }

    /**
     * Sets the value of the isWritable property.
     * 
     */
    public void setIsWritable(boolean value) {
        this.isWritable = value;
    }

}
