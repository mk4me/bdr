package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import motion.applet.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.FileTransferListener;

public class DownloadDialog extends BasicDialog {
	private static String TITLE = "Download file or directory";
	private static String DOWNLOAD_MESSAGE = "Press Download to get selected files.";
	private static String CHECKBOX_LABEL ="Recreate original directory";
	private static String CANCEL_DOWNLOAD = Messages.CANCEL;
	private static String DOWNLOAD_FILE = "Download";
	private static String PATH_LABEL = "Save to: ";
	
	private JLabel pathLabel;
	private JButton downloadButton;
	private JButton cancelButton;
	private JProgressBar progressBar;
	private JCheckBox recreateDirCheckBox;
	private int[] recordId;
	private String path;
	
	public DownloadDialog(int recordId[], String path) {
		super(TITLE, DOWNLOAD_MESSAGE );
		this.recordId = recordId;
		this.path = path;
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		JPanel panel = new JPanel();
		panel.setLayout( new FlowLayout() );
		
		pathLabel = new JLabel();
		panel.add( pathLabel );
		
		recreateDirCheckBox = new JCheckBox( CHECKBOX_LABEL );
		panel.add( recreateDirCheckBox );	

		this.add(panel, BorderLayout.CENTER);

		JPanel leftMargin = new JPanel();
		leftMargin.setPreferredSize( new Dimension(20, 20 ) );
		this.add( leftMargin, BorderLayout.WEST );
		
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
		
		this.pathLabel.setText( PATH_LABEL + path);
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
							
							for (int id:recordId)
								WebServiceInstance.getDatabaseConnection().downloadFile(id, path, new DownloadTransferListener());
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