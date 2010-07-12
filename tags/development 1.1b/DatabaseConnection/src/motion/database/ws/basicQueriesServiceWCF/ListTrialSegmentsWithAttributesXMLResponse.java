
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
 *         &lt;element name="ListTrialSegmentsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrailSegmentWithAttributesList"/>
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
    "listTrialSegmentsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListTrialSegmentsWithAttributesXMLResponse")
public class ListTrialSegmentsWithAttributesXMLResponse {

    @XmlElement(name = "ListTrialSegmentsWithAttributesXMLResult")
    protected ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult listTrialSegmentsWithAttributesXMLResult;

    /**
     * Gets the value of the listTrialSegmentsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult }
     *     
     */
    public ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult getListTrialSegmentsWithAttributesXMLResult() {
        return listTrialSegmentsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listTrialSegmentsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult }
     *     
     */
    public void setListTrialSegmentsWithAttributesXMLResult(ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult value) {
        this.listTrialSegmentsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrailSegmentWithAttributesList"/>
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
        "trailSegmentWithAttributesList"
    })
    public static class ListTrialSegmentsWithAttributesXMLResult {

        @XmlElement(name = "TrailSegmentWithAttributesList", required = true)
        protected TrailSegmentWithAttributesList trailSegmentWithAttributesList;

        /**
         * Gets the value of the trailSegmentWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link TrailSegmentWithAttributesList }
         *     
         */
        public TrailSegmentWithAttributesList getTrailSegmentWithAttributesList() {
            return trailSegmentWithAttributesList;
        }

        /**
         * Sets the value of the trailSegmentWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link TrailSegmentWithAttributesList }
         *     
         */
        public void setTrailSegmentWithAttributesList(TrailSegmentWithAttributesList value) {
            this.trailSegmentWithAttributesList = value;
        }

    }

}
