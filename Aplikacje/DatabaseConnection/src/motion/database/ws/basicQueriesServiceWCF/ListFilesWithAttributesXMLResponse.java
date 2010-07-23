
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
 *         &lt;element name="ListFilesWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileWithAttributesList"/>
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
    "listFilesWithAttributesXMLResult"
})
@XmlRootElement(name = "ListFilesWithAttributesXMLResponse")
public class ListFilesWithAttributesXMLResponse {

    @XmlElement(name = "ListFilesWithAttributesXMLResult")
    protected ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult listFilesWithAttributesXMLResult;

    /**
     * Gets the value of the listFilesWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult }
     *     
     */
    public ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult getListFilesWithAttributesXMLResult() {
        return listFilesWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listFilesWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult }
     *     
     */
    public void setListFilesWithAttributesXMLResult(ListFilesWithAttributesXMLResponse.ListFilesWithAttributesXMLResult value) {
        this.listFilesWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileWithAttributesList"/>
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
        "fileWithAttributesList"
    })
    public static class ListFilesWithAttributesXMLResult {

        @XmlElement(name = "FileWithAttributesList", required = true)
        protected FileWithAttributesList fileWithAttributesList;

        /**
         * Gets the value of the fileWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link FileWithAttributesList }
         *     
         */
        public FileWithAttributesList getFileWithAttributesList() {
            return fileWithAttributesList;
        }

        /**
         * Sets the value of the fileWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link FileWithAttributesList }
         *     
         */
        public void setFileWithAttributesList(FileWithAttributesList value) {
            this.fileWithAttributesList = value;
        }

    }

}
