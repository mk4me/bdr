
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
 *         &lt;element name="ListPerformerSessionsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerSessionWithAttributesList"/>
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
    "listPerformerSessionsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListPerformerSessionsWithAttributesXMLResponse")
public class ListPerformerSessionsWithAttributesXMLResponse {

    @XmlElement(name = "ListPerformerSessionsWithAttributesXMLResult")
    protected ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult listPerformerSessionsWithAttributesXMLResult;

    /**
     * Gets the value of the listPerformerSessionsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult }
     *     
     */
    public ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult getListPerformerSessionsWithAttributesXMLResult() {
        return listPerformerSessionsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listPerformerSessionsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult }
     *     
     */
    public void setListPerformerSessionsWithAttributesXMLResult(ListPerformerSessionsWithAttributesXMLResponse.ListPerformerSessionsWithAttributesXMLResult value) {
        this.listPerformerSessionsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerSessionWithAttributesList"/>
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
        "performerSessionWithAttributesList"
    })
    public static class ListPerformerSessionsWithAttributesXMLResult {

        @XmlElement(name = "PerformerSessionWithAttributesList", required = true)
        protected PerformerSessionWithAttributesList performerSessionWithAttributesList;

        /**
         * Gets the value of the performerSessionWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link PerformerSessionWithAttributesList }
         *     
         */
        public PerformerSessionWithAttributesList getPerformerSessionWithAttributesList() {
            return performerSessionWithAttributesList;
        }

        /**
         * Sets the value of the performerSessionWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link PerformerSessionWithAttributesList }
         *     
         */
        public void setPerformerSessionWithAttributesList(PerformerSessionWithAttributesList value) {
            this.performerSessionWithAttributesList = value;
        }

    }

}
