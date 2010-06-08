package motion.applet.dialogs;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import motion.applet.database.TableNamesInstance;
import motion.applet.webservice.client.WebServiceInstance;

public class PerformerFormDialog extends FormDialog {
	private static String TITLE = "New performer";
	private static String WELCOME_MESSAGE = "Create new performer.";
	
	private FormTextField firstNameField;
	private FormTextField lastNameField;
	
	private FormDateField birthDateField;
	private FormTextAreaField diagnosisField;
	
	public PerformerFormDialog() {
		super(TITLE, WELCOME_MESSAGE);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		//ArrayList<AttributeName> attributes = TableNamesInstance.PERFORMER.getStaticAttributes();
		
		//addFormTextFields(attributes, "Static attributes");
		
		//ArrayList<AttributeGroup> groups = TableNamesInstance.PERFORMER.getGroupedAttributes();
		
		//for (AttributeGroup g : groups) {
			//addFormTextFields(g.getAttributes(), g.getGroupName());
		//}
		
		addFormTextFields();
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int performerID = WebServiceInstance.getDatabaseConnection().createPerformer(
							firstNameField.getData(),
							lastNameField.getData());
					WebServiceInstance.getDatabaseConnection().setPerformerAttribute(
							performerID,
							"date_of_birth",
							birthDateField.getData().toString(),
							false);
					WebServiceInstance.getDatabaseConnection().setPerformerAttribute(
							performerID,
							"diagnosis",
							diagnosisField.getData(),
							false);
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				PerformerFormDialog.this.setVisible(false);
				PerformerFormDialog.this.dispose();
			}
		});
	}
	
	private void addFormTextLabel(String label) {
		JLabel groupNameLabel = new JLabel(label);
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		formPanel.add(groupNameLabel, gridBagConstraints);
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.gridy++;
	}
	
	private void addFormTextFields() {
		
		addFormTextLabel("Static attributes:");
		firstNameField = new FormTextField(
				TableNamesInstance.PERFORMER.getAttributeName("firstName"), gridBagConstraints, formPanel);
		lastNameField = new FormTextField(
				TableNamesInstance.PERFORMER.getAttributeName("lastName"), gridBagConstraints, formPanel);
		
		addFormTextLabel("Defined attributes");
		birthDateField = new FormDateField(
				TableNamesInstance.PERFORMER.getAttributeName("date_of_birth"), gridBagConstraints, formPanel);
		diagnosisField = new FormTextAreaField(
				TableNamesInstance.PERFORMER.getAttributeName("diagnosis"), gridBagConstraints, formPanel);
	}
}
