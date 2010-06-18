
package motion.database.ws.basicQueriesService;

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
@WebServiceClient(name = "BasicQueriesService", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", wsdlLocation = "http://db-bdr.pjwstk.edu.pl/Motion/res/BasicQueriesService.wsdl")
public class BasicQueriesService
    extends Service
{

    private final static URL BASICQUERIESSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://db-bdr.pjwstk.edu.pl/Motion/res/BasicQueriesService.wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BASICQUERIESSERVICE_WSDL_LOCATION = url;
    }

    public BasicQueriesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BasicQueriesService() {
        super(BASICQUERIESSERVICE_WSDL_LOCATION, new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "BasicQueriesService"));
    }

    /**
     * 
     * @return
     *     returns BasicQueriesServiceSoap
     */
    @WebEndpoint(name = "BasicQueriesServiceSoap")
    public BasicQueriesServiceSoap getBasicQueriesServiceSoap() {
        return (BasicQueriesServiceSoap)super.getPort(new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/BasicQueriesService", "BasicQueriesServiceSoap"), BasicQueriesServiceSoap.class);
    }

}
