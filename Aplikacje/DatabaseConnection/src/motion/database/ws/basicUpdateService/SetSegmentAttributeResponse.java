
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetSegmentAttributeResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SetSegmentAttributeResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SetSegmentAttributeResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "setSegmentAttributeResult"
})
@XmlRootElement(name = "SetSegmentAttributeResponse")
public class SetSegmentAttributeResponse {

    @XmlElement(name = "SetSegmentAttributeResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int setSegmentAttributeResult;

    /**
     * Gets the value of the setSegmentAttributeResult property.
     * 
     */
    public int getSetSegmentAttributeResult() {
        return setSegmentAttributeResult;
    }

    /**
     * Sets the value of the setSegmentAttributeResult property.
     * 
     */
    public void setSetSegmentAttributeResult(int value) {
        this.setSegmentAttributeResult = value;
    }

}
