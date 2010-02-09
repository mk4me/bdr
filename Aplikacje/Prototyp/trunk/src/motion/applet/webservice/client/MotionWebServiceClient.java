package motion.applet.webservice.client;

import pl.edu.pjwstk.bytom.ruch.motiondb.basicqueriesservice.ArrayOfFileDetails;
import pl.edu.pjwstk.bytom.ruch.motiondb.basicqueriesservice.ArrayOfSessionDetails;
import pl.edu.pjwstk.bytom.ruch.motiondb.basicqueriesservice.BasicQueriesService;

public class MotionWebServiceClient {
	
	public MotionWebServiceClient() {
		
	}
	
	public void callWebService() {
		BasicQueriesService service = new BasicQueriesService();
		ArrayOfSessionDetails sessionDetails = service.getBasicQueriesServiceSoap().listPerformerSessions(1);
		System.out.println(sessionDetails.getSessionDetails());
		
		ArrayOfFileDetails fileDetails = service.getBasicQueriesServiceSoap().listSessionFiles(1);
		System.out.println(fileDetails.getFileDetails());
		
	}
}
