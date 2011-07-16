
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
 *         &lt;element name="GetTrialByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialDetailsWithAttributes"/>
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
    "getTrialByIdXMLResult"
})
@XmlRootElement(name = "GetTrialByIdXMLResponse")
public class GetTrialByIdXMLResponse {

    @XmlElement(name = "GetTrialByIdXMLResult")
    protected GetTrialByIdXMLResponse.GetTrialByIdXMLResult getTrialByIdXMLResult;

    /**
     * Gets the value of the getTrialByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetTrialByIdXMLResponse.GetTrialByIdXMLResult }
     *     
     */
    public GetTrialByIdXMLResponse.GetTrialByIdXMLResult getGetTrialByIdXMLResult() {
        return getTrialByIdXMLResult;
    }

    /**
     * Sets the value of the getTrialByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetTrialByIdXMLResponse.GetTrialByIdXMLResult }
     *     
     */
    public void setGetTrialByIdXMLResult(GetTrialByIdXMLResponse.GetTrialByIdXMLResult value) {
        this.getTrialByIdXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialDetailsWithAttributes"/>
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
        "trialDetailsWithAttributes"
    })
    public static class GetTrialByIdXMLResult {

        @XmlElement(name = "TrialDetailsWithAttributes", required = true)
        protected TrialDetailsWithAttributes trialDetailsWithAttributes;

        /**
         * Gets the value of the trialDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link TrialDetailsWithAttributes }
         *     
         */
        public TrialDetailsWithAttributes getTrialDetailsWithAttributes() {
            return trialDetailsWithAttributes;
        }

        /**
         * Sets the value of the trialDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link TrialDetailsWithAttributes }
         *     
         */
        public void setTrialDetailsWithAttributes(TrialDetailsWithAttributes value) {
            this.trialDetailsWithAttributes = value;
        }

    }

}
