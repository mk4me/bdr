
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
 *         &lt;element name="ListPerformersWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerWithAttributesList"/>
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
    "listPerformersWithAttributesXMLResult"
})
@XmlRootElement(name = "ListPerformersWithAttributesXMLResponse")
public class ListPerformersWithAttributesXMLResponse {

    @XmlElement(name = "ListPerformersWithAttributesXMLResult")
    protected ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult listPerformersWithAttributesXMLResult;

    /**
     * Gets the value of the listPerformersWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult }
     *     
     */
    public ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult getListPerformersWithAttributesXMLResult() {
        return listPerformersWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listPerformersWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult }
     *     
     */
    public void setListPerformersWithAttributesXMLResult(ListPerformersWithAttributesXMLResponse.ListPerformersWithAttributesXMLResult value) {
        this.listPerformersWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerWithAttributesList"/>
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
        "performerWithAttributesList"
    })
    public static class ListPerformersWithAttributesXMLResult {

        @XmlElement(name = "PerformerWithAttributesList", required = true)
        protected PerformerWithAttributesList performerWithAttributesList;

        /**
         * Gets the value of the performerWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link PerformerWithAttributesList }
         *     
         */
        public PerformerWithAttributesList getPerformerWithAttributesList() {
            return performerWithAttributesList;
        }

        /**
         * Sets the value of the performerWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link PerformerWithAttributesList }
         *     
         */
        public void setPerformerWithAttributesList(PerformerWithAttributesList value) {
            this.performerWithAttributesList = value;
        }

    }

}
