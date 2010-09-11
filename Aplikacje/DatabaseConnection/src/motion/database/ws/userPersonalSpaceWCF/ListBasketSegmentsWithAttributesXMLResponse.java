
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
 *         &lt;element MeasurementConfName="ListBasketSegmentsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketSegmentWithAttributesList"/>
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
    "listBasketSegmentsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListBasketSegmentsWithAttributesXMLResponse")
public class ListBasketSegmentsWithAttributesXMLResponse {

    @XmlElement(name = "ListBasketSegmentsWithAttributesXMLResult")
    protected ListBasketSegmentsWithAttributesXMLResponse.ListBasketSegmentsWithAttributesXMLResult listBasketSegmentsWithAttributesXMLResult;

    /**
     * Gets the value of the listBasketSegmentsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListBasketSegmentsWithAttributesXMLResponse.ListBasketSegmentsWithAttributesXMLResult }
     *     
     */
    public ListBasketSegmentsWithAttributesXMLResponse.ListBasketSegmentsWithAttributesXMLResult getListBasketSegmentsWithAttributesXMLResult() {
        return listBasketSegmentsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listBasketSegmentsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListBasketSegmentsWithAttributesXMLResponse.ListBasketSegmentsWithAttributesXMLResult }
     *     
     */
    public void setListBasketSegmentsWithAttributesXMLResult(ListBasketSegmentsWithAttributesXMLResponse.ListBasketSegmentsWithAttributesXMLResult value) {
        this.listBasketSegmentsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketSegmentWithAttributesList"/>
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
        "basketSegmentWithAttributesList"
    })
    public static class ListBasketSegmentsWithAttributesXMLResult {

        @XmlElement(name = "BasketSegmentWithAttributesList", required = true)
        protected BasketSegmentWithAttributesList basketSegmentWithAttributesList;

        /**
         * Gets the value of the basketSegmentWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link BasketSegmentWithAttributesList }
         *     
         */
        public BasketSegmentWithAttributesList getBasketSegmentWithAttributesList() {
            return basketSegmentWithAttributesList;
        }

        /**
         * Sets the value of the basketSegmentWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link BasketSegmentWithAttributesList }
         *     
         */
        public void setBasketSegmentWithAttributesList(BasketSegmentWithAttributesList value) {
            this.basketSegmentWithAttributesList = value;
        }

    }

}
