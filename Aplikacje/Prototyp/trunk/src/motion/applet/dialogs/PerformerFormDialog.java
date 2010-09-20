package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.PerformerStaticAttributes;

public class PerformerFormDialog extends FormDialog {
	private static String TITLE = "New performer";
	private static String WELCOME_MESSAGE = "Create a new performer.";
	private static String MISSING_FIRST_NAME = "Please input performer's first name.";
	private static String MISSING_LAST_NAME = "Please input performer's last name.";
	private static String CREATING_MESSAGE = "Creating a new performer...";
	
	public PerformerFormDialog() {
		super(TITLE, WELCOME_MESSAGE);
		
		for (EntityAttributeGroup g : EntityKind.performer.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			addDefinedFormFields(g, g.name);
		}
		
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
								
								setDefinedAttributes(performerID);
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
	
	private ArrayList<String> getDeselectedAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(PerformerStaticAttributes.PerformerID.toString());
		
		return attributes;
	}
	
	private String getFirstName() {
		
		return (String) getAttributeValue(EntityKind.performer, PerformerStaticAttributes.FirstName.toString());
	}
	
	private String getLastName() {
		
		return (String) getAttributeValue(EntityKind.performer, PerformerStaticAttributes.LastName.toString());
	}
	
	protected boolean validateResult() {
		if (getFirstName().equals("")) {
			this.messageLabel.setText(MISSING_FIRST_NAME);
			
			return false;
		} else if (getLastName().equals("")) {
			this.messageLabel.setText(MISSING_LAST_NAME);
			
			return false;
		}
		
		return super.validateResult();
	}
}
