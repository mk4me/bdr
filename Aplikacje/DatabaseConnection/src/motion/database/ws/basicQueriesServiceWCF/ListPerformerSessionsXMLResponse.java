
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
 *         &lt;element name="ListPerformerSessionsXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerSessionList"/>
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
    "listPerformerSessionsXMLResult"
})
@XmlRootElement(name = "ListPerformerSessionsXMLResponse")
public class ListPerformerSessionsXMLResponse {

    @XmlElement(name = "ListPerformerSessionsXMLResult")
    protected ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult listPerformerSessionsXMLResult;

    /**
     * Gets the value of the listPerformerSessionsXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult }
     *     
     */
    public ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult getListPerformerSessionsXMLResult() {
        return listPerformerSessionsXMLResult;
    }

    /**
     * Sets the value of the listPerformerSessionsXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult }
     *     
     */
    public void setListPerformerSessionsXMLResult(ListPerformerSessionsXMLResponse.ListPerformerSessionsXMLResult value) {
        this.listPerformerSessionsXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}PerformerSessionList"/>
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
        "performerSessionList"
    })
    public static class ListPerformerSessionsXMLResult {

        @XmlElement(name = "PerformerSessionList", required = true)
        protected PerformerSessionList performerSessionList;

        /**
         * Gets the value of the performerSessionList property.
         * 
         * @return
         *     possible object is
         *     {@link PerformerSessionList }
         *     
         */
        public PerformerSessionList getPerformerSessionList() {
            return performerSessionList;
        }

        /**
         * Sets the value of the performerSessionList property.
         * 
         * @param value
         *     allowed object is
         *     {@link PerformerSessionList }
         *     
         */
        public void setPerformerSessionList(PerformerSessionList value) {
            this.performerSessionList = value;
        }

    }

}
