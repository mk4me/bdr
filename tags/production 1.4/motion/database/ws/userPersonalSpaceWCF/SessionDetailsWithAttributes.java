
package motion.database.ws.userPersonalSpaceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UserID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LabID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MotionKind" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SessionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SessionDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SessionLabel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}Attributes" minOccurs="0"/>
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
    "userID",
    "labID",
    "motionKind",
    "sessionDate",
    "sessionDescription",
    "sessionLabel",
    "attributes"
})
@XmlRootElement(name = "SessionDetailsWithAttributes")
public class SessionDetailsWithAttributes {

    @XmlElement(name = "SessionID")
    protected int sessionID;
    @XmlElement(name = "UserID")
    protected int userID;
    @XmlElement(name = "LabID")
    protected int labID;
    @XmlElement(name = "MotionKind", required = true)
    protected String motionKind;
    @XmlElement(name = "SessionDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sessionDate;
    @XmlElement(name = "SessionDescription", required = true)
    protected String sessionDescription;
    @XmlElement(name = "SessionLabel", required = true)
    protected String sessionLabel;
    @XmlElement(name = "Attributes")
    protected Attributes attributes;

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
     * Gets the value of the motionKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotionKind() {
        return motionKind;
    }

    /**
     * Sets the value of the motionKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotionKind(String value) {
        this.motionKind = value;
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

    /**
     * Gets the value of the sessionLabel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionLabel() {
        return sessionLabel;
    }

    /**
     * Sets the value of the sessionLabel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionLabel(String value) {
        this.sessionLabel = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link Attributes }
     *     
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * Sets the value of the attributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Attributes }
     *     
     */
    public void setAttributes(Attributes value) {
        this.attributes = value;
    }

}
