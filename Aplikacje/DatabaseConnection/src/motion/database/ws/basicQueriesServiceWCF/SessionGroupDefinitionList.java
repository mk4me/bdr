
package motion.database.ws.basicQueriesServiceWCF;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="SessionGroupDefinition" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SessionGroupID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="SessionGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "sessionGroupDefinition"
})
@XmlRootElement(name = "SessionGroupDefinitionList")
public class SessionGroupDefinitionList {

    @XmlElement(name = "SessionGroupDefinition")
    protected List<SessionGroupDefinitionList.SessionGroupDefinition> sessionGroupDefinition;

    /**
     * Gets the value of the sessionGroupDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sessionGroupDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSessionGroupDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SessionGroupDefinitionList.SessionGroupDefinition }
     * 
     * 
     */
    public List<SessionGroupDefinitionList.SessionGroupDefinition> getSessionGroupDefinition() {
        if (sessionGroupDefinition == null) {
            sessionGroupDefinition = new ArrayList<SessionGroupDefinitionList.SessionGroupDefinition>();
        }
        return this.sessionGroupDefinition;
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
     *         &lt;element name="SessionGroupID" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="SessionGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "sessionGroupID",
        "sessionGroupName"
    })
    public static class SessionGroupDefinition {

        @XmlElement(name = "SessionGroupID")
        protected int sessionGroupID;
        @XmlElement(name = "SessionGroupName", required = true)
        protected String sessionGroupName;

        /**
         * Gets the value of the sessionGroupID property.
         * 
         */
        public int getSessionGroupID() {
            return sessionGroupID;
        }

        /**
         * Sets the value of the sessionGroupID property.
         * 
         */
        public void setSessionGroupID(int value) {
            this.sessionGroupID = value;
        }

        /**
         * Gets the value of the sessionGroupName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSessionGroupName() {
            return sessionGroupName;
        }

        /**
         * Sets the value of the sessionGroupName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSessionGroupName(String value) {
            this.sessionGroupName = value;
        }

    }

}
