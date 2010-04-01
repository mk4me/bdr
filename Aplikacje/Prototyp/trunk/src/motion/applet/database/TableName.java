package motion.applet.database;

import java.util.ArrayList;
import java.util.HashMap;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.PerformerStaticAttributes;
import motion.database.SessionStaticAttributes;
import motion.database.TrialStaticAttributes;

public class TableName {
	// Database table names (defined only here for the applet).
	protected static final String PERFORMER_TABLE = "Performer";
	protected static final String SESSION_TABLE = "Sesja";
	protected static final String TRIAL_TABLE = "Obserwacja";
	protected static final String PATIENT_TABLE = "Pacjent";
	
	// English database table names (defined only here for the applet).
	protected static final String PERFORMER_TABLE_ENG = "Performer";
	protected static final String SESSION_TABLE_ENG = "Session";
	protected static final String TRIAL_TABLE_ENG = "Trial";
	protected static final String PATIENT_TABLE_ENG = "Patient";
	
	private final String table;
	private final String label;
	private final ArrayList<AttributeName> staticAttributes = new ArrayList<AttributeName>();
	private final ArrayList<AttributeName> definedAttributes = new ArrayList<AttributeName>();
	
	public TableName(String table, String label) {
		this.table = table;
		this.label = label;
		fillStaticAttributes();
		fillDefinedAttributes();
	}
	
	private void fillStaticAttributes() {
		if (this.table.equals(PERFORMER_TABLE)) {
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.performerID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.firstName.toString(), "String"));
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.lastName.toString(), "String"));
		} else if (this.table.equals(SESSION_TABLE)) {
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.userID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.labID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.motionKindID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.performerID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionDate.toString(), "Date"));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionDescription.toString(), "String"));
		} else if (this.table.equals(TRIAL_TABLE)) {
			staticAttributes.add(new AttributeName(TrialStaticAttributes.trialID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.sessionID.toString(), "Integer"));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.duration.toString(), "Integer"));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.trialDescription.toString(), "String"));
		}
	}
	
	private void fillDefinedAttributes() {
		try {
			HashMap<String, String> results = WebServiceInstance.getDatabaseConnection().listAttributesDefined("_ALL", table);
			for (String s : results.keySet()) {
				definedAttributes.add(new AttributeName(s, toTypeString(results.get(s))));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Types returned by WS from .NET to applet types.
	private String toTypeString(String type) {
		if (type.equals("string")) {
			return AttributeName.STRING_TYPE;
		} else if (type.equals("integer")) {
			return AttributeName.INTEGER_TYPE;
		} else if (type.equals("date")) {
			return AttributeName.DATE_TYPE;
		} else { // Unknown type.
			return AttributeName.UNKNOWN_TYPE;
		}
	}
	
	public String toString() {
		
		return this.label;
	}
	
	public String getTableName() {
		
		return this.table;
	}
	
	public ArrayList<AttributeName> getStaticAttributes() {
		
		return this.staticAttributes;
	}
	
	public ArrayList<AttributeName> getDefinedAttributes() {
		
		return this.definedAttributes;
	}
	
	public ArrayList<AttributeName> getAllAttributes() {
		ArrayList<AttributeName> all = new ArrayList<AttributeName>();
		all.addAll(staticAttributes);
		all.addAll(definedAttributes);
		
		return new ArrayList<AttributeName>(all);
	}
}
