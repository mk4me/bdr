
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
 *         &lt;element name="ListTrialMeasurementsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialMeasurementWithAttributesList"/>
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
    "listTrialMeasurementsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListTrialMeasurementsWithAttributesXMLResponse")
public class ListTrialMeasurementsWithAttributesXMLResponse {

    @XmlElement(name = "ListTrialMeasurementsWithAttributesXMLResult")
    protected ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult listTrialMeasurementsWithAttributesXMLResult;

    /**
     * Gets the value of the listTrialMeasurementsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult }
     *     
     */
    public ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult getListTrialMeasurementsWithAttributesXMLResult() {
        return listTrialMeasurementsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listTrialMeasurementsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult }
     *     
     */
    public void setListTrialMeasurementsWithAttributesXMLResult(ListTrialMeasurementsWithAttributesXMLResponse.ListTrialMeasurementsWithAttributesXMLResult value) {
        this.listTrialMeasurementsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}TrialMeasurementWithAttributesList"/>
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
        "trialMeasurementWithAttributesList"
    })
    public static class ListTrialMeasurementsWithAttributesXMLResult {

        @XmlElement(name = "TrialMeasurementWithAttributesList", required = true)
        protected TrialMeasurementWithAttributesList trialMeasurementWithAttributesList;

        /**
         * Gets the value of the trialMeasurementWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link TrialMeasurementWithAttributesList }
         *     
         */
        public TrialMeasurementWithAttributesList getTrialMeasurementWithAttributesList() {
            return trialMeasurementWithAttributesList;
        }

        /**
         * Sets the value of the trialMeasurementWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link TrialMeasurementWithAttributesList }
         *     
         */
        public void setTrialMeasurementWithAttributesList(TrialMeasurementWithAttributesList value) {
            this.trialMeasurementWithAttributesList = value;
        }

    }

}
