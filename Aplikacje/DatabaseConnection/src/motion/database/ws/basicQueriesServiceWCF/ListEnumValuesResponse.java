
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
 *         &lt;element name="ListEnumValuesResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}EnumValueList"/>
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
    "listEnumValuesResult"
})
@XmlRootElement(name = "ListEnumValuesResponse")
public class ListEnumValuesResponse {

    @XmlElement(name = "ListEnumValuesResult")
    protected ListEnumValuesResponse.ListEnumValuesResult listEnumValuesResult;

    /**
     * Gets the value of the listEnumValuesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListEnumValuesResponse.ListEnumValuesResult }
     *     
     */
    public ListEnumValuesResponse.ListEnumValuesResult getListEnumValuesResult() {
        return listEnumValuesResult;
    }

    /**
     * Sets the value of the listEnumValuesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListEnumValuesResponse.ListEnumValuesResult }
     *     
     */
    public void setListEnumValuesResult(ListEnumValuesResponse.ListEnumValuesResult value) {
        this.listEnumValuesResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}EnumValueList"/>
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
        "enumValueList"
    })
    public static class ListEnumValuesResult {

        @XmlElement(name = "EnumValueList", required = true)
        protected EnumValueList enumValueList;

        /**
         * Gets the value of the enumValueList property.
         * 
         * @return
         *     possible object is
         *     {@link EnumValueList }
         *     
         */
        public EnumValueList getEnumValueList() {
            return enumValueList;
        }

        /**
         * Sets the value of the enumValueList property.
         * 
         * @param value
         *     allowed object is
         *     {@link EnumValueList }
         *     
         */
        public void setEnumValueList(EnumValueList value) {
            this.enumValueList = value;
        }

    }

}
