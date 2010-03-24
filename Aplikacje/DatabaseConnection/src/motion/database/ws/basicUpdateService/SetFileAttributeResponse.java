
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetFileAttributeResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SetFileAttributeResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SetFileAttributeResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "setFileAttributeResult"
})
@XmlRootElement(name = "SetFileAttributeResponse")
public class SetFileAttributeResponse {

    @XmlElement(name = "SetFileAttributeResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int setFileAttributeResult;

    /**
     * Gets the value of the setFileAttributeResult property.
     * 
     */
    public int getSetFileAttributeResult() {
        return setFileAttributeResult;
    }

    /**
     * Sets the value of the setFileAttributeResult property.
     * 
     */
    public void setSetFileAttributeResult(int value) {
        this.setFileAttributeResult = value;
    }

}
