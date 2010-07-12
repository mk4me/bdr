
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
 *         &lt;element name="ListSessionTrialsXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionTrialList"/>
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
    "listSessionTrialsXMLResult"
})
@XmlRootElement(name = "ListSessionTrialsXMLResponse")
public class ListSessionTrialsXMLResponse {

    @XmlElement(name = "ListSessionTrialsXMLResult")
    protected ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult listSessionTrialsXMLResult;

    /**
     * Gets the value of the listSessionTrialsXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult }
     *     
     */
    public ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult getListSessionTrialsXMLResult() {
        return listSessionTrialsXMLResult;
    }

    /**
     * Sets the value of the listSessionTrialsXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult }
     *     
     */
    public void setListSessionTrialsXMLResult(ListSessionTrialsXMLResponse.ListSessionTrialsXMLResult value) {
        this.listSessionTrialsXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionTrialList"/>
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
        "sessionTrialList"
    })
    public static class ListSessionTrialsXMLResult {

        @XmlElement(name = "SessionTrialList", required = true)
        protected SessionTrialList sessionTrialList;

        /**
         * Gets the value of the sessionTrialList property.
         * 
         * @return
         *     possible object is
         *     {@link SessionTrialList }
         *     
         */
        public SessionTrialList getSessionTrialList() {
            return sessionTrialList;
        }

        /**
         * Sets the value of the sessionTrialList property.
         * 
         * @param value
         *     allowed object is
         *     {@link SessionTrialList }
         *     
         */
        public void setSessionTrialList(SessionTrialList value) {
            this.sessionTrialList = value;
        }

    }

}
