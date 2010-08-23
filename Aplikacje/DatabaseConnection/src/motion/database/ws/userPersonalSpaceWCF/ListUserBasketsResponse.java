
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
 *         &lt;element name="ListUserBasketsResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketDefinitionList"/>
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
    "listUserBasketsResult"
})
@XmlRootElement(name = "ListUserBasketsResponse")
public class ListUserBasketsResponse {

    @XmlElement(name = "ListUserBasketsResult")
    protected ListUserBasketsResponse.ListUserBasketsResult listUserBasketsResult;

    /**
     * Gets the value of the listUserBasketsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListUserBasketsResponse.ListUserBasketsResult }
     *     
     */
    public ListUserBasketsResponse.ListUserBasketsResult getListUserBasketsResult() {
        return listUserBasketsResult;
    }

    /**
     * Sets the value of the listUserBasketsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListUserBasketsResponse.ListUserBasketsResult }
     *     
     */
    public void setListUserBasketsResult(ListUserBasketsResponse.ListUserBasketsResult value) {
        this.listUserBasketsResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}BasketDefinitionList"/>
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
        "basketDefinitionList"
    })
    public static class ListUserBasketsResult {

        @XmlElement(name = "BasketDefinitionList", required = true)
        protected BasketDefinitionList basketDefinitionList;

        /**
         * Gets the value of the basketDefinitionList property.
         * 
         * @return
         *     possible object is
         *     {@link BasketDefinitionList }
         *     
         */
        public BasketDefinitionList getBasketDefinitionList() {
            return basketDefinitionList;
        }

        /**
         * Sets the value of the basketDefinitionList property.
         * 
         * @param value
         *     allowed object is
         *     {@link BasketDefinitionList }
         *     
         */
        public void setBasketDefinitionList(BasketDefinitionList value) {
            this.basketDefinitionList = value;
        }

    }

}
