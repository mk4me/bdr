
package motion.database.ws.test;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for ParameterDirection.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParameterDirection">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Input"/>
 *     &lt;enumeration value="InputOutput"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ParameterDirection {

    @XmlEnumValue("Input")
    INPUT("Input"),
    @XmlEnumValue("InputOutput")
    INPUT_OUTPUT("InputOutput");
    private final String value;

    ParameterDirection(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ParameterDirection fromValue(String v) {
        for (ParameterDirection c: ParameterDirection.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
