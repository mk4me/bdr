
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
 *         &lt;element name="ListAttributesDefinedResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}AttributeDefinitionList"/>
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
    "listAttributesDefinedResult"
})
@XmlRootElement(name = "ListAttributesDefinedResponse")
public class ListAttributesDefinedResponse {

    @XmlElement(name = "ListAttributesDefinedResult")
    protected ListAttributesDefinedResponse.ListAttributesDefinedResult listAttributesDefinedResult;

    /**
     * Gets the value of the listAttributesDefinedResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListAttributesDefinedResponse.ListAttributesDefinedResult }
     *     
     */
    public ListAttributesDefinedResponse.ListAttributesDefinedResult getListAttributesDefinedResult() {
        return listAttributesDefinedResult;
    }

    /**
     * Sets the value of the listAttributesDefinedResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListAttributesDefinedResponse.ListAttributesDefinedResult }
     *     
     */
    public void setListAttributesDefinedResult(ListAttributesDefinedResponse.ListAttributesDefinedResult value) {
        this.listAttributesDefinedResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}AttributeDefinitionList"/>
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
        "attributeDefinitionList"
    })
    public static class ListAttributesDefinedResult {

        @XmlElement(name = "AttributeDefinitionList", required = true)
        protected AttributeDefinitionList attributeDefinitionList;

        /**
         * Gets the value of the attributeDefinitionList property.
         * 
         * @return
         *     possible object is
         *     {@link AttributeDefinitionList }
         *     
         */
        public AttributeDefinitionList getAttributeDefinitionList() {
            return attributeDefinitionList;
        }

        /**
         * Sets the value of the attributeDefinitionList property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttributeDefinitionList }
         *     
         */
        public void setAttributeDefinitionList(AttributeDefinitionList value) {
            this.attributeDefinitionList = value;
        }

    }

}
