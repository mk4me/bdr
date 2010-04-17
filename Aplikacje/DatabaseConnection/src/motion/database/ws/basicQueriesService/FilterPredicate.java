
package motion.database.ws.basicQueriesService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FilterPredicate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FilterPredicate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PredicateID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ParentPredicate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ContextEntity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrecedingPredicate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PrecedingOperator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FeatureName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Operator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AggregateFunction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AggregateEntity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterPredicate", propOrder = {
    "predicateID",
    "parentPredicate",
    "contextEntity",
    "precedingPredicate",
    "precedingOperator",
    "featureName",
    "operator",
    "value",
    "aggregateFunction",
    "aggregateEntity"
})
public class FilterPredicate {

    @XmlElement(name = "PredicateID", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int predicateID;
    @XmlElement(name = "ParentPredicate", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int parentPredicate;
    @XmlElement(name = "ContextEntity", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String contextEntity;
    @XmlElement(name = "PrecedingPredicate", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected int precedingPredicate;
    @XmlElement(name = "PrecedingOperator", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String precedingOperator;
    @XmlElement(name = "FeatureName", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String featureName;
    @XmlElement(name = "Operator", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String operator;
    @XmlElement(name = "Value", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String value;
    @XmlElement(name = "AggregateFunction", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String aggregateFunction;
    @XmlElement(name = "AggregateEntity", namespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
    protected String aggregateEntity;

    /**
     * Gets the value of the predicateID property.
     * 
     */
    public int getPredicateID() {
        return predicateID;
    }

    /**
     * Sets the value of the predicateID property.
     * 
     */
    public void setPredicateID(int value) {
        this.predicateID = value;
    }

    /**
     * Gets the value of the parentPredicate property.
     * 
     */
    public int getParentPredicate() {
        return parentPredicate;
    }

    /**
     * Sets the value of the parentPredicate property.
     * 
     */
    public void setParentPredicate(int value) {
        this.parentPredicate = value;
    }

    /**
     * Gets the value of the contextEntity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextEntity() {
        return contextEntity;
    }

    /**
     * Sets the value of the contextEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextEntity(String value) {
        this.contextEntity = value;
    }

    /**
     * Gets the value of the precedingPredicate property.
     * 
     */
    public int getPrecedingPredicate() {
        return precedingPredicate;
    }

    /**
     * Sets the value of the precedingPredicate property.
     * 
     */
    public void setPrecedingPredicate(int value) {
        this.precedingPredicate = value;
    }

    /**
     * Gets the value of the precedingOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrecedingOperator() {
        return precedingOperator;
    }

    /**
     * Sets the value of the precedingOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrecedingOperator(String value) {
        this.precedingOperator = value;
    }

    /**
     * Gets the value of the featureName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Sets the value of the featureName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatureName(String value) {
        this.featureName = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the aggregateFunction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAggregateFunction() {
        return aggregateFunction;
    }

    /**
     * Sets the value of the aggregateFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAggregateFunction(String value) {
        this.aggregateFunction = value;
    }

    /**
     * Gets the value of the aggregateEntity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAggregateEntity() {
        return aggregateEntity;
    }

    /**
     * Sets the value of the aggregateEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAggregateEntity(String value) {
        this.aggregateEntity = value;
    }

}
