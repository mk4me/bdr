
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
 *         &lt;element name="ListMeasurementResultFilesWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementResultFileWithAttributesList"/>
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
    "listMeasurementResultFilesWithAttributesXMLResult"
})
@XmlRootElement(name = "ListMeasurementResultFilesWithAttributesXMLResponse")
public class ListMeasurementResultFilesWithAttributesXMLResponse {

    @XmlElement(name = "ListMeasurementResultFilesWithAttributesXMLResult")
    protected ListMeasurementResultFilesWithAttributesXMLResponse.ListMeasurementResultFilesWithAttributesXMLResult listMeasurementResultFilesWithAttributesXMLResult;

    /**
     * Gets the value of the listMeasurementResultFilesWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListMeasurementResultFilesWithAttributesXMLResponse.ListMeasurementResultFilesWithAttributesXMLResult }
     *     
     */
    public ListMeasurementResultFilesWithAttributesXMLResponse.ListMeasurementResultFilesWithAttributesXMLResult getListMeasurementResultFilesWithAttributesXMLResult() {
        return listMeasurementResultFilesWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listMeasurementResultFilesWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMeasurementResultFilesWithAttributesXMLResponse.ListMeasurementResultFilesWithAttributesXMLResult }
     *     
     */
    public void setListMeasurementResultFilesWithAttributesXMLResult(ListMeasurementResultFilesWithAttributesXMLResponse.ListMeasurementResultFilesWithAttributesXMLResult value) {
        this.listMeasurementResultFilesWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementResultFileWithAttributesList"/>
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
        "measurementResultFileWithAttributesList"
    })
    public static class ListMeasurementResultFilesWithAttributesXMLResult {

        @XmlElement(name = "MeasurementResultFileWithAttributesList", required = true)
        protected MeasurementResultFileWithAttributesList measurementResultFileWithAttributesList;

        /**
         * Gets the value of the measurementResultFileWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link MeasurementResultFileWithAttributesList }
         *     
         */
        public MeasurementResultFileWithAttributesList getMeasurementResultFileWithAttributesList() {
            return measurementResultFileWithAttributesList;
        }

        /**
         * Sets the value of the measurementResultFileWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasurementResultFileWithAttributesList }
         *     
         */
        public void setMeasurementResultFileWithAttributesList(MeasurementResultFileWithAttributesList value) {
            this.measurementResultFileWithAttributesList = value;
        }

    }

}
