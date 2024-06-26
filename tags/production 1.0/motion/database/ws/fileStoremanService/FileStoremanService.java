
package motion.database.ws.fileStoremanService;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b59-fcs
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "FileStoremanService", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", wsdlLocation = "http://dbpawell.pjwstk.edu.pl/Motion/FileStoremanService.asmx?WSDL")
public class FileStoremanService
    extends Service
{

    private final static URL FILESTOREMANSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://dbpawell.pjwstk.edu.pl/Motion/FileStoremanService.asmx?WSDL");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        FILESTOREMANSERVICE_WSDL_LOCATION = url;
    }

    public FileStoremanService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FileStoremanService() {
        super(FILESTOREMANSERVICE_WSDL_LOCATION, new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", "FileStoremanService"));
    }

    /**
     * 
     * @return
     *     returns FileStoremanServiceSoap
     */
    @WebEndpoint(name = "FileStoremanServiceSoap")
    public FileStoremanServiceSoap getFileStoremanServiceSoap() {
        return (FileStoremanServiceSoap)super.getPort(new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", "FileStoremanServiceSoap"), FileStoremanServiceSoap.class);
    }

}
