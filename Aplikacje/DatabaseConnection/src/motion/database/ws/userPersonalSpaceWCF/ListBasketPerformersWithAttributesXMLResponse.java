
package motion.database.ws.userPersonalSpaceWCF;

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
 *         &lt;element name="ListBasketPerformersWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketPerformerWithAttributesList"/>
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
    "listBasketPerformersWithAttributesXMLResult"
})
@XmlRootElement(name = "ListBasketPerformersWithAttributesXMLResponse")
public class ListBasketPerformersWithAttributesXMLResponse {

    @XmlElement(name = "ListBasketPerformersWithAttributesXMLResult")
    protected ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult listBasketPerformersWithAttributesXMLResult;

    /**
     * Gets the value of the listBasketPerformersWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult }
     *     
     */
    public ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult getListBasketPerformersWithAttributesXMLResult() {
        return listBasketPerformersWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listBasketPerformersWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult }
     *     
     */
    public void setListBasketPerformersWithAttributesXMLResult(ListBasketPerformersWithAttributesXMLResponse.ListBasketPerformersWithAttributesXMLResult value) {
        this.listBasketPerformersWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketPerformerWithAttributesList"/>
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
        "basketPerformerWithAttributesList"
    })
    public static class ListBasketPerformersWithAttributesXMLResult {

        @XmlElement(name = "BasketPerformerWithAttributesList", required = true)
        protected BasketPerformerWithAttributesList basketPerformerWithAttributesList;

        /**
         * Gets the value of the basketPerformerWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link BasketPerformerWithAttributesList }
         *     
         */
        public BasketPerformerWithAttributesList getBasketPerformerWithAttributesList() {
            return basketPerformerWithAttributesList;
        }

        /**
         * Sets the value of the basketPerformerWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link BasketPerformerWithAttributesList }
         *     
         */
        public void setBasketPerformerWithAttributesList(BasketPerformerWithAttributesList value) {
            this.basketPerformerWithAttributesList = value;
        }

    }

}
