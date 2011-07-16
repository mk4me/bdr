
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
 *         &lt;element name="ListGroupSessionsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}GroupSessionWithAttributesList"/>
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
    "listGroupSessionsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListGroupSessionsWithAttributesXMLResponse")
public class ListGroupSessionsWithAttributesXMLResponse {

    @XmlElement(name = "ListGroupSessionsWithAttributesXMLResult")
    protected ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult listGroupSessionsWithAttributesXMLResult;

    /**
     * Gets the value of the listGroupSessionsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult }
     *     
     */
    public ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult getListGroupSessionsWithAttributesXMLResult() {
        return listGroupSessionsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listGroupSessionsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult }
     *     
     */
    public void setListGroupSessionsWithAttributesXMLResult(ListGroupSessionsWithAttributesXMLResponse.ListGroupSessionsWithAttributesXMLResult value) {
        this.listGroupSessionsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}GroupSessionWithAttributesList"/>
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
        "groupSessionWithAttributesList"
    })
    public static class ListGroupSessionsWithAttributesXMLResult {

        @XmlElement(name = "GroupSessionWithAttributesList", required = true)
        protected GroupSessionWithAttributesList groupSessionWithAttributesList;

        /**
         * Gets the value of the groupSessionWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link GroupSessionWithAttributesList }
         *     
         */
        public GroupSessionWithAttributesList getGroupSessionWithAttributesList() {
            return groupSessionWithAttributesList;
        }

        /**
         * Sets the value of the groupSessionWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link GroupSessionWithAttributesList }
         *     
         */
        public void setGroupSessionWithAttributesList(GroupSessionWithAttributesList value) {
            this.groupSessionWithAttributesList = value;
        }

    }

}
