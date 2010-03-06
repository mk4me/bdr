
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreatePerformerResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="CreatePerformerResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="CreatePerformerResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "createPerformerResult"
})
@XmlRootElement(name = "CreatePerformerResponse")
public class CreatePerformerResponse {

    @XmlElement(name = "CreatePerformerResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
    protected int createPerformerResult;

    /**
     * Gets the value of the createPerformerResult property.
     * 
     */
    public int getCreatePerformerResult() {
        return createPerformerResult;
    }

    /**
     * Sets the value of the createPerformerResult property.
     * 
     */
    public void setCreatePerformerResult(int value) {
        this.createPerformerResult = value;
    }

}
