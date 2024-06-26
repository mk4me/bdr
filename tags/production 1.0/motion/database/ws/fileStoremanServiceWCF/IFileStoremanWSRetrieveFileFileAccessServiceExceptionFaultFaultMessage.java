
package motion.database.ws.fileStoremanServiceWCF;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "FileAccessServiceException", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService")
public class IFileStoremanWSRetrieveFileFileAccessServiceExceptionFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private FileAccessServiceException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public IFileStoremanWSRetrieveFileFileAccessServiceExceptionFaultFaultMessage(String message, FileAccessServiceException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public IFileStoremanWSRetrieveFileFileAccessServiceExceptionFaultFaultMessage(String message, FileAccessServiceException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: motion.database.ws.fileStoremanServiceWCF.FileAccessServiceException
     */
    public FileAccessServiceException getFaultInfo() {
        return faultInfo;
    }

}
