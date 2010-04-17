package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;
import motion.database.FileTransferListener;

public class UploadDialog extends BasicDialog {
	private static String UPLOAD_TITLE = "Upload session file";
	private static String UPLOAD_FILE = "Upload";
	private static String FILE_PATH = "File:";
	private static String DESCRIPTION = "Description:";
	private static String SESSION = "Session:";
	private static String CANCEL_UPLOAD = "Cancel";
	private static String WELCOME_MESSAGE = "Choose a file to upload.";
	private static String MISSING_FILE_PATH_MESSAGE = "No file selected.";
	private static String MISSING_SESSION_MESSAGE = "No session number entered.";
	private static String PRESS_UPLOAD_MESSAGE = "Press Upload to send file.";
	private static String BROWSE = "Browse";
	
	private JButton browseButton;
	private JButton uploadButton;
	private JButton cancelButton;
	private JTextField filePathText;
	private JTextField sessionNumber;
	private JTextField descriptionText;
	
	private JProgressBar progressBar;
	
	private DatabaseConnection databaseConnection;
	
	public UploadDialog() {
		super(UPLOAD_TITLE, WELCOME_MESSAGE);
		
		this.finishUserInterface();
	}
	
	protected void constructUserInterface() {
		// Form area
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		
		// File path
		JLabel filePathLabel = new JLabel(FILE_PATH);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(filePathLabel, gridBagConstraints);
		filePathLabel.setLabelFor(filePathText);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		this.filePathText = new JTextField(20);
		formPanel.add(filePathText, gridBagConstraints);
		
		// Description
		JLabel descriptionLabel = new JLabel(DESCRIPTION);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		formPanel.add(descriptionLabel, gridBagConstraints);
		descriptionLabel.setLabelFor(descriptionText);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		this.descriptionText = new JTextField(20);
		formPanel.add(descriptionText, gridBagConstraints);
		
		// Session number
		JLabel sessionLabel = new JLabel(SESSION);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		formPanel.add(sessionLabel, gridBagConstraints);
		sessionLabel.setLabelFor(sessionNumber);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		this.sessionNumber = new JTextField(3);
		formPanel.add(sessionNumber, gridBagConstraints);
		
		
		
		this.browseButton = new JButton(BROWSE);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		formPanel.add(browseButton, gridBagConstraints);
		
		this.add(formPanel, BorderLayout.CENTER);
		
		// Button area
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		this.addToButtonPanel(progressBar);
		progressBar.setVisible(false);
		
		uploadButton = new JButton(UPLOAD_FILE);
		this.addToButtonPanel(uploadButton);
		
		cancelButton = new JButton(CANCEL_UPLOAD);
		this.addToButtonPanel(cancelButton);
	}
	
	protected void finishUserInterface() {
		this.setSize(420, 200);
		this.setLocation(200, 200);
	}
	
	protected void addListeners() {
		this.browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter(".c3d session file", "c3d"));
				fileChooser.showOpenDialog(UploadDialog.this);
				File file = fileChooser.getSelectedFile();
				if (file != null) {
					UploadDialog.this.filePathText.setText(file.toString());
					UploadDialog.this.validateResult();
				}
			}
		});
		
		this.uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (UploadDialog.this.validateResult() == true) {
					UploadDialog.this.setProgressBarValue(0);
					UploadDialog.this.showProgressBar();
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						
						@Override
						protected Void doInBackground() throws InterruptedException {
							try {
								WebServiceInstance.getDatabaseConnection().uploadSessionFile(
										UploadDialog.this.getSession(),
										UploadDialog.this.getDescription(),
										UploadDialog.this.getFilePath(),
										new UploadTransferListener());
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							return null;
						}
						
						@Override
						protected void done() {
							UploadDialog.this.setVisible(false);
							UploadDialog.this.dispose();
						}
					};
					worker.execute();
				}
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
				WebServiceInstance.getDatabaseConnection().cancelCurrentFileTransfer();
				UploadDialog.this.setVisible(false);
				UploadDialog.this.dispose();
			}
		});
	}
	
	private String getFilePath() {
		
		return this.filePathText.getText();
	}
	
	private String getDescription() {
		
		return this.descriptionText.getText();
	}
	
	private int getSession() {
		int session = -1;
		try {
			session = Integer.parseInt(this.sessionNumber.getText());
		} catch (NumberFormatException e) {
			this.messageLabel.setText(MISSING_SESSION_MESSAGE);
		}
		
		return session;
	}
	
	private boolean validateResult() {
		if (this.getFilePath().equals("")) {
			this.messageLabel.setText(MISSING_FILE_PATH_MESSAGE);
			
			return false;
		} else if (this.getSession() < 0) {
			this.messageLabel.setText(MISSING_SESSION_MESSAGE);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_UPLOAD_MESSAGE);
		
		return true;
	}
	
	private void setProgressBarValue(int value) {
		this.progressBar.setValue(value);
	}
	
	private void showProgressBar() {
		this.progressBar.setVisible(true);
	}
	
	private class UploadTransferListener implements FileTransferListener {
	
		@Override
		public int getDesiredStepPercent() {
			// TODO Auto-generated method stub
			return 5;
		}
	
		@Override
		public void transferStep() {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void transferStepPercent(final int percent) {
			UploadDialog.this.setProgressBarValue(percent);
		}
	}
}
