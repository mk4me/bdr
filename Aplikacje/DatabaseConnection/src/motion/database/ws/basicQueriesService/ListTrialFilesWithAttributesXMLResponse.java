
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
import motion.database.ws.basicQueriesService.ListTrialFilesWithAttributesXMLResponse.ListTrialFilesWithAttributesXMLResult;


/**
 * <p>Java class for ListTrialFilesWithAttributesXMLResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ListTrialFilesWithAttributesXMLResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ListTrialFilesWithAttributesXMLResult" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialFileWithAttributesList"/>
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
    "listTrialFilesWithAttributesXMLResult"
})
@XmlRootElement(name = "ListTrialFilesWithAttributesXMLResponse")
public class ListTrialFilesWithAttributesXMLResponse {

    @XmlElement(name = "ListTrialFilesWithAttributesXMLResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected ListTrialFilesWithAttributesXMLResult listTrialFilesWithAttributesXMLResult;

    /**
     * Gets the value of the listTrialFilesWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListTrialFilesWithAttributesXMLResult }
     *     
     */
    public ListTrialFilesWithAttributesXMLResult getListTrialFilesWithAttributesXMLResult() {
        return listTrialFilesWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listTrialFilesWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTrialFilesWithAttributesXMLResult }
     *     
     */
    public void setListTrialFilesWithAttributesXMLResult(ListTrialFilesWithAttributesXMLResult value) {
        this.listTrialFilesWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialFileWithAttributesList"/>
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
    public static class ListTrialFilesWithAttributesXMLResult {

        @XmlElementRef(name = "TrialFileWithAttributesList", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", type = TrialFileWithAttributesList.class)
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
         * {@link TrialFileWithAttributesList }
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
