
package motion.database.ws.basicQueriesService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import motion.database.ws.basicQueriesService.SessionTrialWithAttributesList.TrialDetailsWithAttributes;


/**
 * <p>Java class for SessionTrialWithAttributesList element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SessionTrialWithAttributesList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="TrialDetailsWithAttributes" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="TrialID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                     &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                     &lt;element name="TrialDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "trialDetailsWithAttributes"
})
@XmlRootElement(name = "SessionTrialWithAttributesList")
public class SessionTrialWithAttributesList {

    @XmlElement(name = "TrialDetailsWithAttributes", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<TrialDetailsWithAttributes> trialDetailsWithAttributes;

    /**
     * Gets the value of the trialDetailsWithAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trialDetailsWithAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrialDetailsWithAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrialDetailsWithAttributes }
     * 
     * 
     */
    public List<TrialDetailsWithAttributes> getTrialDetailsWithAttributes() {
        if (trialDetailsWithAttributes == null) {
            trialDetailsWithAttributes = new ArrayList<TrialDetailsWithAttributes>();
        }
        return this.trialDetailsWithAttributes;
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
     *         &lt;element name="TrialID" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="SessionID" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="TrialDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
        "trialID",
        "sessionID",
        "trialDescription",
        "duration",
        "attributes"
    })
    public static class TrialDetailsWithAttributes {

        @XmlElement(name = "TrialID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        protected int trialID;
        @XmlElement(name = "SessionID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        protected int sessionID;
        @XmlElement(name = "TrialDescription", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected String trialDescription;
        @XmlElement(name = "Duration", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        protected int duration;
        @XmlElement(name = "Attributes", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        protected Attributes attributes;

        /**
         * Gets the value of the trialID property.
         * 
         */
        public int getTrialID() {
            return trialID;
        }

        /**
         * Sets the value of the trialID property.
         * 
         */
        public void setTrialID(int value) {
            this.trialID = value;
        }

        /**
         * Gets the value of the sessionID property.
         * 
         */
        public int getSessionID() {
            return sessionID;
        }

        /**
         * Sets the value of the sessionID property.
         * 
         */
        public void setSessionID(int value) {
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
         * Gets the value of the duration property.
         * 
         */
        public int getDuration() {
            return duration;
        }

        /**
         * Sets the value of the duration property.
         * 
         */
        public void setDuration(int value) {
            this.duration = value;
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
