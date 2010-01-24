
package motion.database.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * SQL Server SOAP Session
 * 
 * <p>Java class for sqlSession element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="sqlSession">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;attribute name="initiate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *         &lt;attribute name="sessionId" type="{http://www.w3.org/2001/XMLSchema}base64Binary" />
 *         &lt;attribute name="terminate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *         &lt;attribute name="timeout" type="{http://www.w3.org/2001/XMLSchema}int" />
 *         &lt;attribute name="transactionDescriptor" type="{http://www.w3.org/2001/XMLSchema}base64Binary" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "sqlSession")
public class SqlSession {

    @XmlAttribute
    protected Boolean initiate;
    @XmlAttribute
    protected byte[] sessionId;
    @XmlAttribute
    protected Boolean terminate;
    @XmlAttribute
    protected Integer timeout;
    @XmlAttribute
    protected byte[] transactionDescriptor;

    /**
     * Gets the value of the initiate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isInitiate() {
        if (initiate == null) {
            return false;
        } else {
            return initiate;
        }
    }

    /**
     * Sets the value of the initiate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInitiate(Boolean value) {
        this.initiate = value;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSessionId(byte[] value) {
        this.sessionId = ((byte[]) value);
    }

    /**
     * Gets the value of the terminate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isTerminate() {
        if (terminate == null) {
            return false;
        } else {
            return terminate;
        }
    }

    /**
     * Sets the value of the terminate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTerminate(Boolean value) {
        this.terminate = value;
    }

    /**
     * Gets the value of the timeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeout(Integer value) {
        this.timeout = value;
    }

    /**
     * Gets the value of the transactionDescriptor property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getTransactionDescriptor() {
        return transactionDescriptor;
    }

    /**
     * Sets the value of the transactionDescriptor property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setTransactionDescriptor(byte[] value) {
        this.transactionDescriptor = ((byte[]) value);
    }

}
