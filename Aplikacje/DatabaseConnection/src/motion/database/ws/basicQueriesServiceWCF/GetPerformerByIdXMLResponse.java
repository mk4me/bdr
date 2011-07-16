
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
 *         &lt;element name="GetPerformerByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerDetailsWithAttributes"/>
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
    "getPerformerByIdXMLResult"
})
@XmlRootElement(name = "GetPerformerByIdXMLResponse")
public class GetPerformerByIdXMLResponse {

    @XmlElement(name = "GetPerformerByIdXMLResult")
    protected GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult getPerformerByIdXMLResult;

    /**
     * Gets the value of the getPerformerByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult }
     *     
     */
    public GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult getGetPerformerByIdXMLResult() {
        return getPerformerByIdXMLResult;
    }

    /**
     * Sets the value of the getPerformerByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult }
     *     
     */
    public void setGetPerformerByIdXMLResult(GetPerformerByIdXMLResponse.GetPerformerByIdXMLResult value) {
        this.getPerformerByIdXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerDetailsWithAttributes"/>
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
        "performerDetailsWithAttributes"
    })
    public static class GetPerformerByIdXMLResult {

        @XmlElement(name = "PerformerDetailsWithAttributes", required = true)
        protected PerformerDetailsWithAttributes performerDetailsWithAttributes;

        /**
         * Gets the value of the performerDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link PerformerDetailsWithAttributes }
         *     
         */
        public PerformerDetailsWithAttributes getPerformerDetailsWithAttributes() {
            return performerDetailsWithAttributes;
        }

        /**
         * Sets the value of the performerDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link PerformerDetailsWithAttributes }
         *     
         */
        public void setPerformerDetailsWithAttributes(PerformerDetailsWithAttributes value) {
            this.performerDetailsWithAttributes = value;
        }

    }

}
