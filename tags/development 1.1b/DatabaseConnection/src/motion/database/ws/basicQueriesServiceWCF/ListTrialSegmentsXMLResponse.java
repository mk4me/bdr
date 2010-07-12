
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
 *         &lt;element name="ListTrialSegmentsXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrailSegmentList"/>
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
    "listTrialSegmentsXMLResult"
})
@XmlRootElement(name = "ListTrialSegmentsXMLResponse")
public class ListTrialSegmentsXMLResponse {

    @XmlElement(name = "ListTrialSegmentsXMLResult")
    protected ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult listTrialSegmentsXMLResult;

    /**
     * Gets the value of the listTrialSegmentsXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult }
     *     
     */
    public ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult getListTrialSegmentsXMLResult() {
        return listTrialSegmentsXMLResult;
    }

    /**
     * Sets the value of the listTrialSegmentsXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult }
     *     
     */
    public void setListTrialSegmentsXMLResult(ListTrialSegmentsXMLResponse.ListTrialSegmentsXMLResult value) {
        this.listTrialSegmentsXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrailSegmentList"/>
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
        "trailSegmentList"
    })
    public static class ListTrialSegmentsXMLResult {

        @XmlElement(name = "TrailSegmentList", required = true)
        protected TrailSegmentList trailSegmentList;

        /**
         * Gets the value of the trailSegmentList property.
         * 
         * @return
         *     possible object is
         *     {@link TrailSegmentList }
         *     
         */
        public TrailSegmentList getTrailSegmentList() {
            return trailSegmentList;
        }

        /**
         * Sets the value of the trailSegmentList property.
         * 
         * @param value
         *     allowed object is
         *     {@link TrailSegmentList }
         *     
         */
        public void setTrailSegmentList(TrailSegmentList value) {
            this.trailSegmentList = value;
        }

    }

}
