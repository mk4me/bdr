package motion.applet.panels;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.SwingWorker;

import motion.applet.dialogs.ExceptionDialog;
import motion.applet.dialogs.WizardSessionDialog;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.FileTransferListener;
import motion.database.model.Session;

public class WizardSessionBrowserPanel extends WizardPanel {
	private SessionBrowserPanel sessionBrowserPanel;
	private File[] files;
	
	public WizardSessionBrowserPanel(String stepMessage,
			boolean enableCancel, boolean enableBack, boolean enableNext, boolean enableFinish) {
		super(stepMessage, enableCancel, enableBack, enableNext, enableFinish);
		
	}
	
	@Override
	protected void createWizardContents() {
		this.setLayout(new BorderLayout());
	}
	
	@Override
	public void afterNextPressed(WizardPanel wizardPanel) {
		if (wizardPanel instanceof WizardSessionDirectoryPanel) {
			Session session = ((WizardSessionDirectoryPanel) wizardPanel).getSession();
			files = ((WizardSessionDirectoryPanel) wizardPanel).getFiles();
			
			try {
				sessionBrowserPanel = new SessionBrowserPanel();
				sessionBrowserPanel.setSession(new Session[] {session});
				this.add(sessionBrowserPanel, BorderLayout.CENTER);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void finishPressed() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					WebServiceInstance.getDatabaseConnection().uploadSessionFileSet(files, new UploadTransferListener());
					
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
			}
		};
		worker.execute();
	}
	
	private class UploadTransferListener implements FileTransferListener {
		
		@Override
		public int getDesiredStepPercent() {
			
			return 5;
		}
	
		@Override
		public void transferStep() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void transferStepPercent(final int percent) {
			WizardSessionDialog.progressBar.setValue(percent);
		}
	}
}