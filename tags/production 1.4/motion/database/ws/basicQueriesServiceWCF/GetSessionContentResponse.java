
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
 *         &lt;element name="GetSessionContentResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionContent"/>
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
    "getSessionContentResult"
})
@XmlRootElement(name = "GetSessionContentResponse")
public class GetSessionContentResponse {

    @XmlElement(name = "GetSessionContentResult")
    protected GetSessionContentResponse.GetSessionContentResult getSessionContentResult;

    /**
     * Gets the value of the getSessionContentResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSessionContentResponse.GetSessionContentResult }
     *     
     */
    public GetSessionContentResponse.GetSessionContentResult getGetSessionContentResult() {
        return getSessionContentResult;
    }

    /**
     * Sets the value of the getSessionContentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSessionContentResponse.GetSessionContentResult }
     *     
     */
    public void setGetSessionContentResult(GetSessionContentResponse.GetSessionContentResult value) {
        this.getSessionContentResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionContent"/>
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
        "sessionContent"
    })
    public static class GetSessionContentResult {

        @XmlElement(name = "SessionContent", required = true)
        protected SessionContent sessionContent;

        /**
         * Gets the value of the sessionContent property.
         * 
         * @return
         *     possible object is
         *     {@link SessionContent }
         *     
         */
        public SessionContent getSessionContent() {
            return sessionContent;
        }

        /**
         * Sets the value of the sessionContent property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionContent }
         *     
         */
        public void setSessionContent(SessionContent value) {
            this.sessionContent = value;
        }

    }

}
