
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
 *         &lt;element name="GetPerformerConfigurationByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerConfDetailsWithAttributes"/>
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
    "getPerformerConfigurationByIdXMLResult"
})
@XmlRootElement(name = "GetPerformerConfigurationByIdXMLResponse")
public class GetPerformerConfigurationByIdXMLResponse {

    @XmlElement(name = "GetPerformerConfigurationByIdXMLResult")
    protected GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult getPerformerConfigurationByIdXMLResult;

    /**
     * Gets the value of the getPerformerConfigurationByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult }
     *     
     */
    public GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult getGetPerformerConfigurationByIdXMLResult() {
        return getPerformerConfigurationByIdXMLResult;
    }

    /**
     * Sets the value of the getPerformerConfigurationByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult }
     *     
     */
    public void setGetPerformerConfigurationByIdXMLResult(GetPerformerConfigurationByIdXMLResponse.GetPerformerConfigurationByIdXMLResult value) {
        this.getPerformerConfigurationByIdXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerConfDetailsWithAttributes"/>
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
        "performerConfDetailsWithAttributes"
    })
    public static class GetPerformerConfigurationByIdXMLResult {

        @XmlElement(name = "PerformerConfDetailsWithAttributes", required = true)
        protected PerformerConfDetailsWithAttributes performerConfDetailsWithAttributes;

        /**
         * Gets the value of the performerConfDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link PerformerConfDetailsWithAttributes }
         *     
         */
        public PerformerConfDetailsWithAttributes getPerformerConfDetailsWithAttributes() {
            return performerConfDetailsWithAttributes;
        }

        /**
         * Sets the value of the performerConfDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link PerformerConfDetailsWithAttributes }
         *     
         */
        public void setPerformerConfDetailsWithAttributes(PerformerConfDetailsWithAttributes value) {
            this.performerConfDetailsWithAttributes = value;
        }

    }

}
