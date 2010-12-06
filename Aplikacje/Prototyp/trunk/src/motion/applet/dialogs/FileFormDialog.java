package motion.applet.dialogs;

import java.util.ArrayList;

import motion.Messages;
import motion.database.model.DatabaseFile;
import motion.database.model.DatabaseFileStaticAttributes;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;

@SuppressWarnings("serial")
public class FileFormDialog extends FormDialog {
	public static String TITLE = Messages.getString("FileFormDialog.Title"); //$NON-NLS-1$
	private static String TITLE_EDIT = Messages.getString("FileFormDialog.TitleEdit"); //$NON-NLS-1$
	public static String WELCOME_MESSAGE = Messages.getString("FileFormDialg.WelcomeMessage"); //$NON-NLS-1$
	private static String WELCOME_MESSAGE_EDIT = Messages.getString("FileFormDialg.WelcomeMessageEdit"); //$NON-NLS-1$
	private static String MISSING_FILE_NAME = Messages.getString("FileFormDialog.MissingFirstName"); //$NON-NLS-1$
	
	public FileFormDialog(String title, String welcomeMessage) {
		super(title, welcomeMessage);
		
		for (EntityAttributeGroup g : EntityKind.file.getDeselectedAttributeGroupCopies(getDeselectedAttributes())) {
			addFormFields(g, g.name);
		}
		
/*		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (FileFormDialog.this.validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							FileFormDialog.this.messageLabel.setText(CREATING_MESSAGE);
							FileFormDialog.this.createButton.setEnabled(false);
							try {
								int performerID = WebServiceInstance.getDatabaseConnection().createF(
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
							FileFormDialog.this.setVisible(false);
							FileFormDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
*/	}
	
	
	public FileFormDialog(DatabaseFile file) {
		this(TITLE_EDIT, WELCOME_MESSAGE_EDIT);
		fillFormFields(file);
	}
	
	private ArrayList<String> getDeselectedAttributes() {
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(DatabaseFileStaticAttributes.FileID.toString());
		
		return attributes;
	}
	
	private String getFileName() {
		
		return (String) getAttributeValue(EntityKind.file, DatabaseFileStaticAttributes.FileName.toString());
	}
	
	private String getFileDescription() {
		
		return (String) getAttributeValue(EntityKind.file, DatabaseFileStaticAttributes.FileDescription.toString());
	}
	
	protected boolean validateResult() {
		if (getFileName().equals("")) {
			this.messageLabel.setText(MISSING_FILE_NAME);
			
			return false;
		} 
		
		/*
		 * File description not required ?
		 * 
		 * else if (getFileDescription().equals("")) {
			this.messageLabel.setText(MISSING_LAST_NAME);
			
			return false;
		}
		*/
		return super.validateResult();
	}
}
