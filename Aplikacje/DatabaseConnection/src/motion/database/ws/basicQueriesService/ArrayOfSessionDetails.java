
package motion.database.ws.basicQueriesService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSessionDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSessionDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SessionDetails" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionDetails" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSessionDetails", propOrder = {
    "sessionDetails"
})
public class ArrayOfSessionDetails {

    @XmlElement(name = "SessionDetails", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<SessionDetails> sessionDetails;

    /**
     * Gets the value of the sessionDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sessionDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSessionDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SessionDetails }
     * 
     * 
     */
    public List<SessionDetails> getSessionDetails() {
        if (sessionDetails == null) {
            sessionDetails = new ArrayList<SessionDetails>();
        }
        return this.sessionDetails;
    }

}
