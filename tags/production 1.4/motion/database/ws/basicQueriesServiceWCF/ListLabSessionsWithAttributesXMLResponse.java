
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
 *         &lt;element name="ListLabSessionsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}LabSessionWithAttributesList"/>
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
    "listLabSessionsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListLabSessionsWithAttributesXMLResponse")
public class ListLabSessionsWithAttributesXMLResponse {

    @XmlElement(name = "ListLabSessionsWithAttributesXMLResult")
    protected ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult listLabSessionsWithAttributesXMLResult;

    /**
     * Gets the value of the listLabSessionsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult }
     *     
     */
    public ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult getListLabSessionsWithAttributesXMLResult() {
        return listLabSessionsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listLabSessionsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult }
     *     
     */
    public void setListLabSessionsWithAttributesXMLResult(ListLabSessionsWithAttributesXMLResponse.ListLabSessionsWithAttributesXMLResult value) {
        this.listLabSessionsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}LabSessionWithAttributesList"/>
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
        "labSessionWithAttributesList"
    })
    public static class ListLabSessionsWithAttributesXMLResult {

        @XmlElement(name = "LabSessionWithAttributesList", required = true)
        protected LabSessionWithAttributesList labSessionWithAttributesList;

        /**
         * Gets the value of the labSessionWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link LabSessionWithAttributesList }
         *     
         */
        public LabSessionWithAttributesList getLabSessionWithAttributesList() {
            return labSessionWithAttributesList;
        }

        /**
         * Sets the value of the labSessionWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link LabSessionWithAttributesList }
         *     
         */
        public void setLabSessionWithAttributesList(LabSessionWithAttributesList value) {
            this.labSessionWithAttributesList = value;
        }

    }

}
