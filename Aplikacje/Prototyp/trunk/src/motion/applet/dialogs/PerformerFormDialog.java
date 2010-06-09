package motion.applet.dialogs;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.xml.datatype.DatatypeConfigurationException;

import motion.applet.database.TableNamesInstance;
import motion.applet.tables.BasicTable;
import motion.applet.webservice.client.WebServiceInstance;

public class PerformerFormDialog extends FormDialog {
	private static String TITLE = "New performer";
	private static String WELCOME_MESSAGE = "Create new performer.";
	private static String MISSING_FIRST_NAME = "Please input performer's first name.";
	private static String MISSING_LAST_NAME = "Please input performer's last name.";
	private static String MISSING_BIRTH_DATE = "Missing or incorrect birth date";
	private static String PRESS_CREATE_MESSAGE = "Press Create to finish.";
	private static String CREATING_MESSAGE = "Creating a new performer...";
	
	private FormTextField firstNameField;
	private FormTextField lastNameField;
	
	private FormDateField birthDateField;
	private FormTextAreaField diagnosisField;
	
	public PerformerFormDialog() {
		super(TITLE, WELCOME_MESSAGE);
		
		//ArrayList<AttributeName> attributes = TableNamesInstance.PERFORMER.getStaticAttributes();
		
		//addFormTextFields(attributes, "Static attributes");
		
		//ArrayList<AttributeGroup> groups = TableNamesInstance.PERFORMER.getGroupedAttributes();
		
		//for (AttributeGroup g : groups) {
			//addFormTextFields(g.getAttributes(), g.getGroupName());
		//}
		
		addFormFields();
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (PerformerFormDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							PerformerFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							PerformerFormDialog.this.createButton.setEnabled(false);
							try {
								int performerID = WebServiceInstance.getDatabaseConnection().createPerformer(
										getFirstName(),
										getLastName());
								WebServiceInstance.getDatabaseConnection().setPerformerAttribute(
										performerID,
										"date_of_birth",
										getBirthDate(),
										false);
								WebServiceInstance.getDatabaseConnection().setPerformerAttribute(
										performerID,
										"diagnosis",
										getDiagnosis(),
										false);
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							PerformerFormDialog.this.setVisible(false);
							PerformerFormDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
	}
	private void addFormFields() {
		
		addFormTextLabel("Static attributes:");
		firstNameField = new FormTextField(
				TableNamesInstance.PERFORMER.getAttributeName("firstName"), gridBagConstraints, formPanel);
		lastNameField = new FormTextField(
				TableNamesInstance.PERFORMER.getAttributeName("lastName"), gridBagConstraints, formPanel);
		
		addFormTextLabel("Defined attributes:");
		birthDateField = new FormDateField(
				TableNamesInstance.PERFORMER.getAttributeName("date_of_birth"), gridBagConstraints, formPanel, false);
		diagnosisField = new FormTextAreaField(
				TableNamesInstance.PERFORMER.getAttributeName("diagnosis"), gridBagConstraints, formPanel);
	}
	
	private String getFirstName() {
		
		return firstNameField.getData();
	}
	
	private String getLastName() {
		
		return lastNameField.getData();
	}
	
	private String getBirthDate() {
		String birthDate = "";
		try {
			birthDate = birthDateField.getData().toString();
		} catch (ParseException e) {
		} catch (DatatypeConfigurationException e) {
		}
		
		return birthDate;
	}
	
	private String getDiagnosis() {
		
		return diagnosisField.getData();
	}
	
	private boolean validateResult() {
		if (getFirstName().equals("")) {
			this.messageLabel.setText(MISSING_FIRST_NAME);
			
			return false;
		} else if (getLastName().equals("")) {
			this.messageLabel.setText(MISSING_LAST_NAME);
			
			return false;
		} else if (getBirthDate().equals("")) {
			this.messageLabel.setText(MISSING_BIRTH_DATE);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return true;
	}
}
