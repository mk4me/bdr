
package motion.database.ws.basicQueriesService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListPerformerSessionsResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ListPerformerSessionsResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ListPerformerSessionsResult" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}ArrayOfPlainSessionDetails" minOccurs="0"/>
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
    "listPerformerSessionsResult"
})
@XmlRootElement(name = "ListPerformerSessionsResponse")
public class ListPerformerSessionsResponse {

    @XmlElement(name = "ListPerformerSessionsResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected ArrayOfPlainSessionDetails listPerformerSessionsResult;

    /**
     * Gets the value of the listPerformerSessionsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPlainSessionDetails }
     *     
     */
    public ArrayOfPlainSessionDetails getListPerformerSessionsResult() {
        return listPerformerSessionsResult;
    }

    /**
     * Sets the value of the listPerformerSessionsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPlainSessionDetails }
     *     
     */
    public void setListPerformerSessionsResult(ArrayOfPlainSessionDetails value) {
        this.listPerformerSessionsResult = value;
    }

}
