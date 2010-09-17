package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.TrialStaticAttributes;

public class TrialFormDialog extends FormDialog {
	private static String TITLE = "New trial";
	private static String WELCOME_MESSAGE = "Create a new trial.";
	private static String MISSING_TRIAL_DESCRIPTION = "Missing trial description.";
	private static String MISSING_DURATION = "Missing or incorrect duration.";
	private static String CREATING_MESSAGE = "Creating a new trial...";
	
	private int sessionId;
	
	public TrialFormDialog(int sessionId) {
		super(TITLE, WELCOME_MESSAGE);
		this.sessionId = sessionId;
		
		for (EntityAttributeGroup g : EntityKind.trial.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			addDefinedFormFields(g, g.name);
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
	
	private ArrayList<String> getDeselectedAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(TrialStaticAttributes.TrialID.toString());
		
		return attributes;
	}
	
	private String getTrialDescription() {
		
		return (String) getAttributeValue(EntityKind.trial, TrialStaticAttributes.TrialDescription.toString());
	}
	
	private int getDuration() {
		int duration = -1;
		Object value = getAttributeValue(EntityKind.trial, TrialStaticAttributes.Duration.toString());
		if (value != null) {
			duration = (Integer) value;
		}
		
		return duration; 
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