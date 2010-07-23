
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
 *         &lt;element name="ListFilesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileList"/>
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
    "listFilesXMLResult"
})
@XmlRootElement(name = "ListFilesXMLResponse")
public class ListFilesXMLResponse {

    @XmlElement(name = "ListFilesXMLResult")
    protected ListFilesXMLResponse.ListFilesXMLResult listFilesXMLResult;

    /**
     * Gets the value of the listFilesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListFilesXMLResponse.ListFilesXMLResult }
     *     
     */
    public ListFilesXMLResponse.ListFilesXMLResult getListFilesXMLResult() {
        return listFilesXMLResult;
    }

    /**
     * Sets the value of the listFilesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListFilesXMLResponse.ListFilesXMLResult }
     *     
     */
    public void setListFilesXMLResult(ListFilesXMLResponse.ListFilesXMLResult value) {
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
        "fileList"
    })
    public static class ListFilesXMLResult {

        @XmlElement(name = "FileList", required = true)
        protected FileList fileList;

        /**
         * Gets the value of the fileList property.
         * 
         * @return
         *     possible object is
         *     {@link FileList }
         *     
         */
        public FileList getFileList() {
            return fileList;
        }

        /**
         * Sets the value of the fileList property.
         * 
         * @param value
         *     allowed object is
         *     {@link FileList }
         *     
         */
        public void setFileList(FileList value) {
            this.fileList = value;
        }

    }

}
