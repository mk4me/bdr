
package motion.database.ws.administrationWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="olderThanMinutes" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "olderThanMinutes"
})
@XmlRootElement(name = "DownloadAreaCleanup")
public class DownloadAreaCleanup {

    protected int olderThanMinutes;

    /**
     * Gets the value of the olderThanMinutes property.
     * 
     */
    public int getOlderThanMinutes() {
        return olderThanMinutes;
    }

    /**
     * Sets the value of the olderThanMinutes property.
     * 
     */
    public void setOlderThanMinutes(int value) {
        this.olderThanMinutes = value;
    }

}
