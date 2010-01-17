package motion.webservice.client;

import pl.edu.pjwstk.bytom.ruch.motiondb.basicqueriesservice.BasicQueriesService;

public class MotionWebServiceClient {
	
	public MotionWebServiceClient() {
		
	}
	
	public void callWebService() {
		BasicQueriesService service = new BasicQueriesService();
		service.getBasicQueriesServiceSoap().listPerformerSessions(0);
	}
}
