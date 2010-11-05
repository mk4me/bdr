
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
 *         &lt;element name="ListMeasurementConfSessionsWithAttributesXMLResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfSessionWithAttributesList"/>
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
    "listMeasurementConfSessionsWithAttributesXMLResult"
})
@XmlRootElement(name = "ListMeasurementConfSessionsWithAttributesXMLResponse")
public class ListMeasurementConfSessionsWithAttributesXMLResponse {

    @XmlElement(name = "ListMeasurementConfSessionsWithAttributesXMLResult")
    protected ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult listMeasurementConfSessionsWithAttributesXMLResult;

    /**
     * Gets the value of the listMeasurementConfSessionsWithAttributesXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult }
     *     
     */
    public ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult getListMeasurementConfSessionsWithAttributesXMLResult() {
        return listMeasurementConfSessionsWithAttributesXMLResult;
    }

    /**
     * Sets the value of the listMeasurementConfSessionsWithAttributesXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult }
     *     
     */
    public void setListMeasurementConfSessionsWithAttributesXMLResult(ListMeasurementConfSessionsWithAttributesXMLResponse.ListMeasurementConfSessionsWithAttributesXMLResult value) {
        this.listMeasurementConfSessionsWithAttributesXMLResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MeasurementConfSessionWithAttributesList"/>
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
        "measurementConfSessionWithAttributesList"
    })
    public static class ListMeasurementConfSessionsWithAttributesXMLResult {

        @XmlElement(name = "MeasurementConfSessionWithAttributesList", required = true)
        protected MeasurementConfSessionWithAttributesList measurementConfSessionWithAttributesList;

        /**
         * Gets the value of the measurementConfSessionWithAttributesList property.
         * 
         * @return
         *     possible object is
         *     {@link MeasurementConfSessionWithAttributesList }
         *     
         */
        public MeasurementConfSessionWithAttributesList getMeasurementConfSessionWithAttributesList() {
            return measurementConfSessionWithAttributesList;
        }

        /**
         * Sets the value of the measurementConfSessionWithAttributesList property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeasurementConfSessionWithAttributesList }
         *     
         */
        public void setMeasurementConfSessionWithAttributesList(MeasurementConfSessionWithAttributesList value) {
            this.measurementConfSessionWithAttributesList = value;
        }

    }

}
