
package motion.database.ws.basicQueriesService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SessionDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SessionDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UserID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LabID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MotionKindID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PerformerID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SessionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SessionDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SessionDetails", propOrder = {
    "sessionID",
    "userID",
    "labID",
    "motionKindID",
    "performerID",
    "sessionDate",
    "sessionDescription"
})
public class SessionDetails {

    @XmlElement(name = "SessionID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int sessionID;
    @XmlElement(name = "UserID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int userID;
    @XmlElement(name = "LabID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int labID;
    @XmlElement(name = "MotionKindID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int motionKindID;
    @XmlElement(name = "PerformerID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int performerID;
    @XmlElement(name = "SessionDate", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected XMLGregorianCalendar sessionDate;
    @XmlElement(name = "SessionDescription", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String sessionDescription;

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
     * Gets the value of the userID property.
     * 
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     */
    public void setUserID(int value) {
        this.userID = value;
    }

    /**
     * Gets the value of the labID property.
     * 
     */
    public int getLabID() {
        return labID;
    }

    /**
     * Sets the value of the labID property.
     * 
     */
    public void setLabID(int value) {
        this.labID = value;
    }

    /**
     * Gets the value of the motionKindID property.
     * 
     */
    public int getMotionKindID() {
        return motionKindID;
    }

    /**
     * Sets the value of the motionKindID property.
     * 
     */
    public void setMotionKindID(int value) {
        this.motionKindID = value;
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
     * Gets the value of the sessionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSessionDate() {
        return sessionDate;
    }

    /**
     * Sets the value of the sessionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSessionDate(XMLGregorianCalendar value) {
        this.sessionDate = value;
    }

    /**
     * Gets the value of the sessionDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionDescription() {
        return sessionDescription;
    }

    /**
     * Sets the value of the sessionDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionDescription(String value) {
        this.sessionDescription = value;
    }

}
