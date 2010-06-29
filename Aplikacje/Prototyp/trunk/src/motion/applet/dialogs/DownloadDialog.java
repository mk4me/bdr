package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import motion.applet.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.FileTransferListener;

public class DownloadDialog extends BasicDialog {
	private static String TITLE = "Download file or directory";
	private static String DOWNLOAD_MESSAGE = "Press Download.";
	private static String CANCEL_DOWNLOAD = Messages.CANCEL;
	private static String DOWNLOAD_FILE = "Download";
	
	private JLabel pathLabel;
	private JButton downloadButton;
	private JButton cancelButton;
	private JProgressBar progressBar;
	private int recordId;
	private String path;
	
	public DownloadDialog(int recordId, String path) {
		super(TITLE, DOWNLOAD_MESSAGE );
		this.recordId = recordId;
		this.path = path;
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		pathLabel = new JLabel();
		this.add(pathLabel, BorderLayout.CENTER);
		
		// Button area
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		this.addToButtonPanel(progressBar);
		progressBar.setVisible(false);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		
		downloadButton = new JButton(DOWNLOAD_FILE);
		this.addToButtonPanel(downloadButton);
		
		cancelButton = new JButton(CANCEL_DOWNLOAD);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(350, 150);
		this.setLocation(200, 200);
		
		this.pathLabel.setText("Save to: " + path);
	}
	
	@Override
	protected void addListeners() {
		this.downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DownloadDialog.this.setProgressBarValue(0);
				DownloadDialog.this.showProgressBar();
				
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() throws InterruptedException {
						try {
							WebServiceInstance.getDatabaseConnection().downloadFile(recordId, path, new DownloadTransferListener());
						} catch (Exception e1) {
							ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
							exceptionDialog.setVisible(true);
						}
						return null;
					}
					
					@Override
					protected void done() {
						DownloadDialog.this.setVisible(false);
						DownloadDialog.this.dispose();
					}
				};
				worker.execute();
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebServiceInstance.getDatabaseConnection().cancelCurrentFileTransfer();
				DownloadDialog.this.setVisible(false);
				DownloadDialog.this.dispose();
			}
		});
	}
	
	private void setProgressBarValue(int value) {
		this.progressBar.setValue(value);
	}
	
	private void showProgressBar() {
		this.progressBar.setVisible(true);
	}
	
	private class DownloadTransferListener implements FileTransferListener {
		
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
			DownloadDialog.this.setProgressBarValue(percent);	// Doesn't work for downloads.
		}
	}
}