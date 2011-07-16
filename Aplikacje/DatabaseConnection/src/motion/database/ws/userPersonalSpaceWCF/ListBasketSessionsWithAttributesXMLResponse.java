
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
 *         &lt;element name="ListBasketSessionsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketSessionWithAttributesList"/>
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
    "listBasketSessionsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListBasketSessionsWithAttributesXMLResponse")
public class ListBasketSessionsWithAttributesXMLResponse {

    @XmlElement(name = "ListBasketSessionsWithAttributesXMLResult")
    protected ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult listBasketSessionsWithAttributesXMLResult;

    /**
     * Gets the value of the listBasketSessionsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult }
     *     
     */
    public ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult getListBasketSessionsWithAttributesXMLResult() {
        return listBasketSessionsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listBasketSessionsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult }
     *     
     */
    public void setListBasketSessionsWithAttributesXMLResult(ListBasketSessionsWithAttributesXMLResponse.ListBasketSessionsWithAttributesXMLResult value) {
        this.listBasketSessionsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketSessionWithAttributesList"/>
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
        "basketSessionWithAttributesList"
    })
    public static class ListBasketSessionsWithAttributesXMLResult {

        @XmlElement(name = "BasketSessionWithAttributesList", required = true)
        protected BasketSessionWithAttributesList basketSessionWithAttributesList;

        /**
         * Gets the value of the basketSessionWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link BasketSessionWithAttributesList }
         *     
         */
        public BasketSessionWithAttributesList getBasketSessionWithAttributesList() {
            return basketSessionWithAttributesList;
        }

        /**
         * Sets the value of the basketSessionWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link BasketSessionWithAttributesList }
         *     
         */
        public void setBasketSessionWithAttributesList(BasketSessionWithAttributesList value) {
            this.basketSessionWithAttributesList = value;
        }

    }

}
