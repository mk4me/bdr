
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
 *         &lt;element name="ValidateSessionFileSetResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileSetValidationResult"/>
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
    "validateSessionFileSetResult"
})
@XmlRootElement(name = "ValidateSessionFileSetResponse")
public class ValidateSessionFileSetResponse {

    @XmlElement(name = "ValidateSessionFileSetResult")
    protected ValidateSessionFileSetResponse.ValidateSessionFileSetResult validateSessionFileSetResult;

    /**
     * Gets the value of the validateSessionFileSetResult property.
     * 
     * @return
     *     possible object is
     *     {@link ValidateSessionFileSetResponse.ValidateSessionFileSetResult }
     *     
     */
    public ValidateSessionFileSetResponse.ValidateSessionFileSetResult getValidateSessionFileSetResult() {
        return validateSessionFileSetResult;
    }

    /**
     * Sets the value of the validateSessionFileSetResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateSessionFileSetResponse.ValidateSessionFileSetResult }
     *     
     */
    public void setValidateSessionFileSetResult(ValidateSessionFileSetResponse.ValidateSessionFileSetResult value) {
        this.validateSessionFileSetResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FileSetValidationResult"/>
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
        "fileSetValidationResult"
    })
    public static class ValidateSessionFileSetResult {

        @XmlElement(name = "FileSetValidationResult", required = true)
        protected FileSetValidationResult fileSetValidationResult;

        /**
         * Gets the value of the fileSetValidationResult property.
         * 
         * @return
         *     possible object is
         *     {@link FileSetValidationResult }
         *     
         */
        public FileSetValidationResult getFileSetValidationResult() {
            return fileSetValidationResult;
        }

        /**
         * Sets the value of the fileSetValidationResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link FileSetValidationResult }
         *     
         */
        public void setFileSetValidationResult(FileSetValidationResult value) {
            this.fileSetValidationResult = value;
        }

    }

}
