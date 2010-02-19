
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
import javax.xml.datatype.XMLGregorianCalendar;
import motion.database.ws.basicQueriesService.PerformerSessionList.SessionDetails;


/**
 * <p>Java class for PerformerSessionList element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="PerformerSessionList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="SessionDetails" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="LabID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="MotionKindID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="PerformerID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="SessionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                   &lt;attribute name="SessionDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                   &lt;attribute name="SessionID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="UserID" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
    "sessionDetails"
})
@XmlRootElement(name = "PerformerSessionList")
public class PerformerSessionList {

    @XmlElement(name = "SessionDetails", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<SessionDetails> sessionDetails;

    /**
     * Gets the value of the sessionDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sessionDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSessionDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SessionDetails }
     * 
     * 
     */
    public List<SessionDetails> getSessionDetails() {
        if (sessionDetails == null) {
            sessionDetails = new ArrayList<SessionDetails>();
        }
        return this.sessionDetails;
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
     *       &lt;attribute name="LabID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="MotionKindID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="PerformerID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="SessionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       &lt;attribute name="SessionDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="SessionID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="UserID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SessionDetails {

        @XmlAttribute(name = "LabID")
        protected BigInteger labID;
        @XmlAttribute(name = "MotionKindID")
        protected BigInteger motionKindID;
        @XmlAttribute(name = "PerformerID")
        protected BigInteger performerID;
        @XmlAttribute(name = "SessionDate")
        protected XMLGregorianCalendar sessionDate;
        @XmlAttribute(name = "SessionDescription")
        protected String sessionDescription;
        @XmlAttribute(name = "SessionID")
        protected BigInteger sessionID;
        @XmlAttribute(name = "UserID")
        protected BigInteger userID;

        /**
         * Gets the value of the labID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLabID() {
            return labID;
        }

        /**
         * Sets the value of the labID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLabID(BigInteger value) {
            this.labID = value;
        }

        /**
         * Gets the value of the motionKindID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMotionKindID() {
            return motionKindID;
        }

        /**
         * Sets the value of the motionKindID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMotionKindID(BigInteger value) {
            this.motionKindID = value;
        }

        /**
         * Gets the value of the performerID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getPerformerID() {
            return performerID;
        }

        /**
         * Sets the value of the performerID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setPerformerID(BigInteger value) {
            this.performerID = value;
        }

        /**
         * Gets the value of the sessionDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getSessionDate() {
            return sessionDate;
        }

        /**
         * Sets the value of the sessionDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setSessionDate(XMLGregorianCalendar value) {
            this.sessionDate = value;
        }

        /**
         * Gets the value of the sessionDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSessionDescription() {
            return sessionDescription;
        }

        /**
         * Sets the value of the sessionDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSessionDescription(String value) {
            this.sessionDescription = value;
        }

        /**
         * Gets the value of the sessionID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getSessionID() {
            return sessionID;
        }

        /**
         * Sets the value of the sessionID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSessionID(BigInteger value) {
            this.sessionID = value;
        }

        /**
         * Gets the value of the userID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getUserID() {
            return userID;
        }

        /**
         * Sets the value of the userID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setUserID(BigInteger value) {
            this.userID = value;
        }

    }

}
