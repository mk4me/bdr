
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
 *         &lt;element name="ListUsersResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService}UserList"/>
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
    "listUsersResult"
})
@XmlRootElement(name = "ListUsersResponse")
public class ListUsersResponse {

    @XmlElement(name = "ListUsersResult")
    protected ListUsersResponse.ListUsersResult listUsersResult;

    /**
     * Gets the value of the listUsersResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListUsersResponse.ListUsersResult }
     *     
     */
    public ListUsersResponse.ListUsersResult getListUsersResult() {
        return listUsersResult;
    }

    /**
     * Sets the value of the listUsersResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListUsersResponse.ListUsersResult }
     *     
     */
    public void setListUsersResult(ListUsersResponse.ListUsersResult value) {
        this.listUsersResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService}UserList"/>
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
        "userList"
    })
    public static class ListUsersResult {

        @XmlElement(name = "UserList", required = true)
        protected UserList userList;

        /**
         * Gets the value of the userList property.
         * 
         * @return
         *     possible object is
         *     {@link UserList }
         *     
         */
        public UserList getUserList() {
            return userList;
        }

        /**
         * Sets the value of the userList property.
         * 
         * @param value
         *     allowed object is
         *     {@link UserList }
         *     
         */
        public void setUserList(UserList value) {
            this.userList = value;
        }

    }

}
