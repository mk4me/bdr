
package motion.database.ws.basicQueriesService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import motion.database.ws.basicQueriesService.AttributeDefinitionList.AttributeDefinition;


/**
 * <p>Java class for AttributeDefinitionList element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="AttributeDefinitionList">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="AttributeDefinition" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="attributeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="attributeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                     &lt;element name="attributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
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

    @XmlElement(name = "AttributeDefinition", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<AttributeDefinition> attributeDefinition;

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
     * {@link AttributeDefinition }
     * 
     * 
     */
    public List<AttributeDefinition> getAttributeDefinition() {
        if (attributeDefinition == null) {
            attributeDefinition = new ArrayList<AttributeDefinition>();
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
     *         &lt;element name="attributeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attributeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="attributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "attributeGroupName"
    })
    public static class AttributeDefinition {

        @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected String attributeName;
        @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
        protected String attributeType;
        @XmlElement(namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
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
