package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.database.AttributeGroup;
import motion.applet.database.TableNamesInstance;
import motion.applet.webservice.client.WebServiceInstance;

public class TrialFormDialog extends FormDialog {
	private static String TITLE = "New trial";
	private static String WELCOME_MESSAGE = "Create new trial.";
	private static String MISSING_TRIAL_DESCRIPTION = "Missing trial description.";
	private static String MISSING_DURATION = "Missing or incorrect duration.";
	private static String CREATING_MESSAGE = "Creating a new trial...";
	
	private int sessionId;
	
	private FormTextAreaField trialDescriptionField;
	private FormNumberField durationField;
	private FormNumberField relevanceField;
	
	public TrialFormDialog(int sessionId) {
		super(TITLE, WELCOME_MESSAGE);
		this.sessionId = sessionId;
		
		addFormFields();
		
		ArrayList<AttributeGroup> groups = TableNamesInstance.TRIAL.getGroupedAttributes();
		for (AttributeGroup g : groups) {
			addDefinedFormFields(g.getAttributes(), g.getGroupName());
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
										TrialFormDialog.this.getTrialDescription(),
										TrialFormDialog.this.getDuration());
								setDefinedAttributes(TableNamesInstance.TRIAL, trialID);
								/*
								if (!TrialFormDialog.this.getRelevance().equals("")) {
									WebServiceInstance.getDatabaseConnection().setTrialAttribute(
											trialID,
											"relevance",
											TrialFormDialog.this.getRelevance(),
											false);
								}*/
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
	
	private void addFormFields() {
		addFormTextLabel("Static attributes:");
		trialDescriptionField = new FormTextAreaField(
				TableNamesInstance.TRIAL.getAttributeName("trialDescription"), gridBagConstraints, formPanel);
		durationField = new FormNumberField(
				TableNamesInstance.TRIAL.getAttributeName("duration"), gridBagConstraints, formPanel);
		/*
		addFormTextLabel("Defined attributes:");
		relevanceField = new FormNumberField(
				TableNamesInstance.TRIAL.getAttributeName("relevance"), gridBagConstraints, formPanel);*/
	}
	
	private String getTrialDescription() {
		
		return trialDescriptionField.getData();
	}
	
	private int getDuration() {
		int duration = -1;
		
		try {
			duration = durationField.getData();
		} catch (NumberFormatException e) {
			this.messageLabel.setText(MISSING_DURATION);
		}
		
		return duration;
	}
	
	private String getRelevance() {
		String relevance = "";
		try {
			relevance = "" + relevanceField.getData();
		} catch (NumberFormatException e) {
		}
		
		return relevance;
	}
	
	private boolean validateResult() {
		if (getTrialDescription().equals("")) {
			this.messageLabel.setText(MISSING_TRIAL_DESCRIPTION);
			
			return false;
		} else if (getDuration() < 0) {
			this.messageLabel.setText(MISSING_DURATION);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return true;
	}
}