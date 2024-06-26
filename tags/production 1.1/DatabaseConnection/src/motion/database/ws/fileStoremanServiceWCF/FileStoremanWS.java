
package motion.database.ws.fileStoremanServiceWCF;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "FileStoremanWS", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", wsdlLocation = "http://db-bdr.pjwstk.edu.pl/Motion/FileStoremanWS.svc?wsdl")
public class FileStoremanWS
    extends Service
{

    private final static URL FILESTOREMANWS_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(motion.database.ws.fileStoremanServiceWCF.FileStoremanWS.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = motion.database.ws.fileStoremanServiceWCF.FileStoremanWS.class.getResource(".");
            url = new URL(baseUrl, "http://db-bdr.pjwstk.edu.pl/Motion/FileStoremanWS.svc?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://db-bdr.pjwstk.edu.pl/Motion/FileStoremanWS.svc?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        FILESTOREMANWS_WSDL_LOCATION = url;
    }

    public FileStoremanWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FileStoremanWS() {
        super(FILESTOREMANWS_WSDL_LOCATION, new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", "FileStoremanWS"));
    }

    /**
     * 
     * @return
     *     returns IFileStoremanWS
     */
    @WebEndpoint(name = "BasicHttpBinding_IFileStoremanWS")
    public IFileStoremanWS getBasicHttpBindingIFileStoremanWS() {
        return super.getPort(new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", "BasicHttpBinding_IFileStoremanWS"), IFileStoremanWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IFileStoremanWS
     */
    @WebEndpoint(name = "BasicHttpBinding_IFileStoremanWS")
    public IFileStoremanWS getBasicHttpBindingIFileStoremanWS(WebServiceFeature... features) {
        return super.getPort(new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/FileStoremanService", "BasicHttpBinding_IFileStoremanWS"), IFileStoremanWS.class, features);
    }

}
