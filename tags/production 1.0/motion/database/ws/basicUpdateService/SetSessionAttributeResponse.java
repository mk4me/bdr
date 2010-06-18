
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetSessionAttributeResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SetSessionAttributeResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SetSessionAttributeResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "setSessionAttributeResult"
})
@XmlRootElement(name = "SetSessionAttributeResponse")
public class SetSessionAttributeResponse {

    @XmlElement(name = "SetSessionAttributeResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int setSessionAttributeResult;

    /**
     * Gets the value of the setSessionAttributeResult property.
     * 
     */
    public int getSetSessionAttributeResult() {
        return setSessionAttributeResult;
    }

    /**
     * Sets the value of the setSessionAttributeResult property.
     * 
     */
    public void setSetSessionAttributeResult(int value) {
        this.setSessionAttributeResult = value;
    }

}
