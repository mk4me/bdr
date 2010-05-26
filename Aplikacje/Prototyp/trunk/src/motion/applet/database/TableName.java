package motion.applet.database;

import java.util.ArrayList;
import java.util.HashMap;

import motion.applet.Messages;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.AttributeName;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.TrialStaticAttributes;

public class TableName {
	// Database table names (defined only here for the applet).
	protected static final String PERFORMER_TABLE = "Performer";
	protected static final String SESSION_TABLE = "Sesja";
	protected static final String TRIAL_TABLE = "Obserwacja";
	protected static final String PATIENT_TABLE = "Pacjent";
	
	// English database table names (defined only here for the applet).
	protected static final String PERFORMER_TABLE_ENG = Messages.getString("TableName.Performer"); //$NON-NLS-1$
	protected static final String SESSION_TABLE_ENG = Messages.getString("TableName.Session"); //$NON-NLS-1$
	protected static final String TRIAL_TABLE_ENG = Messages.getString("TableName.Trial"); //$NON-NLS-1$
	protected static final String PATIENT_TABLE_ENG = Messages.getString("TableName.Patient"); //$NON-NLS-1$
	
	private final String table;
	private final String label;
	private final ArrayList<AttributeName> staticAttributes = new ArrayList<AttributeName>();
	//private final ArrayList<AttributeName> definedAttributes = new ArrayList<AttributeName>();
	private final ArrayList<AttributeGroup> groupedDefinedAttributes = new ArrayList<AttributeGroup>();
	
	public TableName(String table, String label) {
		this.table = table;
		this.label = label;
		fillStaticAttributes();
		fillDefinedAttributes();
	}
	
	private void fillStaticAttributes() {
		if (this.table.equals(PERFORMER_TABLE)) {
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.performerID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.firstName.toString(), AttributeName.STRING_TYPE));
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.lastName.toString(), AttributeName.STRING_TYPE));
		} else if (this.table.equals(SESSION_TABLE)) {
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.userID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.labID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.motionKindID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.performerID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionDate.toString(), AttributeName.DATE_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionDescription.toString(), AttributeName.STRING_TYPE));
		} else if (this.table.equals(TRIAL_TABLE)) {
			staticAttributes.add(new AttributeName(TrialStaticAttributes.trialID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.sessionID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.duration.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.trialDescription.toString(), AttributeName.STRING_TYPE));
		}
	}
	
	private void fillDefinedAttributes() {
		try {
			//HashMap<String, String> results = WebServiceInstance.getDatabaseConnection().listAttributesDefined("_ALL", table);
			//for (String s : results.keySet()) {
				//definedAttributes.add(new AttributeName(s, toTypeString(results.get(s))));
			//}
			
			HashMap<String, EntityAttributeGroup> results = WebServiceInstance.getDatabaseConnection().listGrouppedAttributesDefined(table);
			for (String s : results.keySet()) {
				ArrayList<AttributeName> attributes = new ArrayList<AttributeName>();
				for (EntityAttribute e : results.get(s)) {
					attributes.add(new AttributeName(e.name, toTypeString(e.type)));
				}
				groupedDefinedAttributes.add(new AttributeGroup(s, attributes));
			}
		} catch (Exception e) {
			ExceptionDialog exceptionDialog = new ExceptionDialog(e);
			exceptionDialog.setVisible(true);
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
		ArrayList<AttributeName> all = new ArrayList<AttributeName>();
		for (AttributeGroup a : this.groupedDefinedAttributes) {
			all.addAll(a.getAttributes());
		}
		
		return new ArrayList<AttributeName>(all);
		//return this.definedAttributes;
	}
	
	public ArrayList<AttributeName> getAllAttributes() {
		ArrayList<AttributeName> all = new ArrayList<AttributeName>();
		all.addAll(staticAttributes);
		all.addAll(getDefinedAttributes());
		
		return new ArrayList<AttributeName>(all);
	}
	
	public ArrayList<AttributeName> getSelectedAttributes(ArrayList<String> selectedAttributes) {
		if (selectedAttributes.isEmpty()) {
			
			//return getAllAttributes();
			return new ArrayList<AttributeName>();
		} else {
			ArrayList<AttributeName> all = new ArrayList<AttributeName>();
			for (AttributeName a : staticAttributes) {
				if (selectedAttributes.contains(a.toString())) {
					all.add(a);
				}
			}
			
			for (AttributeName a : getDefinedAttributes()) {
				if (selectedAttributes.contains(a.toString())) {
					all.add(a);
				}
			}
			
			return new ArrayList<AttributeName>(all);
		}
	}
	
	public ArrayList<AttributeGroup> getGroupedAttributes() {
		
		return this.groupedDefinedAttributes;
	}
}
