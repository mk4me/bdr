
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
 *         &lt;element name="ListFileAttributeDataXMLResult" minOccurs="0">
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
    "listFileAttributeDataXMLResult"
})
@XmlRootElement(name = "ListFileAttributeDataXMLResponse")
public class ListFileAttributeDataXMLResponse {

    @XmlElement(name = "ListFileAttributeDataXMLResult")
    protected ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult listFileAttributeDataXMLResult;

    /**
     * Gets the value of the listFileAttributeDataXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult }
     *     
     */
    public ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult getListFileAttributeDataXMLResult() {
        return listFileAttributeDataXMLResult;
    }

    /**
     * Sets the value of the listFileAttributeDataXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult }
     *     
     */
    public void setListFileAttributeDataXMLResult(ListFileAttributeDataXMLResponse.ListFileAttributeDataXMLResult value) {
        this.listFileAttributeDataXMLResult = value;
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
    public static class ListFileAttributeDataXMLResult {

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
