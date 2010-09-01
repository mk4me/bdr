
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
 *         &lt;element name="subjectEntity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "subjectEntity"
})
@XmlRootElement(name = "ListFileAttributeDataWithAttributesXML")
public class ListFileAttributeDataWithAttributesXML {

    protected int subjectID;
    protected String subjectEntity;

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
     * Gets the value of the subjectEntity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectEntity() {
        return subjectEntity;
    }

    /**
     * Sets the value of the subjectEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectEntity(String value) {
        this.subjectEntity = value;
    }

}
