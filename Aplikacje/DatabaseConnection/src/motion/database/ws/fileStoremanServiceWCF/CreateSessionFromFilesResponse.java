
package motion.database.ws.fileStoremanServiceWCF;

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
 *         &lt;element name="CreateSessionFromFilesResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "createSessionFromFilesResult"
})
@XmlRootElement(name = "CreateSessionFromFilesResponse")
public class CreateSessionFromFilesResponse {

    @XmlElement(name = "CreateSessionFromFilesResult")
    protected int createSessionFromFilesResult;

    /**
     * Gets the value of the createSessionFromFilesResult property.
     * 
     */
    public int getCreateSessionFromFilesResult() {
        return createSessionFromFilesResult;
    }

    /**
     * Sets the value of the createSessionFromFilesResult property.
     * 
     */
    public void setCreateSessionFromFilesResult(int value) {
        this.createSessionFromFilesResult = value;
    }

}
