
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
 *         &lt;element name="GenericQueryUniformXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}GenericUniformAttributesQueryResult"/>
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
    "genericQueryUniformXMLResult"
})
@XmlRootElement(name = "GenericQueryUniformXMLResponse")
public class GenericQueryUniformXMLResponse {

    @XmlElement(name = "GenericQueryUniformXMLResult")
    protected GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult genericQueryUniformXMLResult;

    /**
     * Gets the value of the genericQueryUniformXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult }
     *     
     */
    public GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult getGenericQueryUniformXMLResult() {
        return genericQueryUniformXMLResult;
    }

    /**
     * Sets the value of the genericQueryUniformXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult }
     *     
     */
    public void setGenericQueryUniformXMLResult(GenericQueryUniformXMLResponse.GenericQueryUniformXMLResult value) {
        this.genericQueryUniformXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}GenericUniformAttributesQueryResult"/>
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
        "genericUniformAttributesQueryResult"
    })
    public static class GenericQueryUniformXMLResult {

        @XmlElement(name = "GenericUniformAttributesQueryResult", required = true)
        protected GenericUniformAttributesQueryResult genericUniformAttributesQueryResult;

        /**
         * Gets the value of the genericUniformAttributesQueryResult property.
         * 
         * @return
         *     possible object is
         *     {@link GenericUniformAttributesQueryResult }
         *     
         */
        public GenericUniformAttributesQueryResult getGenericUniformAttributesQueryResult() {
            return genericUniformAttributesQueryResult;
        }

        /**
         * Sets the value of the genericUniformAttributesQueryResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link GenericUniformAttributesQueryResult }
         *     
         */
        public void setGenericUniformAttributesQueryResult(GenericUniformAttributesQueryResult value) {
            this.genericUniformAttributesQueryResult = value;
        }

    }

}
