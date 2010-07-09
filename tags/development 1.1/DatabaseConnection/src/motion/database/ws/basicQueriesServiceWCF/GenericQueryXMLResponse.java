
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
 *         &lt;element name="GenericQueryXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}GenericQueryResult"/>
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
    "genericQueryXMLResult"
})
@XmlRootElement(name = "GenericQueryXMLResponse")
public class GenericQueryXMLResponse {

    @XmlElement(name = "GenericQueryXMLResult")
    protected GenericQueryXMLResponse.GenericQueryXMLResult genericQueryXMLResult;

    /**
     * Gets the value of the genericQueryXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GenericQueryXMLResponse.GenericQueryXMLResult }
     *     
     */
    public GenericQueryXMLResponse.GenericQueryXMLResult getGenericQueryXMLResult() {
        return genericQueryXMLResult;
    }

    /**
     * Sets the value of the genericQueryXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericQueryXMLResponse.GenericQueryXMLResult }
     *     
     */
    public void setGenericQueryXMLResult(GenericQueryXMLResponse.GenericQueryXMLResult value) {
        this.genericQueryXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}GenericQueryResult"/>
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
        "genericQueryResult"
    })
    public static class GenericQueryXMLResult {

        @XmlElement(name = "GenericQueryResult", required = true)
        protected GenericQueryResult genericQueryResult;

        /**
         * Gets the value of the genericQueryResult property.
         * 
         * @return
         *     possible object is
         *     {@link GenericQueryResult }
         *     
         */
        public GenericQueryResult getGenericQueryResult() {
            return genericQueryResult;
        }

        /**
         * Sets the value of the genericQueryResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link GenericQueryResult }
         *     
         */
        public void setGenericQueryResult(GenericQueryResult value) {
            this.genericQueryResult = value;
        }

    }

}
