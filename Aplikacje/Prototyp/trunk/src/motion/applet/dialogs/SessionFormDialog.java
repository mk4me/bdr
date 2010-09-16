package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.SwingWorker;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.panels.PrivilegesPanel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.MotionKind;
import motion.database.ws.SessionPrivilegesSetter;

public class SessionFormDialog extends FormDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create a new session.";
	private static String MISSING_SESSION_DATE = "Missing or incorrect session date.";
	private static String MISSING_MOTION_KIND = "Missing motion kind.";
	private static String CREATING_MESSAGE = "Creating a new session...";
	
	private FormDateField sessionDateField;
	private FormTextAreaField sessionDescriptionField;
	private FormListField motionKindField;
	private FormTextField markerSetField;
	private FormNumberField numberOfTrialsField;
	private PrivilegesPanel privilegesPanel;
	
	public SessionFormDialog() {
		super(TITLE, WELCOME_MESSAGE, true);
		
		for (EntityAttributeGroup g : EntityKind.session.getGroupedAttributeCopies()) {
			addDefinedFormFields(g, g.name);
		}
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (SessionFormDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							SessionFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							SessionFormDialog.this.createButton.setEnabled(false);
							try {
								int sessionID = WebServiceInstance.getDatabaseConnection().createSession(
										new int[]{},
										SessionFormDialog.this.getSessionDescription(),
										AppletToolBar.getLabId(),
										SessionFormDialog.this.getSessionDate(),
										SessionFormDialog.this.getMotionKind());
								
								setDefinedAttributes(EntityKind.session, sessionID);
								
								/*
								WebServiceInstance.getDatabaseConnection().setSessionAttribute(
										sessionID,
										"marker_set",
										SessionFormDialog.this.getMarkerSet(),
										false);
								if (!SessionFormDialog.this.getNumberOfTrials().equals("")) {
									WebServiceInstance.getDatabaseConnection().setSessionAttribute(
											sessionID,
											"number_of_trials",
											SessionFormDialog.this.getNumberOfTrials(),
											false);
								}*/
								
								//privileges
								SessionPrivilegesSetter sessionPrivileges = new SessionPrivilegesSetter(sessionID, privilegesPanel.getResult() );
								sessionPrivileges.setUserPrivileges( privilegesPanel.getPrivileges() );
								sessionPrivileges.performDatabaseUpdate();
								
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							SessionFormDialog.this.setVisible(false);
							SessionFormDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
		
		privilegesPanel = new PrivilegesPanel(this); 
		this.getFormTabbedPane().addTab("Privileges", privilegesPanel);
	}
	
	private String[] getMotionKinds() throws Exception {	//TODO: Add thread.
		Vector<MotionKind> motionKinds = WebServiceInstance.getDatabaseConnection().listMotionKindsDefined();
		String[] motionKindsStrings = new String[motionKinds.size()];
		
		int i = 0;
		for (MotionKind m : motionKinds) {
			motionKindsStrings[i] = m.name;
			i++;
		}
		
		return motionKindsStrings;
	}
	
	private XMLGregorianCalendar getSessionDate() {
		try {
			return sessionDateField.getData();
		} catch (ParseException e) {
		} catch (DatatypeConfigurationException e) {
		}
		
		return null;
	}
	
	private String getSessionDescription() {
		
		return sessionDescriptionField.getData();
	}
	
	private String getMotionKind() {
		
		return motionKindField.getData();
	}
	
	private String getMarkerSet() {
		
		return markerSetField.getData();
	}
	
	private String getNumberOfTrials() {
		String numberOfTrials = "";
		try {
			numberOfTrials = "" + numberOfTrialsField.getData();
		} catch (NumberFormatException e) {
		}
		
		return numberOfTrials;
	}
	
	private boolean validateResult() {
		if (getSessionDate() == null) {
			this.messageLabel.setText(MISSING_SESSION_DATE);
			
			return false;
		} else if (getMotionKind().equals("")) {
			this.messageLabel.setText(MISSING_MOTION_KIND);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return true;
	}
}