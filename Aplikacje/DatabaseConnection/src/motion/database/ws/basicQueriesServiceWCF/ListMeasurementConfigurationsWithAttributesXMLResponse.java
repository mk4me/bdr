
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
 *         &lt;element name="ListMeasurementConfigurationsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfWithAttributesList"/>
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
    "listMeasurementConfigurationsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListMeasurementConfigurationsWithAttributesXMLResponse")
public class ListMeasurementConfigurationsWithAttributesXMLResponse {

    @XmlElement(name = "ListMeasurementConfigurationsWithAttributesXMLResult")
    protected ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult listMeasurementConfigurationsWithAttributesXMLResult;

    /**
     * Gets the value of the listMeasurementConfigurationsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult }
     *     
     */
    public ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult getListMeasurementConfigurationsWithAttributesXMLResult() {
        return listMeasurementConfigurationsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listMeasurementConfigurationsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult }
     *     
     */
    public void setListMeasurementConfigurationsWithAttributesXMLResult(ListMeasurementConfigurationsWithAttributesXMLResponse.ListMeasurementConfigurationsWithAttributesXMLResult value) {
        this.listMeasurementConfigurationsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfWithAttributesList"/>
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
        "measurementConfWithAttributesList"
    })
    public static class ListMeasurementConfigurationsWithAttributesXMLResult {

        @XmlElement(name = "MeasurementConfWithAttributesList", required = true)
        protected MeasurementConfWithAttributesList measurementConfWithAttributesList;

        /**
         * Gets the value of the measurementConfWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link MeasurementConfWithAttributesList }
         *     
         */
        public MeasurementConfWithAttributesList getMeasurementConfWithAttributesList() {
            return measurementConfWithAttributesList;
        }

        /**
         * Sets the value of the measurementConfWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasurementConfWithAttributesList }
         *     
         */
        public void setMeasurementConfWithAttributesList(MeasurementConfWithAttributesList value) {
            this.measurementConfWithAttributesList = value;
        }

    }

}
