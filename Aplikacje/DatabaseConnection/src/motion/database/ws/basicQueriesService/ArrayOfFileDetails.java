
package motion.database.ws.basicQueriesService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFileDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFileDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FileDetails" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileDetails" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFileDetails", propOrder = {
    "fileDetails"
})
public class ArrayOfFileDetails {

    @XmlElement(name = "FileDetails", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<FileDetails> fileDetails;

    /**
     * Gets the value of the fileDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileDetails }
     * 
     * 
     */
    public List<FileDetails> getFileDetails() {
        if (fileDetails == null) {
            fileDetails = new ArrayList<FileDetails>();
        }
        return this.fileDetails;
    }

}
