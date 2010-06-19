package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.SwingWorker;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.database.TableNamesInstance;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.MotionKind;

public class SessionFormDialog extends FormDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create new session.";
	private static String MISSING_SESSION_DATE = "Missing or incorrect session date.";
	private static String MISSING_MOTION_KIND = "Missing motion kind.";
	private static String CREATING_MESSAGE = "Creating a new session...";
	
	private FormDateField sessionDateField;
	private FormTextAreaField sessionDescriptionField;
	private FormListField motionKindField;
	private FormTextField markerSetField;
	private FormNumberField numberOfTrialsField;
	
	private int performerId;
	
	public SessionFormDialog(int performerId) {
		super(TITLE, WELCOME_MESSAGE);
		this.performerId = performerId;
		
		addFormFields();
		
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
										SessionFormDialog.this.performerId,
										new int[]{},
										SessionFormDialog.this.getSessionDescription(),
										AppletToolBar.getLabId(),
										1,
										SessionFormDialog.this.getSessionDate(),
										SessionFormDialog.this.getMotionKind());
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
								}
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
	}
	
	private void addFormFields() {
		
		addFormTextLabel("Static attributes:");
		sessionDateField = new FormDateField(
				TableNamesInstance.SESSION.getAttributeName("sessionDate"), gridBagConstraints, formPanel, true);
		sessionDescriptionField = new FormTextAreaField(
				TableNamesInstance.SESSION.getAttributeName("sessionDescription"), gridBagConstraints, formPanel);
		try {
			motionKindField = new FormListField(
					TableNamesInstance.SESSION.getAttributeName("motionKindID"), gridBagConstraints, formPanel, getMotionKinds());
		} catch (Exception e) {
			ExceptionDialog exceptionDialog = new ExceptionDialog(e);
			exceptionDialog.setVisible(true);
		}
		
		addFormTextLabel("Defined attributes:");
		markerSetField = new FormTextField(
				TableNamesInstance.SESSION.getAttributeName("marker_set"), gridBagConstraints, formPanel);
		numberOfTrialsField = new FormNumberField(
				TableNamesInstance.SESSION.getAttributeName("number_of_trials"), gridBagConstraints, formPanel);
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