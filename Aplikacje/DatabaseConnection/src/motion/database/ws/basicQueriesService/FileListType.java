
package motion.database.ws.basicQueriesService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import motion.database.ws.basicQueriesService.FileListType.FileDetails;


/**
 * <p>Java class for FileListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FileListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FileDetails" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="FileDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="FileID" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="FileName" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "FileListType", propOrder = {
    "fileDetails"
})
public class FileListType {

    @XmlElement(name = "FileDetails", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", required = true)
    protected List<FileDetails> fileDetails;

    /**
     * Gets the value of the fileDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileDetails }
     * 
     * 
     */
    public List<FileDetails> getFileDetails() {
        if (fileDetails == null) {
            fileDetails = new ArrayList<FileDetails>();
        }
        return this.fileDetails;
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
     *       &lt;attribute name="FileDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="FileID" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="FileName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class FileDetails {

        @XmlAttribute(name = "FileDescription")
        protected String fileDescription;
        @XmlAttribute(name = "FileID")
        protected BigInteger fileID;
        @XmlAttribute(name = "FileName")
        protected String fileName;

        /**
         * Gets the value of the fileDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFileDescription() {
            return fileDescription;
        }

        /**
         * Sets the value of the fileDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFileDescription(String value) {
            this.fileDescription = value;
        }

        /**
         * Gets the value of the fileID property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getFileID() {
            return fileID;
        }

        /**
         * Sets the value of the fileID property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setFileID(BigInteger value) {
            this.fileID = value;
        }

        /**
         * Gets the value of the fileName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * Sets the value of the fileName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFileName(String value) {
            this.fileName = value;
        }

    }

}
