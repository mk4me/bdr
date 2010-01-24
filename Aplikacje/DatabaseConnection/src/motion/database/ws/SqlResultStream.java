
package motion.database.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SqlResultStream complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SqlResultStream">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="SqlRowSet" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types}SqlRowSet"/>
 *         &lt;element name="SqlXml" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types}SqlXml"/>
 *         &lt;element name="SqlMessage" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlMessage}SqlMessage"/>
 *         &lt;element name="SqlRowCount" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlRowCount}SqlRowCount"/>
 *         &lt;element name="SqlResultCode" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types}SqlResultCode"/>
 *         &lt;element name="SqlTransaction" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlTransaction}SqlTransaction"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SqlResultStream", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream", propOrder = {
    "sqlRowSetOrSqlXmlOrSqlMessage"
})
public class SqlResultStream {

    @XmlElements({
        @XmlElement(name = "SqlMessage", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream", required = true, type = SqlMessage.class),
        @XmlElement(name = "SqlXml", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream", required = true, type = SqlXml.class),
        @XmlElement(name = "SqlRowSet", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream", required = true, type = SqlRowSet.class),
        @XmlElement(name = "SqlRowCount", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream", required = true, type = SqlRowCount.class),
        @XmlElement(name = "SqlTransaction", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream", required = true, type = SqlTransaction.class),
        @XmlElement(name = "SqlResultCode", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlResultStream", required = true, type = Integer.class)
    })
    protected List<Object> sqlRowSetOrSqlXmlOrSqlMessage;

    /**
     * Gets the value of the sqlRowSetOrSqlXmlOrSqlMessage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sqlRowSetOrSqlXmlOrSqlMessage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSqlRowSetOrSqlXmlOrSqlMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SqlMessage }
     * {@link SqlXml }
     * {@link SqlRowSet }
     * {@link SqlRowCount }
     * {@link SqlTransaction }
     * {@link Integer }
     * 
     * 
     */
    public List<Object> getSqlRowSetOrSqlXmlOrSqlMessage() {
        if (sqlRowSetOrSqlXmlOrSqlMessage == null) {
            sqlRowSetOrSqlXmlOrSqlMessage = new ArrayList<Object>();
        }
        return this.sqlRowSetOrSqlXmlOrSqlMessage;
    }

}
