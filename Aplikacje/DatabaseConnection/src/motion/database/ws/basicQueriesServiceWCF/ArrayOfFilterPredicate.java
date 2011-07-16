
package motion.database.ws.basicQueriesServiceWCF;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFilterPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFilterPredicate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FilterPredicate" type="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}FilterPredicate" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFilterPredicate", propOrder = {
    "filterPredicate"
})
public class ArrayOfFilterPredicate {

    @XmlElement(name = "FilterPredicate")
    protected List<FilterPredicate> filterPredicate;

    /**
     * Gets the value of the filterPredicate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filterPredicate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilterPredicate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FilterPredicate }
     * 
     * 
     */
    public List<FilterPredicate> getFilterPredicate() {
        if (filterPredicate == null) {
            filterPredicate = new ArrayList<FilterPredicate>();
        }
        return this.filterPredicate;
    }

}
