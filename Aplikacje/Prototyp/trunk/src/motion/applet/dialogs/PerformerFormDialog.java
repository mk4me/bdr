package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;

public class PerformerFormDialog extends FormDialog {
	public static String TITLE = Messages.getString("PerformerFormDialog.Title"); //$NON-NLS-1$
	private static String TITLE_EDIT = Messages.getString("PerformerFormDialog.TitleEdit"); //$NON-NLS-1$
	public static String WELCOME_MESSAGE = Messages.getString("PerformerFormDialg.WelcomeMessage"); //$NON-NLS-1$
	private static String WELCOME_MESSAGE_EDIT = Messages.getString("PerformerFormDialg.WelcomeMessageEdit"); //$NON-NLS-1$
	private static String MISSING_FIRST_NAME = Messages.getString("PerformerFormDialog.MissingFirstName"); //$NON-NLS-1$
	private static String MISSING_LAST_NAME = Messages.getString("PerformerFormDialog.MissingLastName"); //$NON-NLS-1$
	private static String CREATING_MESSAGE = Messages.getString("PerformerFormDialog.CreatingMessage"); //$NON-NLS-1$
	private static String MISSING_PERFORMER_ID = Messages.getString("PerformerFormDialog.MissingPerformerId"); //$NON-NLS-1$
	private static String ID_EXISTS = Messages.getString("PerformerFormDialog.IdExists"); //$NON-NLS-1$
	
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
						private boolean created = false;
						
						@Override
						protected Void doInBackground() throws InterruptedException {
							PerformerFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							PerformerFormDialog.this.createButton.setEnabled(false);
							
							try {
								int performerID = WebServiceInstance.getDatabaseConnection().createPerformer(
										PerformerFormDialog.this.getPerformerId());
								
								setDefinedAttributes(performerID);
								created = true;
							} catch (Exception e1) {
								PerformerFormDialog.this.messageLabel.setText(ID_EXISTS);
								PerformerFormDialog.this.createButton.setEnabled(true);
								/*
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
								*/
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							if (created == true) {
								PerformerFormDialog.this.setVisible(false);
								PerformerFormDialog.this.dispose();
							}
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
		//attributes.add(PerformerStaticAttributes.PerformerID.toString());
		
		return attributes;
	}
	/*
	private String getFirstName() {
		
		return (String) getAttributeValue(EntityKind.performer, PerformerStaticAttributes.FirstName.toString());
	}
	
	private String getLastName() {
		
		return (String) getAttributeValue(EntityKind.performer, PerformerStaticAttributes.LastName.toString());
	}
	*/
	private Integer getPerformerId() {
		
		return (Integer) getAttributeValue(EntityKind.performer, PerformerStaticAttributes.PerformerID.toString());
	}
	
	protected boolean validateResult() {/*
		if (getFirstName().equals("")) {
			this.messageLabel.setText(MISSING_FIRST_NAME);
			
			return false;
		} else if (getLastName().equals("")) {
			this.messageLabel.setText(MISSING_LAST_NAME);
			
			return false;
		}
		*/
		if (getPerformerId() == null) {
			this.messageLabel.setText(MISSING_PERFORMER_ID);
			
			return false;
		} else if (getPerformerId() < 1) {
			this.messageLabel.setText(MISSING_PERFORMER_ID);
				
			return false;
		}
		
		return super.validateResult();
	}
}
