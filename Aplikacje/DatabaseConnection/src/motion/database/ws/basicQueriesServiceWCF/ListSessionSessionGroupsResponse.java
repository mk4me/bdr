
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
 *         &lt;element name="ListSessionSessionGroupsResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionSessionGroupList"/>
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
    "listSessionSessionGroupsResult"
})
@XmlRootElement(name = "ListSessionSessionGroupsResponse")
public class ListSessionSessionGroupsResponse {

    @XmlElement(name = "ListSessionSessionGroupsResult")
    protected ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult listSessionSessionGroupsResult;

    /**
     * Gets the value of the listSessionSessionGroupsResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult }
     *     
     */
    public ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult getListSessionSessionGroupsResult() {
        return listSessionSessionGroupsResult;
    }

    /**
     * Sets the value of the listSessionSessionGroupsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult }
     *     
     */
    public void setListSessionSessionGroupsResult(ListSessionSessionGroupsResponse.ListSessionSessionGroupsResult value) {
        this.listSessionSessionGroupsResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionSessionGroupList"/>
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
        "sessionSessionGroupList"
    })
    public static class ListSessionSessionGroupsResult {

        @XmlElement(name = "SessionSessionGroupList", required = true)
        protected SessionSessionGroupList sessionSessionGroupList;

        /**
         * Gets the value of the sessionSessionGroupList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionSessionGroupList }
         *     
         */
        public SessionSessionGroupList getSessionSessionGroupList() {
            return sessionSessionGroupList;
        }

        /**
         * Sets the value of the sessionSessionGroupList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionSessionGroupList }
         *     
         */
        public void setSessionSessionGroupList(SessionSessionGroupList value) {
            this.sessionSessionGroupList = value;
        }

    }

}
