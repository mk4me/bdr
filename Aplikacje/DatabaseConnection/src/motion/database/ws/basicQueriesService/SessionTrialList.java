
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
import motion.database.ws.basicQueriesService.SessionTrialList.TrialDetails;


/**
 * <p>Java class for SessionTrialList element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SessionTrialList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="TrialDetails" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="SessionID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                   &lt;attribute name="TrialDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "trialDetails"
})
@XmlRootElement(name = "SessionTrialList")
public class SessionTrialList {

    @XmlElement(name = "TrialDetails", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<TrialDetails> trialDetails;

    /**
     * Gets the value of the trialDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trialDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrialDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrialDetails }
     * 
     * 
     */
    public List<TrialDetails> getTrialDetails() {
        if (trialDetails == null) {
            trialDetails = new ArrayList<TrialDetails>();
        }
        return this.trialDetails;
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
     *       &lt;attribute name="Duration" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="SessionID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="TrialDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class TrialDetails {

        @XmlAttribute(name = "Duration")
        protected BigInteger duration;
        @XmlAttribute(name = "SessionID")
        protected BigInteger sessionID;
        @XmlAttribute(name = "TrialDescription")
        protected String trialDescription;
        @XmlAttribute(name = "TrialID")
        protected BigInteger trialID;

        /**
         * Gets the value of the duration property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDuration() {
            return duration;
        }

        /**
         * Sets the value of the duration property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDuration(BigInteger value) {
            this.duration = value;
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
         * Gets the value of the trialDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrialDescription() {
            return trialDescription;
        }

        /**
         * Sets the value of the trialDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrialDescription(String value) {
            this.trialDescription = value;
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
