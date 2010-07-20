
package motion.database.ws.UserPersonalSpaceWCF;

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
 *         &lt;element name="ListBasketTrialsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketTrialWithAttributesList"/>
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
    "listBasketTrialsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListBasketTrialsWithAttributesXMLResponse")
public class ListBasketTrialsWithAttributesXMLResponse {

    @XmlElement(name = "ListBasketTrialsWithAttributesXMLResult")
    protected ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult listBasketTrialsWithAttributesXMLResult;

    /**
     * Gets the value of the listBasketTrialsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult }
     *     
     */
    public ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult getListBasketTrialsWithAttributesXMLResult() {
        return listBasketTrialsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listBasketTrialsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult }
     *     
     */
    public void setListBasketTrialsWithAttributesXMLResult(ListBasketTrialsWithAttributesXMLResponse.ListBasketTrialsWithAttributesXMLResult value) {
        this.listBasketTrialsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketTrialWithAttributesList"/>
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
        "basketTrialWithAttributesList"
    })
    public static class ListBasketTrialsWithAttributesXMLResult {

        @XmlElement(name = "BasketTrialWithAttributesList", required = true)
        protected BasketTrialWithAttributesList basketTrialWithAttributesList;

        /**
         * Gets the value of the basketTrialWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link BasketTrialWithAttributesList }
         *     
         */
        public BasketTrialWithAttributesList getBasketTrialWithAttributesList() {
            return basketTrialWithAttributesList;
        }

        /**
         * Sets the value of the basketTrialWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link BasketTrialWithAttributesList }
         *     
         */
        public void setBasketTrialWithAttributesList(BasketTrialWithAttributesList value) {
            this.basketTrialWithAttributesList = value;
        }

    }

}
