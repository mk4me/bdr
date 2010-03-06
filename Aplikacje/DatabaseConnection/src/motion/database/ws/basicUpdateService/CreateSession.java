
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateSession element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="CreateSession">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="performerID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="sessionGroupIDs" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService}ArrayOfInt" minOccurs="0"/>
 *           &lt;element name="sessionData" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService}SessionData"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "performerID",
    "sessionGroupIDs",
    "sessionData"
})
@XmlRootElement(name = "CreateSession")
public class CreateSession {

    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int performerID;
    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected ArrayOfInt sessionGroupIDs;
    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", required = true)
    protected SessionData sessionData;

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

    /**
     * Gets the value of the sessionData property.
     * 
     * @return
     *     possible object is
     *     {@link SessionData }
     *     
     */
    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * Sets the value of the sessionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionData }
     *     
     */
    public void setSessionData(SessionData value) {
        this.sessionData = value;
    }

}
