
package motion.database.ws.basicQueriesServiceWCF;

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
 *         &lt;element name="AttributeGroupDefinition" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AttributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DescribedEntity" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "attributeGroupDefinition"
})
@XmlRootElement(name = "AttributeGroupDefinitionList")
public class AttributeGroupDefinitionList {

    @XmlElement(name = "AttributeGroupDefinition")
    protected List<AttributeGroupDefinitionList.AttributeGroupDefinition> attributeGroupDefinition;

    /**
     * Gets the value of the attributeGroupDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeGroupDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeGroupDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeGroupDefinitionList.AttributeGroupDefinition }
     * 
     * 
     */
    public List<AttributeGroupDefinitionList.AttributeGroupDefinition> getAttributeGroupDefinition() {
        if (attributeGroupDefinition == null) {
            attributeGroupDefinition = new ArrayList<AttributeGroupDefinitionList.AttributeGroupDefinition>();
        }
        return this.attributeGroupDefinition;
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
     *         &lt;element name="AttributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DescribedEntity" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "attributeGroupName",
        "describedEntity"
    })
    public static class AttributeGroupDefinition {

        @XmlElement(name = "AttributeGroupName", required = true)
        protected String attributeGroupName;
        @XmlElement(name = "DescribedEntity", required = true)
        protected String describedEntity;

        /**
         * Gets the value of the attributeGroupName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttributeGroupName() {
            return attributeGroupName;
        }

        /**
         * Sets the value of the attributeGroupName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttributeGroupName(String value) {
            this.attributeGroupName = value;
        }

        /**
         * Gets the value of the describedEntity property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescribedEntity() {
            return describedEntity;
        }

        /**
         * Sets the value of the describedEntity property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescribedEntity(String value) {
            this.describedEntity = value;
        }

    }

}
