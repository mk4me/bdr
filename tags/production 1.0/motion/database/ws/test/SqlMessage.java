
package motion.database.ws.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SqlMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SqlMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Class" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage}nonNegativeInteger"/>
 *         &lt;element name="LineNumber" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage}nonNegativeInteger"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Number" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage}nonNegativeInteger"/>
 *         &lt;element name="Procedure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Server" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="State" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage}nonNegativeInteger"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SqlMessage", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage", propOrder = {
    "clazz",
    "lineNumber",
    "message",
    "number",
    "procedure",
    "server",
    "source",
    "state"
})
public class SqlMessage {

    @XmlElement(name = "Class", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage")
    protected int clazz;
    @XmlElement(name = "LineNumber", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage")
    protected int lineNumber;
    @XmlElement(name = "Message", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage", required = true)
    protected String message;
    @XmlElement(name = "Number", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage")
    protected int number;
    @XmlElement(name = "Procedure", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage")
    protected String procedure;
    @XmlElement(name = "Server", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage")
    protected String server;
    @XmlElement(name = "Source", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage", required = true)
    protected String source;
    @XmlElement(name = "State", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage")
    protected int state;

    /**
     * Gets the value of the clazz property.
     * 
     */
    public int getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     */
    public void setClazz(int value) {
        this.clazz = value;
    }

    /**
     * Gets the value of the lineNumber property.
     * 
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets the value of the lineNumber property.
     * 
     */
    public void setLineNumber(int value) {
        this.lineNumber = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the number property.
     * 
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     */
    public void setNumber(int value) {
        this.number = value;
    }

    /**
     * Gets the value of the procedure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcedure() {
        return procedure;
    }

    /**
     * Sets the value of the procedure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcedure(String value) {
        this.procedure = value;
    }

    /**
     * Gets the value of the server property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the value of the server property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServer(String value) {
        this.server = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the state property.
     * 
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     */
    public void setState(int value) {
        this.state = value;
    }

}
