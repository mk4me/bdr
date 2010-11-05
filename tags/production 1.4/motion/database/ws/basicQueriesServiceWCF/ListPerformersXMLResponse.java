
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
 *         &lt;element name="ListPerformersXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerList"/>
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
    "listPerformersXMLResult"
})
@XmlRootElement(name = "ListPerformersXMLResponse")
public class ListPerformersXMLResponse {

    @XmlElement(name = "ListPerformersXMLResult")
    protected ListPerformersXMLResponse.ListPerformersXMLResult listPerformersXMLResult;

    /**
     * Gets the value of the listPerformersXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListPerformersXMLResponse.ListPerformersXMLResult }
     *     
     */
    public ListPerformersXMLResponse.ListPerformersXMLResult getListPerformersXMLResult() {
        return listPerformersXMLResult;
    }

    /**
     * Sets the value of the listPerformersXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListPerformersXMLResponse.ListPerformersXMLResult }
     *     
     */
    public void setListPerformersXMLResult(ListPerformersXMLResponse.ListPerformersXMLResult value) {
        this.listPerformersXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerList"/>
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
        "performerList"
    })
    public static class ListPerformersXMLResult {

        @XmlElement(name = "PerformerList", required = true)
        protected PerformerList performerList;

        /**
         * Gets the value of the performerList property.
         * 
         * @return
         *     possible object is
         *     {@link PerformerList }
         *     
         */
        public PerformerList getPerformerList() {
            return performerList;
        }

        /**
         * Sets the value of the performerList property.
         * 
         * @param value
         *     allowed object is
         *     {@link PerformerList }
         *     
         */
        public void setPerformerList(PerformerList value) {
            this.performerList = value;
        }

    }

}
