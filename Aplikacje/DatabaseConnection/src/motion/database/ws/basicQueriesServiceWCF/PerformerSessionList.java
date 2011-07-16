
package motion.database.ws.basicQueriesServiceWCF;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="SessionDetails" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="UserID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="LabID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="MotionKind" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SessionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                 &lt;attribute name="SessionName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Tags" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SessionDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SessionLabel" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "sessionDetails"
})
@XmlRootElement(name = "PerformerSessionList")
public class PerformerSessionList {

    @XmlElement(name = "SessionDetails")
    protected List<PerformerSessionList.SessionDetails> sessionDetails;

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
     * {@link PerformerSessionList.SessionDetails }
     * 
     * 
     */
    public List<PerformerSessionList.SessionDetails> getSessionDetails() {
        if (sessionDetails == null) {
            sessionDetails = new ArrayList<PerformerSessionList.SessionDetails>();
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
     *       &lt;attribute name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="UserID" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="LabID" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="MotionKind" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="SessionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       &lt;attribute name="SessionName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Tags" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="SessionDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="SessionLabel" type="{http://www.w3.org/2001/XMLSchema}string" />
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

        @XmlAttribute(name = "SessionID")
        protected Integer sessionID;
        @XmlAttribute(name = "UserID")
        protected Integer userID;
        @XmlAttribute(name = "LabID")
        protected Integer labID;
        @XmlAttribute(name = "MotionKind")
        protected String motionKind;
        @XmlAttribute(name = "SessionDate")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar sessionDate;
        @XmlAttribute(name = "SessionName")
        protected String sessionName;
        @XmlAttribute(name = "Tags")
        protected String tags;
        @XmlAttribute(name = "SessionDescription")
        protected String sessionDescription;
        @XmlAttribute(name = "SessionLabel")
        protected String sessionLabel;

        /**
         * Gets the value of the sessionID property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getSessionID() {
            return sessionID;
        }

        /**
         * Sets the value of the sessionID property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setSessionID(Integer value) {
            this.sessionID = value;
        }

        /**
         * Gets the value of the userID property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getUserID() {
            return userID;
        }

        /**
         * Sets the value of the userID property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setUserID(Integer value) {
            this.userID = value;
        }

        /**
         * Gets the value of the labID property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getLabID() {
            return labID;
        }

        /**
         * Sets the value of the labID property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setLabID(Integer value) {
            this.labID = value;
        }

        /**
         * Gets the value of the motionKind property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMotionKind() {
            return motionKind;
        }

        /**
         * Sets the value of the motionKind property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMotionKind(String value) {
            this.motionKind = value;
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
         * Gets the value of the sessionName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSessionName() {
            return sessionName;
        }

        /**
         * Sets the value of the sessionName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSessionName(String value) {
            this.sessionName = value;
        }

        /**
         * Gets the value of the tags property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTags() {
            return tags;
        }

        /**
         * Sets the value of the tags property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTags(String value) {
            this.tags = value;
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
         * Gets the value of the sessionLabel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSessionLabel() {
            return sessionLabel;
        }

        /**
         * Sets the value of the sessionLabel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSessionLabel(String value) {
            this.sessionLabel = value;
        }

    }

}
