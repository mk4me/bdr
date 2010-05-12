
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
import motion.database.ws.basicQueriesService.ListFilesXMLResponse.ListFilesXMLResult;


/**
 * <p>Java class for ListFilesXMLResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="ListFilesXMLResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="ListFilesXMLResult" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileList"/>
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
    "listFilesXMLResult"
})
@XmlRootElement(name = "ListFilesXMLResponse")
public class ListFilesXMLResponse {

    @XmlElement(name = "ListFilesXMLResult", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected ListFilesXMLResult listFilesXMLResult;

    /**
     * Gets the value of the listFilesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListFilesXMLResult }
     *     
     */
    public ListFilesXMLResult getListFilesXMLResult() {
        return listFilesXMLResult;
    }

    /**
     * Sets the value of the listFilesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListFilesXMLResult }
     *     
     */
    public void setListFilesXMLResult(ListFilesXMLResult value) {
        this.listFilesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileList"/>
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
    public static class ListFilesXMLResult {

        @XmlElementRef(name = "FileList", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", type = FileList.class)
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
         * {@link FileList }
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
