package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;

public class PerformerFormDialog extends FormDialog {
	public static String TITLE = "New performer";
	private static String TITLE_EDIT = "Edit performer";
	public static String WELCOME_MESSAGE = "Create a new performer.";
	private static String WELCOME_MESSAGE_EDIT = "Edit performer attribute values.";
	private static String MISSING_FIRST_NAME = "Please input performer's first name.";
	private static String MISSING_LAST_NAME = "Please input performer's last name.";
	private static String CREATING_MESSAGE = "Creating a new performer...";
	
	public PerformerFormDialog(String title, String welcomeMessage) {
		super(title, welcomeMessage);
		
		for (EntityAttributeGroup g : EntityKind.performer.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			addFormFields(g, g.name);
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
	
	public PerformerFormDialog(Performer performer) {
		this(TITLE_EDIT, WELCOME_MESSAGE_EDIT);
		fillFormFields(performer);
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
