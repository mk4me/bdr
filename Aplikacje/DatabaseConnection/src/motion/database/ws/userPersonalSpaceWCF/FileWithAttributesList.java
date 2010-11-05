
package motion.database.ws.userPersonalSpaceWCF;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="FileDetailsWithAttributes" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}Attributes" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="FileID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="FileName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="FileDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="SubdirPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "fileDetailsWithAttributes"
})
@XmlRootElement(name = "FileWithAttributesList")
public class FileWithAttributesList {

    @XmlElement(name = "FileDetailsWithAttributes")
    protected List<FileWithAttributesList.FileDetailsWithAttributes> fileDetailsWithAttributes;

    /**
     * Gets the value of the fileDetailsWithAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileDetailsWithAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileDetailsWithAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileWithAttributesList.FileDetailsWithAttributes }
     * 
     * 
     */
    public List<FileWithAttributesList.FileDetailsWithAttributes> getFileDetailsWithAttributes() {
        if (fileDetailsWithAttributes == null) {
            fileDetailsWithAttributes = new ArrayList<FileWithAttributesList.FileDetailsWithAttributes>();
        }
        return this.fileDetailsWithAttributes;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService}Attributes" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="FileID" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="FileName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="FileDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="SubdirPath" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="AttributeName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "attributes"
    })
    public static class FileDetailsWithAttributes {

        @XmlElement(name = "Attributes")
        protected Attributes attributes;
        @XmlAttribute(name = "FileID")
        protected Integer fileID;
        @XmlAttribute(name = "FileName")
        protected String fileName;
        @XmlAttribute(name = "FileDescription")
        protected String fileDescription;
        @XmlAttribute(name = "SubdirPath")
        protected String subdirPath;
        @XmlAttribute(name = "AttributeName")
        protected String attributeName;

        /**
         * Gets the value of the attributes property.
         * 
         * @return
         *     possible object is
         *     {@link Attributes }
         *     
         */
        public Attributes getAttributes() {
            return attributes;
        }

        /**
         * Sets the value of the attributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link Attributes }
         *     
         */
        public void setAttributes(Attributes value) {
            this.attributes = value;
        }

        /**
         * Gets the value of the fileID property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getFileID() {
            return fileID;
        }

        /**
         * Sets the value of the fileID property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setFileID(Integer value) {
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
         * Gets the value of the subdirPath property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubdirPath() {
            return subdirPath;
        }

        /**
         * Sets the value of the subdirPath property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubdirPath(String value) {
            this.subdirPath = value;
        }

        /**
         * Gets the value of the attributeName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttributeName() {
            return attributeName;
        }

        /**
         * Sets the value of the attributeName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttributeName(String value) {
            this.attributeName = value;
        }

    }

}
