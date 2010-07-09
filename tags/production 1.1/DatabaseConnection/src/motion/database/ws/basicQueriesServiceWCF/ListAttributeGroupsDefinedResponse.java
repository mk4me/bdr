
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
 *         &lt;element name="ListAttributeGroupsDefinedResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}AttributeGroupDefinitionList"/>
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
    "listAttributeGroupsDefinedResult"
})
@XmlRootElement(name = "ListAttributeGroupsDefinedResponse")
public class ListAttributeGroupsDefinedResponse {

    @XmlElement(name = "ListAttributeGroupsDefinedResult")
    protected ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult listAttributeGroupsDefinedResult;

    /**
     * Gets the value of the listAttributeGroupsDefinedResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult }
     *     
     */
    public ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult getListAttributeGroupsDefinedResult() {
        return listAttributeGroupsDefinedResult;
    }

    /**
     * Sets the value of the listAttributeGroupsDefinedResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult }
     *     
     */
    public void setListAttributeGroupsDefinedResult(ListAttributeGroupsDefinedResponse.ListAttributeGroupsDefinedResult value) {
        this.listAttributeGroupsDefinedResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}AttributeGroupDefinitionList"/>
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
        "attributeGroupDefinitionList"
    })
    public static class ListAttributeGroupsDefinedResult {

        @XmlElement(name = "AttributeGroupDefinitionList", required = true)
        protected AttributeGroupDefinitionList attributeGroupDefinitionList;

        /**
         * Gets the value of the attributeGroupDefinitionList property.
         * 
         * @return
         *     possible object is
         *     {@link AttributeGroupDefinitionList }
         *     
         */
        public AttributeGroupDefinitionList getAttributeGroupDefinitionList() {
            return attributeGroupDefinitionList;
        }

        /**
         * Sets the value of the attributeGroupDefinitionList property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttributeGroupDefinitionList }
         *     
         */
        public void setAttributeGroupDefinitionList(AttributeGroupDefinitionList value) {
            this.attributeGroupDefinitionList = value;
        }

    }

}
