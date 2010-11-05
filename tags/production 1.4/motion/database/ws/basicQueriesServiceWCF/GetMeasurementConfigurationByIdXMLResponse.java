
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
 *         &lt;element name="GetMeasurementConfigurationByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfDetailsWithAttributes"/>
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
    "getMeasurementConfigurationByIdXMLResult"
})
@XmlRootElement(name = "GetMeasurementConfigurationByIdXMLResponse")
public class GetMeasurementConfigurationByIdXMLResponse {

    @XmlElement(name = "GetMeasurementConfigurationByIdXMLResult")
    protected GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult getMeasurementConfigurationByIdXMLResult;

    /**
     * Gets the value of the getMeasurementConfigurationByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult }
     *     
     */
    public GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult getGetMeasurementConfigurationByIdXMLResult() {
        return getMeasurementConfigurationByIdXMLResult;
    }

    /**
     * Sets the value of the getMeasurementConfigurationByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult }
     *     
     */
    public void setGetMeasurementConfigurationByIdXMLResult(GetMeasurementConfigurationByIdXMLResponse.GetMeasurementConfigurationByIdXMLResult value) {
        this.getMeasurementConfigurationByIdXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfDetailsWithAttributes"/>
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
        "measurementConfDetailsWithAttributes"
    })
    public static class GetMeasurementConfigurationByIdXMLResult {

        @XmlElement(name = "MeasurementConfDetailsWithAttributes", required = true)
        protected MeasurementConfDetailsWithAttributes measurementConfDetailsWithAttributes;

        /**
         * Gets the value of the measurementConfDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link MeasurementConfDetailsWithAttributes }
         *     
         */
        public MeasurementConfDetailsWithAttributes getMeasurementConfDetailsWithAttributes() {
            return measurementConfDetailsWithAttributes;
        }

        /**
         * Sets the value of the measurementConfDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasurementConfDetailsWithAttributes }
         *     
         */
        public void setMeasurementConfDetailsWithAttributes(MeasurementConfDetailsWithAttributes value) {
            this.measurementConfDetailsWithAttributes = value;
        }

    }

}
