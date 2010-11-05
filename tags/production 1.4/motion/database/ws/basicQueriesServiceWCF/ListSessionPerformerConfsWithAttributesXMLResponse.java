
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
 *         &lt;element name="ListSessionPerformerConfsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionPerformerConfWithAttributesList"/>
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
    "listSessionPerformerConfsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListSessionPerformerConfsWithAttributesXMLResponse")
public class ListSessionPerformerConfsWithAttributesXMLResponse {

    @XmlElement(name = "ListSessionPerformerConfsWithAttributesXMLResult")
    protected ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult listSessionPerformerConfsWithAttributesXMLResult;

    /**
     * Gets the value of the listSessionPerformerConfsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult }
     *     
     */
    public ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult getListSessionPerformerConfsWithAttributesXMLResult() {
        return listSessionPerformerConfsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listSessionPerformerConfsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult }
     *     
     */
    public void setListSessionPerformerConfsWithAttributesXMLResult(ListSessionPerformerConfsWithAttributesXMLResponse.ListSessionPerformerConfsWithAttributesXMLResult value) {
        this.listSessionPerformerConfsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionPerformerConfWithAttributesList"/>
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
        "sessionPerformerConfWithAttributesList"
    })
    public static class ListSessionPerformerConfsWithAttributesXMLResult {

        @XmlElement(name = "SessionPerformerConfWithAttributesList", required = true)
        protected SessionPerformerConfWithAttributesList sessionPerformerConfWithAttributesList;

        /**
         * Gets the value of the sessionPerformerConfWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionPerformerConfWithAttributesList }
         *     
         */
        public SessionPerformerConfWithAttributesList getSessionPerformerConfWithAttributesList() {
            return sessionPerformerConfWithAttributesList;
        }

        /**
         * Sets the value of the sessionPerformerConfWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionPerformerConfWithAttributesList }
         *     
         */
        public void setSessionPerformerConfWithAttributesList(SessionPerformerConfWithAttributesList value) {
            this.sessionPerformerConfWithAttributesList = value;
        }

    }

}
