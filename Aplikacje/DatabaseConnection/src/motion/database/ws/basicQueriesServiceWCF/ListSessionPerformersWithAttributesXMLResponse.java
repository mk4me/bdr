
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
 *         &lt;element name="ListSessionPerformersWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionPerformerWithAttributesList"/>
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
    "listSessionPerformersWithAttributesXMLResult"
})
@XmlRootElement(name = "ListSessionPerformersWithAttributesXMLResponse")
public class ListSessionPerformersWithAttributesXMLResponse {

    @XmlElement(name = "ListSessionPerformersWithAttributesXMLResult")
    protected ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult listSessionPerformersWithAttributesXMLResult;

    /**
     * Gets the value of the listSessionPerformersWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult }
     *     
     */
    public ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult getListSessionPerformersWithAttributesXMLResult() {
        return listSessionPerformersWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listSessionPerformersWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult }
     *     
     */
    public void setListSessionPerformersWithAttributesXMLResult(ListSessionPerformersWithAttributesXMLResponse.ListSessionPerformersWithAttributesXMLResult value) {
        this.listSessionPerformersWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionPerformerWithAttributesList"/>
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
        "sessionPerformerWithAttributesList"
    })
    public static class ListSessionPerformersWithAttributesXMLResult {

        @XmlElement(name = "SessionPerformerWithAttributesList", required = true)
        protected SessionPerformerWithAttributesList sessionPerformerWithAttributesList;

        /**
         * Gets the value of the sessionPerformerWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionPerformerWithAttributesList }
         *     
         */
        public SessionPerformerWithAttributesList getSessionPerformerWithAttributesList() {
            return sessionPerformerWithAttributesList;
        }

        /**
         * Sets the value of the sessionPerformerWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionPerformerWithAttributesList }
         *     
         */
        public void setSessionPerformerWithAttributesList(SessionPerformerWithAttributesList value) {
            this.sessionPerformerWithAttributesList = value;
        }

    }

}
