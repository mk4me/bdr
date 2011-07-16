
package motion.database.ws.basicUpdatesServiceWCF;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "UpdateException", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicUpdatesService")
public class IBasicUpdatesWSAssignSessionToGroupUpdateExceptionFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private UpdateException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public IBasicUpdatesWSAssignSessionToGroupUpdateExceptionFaultFaultMessage(String message, UpdateException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public IBasicUpdatesWSAssignSessionToGroupUpdateExceptionFaultFaultMessage(String message, UpdateException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: motion.database.ws.basicUpdatesServiceWCF.UpdateException
     */
    public UpdateException getFaultInfo() {
        return faultInfo;
    }

}
