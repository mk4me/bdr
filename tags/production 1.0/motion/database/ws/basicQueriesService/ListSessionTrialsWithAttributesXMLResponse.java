
package motion.database.ws.basicQueriesService;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import motion.database.ws.basicQueriesService.ListSessionTrialsWithAttributesXMLResponse.ListSessionTrialsWithAttributesXMLResult;


/**
 * <p>Java class for ListSessionTrialsWithAttributesXMLResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ListSessionTrialsWithAttributesXMLResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ListSessionTrialsWithAttributesXMLResult" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionTrialWithAttributesList"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
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

    @XmlElement(name = "ListSessionTrialsWithAttributesXMLResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected ListSessionTrialsWithAttributesXMLResult listSessionTrialsWithAttributesXMLResult;

    /**
     * Gets the value of the listSessionTrialsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSessionTrialsWithAttributesXMLResult }
     *     
     */
    public ListSessionTrialsWithAttributesXMLResult getListSessionTrialsWithAttributesXMLResult() {
        return listSessionTrialsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listSessionTrialsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSessionTrialsWithAttributesXMLResult }
     *     
     */
    public void setListSessionTrialsWithAttributesXMLResult(ListSessionTrialsWithAttributesXMLResult value) {
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
        "content"
    })
    public static class ListSessionTrialsWithAttributesXMLResult {

        @XmlElementRef(name = "SessionTrialWithAttributesList", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", type = SessionTrialWithAttributesList.class)
        @XmlMixed
        protected List<Object> content;

        /**
         * Gets the value of the content property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the content property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SessionTrialWithAttributesList }
         * {@link String }
         * 
         * 
         */
        public List<Object> getContent() {
            if (content == null) {
                content = new ArrayList<Object>();
            }
            return this.content;
        }

    }

}
