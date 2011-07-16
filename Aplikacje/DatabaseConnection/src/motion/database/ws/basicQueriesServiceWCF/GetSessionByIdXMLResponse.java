
package motion.database.ws.basicQueriesServiceWCF;

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
 *         &lt;element name="GetSessionByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionDetailsWithAttributes"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "getSessionByIdXMLResult"
})
@XmlRootElement(name = "GetSessionByIdXMLResponse")
public class GetSessionByIdXMLResponse {

    @XmlElement(name = "GetSessionByIdXMLResult")
    protected GetSessionByIdXMLResponse.GetSessionByIdXMLResult getSessionByIdXMLResult;

    /**
     * Gets the value of the getSessionByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSessionByIdXMLResponse.GetSessionByIdXMLResult }
     *     
     */
    public GetSessionByIdXMLResponse.GetSessionByIdXMLResult getGetSessionByIdXMLResult() {
        return getSessionByIdXMLResult;
    }

    /**
     * Sets the value of the getSessionByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSessionByIdXMLResponse.GetSessionByIdXMLResult }
     *     
     */
    public void setGetSessionByIdXMLResult(GetSessionByIdXMLResponse.GetSessionByIdXMLResult value) {
        this.getSessionByIdXMLResult = value;
    }


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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionDetailsWithAttributes"/>
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
        "sessionDetailsWithAttributes"
    })
    public static class GetSessionByIdXMLResult {

        @XmlElement(name = "SessionDetailsWithAttributes", required = true)
        protected SessionDetailsWithAttributes sessionDetailsWithAttributes;

        /**
         * Gets the value of the sessionDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link SessionDetailsWithAttributes }
         *     
         */
        public SessionDetailsWithAttributes getSessionDetailsWithAttributes() {
            return sessionDetailsWithAttributes;
        }

        /**
         * Sets the value of the sessionDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionDetailsWithAttributes }
         *     
         */
        public void setSessionDetailsWithAttributes(SessionDetailsWithAttributes value) {
            this.sessionDetailsWithAttributes = value;
        }

    }

}
