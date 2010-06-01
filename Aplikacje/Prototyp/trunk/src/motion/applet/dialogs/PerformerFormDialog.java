package motion.applet.dialogs;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JLabel;

import motion.applet.database.AttributeGroup;
import motion.applet.database.TableNamesInstance;
import motion.database.model.AttributeName;

public class PerformerFormDialog extends FormDialog {
	private static String TITLE = "New performer";
	private static String WELCOME_MESSAGE = "Create new performer.";
	
	public PerformerFormDialog() {
		super(TITLE, WELCOME_MESSAGE);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		ArrayList<AttributeName> attributes = TableNamesInstance.PERFORMER.getStaticAttributes();
		
		addFormTextFields(attributes, "Static attributes");
		
		ArrayList<AttributeGroup> groups = TableNamesInstance.PERFORMER.getGroupedAttributes();
		
		for (AttributeGroup g : groups) {
			addFormTextFields(g.getAttributes(), g.getGroupName());
		}
	}
	
	private void addFormTextFields(ArrayList<AttributeName> attributes, String groupName) {
		JLabel groupNameLabel = new JLabel(groupName);
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		formPanel.add(groupNameLabel, gridBagConstraints);
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.gridy++;
		
		for (AttributeName a : attributes) {
			if (a.getType().equals(AttributeName.STRING_TYPE)) {
				FormTextField formTextField = new FormTextField(a, gridBagConstraints, formPanel);
			}
		}
	}
}
