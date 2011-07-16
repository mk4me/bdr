
package motion.database.ws.authorizationWCF;

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
 *         &lt;element name="ListSessionPrivilegesResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService}SessionPrivilegeList"/>
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
    "listSessionPrivilegesResult"
})
@XmlRootElement(name = "ListSessionPrivilegesResponse")
public class ListSessionPrivilegesResponse {

    @XmlElement(name = "ListSessionPrivilegesResult")
    protected ListSessionPrivilegesResponse.ListSessionPrivilegesResult listSessionPrivilegesResult;

    /**
     * Gets the value of the listSessionPrivilegesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionPrivilegesResponse.ListSessionPrivilegesResult }
     *     
     */
    public ListSessionPrivilegesResponse.ListSessionPrivilegesResult getListSessionPrivilegesResult() {
        return listSessionPrivilegesResult;
    }

    /**
     * Sets the value of the listSessionPrivilegesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionPrivilegesResponse.ListSessionPrivilegesResult }
     *     
     */
    public void setListSessionPrivilegesResult(ListSessionPrivilegesResponse.ListSessionPrivilegesResult value) {
        this.listSessionPrivilegesResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService}SessionPrivilegeList"/>
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
        "sessionPrivilegeList"
    })
    public static class ListSessionPrivilegesResult {

        @XmlElement(name = "SessionPrivilegeList", required = true)
        protected SessionPrivilegeList sessionPrivilegeList;

        /**
         * Gets the value of the sessionPrivilegeList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionPrivilegeList }
         *     
         */
        public SessionPrivilegeList getSessionPrivilegeList() {
            return sessionPrivilegeList;
        }

        /**
         * Sets the value of the sessionPrivilegeList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionPrivilegeList }
         *     
         */
        public void setSessionPrivilegeList(SessionPrivilegeList value) {
            this.sessionPrivilegeList = value;
        }

    }

}
