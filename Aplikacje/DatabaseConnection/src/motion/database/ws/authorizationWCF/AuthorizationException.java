
package motion.database.ws.authorizationWCF;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthorizationException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorizationException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssueKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorizationException", propOrder = {
    "details",
    "issueKind"
})
public class AuthorizationException {

    @XmlElementRef(name = "Details", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", type = JAXBElement.class)
    protected JAXBElement<String> details;
    @XmlElementRef(name = "IssueKind", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", type = JAXBElement.class)
    protected JAXBElement<String> issueKind;

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDetails(JAXBElement<String> value) {
        this.details = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the issueKind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIssueKind() {
        return issueKind;
    }

    /**
     * Sets the value of the issueKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIssueKind(JAXBElement<String> value) {
        this.issueKind = ((JAXBElement<String> ) value);
    }

}
