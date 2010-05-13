
package motion.database.ws.basicQueriesServiceWCF;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "QueryException", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService")
public class IBasicQueriesWSListTrialSegmentsXMLQueryExceptionFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private QueryException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public IBasicQueriesWSListTrialSegmentsXMLQueryExceptionFaultFaultMessage(String message, QueryException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public IBasicQueriesWSListTrialSegmentsXMLQueryExceptionFaultFaultMessage(String message, QueryException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: motion.database.ws.BasicQueriesServiceWCF.QueryException
     */
    public QueryException getFaultInfo() {
        return faultInfo;
    }

}
