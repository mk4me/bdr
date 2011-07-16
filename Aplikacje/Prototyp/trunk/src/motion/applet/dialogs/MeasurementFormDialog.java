/*package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Measurement;
import motion.database.model.MeasurementConfiguration;
import motion.database.model.MeasurementStaticAttributes;

public class MeasurementFormDialog extends FormDialog {
	public static String TITLE = "New measurement";
	private static String TITLE_EDIT = "Edit measurement";
	public static String WELCOME_MESSAGE = "Create a new measurement.";
	private static String WELCOME_MESSAGE_EDIT = "Edit measurement attribute values.";
	private static String MISSING_MEASUREMENT_CONFIGURATION_ID = "Missing measurement configuration ID.";
	private static String CREATING_MESSAGE = "Creating a new measurement...";
	
	private int trialId;
	
	public MeasurementFormDialog(String title, String welcomeMessage, int trialId) {
		super(title, welcomeMessage);
		this.trialId = trialId;
		
		for (EntityAttributeGroup g : EntityKind.measurement.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			addFormFields(g, g.name);
		}
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MeasurementFormDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							MeasurementFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							MeasurementFormDialog.this.createButton.setEnabled(false);
							try {
								int measurementID = WebServiceInstance.getDatabaseConnection().createMeasurement(
										MeasurementFormDialog.this.trialId,
										MeasurementFormDialog.this.getMeasurementConfigurationId());
								setDefinedAttributes(measurementID);
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							MeasurementFormDialog.this.setVisible(false);
							MeasurementFormDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
	}
	
	public MeasurementFormDialog(int trialId, Measurement measurement) {
		this(TITLE_EDIT, WELCOME_MESSAGE_EDIT, trialId);
		fillFormFields(measurement);
	}
	
	private ArrayList<String> getDeselectedAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(MeasurementStaticAttributes.MeasurementID.toString());
		attributes.add(MeasurementStaticAttributes.TrialID.toString());
		
		return attributes;
	}
	
	private int getMeasurementConfigurationId() {
		
		return ((MeasurementConfiguration) getAttributeValue(EntityKind.measurement, MeasurementStaticAttributes.MeasurementConfID.toString())).getId();
	}
	
	protected boolean validateResult() {
		if (getMeasurementConfigurationId() < 0) {	// FIXME .equals("")
			this.messageLabel.setText(MISSING_MEASUREMENT_CONFIGURATION_ID);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return super.validateResult();
	}
}
*/