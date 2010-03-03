
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
import motion.database.ws.basicQueriesService.ListTrialSegmentsWithAttributesXMLResponse.ListTrialSegmentsWithAttributesXMLResult;


/**
 * <p>Java class for ListTrialSegmentsWithAttributesXMLResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ListTrialSegmentsWithAttributesXMLResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ListTrialSegmentsWithAttributesXMLResult" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrailSegmentWithAttributesList"/>
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
    "listTrialSegmentsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListTrialSegmentsWithAttributesXMLResponse")
public class ListTrialSegmentsWithAttributesXMLResponse {

    @XmlElement(name = "ListTrialSegmentsWithAttributesXMLResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected ListTrialSegmentsWithAttributesXMLResult listTrialSegmentsWithAttributesXMLResult;

    /**
     * Gets the value of the listTrialSegmentsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListTrialSegmentsWithAttributesXMLResult }
     *     
     */
    public ListTrialSegmentsWithAttributesXMLResult getListTrialSegmentsWithAttributesXMLResult() {
        return listTrialSegmentsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listTrialSegmentsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTrialSegmentsWithAttributesXMLResult }
     *     
     */
    public void setListTrialSegmentsWithAttributesXMLResult(ListTrialSegmentsWithAttributesXMLResult value) {
        this.listTrialSegmentsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrailSegmentWithAttributesList"/>
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
    public static class ListTrialSegmentsWithAttributesXMLResult {

        @XmlElementRef(name = "TrailSegmentWithAttributesList", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", type = TrailSegmentWithAttributesList.class)
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
         * {@link TrailSegmentWithAttributesList }
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
