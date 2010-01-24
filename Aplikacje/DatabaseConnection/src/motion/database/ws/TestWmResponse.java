
package motion.database.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for test_wmResponse element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="test_wmResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="test_wmResult" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream}SqlResultStream"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "testWmResult"
})
@XmlRootElement(name = "test_wmResponse", namespace = "http://tempuri.org")
public class TestWmResponse {

    @XmlElement(name = "test_wmResult", namespace = "http://tempuri.org", required = true)
    protected SqlResultStream testWmResult;

    /**
     * Gets the value of the testWmResult property.
     * 
     * @return
     *     possible object is
     *     {@link SqlResultStream }
     *     
     */
    public SqlResultStream getTestWmResult() {
        return testWmResult;
    }

    /**
     * Sets the value of the testWmResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SqlResultStream }
     *     
     */
    public void setTestWmResult(SqlResultStream value) {
        this.testWmResult = value;
    }

}
