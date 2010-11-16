package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.MeasurementConfiguration;
import motion.database.model.MeasurementConfigurationStaticAttributes;

public class MeasurementConfigurationFormDialog extends FormDialog {
	public static String TITLE = "New measurement configuration";
	private static String TITLE_EDIT = "Edit measurement configuration";
	public static String WELCOME_MESSAGE = "Create attribute new measurement configuration.";
	private static String WELCOME_MESSAGE_EDIT = "Edit measurement configuration attribute values.";
	private static String MISSING_MEASUREMENT_CONFIGURATION_NAME = "Missing measurement configuration name.";
	private static String MISSING_MEASUREMENT_CONFIGURATION_KIND = "Missing measurement configuration kind.";
	private static String MISSING_MEASUREMENT_CONFIGURATION_DESCRIPTION = "Missing measurement configuration description.";
	private static String CREATING_MESSAGE = "Creating attribute new measurement configuration...";
	
	public MeasurementConfigurationFormDialog(String title, String welcomeMessage) {
		super(title, welcomeMessage);
		
		for (EntityAttributeGroup g : EntityKind.measurement_conf.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			addFormFields(g, g.name);
		}
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MeasurementConfigurationFormDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							MeasurementConfigurationFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							MeasurementConfigurationFormDialog.this.createButton.setEnabled(false);
							try {
								int trialID = WebServiceInstance.getDatabaseConnection().createMeasurementConfiguration(
										MeasurementConfigurationFormDialog.this.getMeasurementConfigurationName(),
										MeasurementConfigurationFormDialog.this.getMeasurementConfigurationKind(),
										MeasurementConfigurationFormDialog.this.getMeasurementConfigurationDescription());
								setDefinedAttributes(trialID);
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							MeasurementConfigurationFormDialog.this.setVisible(false);
							MeasurementConfigurationFormDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
	}
	
	public MeasurementConfigurationFormDialog(MeasurementConfiguration measurementConfiguration) {
		this(TITLE_EDIT, WELCOME_MESSAGE_EDIT);
		fillFormFields(measurementConfiguration);
	}
	
	private ArrayList<String> getDeselectedAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(MeasurementConfigurationStaticAttributes.MeasurementConfID.toString());
		
		return attributes;
	}
	
	private String getMeasurementConfigurationName() {
		
		return (String) getAttributeValue(EntityKind.measurement_conf, MeasurementConfigurationStaticAttributes.MeasurementConfName.toString());
	}
	
	private String getMeasurementConfigurationKind() {
		
		return (String) getAttributeValue(EntityKind.measurement_conf, MeasurementConfigurationStaticAttributes.MeasurementConfKind.toString());
	}
	
	private String getMeasurementConfigurationDescription() {
		
		return (String) getAttributeValue(EntityKind.measurement_conf, MeasurementConfigurationStaticAttributes.MeasurementConfDescription.toString());
	}
	
	protected boolean validateResult() {
		if (getMeasurementConfigurationName().equals("")) {
			this.messageLabel.setText(MISSING_MEASUREMENT_CONFIGURATION_NAME);
			
			return false;
		} else if (getMeasurementConfigurationKind().equals("")) {
			this.messageLabel.setText(MISSING_MEASUREMENT_CONFIGURATION_KIND);
			
			return false;
		} else if (getMeasurementConfigurationDescription().equals("")) {
			this.messageLabel.setText(MISSING_MEASUREMENT_CONFIGURATION_DESCRIPTION);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return super.validateResult();
	}
}
