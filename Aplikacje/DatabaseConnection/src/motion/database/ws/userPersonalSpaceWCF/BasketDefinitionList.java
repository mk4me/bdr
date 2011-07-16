
package motion.database.ws.userPersonalSpaceWCF;

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
 *         &lt;element name="BasketDefinition" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BasketName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "basketDefinition"
})
@XmlRootElement(name = "BasketDefinitionList")
public class BasketDefinitionList {

    @XmlElement(name = "BasketDefinition")
    protected List<BasketDefinitionList.BasketDefinition> basketDefinition;

    /**
     * Gets the value of the basketDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the basketDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBasketDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BasketDefinitionList.BasketDefinition }
     * 
     * 
     */
    public List<BasketDefinitionList.BasketDefinition> getBasketDefinition() {
        if (basketDefinition == null) {
            basketDefinition = new ArrayList<BasketDefinitionList.BasketDefinition>();
        }
        return this.basketDefinition;
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
     *         &lt;element name="BasketName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "basketName"
    })
    public static class BasketDefinition {

        @XmlElement(name = "BasketName", required = true)
        protected String basketName;

        /**
         * Gets the value of the basketName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBasketName() {
            return basketName;
        }

        /**
         * Sets the value of the basketName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBasketName(String value) {
            this.basketName = value;
        }

    }

}
