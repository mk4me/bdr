
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
 *         &lt;element name="ListLabPerformersWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}LabPerformerWithAttributesList"/>
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
    "listLabPerformersWithAttributesXMLResult"
})
@XmlRootElement(name = "ListLabPerformersWithAttributesXMLResponse")
public class ListLabPerformersWithAttributesXMLResponse {

    @XmlElement(name = "ListLabPerformersWithAttributesXMLResult")
    protected ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult listLabPerformersWithAttributesXMLResult;

    /**
     * Gets the value of the listLabPerformersWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult }
     *     
     */
    public ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult getListLabPerformersWithAttributesXMLResult() {
        return listLabPerformersWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listLabPerformersWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult }
     *     
     */
    public void setListLabPerformersWithAttributesXMLResult(ListLabPerformersWithAttributesXMLResponse.ListLabPerformersWithAttributesXMLResult value) {
        this.listLabPerformersWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}LabPerformerWithAttributesList"/>
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
        "labPerformerWithAttributesList"
    })
    public static class ListLabPerformersWithAttributesXMLResult {

        @XmlElement(name = "LabPerformerWithAttributesList", required = true)
        protected LabPerformerWithAttributesList labPerformerWithAttributesList;

        /**
         * Gets the value of the labPerformerWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link LabPerformerWithAttributesList }
         *     
         */
        public LabPerformerWithAttributesList getLabPerformerWithAttributesList() {
            return labPerformerWithAttributesList;
        }

        /**
         * Sets the value of the labPerformerWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link LabPerformerWithAttributesList }
         *     
         */
        public void setLabPerformerWithAttributesList(LabPerformerWithAttributesList value) {
            this.labPerformerWithAttributesList = value;
        }

    }

}
