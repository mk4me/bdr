
package motion.database.ws.basicQueriesService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import motion.database.ws.basicQueriesService.TrailSegmentWithAttributesList.SegmentDetailsWithAttributes;


/**
 * <p>Java class for TrailSegmentWithAttributesList element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="TrailSegmentWithAttributesList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SegmentDetailsWithAttributes" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="SegmentID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                     &lt;element name="TrialID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                     &lt;element name="SegmentName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                     &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                     &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}Attributes" minOccurs="0"/>
 *                   &lt;/sequence>
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
    "segmentDetailsWithAttributes"
})
@XmlRootElement(name = "TrailSegmentWithAttributesList")
public class TrailSegmentWithAttributesList {

    @XmlElement(name = "SegmentDetailsWithAttributes", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<SegmentDetailsWithAttributes> segmentDetailsWithAttributes;

    /**
     * Gets the value of the segmentDetailsWithAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the segmentDetailsWithAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSegmentDetailsWithAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SegmentDetailsWithAttributes }
     * 
     * 
     */
    public List<SegmentDetailsWithAttributes> getSegmentDetailsWithAttributes() {
        if (segmentDetailsWithAttributes == null) {
            segmentDetailsWithAttributes = new ArrayList<SegmentDetailsWithAttributes>();
        }
        return this.segmentDetailsWithAttributes;
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
     *         &lt;element name="SegmentID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="TrialID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="SegmentName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}Attributes" minOccurs="0"/>
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
        "segmentID",
        "trialID",
        "segmentName",
        "startTime",
        "endTime",
        "attributes"
    })
    public static class SegmentDetailsWithAttributes {

        @XmlElement(name = "SegmentID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected BigInteger segmentID;
        @XmlElement(name = "TrialID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected BigInteger trialID;
        @XmlElement(name = "SegmentName", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected String segmentName;
        @XmlElement(name = "StartTime", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected BigInteger startTime;
        @XmlElement(name = "EndTime", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected BigInteger endTime;
        @XmlElement(name = "Attributes", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        protected Attributes attributes;

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
         * Gets the value of the attributes property.
         * 
         * @return
         *     possible object is
         *     {@link Attributes }
         *     
         */
        public Attributes getAttributes() {
            return attributes;
        }

        /**
         * Sets the value of the attributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link Attributes }
         *     
         */
        public void setAttributes(Attributes value) {
            this.attributes = value;
        }

    }

}
