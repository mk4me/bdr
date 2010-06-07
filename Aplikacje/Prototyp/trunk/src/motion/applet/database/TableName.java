package motion.applet.database;

import java.util.ArrayList;
import java.util.HashMap;

import motion.applet.Messages;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.AttributeName;
import motion.database.model.DatabaseFileStaticAttributes;
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
	protected static final String FILE_TABLE = "Plik";
	
	// English entity names (defined only here for the applet).
	protected static final String PERFORMER_ENTITY = "Performer";
	protected static final String SESSION_ENTITY = "Session";
	protected static final String TRIAL_ENTITY = "Trial";
	protected static final String PATIENT_ENTITY = "Patient";
	protected static final String FILE_ENTITY = "File";
	
	// Translatable database table names.
	protected static final String PERFORMER_LABEL = Messages.getString("TableName.Performer"); //$NON-NLS-1$
	protected static final String SESSION_LABEL = Messages.getString("TableName.Session"); //$NON-NLS-1$
	protected static final String TRIAL_LABEL = Messages.getString("TableName.Trial"); //$NON-NLS-1$
	protected static final String PATIENT_LABEL = Messages.getString("TableName.Patient"); //$NON-NLS-1$
	protected static final String FILE_LABEL = Messages.getString("TableName.File"); //$NON-NLS-1$
	
	private final String table;	// Database table name (in Polish)
	private final String entity;	// Entity name (in English)
	private final String label;	// Table name (in English or in Polish depending on language setting)
	private final ArrayList<AttributeName> staticAttributes = new ArrayList<AttributeName>();
	//private final ArrayList<AttributeName> definedAttributes = new ArrayList<AttributeName>();
	private final ArrayList<AttributeGroup> groupedDefinedAttributes = new ArrayList<AttributeGroup>();
	
	public TableName(String table, String entity, String label) {
		this.table = table;
		this.entity = entity;
		this.label = label;
		
		fillStaticAttributes();
		fillDefinedAttributes();
	}
	
	private void fillStaticAttributes() {
		if (this.entity.equals(PERFORMER_ENTITY)) {
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.performerID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.firstName.toString(), AttributeName.STRING_TYPE));
			staticAttributes.add(new AttributeName(PerformerStaticAttributes.lastName.toString(), AttributeName.STRING_TYPE));
		} else if (this.entity.equals(SESSION_ENTITY)) {
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.userID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.labID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.motionKindID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.performerID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionDate.toString(), AttributeName.DATE_TYPE));
			staticAttributes.add(new AttributeName(SessionStaticAttributes.sessionDescription.toString(), AttributeName.STRING_TYPE));
		} else if (this.entity.equals(TRIAL_ENTITY)) {
			staticAttributes.add(new AttributeName(TrialStaticAttributes.trialID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.sessionID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.duration.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(TrialStaticAttributes.trialDescription.toString(), AttributeName.STRING_TYPE));
		} else if (this.entity.equals(FILE_ENTITY)) {
			staticAttributes.add(new AttributeName(DatabaseFileStaticAttributes.fileID.toString(), AttributeName.INTEGER_TYPE));
			staticAttributes.add(new AttributeName(DatabaseFileStaticAttributes.fileName.toString(), AttributeName.STRING_TYPE));
			staticAttributes.add(new AttributeName(DatabaseFileStaticAttributes.fileDescription.toString(), AttributeName.STRING_TYPE));
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
		
		return "Table: " + this.table + ", Entity: " + this.entity + ", Label: " + this.label;
	}
	
	public String getTable() {
		
		return this.table;
	}
	
	public String getEntity() {
		
		return this.entity;
	}
	
	public String getLabel() {
		
		return this.label;
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
	/*
	public AttributeName getStaticAttributeName(String attributeName) {
		for (AttributeName a : this.staticAttributes) {
			if (a.toString().equals(attributeName)) {
				
				return a;
			}
		}
		
		return null;
	}*/
}
