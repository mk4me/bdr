
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
 *         &lt;element name="ListMotionKindsDefinedResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MotionKindDefinitionList"/>
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
    "listMotionKindsDefinedResult"
})
@XmlRootElement(name = "ListMotionKindsDefinedResponse")
public class ListMotionKindsDefinedResponse {

    @XmlElement(name = "ListMotionKindsDefinedResult")
    protected ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult listMotionKindsDefinedResult;

    /**
     * Gets the value of the listMotionKindsDefinedResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult }
     *     
     */
    public ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult getListMotionKindsDefinedResult() {
        return listMotionKindsDefinedResult;
    }

    /**
     * Sets the value of the listMotionKindsDefinedResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult }
     *     
     */
    public void setListMotionKindsDefinedResult(ListMotionKindsDefinedResponse.ListMotionKindsDefinedResult value) {
        this.listMotionKindsDefinedResult = value;
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
     *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}MotionKindDefinitionList"/>
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
        "motionKindDefinitionList"
    })
    public static class ListMotionKindsDefinedResult {

        @XmlElement(name = "MotionKindDefinitionList", required = true)
        protected MotionKindDefinitionList motionKindDefinitionList;

        /**
         * Gets the value of the motionKindDefinitionList property.
         * 
         * @return
         *     possible object is
         *     {@link MotionKindDefinitionList }
         *     
         */
        public MotionKindDefinitionList getMotionKindDefinitionList() {
            return motionKindDefinitionList;
        }

        /**
         * Sets the value of the motionKindDefinitionList property.
         * 
         * @param value
         *     allowed object is
         *     {@link MotionKindDefinitionList }
         *     
         */
        public void setMotionKindDefinitionList(MotionKindDefinitionList value) {
            this.motionKindDefinitionList = value;
        }

    }

}
