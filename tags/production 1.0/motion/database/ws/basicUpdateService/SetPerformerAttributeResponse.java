
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetPerformerAttributeResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SetPerformerAttributeResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SetPerformerAttributeResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "setPerformerAttributeResult"
})
@XmlRootElement(name = "SetPerformerAttributeResponse")
public class SetPerformerAttributeResponse {

    @XmlElement(name = "SetPerformerAttributeResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int setPerformerAttributeResult;

    /**
     * Gets the value of the setPerformerAttributeResult property.
     * 
     */
    public int getSetPerformerAttributeResult() {
        return setPerformerAttributeResult;
    }

    /**
     * Sets the value of the setPerformerAttributeResult property.
     * 
     */
    public void setSetPerformerAttributeResult(int value) {
        this.setPerformerAttributeResult = value;
    }

}
