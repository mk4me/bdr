
package motion.database.ws.authorizationWCF;

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
 *         &lt;element name="SessionPrivilege" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="Login" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="CanWrite" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="IsPublic" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="IsPublicWritable" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionPrivilege"
})
@XmlRootElement(name = "SessionPrivilegeList")
public class SessionPrivilegeList {

    @XmlElement(name = "SessionPrivilege")
    protected List<SessionPrivilegeList.SessionPrivilege> sessionPrivilege;
    @XmlAttribute(name = "IsPublic")
    protected Integer isPublic;
    @XmlAttribute(name = "IsPublicWritable")
    protected Integer isPublicWritable;

    /**
     * Gets the value of the sessionPrivilege property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sessionPrivilege property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSessionPrivilege().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SessionPrivilegeList.SessionPrivilege }
     * 
     * 
     */
    public List<SessionPrivilegeList.SessionPrivilege> getSessionPrivilege() {
        if (sessionPrivilege == null) {
            sessionPrivilege = new ArrayList<SessionPrivilegeList.SessionPrivilege>();
        }
        return this.sessionPrivilege;
    }

    /**
     * Gets the value of the isPublic property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsPublic() {
        return isPublic;
    }

    /**
     * Sets the value of the isPublic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsPublic(Integer value) {
        this.isPublic = value;
    }

    /**
     * Gets the value of the isPublicWritable property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsPublicWritable() {
        return isPublicWritable;
    }

    /**
     * Sets the value of the isPublicWritable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsPublicWritable(Integer value) {
        this.isPublicWritable = value;
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
     *       &lt;attribute name="Login" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="CanWrite" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SessionPrivilege {

        @XmlAttribute(name = "Login")
        protected String login;
        @XmlAttribute(name = "CanWrite")
        protected Boolean canWrite;

        /**
         * Gets the value of the login property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLogin() {
            return login;
        }

        /**
         * Sets the value of the login property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLogin(String value) {
            this.login = value;
        }

        /**
         * Gets the value of the canWrite property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isCanWrite() {
            return canWrite;
        }

        /**
         * Sets the value of the canWrite property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCanWrite(Boolean value) {
            this.canWrite = value;
        }

    }

}
