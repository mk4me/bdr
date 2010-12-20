package motion.applet.panels;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFileChooser;

import motion.applet.dialogs.ExceptionDialog;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.Session;
import motion.database.model.SessionValidationInfo;

public class WizardSessionDirectoryPanel extends WizardPanel {
	private JFileChooser fileChooser;
	private Session session;
	private File[] files;
	
	public WizardSessionDirectoryPanel(String stepMessage,
			boolean enableCancel, boolean enableBack, boolean enableNext, boolean enableFinish) {
		super(stepMessage, enableCancel, enableBack, enableNext, enableFinish);
		
	}
	
	@Override
	protected void createWizardContents() {
		this.setLayout(new BorderLayout());
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setControlButtonsAreShown(false);
		this.add(fileChooser, BorderLayout.CENTER);
	}
	
	@Override
	public boolean nextPressed() {
		final File file = fileChooser.getSelectedFile();
		if (file != null) {
			files = file.listFiles();
			//System.out.println(file);
			//SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				//@Override
				//protected Void doInBackground() throws InterruptedException {
					try {
						SessionValidationInfo sessionValidationInfo = WebServiceInstance.getDatabaseConnection().validateSessionFileSet(files);
						if (sessionValidationInfo.errors != null) {
								errorMessage = sessionValidationInfo.errors.toString();
						} else if (sessionValidationInfo.session != null) {
							session = sessionValidationInfo.session;
							//System.out.println(session.toStringAllAttributes());
							
							return true;
						}
					} catch (Exception e1) {
						//ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
						//exceptionDialog.setVisible(true);
						errorMessage = "Incorrect directory contents.";
					}
					
					//return null;
				//}
				
				//@Override
				//protected void done() {
				//}
			//};
			//worker.execute();
			
			return false;
		} else {
			errorMessage = "No directory selected";
			
			return false;
		}
	}
	
	public Session getSession() {
		
		return session;
	}
	
	public File[] getFiles() {
		
		return files;
	}
}