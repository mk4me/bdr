
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
 *         &lt;element name="ListSessionContentsResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionContentList"/>
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
    "listSessionContentsResult"
})
@XmlRootElement(name = "ListSessionContentsResponse")
public class ListSessionContentsResponse {

    @XmlElement(name = "ListSessionContentsResult")
    protected ListSessionContentsResponse.ListSessionContentsResult listSessionContentsResult;

    /**
     * Gets the value of the listSessionContentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionContentsResponse.ListSessionContentsResult }
     *     
     */
    public ListSessionContentsResponse.ListSessionContentsResult getListSessionContentsResult() {
        return listSessionContentsResult;
    }

    /**
     * Sets the value of the listSessionContentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionContentsResponse.ListSessionContentsResult }
     *     
     */
    public void setListSessionContentsResult(ListSessionContentsResponse.ListSessionContentsResult value) {
        this.listSessionContentsResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionContentList"/>
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
        "sessionContentList"
    })
    public static class ListSessionContentsResult {

        @XmlElement(name = "SessionContentList", required = true)
        protected SessionContentList sessionContentList;

        /**
         * Gets the value of the sessionContentList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionContentList }
         *     
         */
        public SessionContentList getSessionContentList() {
            return sessionContentList;
        }

        /**
         * Sets the value of the sessionContentList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionContentList }
         *     
         */
        public void setSessionContentList(SessionContentList value) {
            this.sessionContentList = value;
        }

    }

}
