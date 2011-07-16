
package motion.database.ws.userPersonalSpaceWCF;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="AttributeGroupViewConfiguration" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AttributeViewList">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AttributeView" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="Show" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="AttributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="DescribedEntity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Show" type="{http://www.w3.org/2001/XMLSchema}int" />
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
    "attributeGroupViewConfiguration"
})
@XmlRootElement(name = "AttributeGroupViewConfigurationList")
public class AttributeGroupViewConfigurationList {

    @XmlElement(name = "AttributeGroupViewConfiguration")
    protected List<AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration> attributeGroupViewConfiguration;

    /**
     * Gets the value of the attributeGroupViewConfiguration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeGroupViewConfiguration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeGroupViewConfiguration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration }
     * 
     * 
     */
    public List<AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration> getAttributeGroupViewConfiguration() {
        if (attributeGroupViewConfiguration == null) {
            attributeGroupViewConfiguration = new ArrayList<AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration>();
        }
        return this.attributeGroupViewConfiguration;
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
     *         &lt;element name="AttributeViewList">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AttributeView" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="Show" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="AttributeGroupName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="DescribedEntity" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Show" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "attributeViewList"
    })
    public static class AttributeGroupViewConfiguration {

        @XmlElement(name = "AttributeViewList", required = true)
        protected AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList attributeViewList;
        @XmlAttribute(name = "AttributeGroupName")
        protected String attributeGroupName;
        @XmlAttribute(name = "DescribedEntity")
        protected String describedEntity;
        @XmlAttribute(name = "Show")
        protected Integer show;

        /**
         * Gets the value of the attributeViewList property.
         * 
         * @return
         *     possible object is
         *     {@link AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList }
         *     
         */
        public AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList getAttributeViewList() {
            return attributeViewList;
        }

        /**
         * Sets the value of the attributeViewList property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList }
         *     
         */
        public void setAttributeViewList(AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList value) {
            this.attributeViewList = value;
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

        /**
         * Gets the value of the show property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getShow() {
            return show;
        }

        /**
         * Sets the value of the show property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setShow(Integer value) {
            this.show = value;
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
         *         &lt;element name="AttributeView" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="Show" type="{http://www.w3.org/2001/XMLSchema}int" />
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
            "attributeView"
        })
        public static class AttributeViewList {

            @XmlElement(name = "AttributeView", required = true)
            protected List<AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList.AttributeView> attributeView;

            /**
             * Gets the value of the attributeView property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the attributeView property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAttributeView().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList.AttributeView }
             * 
             * 
             */
            public List<AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList.AttributeView> getAttributeView() {
                if (attributeView == null) {
                    attributeView = new ArrayList<AttributeGroupViewConfigurationList.AttributeGroupViewConfiguration.AttributeViewList.AttributeView>();
                }
                return this.attributeView;
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
             *       &lt;attribute name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="Show" type="{http://www.w3.org/2001/XMLSchema}int" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class AttributeView {

                @XmlAttribute(name = "AttributeName")
                protected String attributeName;
                @XmlAttribute(name = "Show")
                protected Integer show;

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
                 * Gets the value of the show property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getShow() {
                    return show;
                }

                /**
                 * Sets the value of the show property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setShow(Integer value) {
                    this.show = value;
                }

            }

        }

    }

}
