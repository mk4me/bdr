
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
 *         &lt;element name="ListSessionTrialsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionTrialWithAttributesList"/>
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
    "listSessionTrialsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListSessionTrialsWithAttributesXMLResponse")
public class ListSessionTrialsWithAttributesXMLResponse {

    @XmlElement(name = "ListSessionTrialsWithAttributesXMLResult")
    protected ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult listSessionTrialsWithAttributesXMLResult;

    /**
     * Gets the value of the listSessionTrialsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult }
     *     
     */
    public ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult getListSessionTrialsWithAttributesXMLResult() {
        return listSessionTrialsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listSessionTrialsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult }
     *     
     */
    public void setListSessionTrialsWithAttributesXMLResult(ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult value) {
        this.listSessionTrialsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionTrialWithAttributesList"/>
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
        "sessionTrialWithAttributesList"
    })
    public static class ListSessionTrialsWithAttributesXMLResult {

        @XmlElement(name = "SessionTrialWithAttributesList", required = true)
        protected SessionTrialWithAttributesList sessionTrialWithAttributesList;

        /**
         * Gets the value of the sessionTrialWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionTrialWithAttributesList }
         *     
         */
        public SessionTrialWithAttributesList getSessionTrialWithAttributesList() {
            return sessionTrialWithAttributesList;
        }

        /**
         * Sets the value of the sessionTrialWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionTrialWithAttributesList }
         *     
         */
        public void setSessionTrialWithAttributesList(SessionTrialWithAttributesList value) {
            this.sessionTrialWithAttributesList = value;
        }

    }

}
