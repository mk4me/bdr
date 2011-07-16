
package motion.database.ws.basicQueriesServiceWCF;

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
 *         &lt;element name="fileNames" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}ArrayOfFileNameEntry" minOccurs="0"/>
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
    "fileNames"
})
@XmlRootElement(name = "ValidateSessionFileSet")
public class ValidateSessionFileSet {

    protected ArrayOfFileNameEntry fileNames;

    /**
     * Gets the value of the fileNames property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFileNameEntry }
     *     
     */
    public ArrayOfFileNameEntry getFileNames() {
        return fileNames;
    }

    /**
     * Sets the value of the fileNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFileNameEntry }
     *     
     */
    public void setFileNames(ArrayOfFileNameEntry value) {
        this.fileNames = value;
    }

}
