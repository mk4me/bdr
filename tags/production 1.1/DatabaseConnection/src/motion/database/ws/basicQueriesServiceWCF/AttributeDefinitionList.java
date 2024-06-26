
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
 *         &lt;element name="AttributeDefinition" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="AttributeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="AttributeEnum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="AttributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "attributeDefinition"
})
@XmlRootElement(name = "AttributeDefinitionList")
public class AttributeDefinitionList {

    @XmlElement(name = "AttributeDefinition")
    protected List<AttributeDefinitionList.AttributeDefinition> attributeDefinition;

    /**
     * Gets the value of the attributeDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeDefinitionList.AttributeDefinition }
     * 
     * 
     */
    public List<AttributeDefinitionList.AttributeDefinition> getAttributeDefinition() {
        if (attributeDefinition == null) {
            attributeDefinition = new ArrayList<AttributeDefinitionList.AttributeDefinition>();
        }
        return this.attributeDefinition;
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
     *         &lt;element name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="AttributeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="AttributeEnum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="AttributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "attributeName",
        "attributeType",
        "attributeEnum",
        "attributeGroupName"
    })
    public static class AttributeDefinition {

        @XmlElement(name = "AttributeName", required = true)
        protected String attributeName;
        @XmlElement(name = "AttributeType", required = true)
        protected String attributeType;
        @XmlElement(name = "AttributeEnum")
        protected Integer attributeEnum;
        @XmlElement(name = "AttributeGroupName", required = true)
        protected String attributeGroupName;

        /**
         * Gets the value of the attributeName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttributeName() {
            return attributeName;
        }

        /**
         * Sets the value of the attributeName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttributeName(String value) {
            this.attributeName = value;
        }

        /**
         * Gets the value of the attributeType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttributeType() {
            return attributeType;
        }

        /**
         * Sets the value of the attributeType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttributeType(String value) {
            this.attributeType = value;
        }

        /**
         * Gets the value of the attributeEnum property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getAttributeEnum() {
            return attributeEnum;
        }

        /**
         * Sets the value of the attributeEnum property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setAttributeEnum(Integer value) {
            this.attributeEnum = value;
        }

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

    }

}
