
package motion.database.ws.userPersonalSpaceWCF;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "UPSException", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/UserPersonalSpaceService")
public class IUserPersonalSpaceWSListViewConfigurationUPSExceptionFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private UPSException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public IUserPersonalSpaceWSListViewConfigurationUPSExceptionFaultFaultMessage(String message, UPSException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public IUserPersonalSpaceWSListViewConfigurationUPSExceptionFaultFaultMessage(String message, UPSException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: motion.database.ws.userPersonalSpaceWCF.UPSException
     */
    public UPSException getFaultInfo() {
        return faultInfo;
    }

}
