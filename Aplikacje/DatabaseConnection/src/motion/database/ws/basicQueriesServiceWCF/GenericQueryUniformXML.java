
package motion.database.ws.basicQueriesServiceWCF;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="filter" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}ArrayOfFilterPredicate" minOccurs="0"/>
 *         &lt;element name="entitiesToInclude" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}ArrayOfString" minOccurs="0"/>
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
    "filter",
    "entitiesToInclude"
})
@XmlRootElement(name = "GenericQueryUniformXML")
public class GenericQueryUniformXML {

    protected ArrayOfFilterPredicate filter;
    protected ArrayOfString entitiesToInclude;

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFilterPredicate }
     *     
     */
    public ArrayOfFilterPredicate getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFilterPredicate }
     *     
     */
    public void setFilter(ArrayOfFilterPredicate value) {
        this.filter = value;
    }

    /**
     * Gets the value of the entitiesToInclude property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getEntitiesToInclude() {
        return entitiesToInclude;
    }

    /**
     * Sets the value of the entitiesToInclude property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setEntitiesToInclude(ArrayOfString value) {
        this.entitiesToInclude = value;
    }

}
