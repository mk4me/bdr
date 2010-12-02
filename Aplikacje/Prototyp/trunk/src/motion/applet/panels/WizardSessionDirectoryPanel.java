package motion.applet.panels;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import motion.applet.dialogs.ExceptionDialog;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.Session;

public class WizardSessionDirectoryPanel extends WizardPanel {
	private JFileChooser fileChooser;
	private Session session;
	
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
			System.out.println(file);
			//SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				//@Override
				//protected Void doInBackground() throws InterruptedException {
					try {
						session = WebServiceInstance.getDatabaseConnection().validateSessionFileSet(file.list());
						System.out.println(session.toStringAllAttributes());

					} catch (Exception e1) {
						ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
						exceptionDialog.setVisible(true);
					}
					
					//return null;
				//}
				
				//@Override
				//protected void done() {
				//}
			//};
			//worker.execute();
			
			return true;
		} else {
			errorMessage = "No directory selected";
			
			return false;
		}
	}
	
	public Session getSession() {
		
		return session;
	}
}