
package motion.database.ws.administrationWCF;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "AdministrationOperationException", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AdministrationService")
public class IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private AdministrationOperationException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage(String message, AdministrationOperationException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public IAdministrationWSDefineAttriubeGroupAdministrationOperationExceptionFaultFaultMessage(String message, AdministrationOperationException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: motion.database.ws.administrationWCF.AdministrationOperationException
     */
    public AdministrationOperationException getFaultInfo() {
        return faultInfo;
    }

}
