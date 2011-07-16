
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
 *         &lt;element name="isPublic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "isPublic",
    "isWritable"
})
@XmlRootElement(name = "AlterSessionVisibility")
public class AlterSessionVisibility {

    protected int sessionID;
    protected boolean isPublic;
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
     * Gets the value of the isPublic property.
     * 
     */
    public boolean isIsPublic() {
        return isPublic;
    }

    /**
     * Sets the value of the isPublic property.
     * 
     */
    public void setIsPublic(boolean value) {
        this.isPublic = value;
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
