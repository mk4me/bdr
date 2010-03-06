
package motion.database.ws.basicQueriesService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import motion.database.ws.basicQueriesService.PerformerWithAttributesList.PerformerDetailsWithAttributes;


/**
 * <p>Java class for PerformerWithAttributesList element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="PerformerWithAttributesList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="PerformerDetailsWithAttributes" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="PerformerID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                     &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "performerDetailsWithAttributes"
})
@XmlRootElement(name = "PerformerWithAttributesList")
public class PerformerWithAttributesList {

    @XmlElement(name = "PerformerDetailsWithAttributes", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<PerformerDetailsWithAttributes> performerDetailsWithAttributes;

    /**
     * Gets the value of the performerDetailsWithAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the performerDetailsWithAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerformerDetailsWithAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerformerDetailsWithAttributes }
     * 
     * 
     */
    public List<PerformerDetailsWithAttributes> getPerformerDetailsWithAttributes() {
        if (performerDetailsWithAttributes == null) {
            performerDetailsWithAttributes = new ArrayList<PerformerDetailsWithAttributes>();
        }
        return this.performerDetailsWithAttributes;
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
     *         &lt;element name="PerformerID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "performerID",
        "firstName",
        "lastName",
        "attributes"
    })
    public static class PerformerDetailsWithAttributes {

        @XmlElement(name = "PerformerID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected BigInteger performerID;
        @XmlElement(name = "FirstName", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected String firstName;
        @XmlElement(name = "LastName", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected String lastName;
        @XmlElement(name = "Attributes", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
        protected Attributes attributes;

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
         * Gets the value of the firstName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Sets the value of the firstName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirstName(String value) {
            this.firstName = value;
        }

        /**
         * Gets the value of the lastName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Sets the value of the lastName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLastName(String value) {
            this.lastName = value;
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
