
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
 *         &lt;element name="ListViewConfigurationResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}AttributeGroupViewConfigurationList"/>
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
    "listViewConfigurationResult"
})
@XmlRootElement(name = "ListViewConfigurationResponse")
public class ListViewConfigurationResponse {

    @XmlElement(name = "ListViewConfigurationResult")
    protected ListViewConfigurationResponse.ListViewConfigurationResult listViewConfigurationResult;

    /**
     * Gets the value of the listViewConfigurationResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListViewConfigurationResponse.ListViewConfigurationResult }
     *     
     */
    public ListViewConfigurationResponse.ListViewConfigurationResult getListViewConfigurationResult() {
        return listViewConfigurationResult;
    }

    /**
     * Sets the value of the listViewConfigurationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListViewConfigurationResponse.ListViewConfigurationResult }
     *     
     */
    public void setListViewConfigurationResult(ListViewConfigurationResponse.ListViewConfigurationResult value) {
        this.listViewConfigurationResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}AttributeGroupViewConfigurationList"/>
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
        "attributeGroupViewConfigurationList"
    })
    public static class ListViewConfigurationResult {

        @XmlElement(name = "AttributeGroupViewConfigurationList", required = true)
        protected AttributeGroupViewConfigurationList attributeGroupViewConfigurationList;

        /**
         * Gets the value of the attributeGroupViewConfigurationList property.
         * 
         * @return
         *     possible object is
         *     {@link AttributeGroupViewConfigurationList }
         *     
         */
        public AttributeGroupViewConfigurationList getAttributeGroupViewConfigurationList() {
            return attributeGroupViewConfigurationList;
        }

        /**
         * Sets the value of the attributeGroupViewConfigurationList property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttributeGroupViewConfigurationList }
         *     
         */
        public void setAttributeGroupViewConfigurationList(AttributeGroupViewConfigurationList value) {
            this.attributeGroupViewConfigurationList = value;
        }

    }

}
