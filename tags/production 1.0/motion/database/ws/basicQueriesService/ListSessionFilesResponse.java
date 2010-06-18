
package motion.database.ws.basicQueriesService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListSessionFilesResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ListSessionFilesResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ListSessionFilesResult" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}ArrayOfPlainFileDetails" minOccurs="0"/>
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
    "listSessionFilesResult"
})
@XmlRootElement(name = "ListSessionFilesResponse")
public class ListSessionFilesResponse {

    @XmlElement(name = "ListSessionFilesResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected ArrayOfPlainFileDetails listSessionFilesResult;

    /**
     * Gets the value of the listSessionFilesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPlainFileDetails }
     *     
     */
    public ArrayOfPlainFileDetails getListSessionFilesResult() {
        return listSessionFilesResult;
    }

    /**
     * Sets the value of the listSessionFilesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPlainFileDetails }
     *     
     */
    public void setListSessionFilesResult(ArrayOfPlainFileDetails value) {
        this.listSessionFilesResult = value;
    }

}
