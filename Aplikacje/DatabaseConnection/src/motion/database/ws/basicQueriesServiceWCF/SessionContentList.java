
package motion.database.ws.basicQueriesServiceWCF;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService}SessionContent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="currentPage" type="{http://www.w3.org/2001/XMLSchema}short" />
 *       &lt;attribute name="pages" type="{http://www.w3.org/2001/XMLSchema}short" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionContent"
})
@XmlRootElement(name = "SessionContentList")
public class SessionContentList {

    @XmlElement(name = "SessionContent")
    protected List<SessionContent> sessionContent;
    @XmlAttribute
    protected Short currentPage;
    @XmlAttribute
    protected Short pages;

    /**
     * Gets the value of the sessionContent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sessionContent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSessionContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SessionContent }
     * 
     * 
     */
    public List<SessionContent> getSessionContent() {
        if (sessionContent == null) {
            sessionContent = new ArrayList<SessionContent>();
        }
        return this.sessionContent;
    }

    /**
     * Gets the value of the currentPage property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the value of the currentPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCurrentPage(Short value) {
        this.currentPage = value;
    }

    /**
     * Gets the value of the pages property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getPages() {
        return pages;
    }

    /**
     * Sets the value of the pages property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPages(Short value) {
        this.pages = value;
    }

}
