
package motion.database.ws.basicQueriesService;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the motion.database.ws.basicQueriesService package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: motion.database.ws.basicQueriesService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListPerformerSessions }
     * 
     */
    public ListPerformerSessions createListPerformerSessions() {
        return new ListPerformerSessions();
    }

    /**
     * Create an instance of {@link ListPerformerSessionsResponse }
     * 
     */
    public ListPerformerSessionsResponse createListPerformerSessionsResponse() {
        return new ListPerformerSessionsResponse();
    }

    /**
     * Create an instance of {@link SessionDetails }
     * 
     */
    public SessionDetails createSessionDetails() {
        return new SessionDetails();
    }

    /**
     * Create an instance of {@link ListSessionFilesResponse }
     * 
     */
    public ListSessionFilesResponse createListSessionFilesResponse() {
        return new ListSessionFilesResponse();
    }

    /**
     * Create an instance of {@link ArrayOfFileDetails }
     * 
     */
    public ArrayOfFileDetails createArrayOfFileDetails() {
        return new ArrayOfFileDetails();
    }

    /**
     * Create an instance of {@link ListSessionFiles }
     * 
     */
    public ListSessionFiles createListSessionFiles() {
        return new ListSessionFiles();
    }

    /**
     * Create an instance of {@link FileDetails }
     * 
     */
    public FileDetails createFileDetails() {
        return new FileDetails();
    }

    /**
     * Create an instance of {@link ArrayOfSessionDetails }
     * 
     */
    public ArrayOfSessionDetails createArrayOfSessionDetails() {
        return new ArrayOfSessionDetails();
    }

}
