package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;

public class TrialFormDialog extends FormDialog {
	public static String TITLE = "New trial";
	private static String TITLE_EDIT = "Edit trial";
	public static String WELCOME_MESSAGE = "Create attribute new trial.";
	private static String WELCOME_MESSAGE_EDIT = "Edit trial attribute values.";
	private static String MISSING_TRIAL_DESCRIPTION = "Missing trial description.";
	private static String CREATING_MESSAGE = "Creating attribute new trial...";
	
	private int sessionId;
	
	public TrialFormDialog(String title, String welcomeMessage, int sessionId) {
		super(title, welcomeMessage);
		this.sessionId = sessionId;
		
		for (EntityAttributeGroup g : EntityKind.trial.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			addFormFields(g, g.name);
		}
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (TrialFormDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							TrialFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							TrialFormDialog.this.createButton.setEnabled(false);
							try {
								int trialID = WebServiceInstance.getDatabaseConnection().createTrial(
										TrialFormDialog.this.sessionId,
										TrialFormDialog.this.getTrialDescription()
										);
								setDefinedAttributes(trialID);
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							TrialFormDialog.this.setVisible(false);
							TrialFormDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
	}
	
	public TrialFormDialog(int sessionId, Trial trial) {
		this(TITLE_EDIT, WELCOME_MESSAGE_EDIT, sessionId);
		fillFormFields(trial);
	}
	
	private ArrayList<String> getDeselectedAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(TrialStaticAttributes.TrialID.toString());
		attributes.add(TrialStaticAttributes.SessionID.toString());
		
		return attributes;
	}
	
	private String getTrialDescription() {
		
		return (String) getAttributeValue(EntityKind.trial, TrialStaticAttributes.TrialDescription.toString());
	}
	
	protected boolean validateResult() {
		// Allow empty string static attributes.
		/*
		if (getTrialDescription().equals("")) {
			this.messageLabel.setText(MISSING_TRIAL_DESCRIPTION);
			
			return false;
		} 
		*/
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return super.validateResult();
	}
}