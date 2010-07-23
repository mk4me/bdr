
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
 *         &lt;element name="ListStoredFiltersResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}FilterList"/>
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
    "listStoredFiltersResult"
})
@XmlRootElement(name = "ListStoredFiltersResponse")
public class ListStoredFiltersResponse {

    @XmlElement(name = "ListStoredFiltersResult")
    protected ListStoredFiltersResponse.ListStoredFiltersResult listStoredFiltersResult;

    /**
     * Gets the value of the listStoredFiltersResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListStoredFiltersResponse.ListStoredFiltersResult }
     *     
     */
    public ListStoredFiltersResponse.ListStoredFiltersResult getListStoredFiltersResult() {
        return listStoredFiltersResult;
    }

    /**
     * Sets the value of the listStoredFiltersResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListStoredFiltersResponse.ListStoredFiltersResult }
     *     
     */
    public void setListStoredFiltersResult(ListStoredFiltersResponse.ListStoredFiltersResult value) {
        this.listStoredFiltersResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}FilterList"/>
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
        "filterList"
    })
    public static class ListStoredFiltersResult {

        @XmlElement(name = "FilterList", required = true)
        protected FilterList filterList;

        /**
         * Gets the value of the filterList property.
         * 
         * @return
         *     possible object is
         *     {@link FilterList }
         *     
         */
        public FilterList getFilterList() {
            return filterList;
        }

        /**
         * Sets the value of the filterList property.
         * 
         * @param value
         *     allowed object is
         *     {@link FilterList }
         *     
         */
        public void setFilterList(FilterList value) {
            this.filterList = value;
        }

    }

}
