
package motion.database.ws.basicUpdatesServiceWCF;

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
 *         &lt;element name="labID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="motionKindName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sessionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="sessionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tags" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sessionDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sessionGroupIDs" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService}ArrayOfInt" minOccurs="0"/>
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
    "labID",
    "motionKindName",
    "sessionDate",
    "sessionName",
    "tags",
    "sessionDescription",
    "sessionGroupIDs"
})
@XmlRootElement(name = "CreateSession")
public class CreateSession {

    protected int labID;
    protected String motionKindName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sessionDate;
    protected String sessionName;
    protected String tags;
    protected String sessionDescription;
    protected ArrayOfInt sessionGroupIDs;

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
     * Gets the value of the motionKindName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotionKindName() {
        return motionKindName;
    }

    /**
     * Sets the value of the motionKindName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotionKindName(String value) {
        this.motionKindName = value;
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
     * Gets the value of the sessionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionName() {
        return sessionName;
    }

    /**
     * Sets the value of the sessionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionName(String value) {
        this.sessionName = value;
    }

    /**
     * Gets the value of the tags property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTags() {
        return tags;
    }

    /**
     * Sets the value of the tags property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTags(String value) {
        this.tags = value;
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
     * Gets the value of the sessionGroupIDs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInt }
     *     
     */
    public ArrayOfInt getSessionGroupIDs() {
        return sessionGroupIDs;
    }

    /**
     * Sets the value of the sessionGroupIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInt }
     *     
     */
    public void setSessionGroupIDs(ArrayOfInt value) {
        this.sessionGroupIDs = value;
    }

}
