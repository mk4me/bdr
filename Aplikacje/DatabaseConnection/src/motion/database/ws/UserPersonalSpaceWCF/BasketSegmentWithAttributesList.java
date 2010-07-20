
package motion.database.ws.UserPersonalSpaceWCF;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}SegmentDetailsWithAttributes" maxOccurs="unbounded" minOccurs="0"/>
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
    "segmentDetailsWithAttributes"
})
@XmlRootElement(name = "BasketSegmentWithAttributesList")
public class BasketSegmentWithAttributesList {

    @XmlElement(name = "SegmentDetailsWithAttributes")
    protected List<SegmentDetailsWithAttributes> segmentDetailsWithAttributes;

    /**
     * Gets the value of the segmentDetailsWithAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the segmentDetailsWithAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSegmentDetailsWithAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SegmentDetailsWithAttributes }
     * 
     * 
     */
    public List<SegmentDetailsWithAttributes> getSegmentDetailsWithAttributes() {
        if (segmentDetailsWithAttributes == null) {
            segmentDetailsWithAttributes = new ArrayList<SegmentDetailsWithAttributes>();
        }
        return this.segmentDetailsWithAttributes;
    }

}
