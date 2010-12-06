
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
 *         &lt;element name="GetFileDataByIdXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileDetailsWithAttributes"/>
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
    "getFileDataByIdXMLResult"
})
@XmlRootElement(name = "GetFileDataByIdXMLResponse")
public class GetFileDataByIdXMLResponse {

    @XmlElement(name = "GetFileDataByIdXMLResult")
    protected GetFileDataByIdXMLResponse.GetFileDataByIdXMLResult getFileDataByIdXMLResult;

    /**
     * Gets the value of the getFileDataByIdXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetFileDataByIdXMLResponse.GetFileDataByIdXMLResult }
     *     
     */
    public GetFileDataByIdXMLResponse.GetFileDataByIdXMLResult getGetFileDataByIdXMLResult() {
        return getFileDataByIdXMLResult;
    }

    /**
     * Sets the value of the getFileDataByIdXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetFileDataByIdXMLResponse.GetFileDataByIdXMLResult }
     *     
     */
    public void setGetFileDataByIdXMLResult(GetFileDataByIdXMLResponse.GetFileDataByIdXMLResult value) {
        this.getFileDataByIdXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileDetailsWithAttributes"/>
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
    public static class GetFileDataByIdXMLResult {

        @XmlElement(name = "FileDetailsWithAttributes", required = true)
        protected FileDetailsWithAttributes fileDetailsWithAttributes;

        /**
         * Gets the value of the fileDetailsWithAttributes property.
         * 
         * @return
         *     possible object is
         *     {@link FileDetailsWithAttributes }
         *     
         */
        public FileDetailsWithAttributes getFileDetailsWithAttributes() {
            return fileDetailsWithAttributes;
        }

        /**
         * Sets the value of the fileDetailsWithAttributes property.
         * 
         * @param value
         *     allowed object is
         *     {@link FileDetailsWithAttributes }
         *     
         */
        public void setFileDetailsWithAttributes(FileDetailsWithAttributes value) {
            this.fileDetailsWithAttributes = value;
        }

    }

}
