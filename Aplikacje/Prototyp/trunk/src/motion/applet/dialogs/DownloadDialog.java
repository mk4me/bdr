package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import motion.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.FileTransferListener;

public class DownloadDialog extends BasicDialog {
	private static String TITLE = "Download file or directory";
	private static String DOWNLOAD_MESSAGE = "Press Download to get ";
	private static String DOWNLOAD_MESSAGE_ENDING = " selected file.";
	private static String DOWNLOAD_MESSAGE_ENDING_PRURAL = " selected files.";
	private static String CHECKBOX_LABEL ="Recreate original directory";
	private static String CANCEL_DOWNLOAD = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String DOWNLOAD_FILE = "Download";
	private static String PATH_LABEL = "Save to: ";
	
	private JLabel pathLabel;
	private JButton downloadButton;
	private JButton cancelButton;
	private JProgressBar progressBar;
	private JCheckBox recreateDirCheckBox;
	private int[] recordId;
	private String path;
	private JProgressBar totalProgressBar;
	
	public DownloadDialog(int recordId[], String path) {
		super(TITLE, DOWNLOAD_MESSAGE+recordId.length+ (recordId.length>1?DOWNLOAD_MESSAGE_ENDING_PRURAL:DOWNLOAD_MESSAGE_ENDING) );
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
		panel.add( progressBar );
		progressBar.setVisible(false);
		progressBar.setString( "Partial progress" );
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted( true );

		totalProgressBar =  new JProgressBar();
		totalProgressBar.setString( "Total progress" );
		totalProgressBar.setStringPainted( true );
		totalProgressBar.setMinimumSize( new Dimension( 200, totalProgressBar.getHeight() ) );
		totalProgressBar.setVisible(false);
		panel.add( totalProgressBar );

		downloadButton = new JButton(DOWNLOAD_FILE);
		this.addToButtonPanel(downloadButton);
		
		cancelButton = new JButton(CANCEL_DOWNLOAD);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {

		this.totalProgressBar.setMinimum(0);
		this.totalProgressBar.setMaximum( recordId.length-1 );
		
		this.setSize(300, 200);
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
							progressBar.setVisible(true);
							totalProgressBar.setVisible(true);

							for (int id:recordId)
							{
								WebServiceInstance.getDatabaseConnection().downloadFile(id, path, new DownloadTransferListener(), recreateDirCheckBox.isSelected());
								totalProgressBar.setValue( totalProgressBar.getValue() + 1 );
							}
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
	
	public static void main(String [] args)
	{
		DownloadDialog d = new DownloadDialog( new int[]{0, 1, 2, 4}, "/home/kk/" );
		d.setVisible(true);
	}
}