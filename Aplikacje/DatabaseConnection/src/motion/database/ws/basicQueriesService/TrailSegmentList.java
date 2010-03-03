
package motion.database.ws.basicQueriesService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import motion.database.ws.basicQueriesService.TrailSegmentList.SegmentDetails;


/**
 * <p>Java class for TrailSegmentList element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="TrailSegmentList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SegmentDetails" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="EndTime" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="SegmentID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="SegmentName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;attribute name="StartTime" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="TrialID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "segmentDetails"
})
@XmlRootElement(name = "TrailSegmentList")
public class TrailSegmentList {

    @XmlElement(name = "SegmentDetails", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<SegmentDetails> segmentDetails;

    /**
     * Gets the value of the segmentDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the segmentDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSegmentDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SegmentDetails }
     * 
     * 
     */
    public List<SegmentDetails> getSegmentDetails() {
        if (segmentDetails == null) {
            segmentDetails = new ArrayList<SegmentDetails>();
        }
        return this.segmentDetails;
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
     *       &lt;attribute name="EndTime" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="SegmentID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="SegmentName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="StartTime" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="TrialID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SegmentDetails {

        @XmlAttribute(name = "EndTime")
        protected BigInteger endTime;
        @XmlAttribute(name = "SegmentID")
        protected BigInteger segmentID;
        @XmlAttribute(name = "SegmentName")
        protected String segmentName;
        @XmlAttribute(name = "StartTime")
        protected BigInteger startTime;
        @XmlAttribute(name = "TrialID")
        protected BigInteger trialID;

        /**
         * Gets the value of the endTime property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getEndTime() {
            return endTime;
        }

        /**
         * Sets the value of the endTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setEndTime(BigInteger value) {
            this.endTime = value;
        }

        /**
         * Gets the value of the segmentID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getSegmentID() {
            return segmentID;
        }

        /**
         * Sets the value of the segmentID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSegmentID(BigInteger value) {
            this.segmentID = value;
        }

        /**
         * Gets the value of the segmentName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSegmentName() {
            return segmentName;
        }

        /**
         * Sets the value of the segmentName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSegmentName(String value) {
            this.segmentName = value;
        }

        /**
         * Gets the value of the startTime property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getStartTime() {
            return startTime;
        }

        /**
         * Sets the value of the startTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setStartTime(BigInteger value) {
            this.startTime = value;
        }

        /**
         * Gets the value of the trialID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTrialID() {
            return trialID;
        }

        /**
         * Sets the value of the trialID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTrialID(BigInteger value) {
            this.trialID = value;
        }

    }

}
