
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
 *         &lt;element name="ListSessionGroupsDefinedResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionGroupDefinitionList"/>
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
    "listSessionGroupsDefinedResult"
})
@XmlRootElement(name = "ListSessionGroupsDefinedResponse")
public class ListSessionGroupsDefinedResponse {

    @XmlElement(name = "ListSessionGroupsDefinedResult")
    protected ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult listSessionGroupsDefinedResult;

    /**
     * Gets the value of the listSessionGroupsDefinedResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult }
     *     
     */
    public ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult getListSessionGroupsDefinedResult() {
        return listSessionGroupsDefinedResult;
    }

    /**
     * Sets the value of the listSessionGroupsDefinedResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult }
     *     
     */
    public void setListSessionGroupsDefinedResult(ListSessionGroupsDefinedResponse.ListSessionGroupsDefinedResult value) {
        this.listSessionGroupsDefinedResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionGroupDefinitionList"/>
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
        "sessionGroupDefinitionList"
    })
    public static class ListSessionGroupsDefinedResult {

        @XmlElement(name = "SessionGroupDefinitionList", required = true)
        protected SessionGroupDefinitionList sessionGroupDefinitionList;

        /**
         * Gets the value of the sessionGroupDefinitionList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionGroupDefinitionList }
         *     
         */
        public SessionGroupDefinitionList getSessionGroupDefinitionList() {
            return sessionGroupDefinitionList;
        }

        /**
         * Sets the value of the sessionGroupDefinitionList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionGroupDefinitionList }
         *     
         */
        public void setSessionGroupDefinitionList(SessionGroupDefinitionList value) {
            this.sessionGroupDefinitionList = value;
        }

    }

}
