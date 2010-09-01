
package motion.database.ws.basicQueriesServiceWCF;

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
 *         &lt;element name="sessionGroupID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "sessionGroupID"
})
@XmlRootElement(name = "ListGroupSessionsWithAttributesXML")
public class ListGroupSessionsWithAttributesXML {

    protected int sessionGroupID;

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

}
