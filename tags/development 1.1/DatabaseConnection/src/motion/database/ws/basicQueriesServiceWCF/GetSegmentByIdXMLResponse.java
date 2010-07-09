
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
 *         &lt;element name="GetSegmentByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SegmentDetailsWithAttributes"/>
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
    "getSegmentByIdXMLResult"
})
@XmlRootElement(name = "GetSegmentByIdXMLResponse")
public class GetSegmentByIdXMLResponse {

    @XmlElement(name = "GetSegmentByIdXMLResult")
    protected GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult getSegmentByIdXMLResult;

    /**
     * Gets the value of the getSegmentByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult }
     *     
     */
    public GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult getGetSegmentByIdXMLResult() {
        return getSegmentByIdXMLResult;
    }

    /**
     * Sets the value of the getSegmentByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult }
     *     
     */
    public void setGetSegmentByIdXMLResult(GetSegmentByIdXMLResponse.GetSegmentByIdXMLResult value) {
        this.getSegmentByIdXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SegmentDetailsWithAttributes"/>
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
        "segmentDetailsWithAttributes"
    })
    public static class GetSegmentByIdXMLResult {

        @XmlElement(name = "SegmentDetailsWithAttributes", required = true)
        protected SegmentDetailsWithAttributes segmentDetailsWithAttributes;

        /**
         * Gets the value of the segmentDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link SegmentDetailsWithAttributes }
         *     
         */
        public SegmentDetailsWithAttributes getSegmentDetailsWithAttributes() {
            return segmentDetailsWithAttributes;
        }

        /**
         * Sets the value of the segmentDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link SegmentDetailsWithAttributes }
         *     
         */
        public void setSegmentDetailsWithAttributes(SegmentDetailsWithAttributes value) {
            this.segmentDetailsWithAttributes = value;
        }

    }

}
