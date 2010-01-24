
package motion.database.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Receive environment change notifications.
 * 
 * <p>Java class for environmentChangeNotifications element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="environmentChangeNotifications">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;attribute name="databaseChange" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *         &lt;attribute name="languageChange" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *         &lt;attribute name="transactionBoundary" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "environmentChangeNotifications")
public class EnvironmentChangeNotifications {

    @XmlAttribute
    protected Boolean databaseChange;
    @XmlAttribute
    protected Boolean languageChange;
    @XmlAttribute
    protected Boolean transactionBoundary;

    /**
     * Gets the value of the databaseChange property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDatabaseChange() {
        if (databaseChange == null) {
            return false;
        } else {
            return databaseChange;
        }
    }

    /**
     * Sets the value of the databaseChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDatabaseChange(Boolean value) {
        this.databaseChange = value;
    }

    /**
     * Gets the value of the languageChange property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isLanguageChange() {
        if (languageChange == null) {
            return false;
        } else {
            return languageChange;
        }
    }

    /**
     * Sets the value of the languageChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLanguageChange(Boolean value) {
        this.languageChange = value;
    }

    /**
     * Gets the value of the transactionBoundary property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isTransactionBoundary() {
        if (transactionBoundary == null) {
            return false;
        } else {
            return transactionBoundary;
        }
    }

    /**
     * Sets the value of the transactionBoundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTransactionBoundary(Boolean value) {
        this.transactionBoundary = value;
    }

}
