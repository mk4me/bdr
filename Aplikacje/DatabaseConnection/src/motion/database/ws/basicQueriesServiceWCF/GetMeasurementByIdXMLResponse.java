
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
 *         &lt;element name="GetMeasurementByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementDetailsWithAttributes"/>
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
    "getMeasurementByIdXMLResult"
})
@XmlRootElement(name = "GetMeasurementByIdXMLResponse")
public class GetMeasurementByIdXMLResponse {

    @XmlElement(name = "GetMeasurementByIdXMLResult")
    protected GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult getMeasurementByIdXMLResult;

    /**
     * Gets the value of the getMeasurementByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult }
     *     
     */
    public GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult getGetMeasurementByIdXMLResult() {
        return getMeasurementByIdXMLResult;
    }

    /**
     * Sets the value of the getMeasurementByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult }
     *     
     */
    public void setGetMeasurementByIdXMLResult(GetMeasurementByIdXMLResponse.GetMeasurementByIdXMLResult value) {
        this.getMeasurementByIdXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementDetailsWithAttributes"/>
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
        "measurementDetailsWithAttributes"
    })
    public static class GetMeasurementByIdXMLResult {

        @XmlElement(name = "MeasurementDetailsWithAttributes", required = true)
        protected MeasurementDetailsWithAttributes measurementDetailsWithAttributes;

        /**
         * Gets the value of the measurementDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link MeasurementDetailsWithAttributes }
         *     
         */
        public MeasurementDetailsWithAttributes getMeasurementDetailsWithAttributes() {
            return measurementDetailsWithAttributes;
        }

        /**
         * Sets the value of the measurementDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasurementDetailsWithAttributes }
         *     
         */
        public void setMeasurementDetailsWithAttributes(MeasurementDetailsWithAttributes value) {
            this.measurementDetailsWithAttributes = value;
        }

    }

}
