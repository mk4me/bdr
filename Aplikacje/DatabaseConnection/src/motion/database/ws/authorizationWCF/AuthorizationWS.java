
package motion.database.ws.authorizationWCF;

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
@WebServiceClient(name = "AuthorizationWS", targetNamespace = "http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", wsdlLocation = "http://dbpawell.pjwstk.edu.pl/Motion/res/AuthorizationWSStandalone.wsdl")
public class AuthorizationWS
    extends Service
{

    private final static URL AUTHORIZATIONWS_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(motion.database.ws.authorizationWCF.AuthorizationWS.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = motion.database.ws.authorizationWCF.AuthorizationWS.class.getResource(".");
            url = new URL(baseUrl, "http://dbpawell.pjwstk.edu.pl/Motion/res/AuthorizationWSStandalone.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://dbpawell.pjwstk.edu.pl/Motion/res/AuthorizationWSStandalone.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        AUTHORIZATIONWS_WSDL_LOCATION = url;
    }

    public AuthorizationWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuthorizationWS() {
        super(AUTHORIZATIONWS_WSDL_LOCATION, new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", "AuthorizationWS"));
    }

    /**
     * 
     * @return
     *     returns IAuthorizationWS
     */
    @WebEndpoint(name = "BasicHttpBinding_IAuthorizationWS")
    public IAuthorizationWS getBasicHttpBindingIAuthorizationWS() {
        return super.getPort(new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", "BasicHttpBinding_IAuthorizationWS"), IAuthorizationWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IAuthorizationWS
     */
    @WebEndpoint(name = "BasicHttpBinding_IAuthorizationWS")
    public IAuthorizationWS getBasicHttpBindingIAuthorizationWS(WebServiceFeature... features) {
        return super.getPort(new QName("http://ruch.bytom.pjwstk.edu.pl/MotionDB/AuthorizationService", "BasicHttpBinding_IAuthorizationWS"), IAuthorizationWS.class, features);
    }

}
