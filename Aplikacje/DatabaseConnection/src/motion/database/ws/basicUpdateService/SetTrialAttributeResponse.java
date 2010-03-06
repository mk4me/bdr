
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetTrialAttributeResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SetTrialAttributeResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SetTrialAttributeResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "setTrialAttributeResult"
})
@XmlRootElement(name = "SetTrialAttributeResponse")
public class SetTrialAttributeResponse {

    @XmlElement(name = "SetTrialAttributeResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected boolean setTrialAttributeResult;

    /**
     * Gets the value of the setTrialAttributeResult property.
     * 
     */
    public boolean isSetTrialAttributeResult() {
        return setTrialAttributeResult;
    }

    /**
     * Sets the value of the setTrialAttributeResult property.
     * 
     */
    public void setSetTrialAttributeResult(boolean value) {
        this.setTrialAttributeResult = value;
    }

}
