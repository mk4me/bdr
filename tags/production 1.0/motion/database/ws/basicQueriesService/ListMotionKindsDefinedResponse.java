
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
import motion.database.ws.basicQueriesService.ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult;


/**
 * <p>Java class for ListMotionKindsDefinedResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ListMotionKindsDefinedResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ListMotionKindsDefinedResult" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MotionKindDefinitionList"/>
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
    "listMotionKindsDefinedResult"
})
@XmlRootElement(name = "ListMotionKindsDefinedResponse")
public class ListMotionKindsDefinedResponse {

    @XmlElement(name = "ListMotionKindsDefinedResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected ListMotionKindsDefinedResult listMotionKindsDefinedResult;

    /**
     * Gets the value of the listMotionKindsDefinedResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListMotionKindsDefinedResult }
     *     
     */
    public ListMotionKindsDefinedResult getListMotionKindsDefinedResult() {
        return listMotionKindsDefinedResult;
    }

    /**
     * Sets the value of the listMotionKindsDefinedResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMotionKindsDefinedResult }
     *     
     */
    public void setListMotionKindsDefinedResult(ListMotionKindsDefinedResult value) {
        this.listMotionKindsDefinedResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MotionKindDefinitionList"/>
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
    public static class ListMotionKindsDefinedResult {

        @XmlElementRef(name = "MotionKindDefinitionList", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", type = MotionKindDefinitionList.class)
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
         * {@link MotionKindDefinitionList }
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
