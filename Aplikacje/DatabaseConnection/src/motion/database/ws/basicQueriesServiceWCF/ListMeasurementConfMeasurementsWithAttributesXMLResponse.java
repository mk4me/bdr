
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
 *         &lt;element name="ListMeasurementConfMeasurementsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfMeasurementWithAttributesList"/>
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
    "listMeasurementConfMeasurementsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListMeasurementConfMeasurementsWithAttributesXMLResponse")
public class ListMeasurementConfMeasurementsWithAttributesXMLResponse {

    @XmlElement(name = "ListMeasurementConfMeasurementsWithAttributesXMLResult")
    protected ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult listMeasurementConfMeasurementsWithAttributesXMLResult;

    /**
     * Gets the value of the listMeasurementConfMeasurementsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult }
     *     
     */
    public ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult getListMeasurementConfMeasurementsWithAttributesXMLResult() {
        return listMeasurementConfMeasurementsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listMeasurementConfMeasurementsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult }
     *     
     */
    public void setListMeasurementConfMeasurementsWithAttributesXMLResult(ListMeasurementConfMeasurementsWithAttributesXMLResponse.ListMeasurementConfMeasurementsWithAttributesXMLResult value) {
        this.listMeasurementConfMeasurementsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfMeasurementWithAttributesList"/>
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
        "measurementConfMeasurementWithAttributesList"
    })
    public static class ListMeasurementConfMeasurementsWithAttributesXMLResult {

        @XmlElement(name = "MeasurementConfMeasurementWithAttributesList", required = true)
        protected MeasurementConfMeasurementWithAttributesList measurementConfMeasurementWithAttributesList;

        /**
         * Gets the value of the measurementConfMeasurementWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link MeasurementConfMeasurementWithAttributesList }
         *     
         */
        public MeasurementConfMeasurementWithAttributesList getMeasurementConfMeasurementWithAttributesList() {
            return measurementConfMeasurementWithAttributesList;
        }

        /**
         * Sets the value of the measurementConfMeasurementWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasurementConfMeasurementWithAttributesList }
         *     
         */
        public void setMeasurementConfMeasurementWithAttributesList(MeasurementConfMeasurementWithAttributesList value) {
            this.measurementConfMeasurementWithAttributesList = value;
        }

    }

}
