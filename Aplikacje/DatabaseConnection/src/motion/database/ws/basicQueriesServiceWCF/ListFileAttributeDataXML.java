
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
 *         &lt;element name="subjectID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="subjectType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "subjectID",
    "subjectType"
})
@XmlRootElement(name = "ListFileAttributeDataXML")
public class ListFileAttributeDataXML {

    protected int subjectID;
    protected String subjectType;

    /**
     * Gets the value of the subjectID property.
     * 
     */
    public int getSubjectID() {
        return subjectID;
    }

    /**
     * Sets the value of the subjectID property.
     * 
     */
    public void setSubjectID(int value) {
        this.subjectID = value;
    }

    /**
     * Gets the value of the subjectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * Sets the value of the subjectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectType(String value) {
        this.subjectType = value;
    }

}
