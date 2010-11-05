
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
 *         &lt;element name="ListMeasurementPerformersWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementPerformerWithAttributesList"/>
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
    "listMeasurementPerformersWithAttributesXMLResult"
})
@XmlRootElement(name = "ListMeasurementPerformersWithAttributesXMLResponse")
public class ListMeasurementPerformersWithAttributesXMLResponse {

    @XmlElement(name = "ListMeasurementPerformersWithAttributesXMLResult")
    protected ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult listMeasurementPerformersWithAttributesXMLResult;

    /**
     * Gets the value of the listMeasurementPerformersWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult }
     *     
     */
    public ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult getListMeasurementPerformersWithAttributesXMLResult() {
        return listMeasurementPerformersWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listMeasurementPerformersWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult }
     *     
     */
    public void setListMeasurementPerformersWithAttributesXMLResult(ListMeasurementPerformersWithAttributesXMLResponse.ListMeasurementPerformersWithAttributesXMLResult value) {
        this.listMeasurementPerformersWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementPerformerWithAttributesList"/>
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
        "measurementPerformerWithAttributesList"
    })
    public static class ListMeasurementPerformersWithAttributesXMLResult {

        @XmlElement(name = "MeasurementPerformerWithAttributesList", required = true)
        protected MeasurementPerformerWithAttributesList measurementPerformerWithAttributesList;

        /**
         * Gets the value of the measurementPerformerWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link MeasurementPerformerWithAttributesList }
         *     
         */
        public MeasurementPerformerWithAttributesList getMeasurementPerformerWithAttributesList() {
            return measurementPerformerWithAttributesList;
        }

        /**
         * Sets the value of the measurementPerformerWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasurementPerformerWithAttributesList }
         *     
         */
        public void setMeasurementPerformerWithAttributesList(MeasurementPerformerWithAttributesList value) {
            this.measurementPerformerWithAttributesList = value;
        }

    }

}
