
package motion.database.ws.basicQueriesServiceWCF;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="MotionKindDefinition" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MotionKind" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="MotionKindName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "motionKindDefinition"
})
@XmlRootElement(name = "MotionKindDefinitionList")
public class MotionKindDefinitionList {

    @XmlElement(name = "MotionKindDefinition")
    protected List<MotionKindDefinitionList.MotionKindDefinition> motionKindDefinition;

    /**
     * Gets the value of the motionKindDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the motionKindDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMotionKindDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MotionKindDefinitionList.MotionKindDefinition }
     * 
     * 
     */
    public List<MotionKindDefinitionList.MotionKindDefinition> getMotionKindDefinition() {
        if (motionKindDefinition == null) {
            motionKindDefinition = new ArrayList<MotionKindDefinitionList.MotionKindDefinition>();
        }
        return this.motionKindDefinition;
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
     *         &lt;element name="MotionKind" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="MotionKindName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "motionKindID",
        "motionKindName"
    })
    public static class MotionKindDefinition {

        @XmlElement(name = "MotionKind")
        protected int motionKindID;
        @XmlElement(name = "MotionKindName", required = true)
        protected String motionKindName;

        /**
         * Gets the value of the motionKindID property.
         * 
         */
        public int getMotionKindID() {
            return motionKindID;
        }

        /**
         * Sets the value of the motionKindID property.
         * 
         */
        public void setMotionKindID(int value) {
            this.motionKindID = value;
        }

        /**
         * Gets the value of the motionKindName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMotionKindName() {
            return motionKindName;
        }

        /**
         * Sets the value of the motionKindName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMotionKindName(String value) {
            this.motionKindName = value;
        }

    }

}
