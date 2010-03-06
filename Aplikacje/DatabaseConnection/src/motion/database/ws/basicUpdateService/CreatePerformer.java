
package motion.database.ws.basicUpdateService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreatePerformer element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="CreatePerformer">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="performerData" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService}PerformerData"/>
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
    "performerData"
})
@XmlRootElement(name = "CreatePerformer")
public class CreatePerformer {

    @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService", required = true)
    protected PerformerData performerData;

    /**
     * Gets the value of the performerData property.
     * 
     * @return
     *     possible object is
     *     {@link PerformerData }
     *     
     */
    public PerformerData getPerformerData() {
        return performerData;
    }

    /**
     * Sets the value of the performerData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PerformerData }
     *     
     */
    public void setPerformerData(PerformerData value) {
        this.performerData = value;
    }

}
