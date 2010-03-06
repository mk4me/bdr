
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateTrial element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="CreateTrial">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="trialData" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService}TrialData"/>
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
    "sessionID",
    "trialData"
})
@XmlRootElement(name = "CreateTrial")
public class CreateTrial {

    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int sessionID;
    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", required = true)
    protected TrialData trialData;

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
     * Gets the value of the trialData property.
     * 
     * @return
     *     possible object is
     *     {@link TrialData }
     *     
     */
    public TrialData getTrialData() {
        return trialData;
    }

    /**
     * Sets the value of the trialData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrialData }
     *     
     */
    public void setTrialData(TrialData value) {
        this.trialData = value;
    }

}
