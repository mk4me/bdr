
package motion.database.ws.test;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSqlParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSqlParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SqlParameter" type="{http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlParameter}SqlParameter" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSqlParameter", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlParameter", propOrder = {
    "sqlParameter"
})
public class ArrayOfSqlParameter {

    @XmlElement(name = "SqlParameter", namespace = "http://schemas.microsoft.com/sqlserver/2004/SOAP/types/SqlParameter", required = true)
    protected List<SqlParameter> sqlParameter;

    /**
     * Gets the value of the sqlParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sqlParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSqlParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SqlParameter }
     * 
     * 
     */
    public List<SqlParameter> getSqlParameter() {
        if (sqlParameter == null) {
            sqlParameter = new ArrayList<SqlParameter>();
        }
        return this.sqlParameter;
    }

}
