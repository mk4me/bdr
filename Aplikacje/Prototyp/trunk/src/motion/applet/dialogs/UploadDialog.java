package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import motion.applet.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;
import motion.database.FileTransferListener;

public class UploadDialog extends BasicDialog {
	private static String UPLOAD_TITLE = Messages.getString("UploadDialog.UploadTitle"); //$NON-NLS-1$
	private static String UPLOAD_FILE = Messages.getString("Upload"); //$NON-NLS-1$
	private static String FILE_PATH = Messages.getString("File"); //$NON-NLS-1$
	private static String DIRECTORY_PATH = Messages.getString("Directory"); //$NON-NLS-1$
	private static String DESCRIPTION = Messages.getString("Description") + Messages.COLON; //$NON-NLS-1$
	private static String SESSION = Messages.getString("Session") + Messages.COLON; //$NON-NLS-1$
	private static String CANCEL_UPLOAD = Messages.CANCEL;
	private static String WELCOME_MESSAGE = Messages.getString("UploadDialog.ChooseAFileToUpload"); //$NON-NLS-1$
	private static String MISSING_FILE_PATH_MESSAGE = Messages.getString("UploadDialog.NoFileSelected"); //$NON-NLS-1$
	private static String MISSING_SESSION_MESSAGE = Messages.getString("UploadDialog.NoSessionNumberEntered"); //$NON-NLS-1$
	private static String PRESS_UPLOAD_MESSAGE = Messages.getString("UploadDialog.PressUploadToSendFile"); //$NON-NLS-1$
	private static String BROWSE = Messages.getString("Browse"); //$NON-NLS-1$
	
	private JRadioButton fileRadioButton;
	private JRadioButton directoryRadioButton;
	private JButton browseButton;
	private JButton uploadButton;
	private JButton cancelButton;
	private JTextField filePathText;
	private JTextField sessionNumber;
	private JTextField descriptionText;
	
	private boolean directoryUpload;
	
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
		
		// File
		fileRadioButton = new JRadioButton(FILE_PATH);
		fileRadioButton.setSelected(true);
		directoryUpload = false;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(fileRadioButton, gridBagConstraints);
		
		// Directory
		directoryRadioButton = new JRadioButton(DIRECTORY_PATH);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		formPanel.add(directoryRadioButton, gridBagConstraints);
		
		// Radio button group
		ButtonGroup group = new ButtonGroup();
		group.add(fileRadioButton);
		group.add(directoryRadioButton);
		
		// Path
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		this.filePathText = new JTextField(20);
		formPanel.add(filePathText, gridBagConstraints);
		
		// Description
		JLabel descriptionLabel = new JLabel(DESCRIPTION);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		formPanel.add(descriptionLabel, gridBagConstraints);
		descriptionLabel.setLabelFor(descriptionText);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		this.descriptionText = new JTextField(20);
		formPanel.add(descriptionText, gridBagConstraints);
		
		// Session number
		JLabel sessionLabel = new JLabel(SESSION);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		formPanel.add(sessionLabel, gridBagConstraints);
		sessionLabel.setLabelFor(sessionNumber);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		this.sessionNumber = new JTextField(3);
		formPanel.add(sessionNumber, gridBagConstraints);
		
		// Browse button
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
		this.fileRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (UploadDialog.this.directoryUpload != false) {
					UploadDialog.this.filePathText.setText("");
					UploadDialog.this.descriptionText.setEnabled(true);
				}
				UploadDialog.this.directoryUpload = false;
			}
		});
		
		this.directoryRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (UploadDialog.this.directoryUpload != true) {
					UploadDialog.this.filePathText.setText("");
					UploadDialog.this.descriptionText.setEnabled(false);
				}
				UploadDialog.this.directoryUpload = true;
			}
		});
		
		this.browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (UploadDialog.this.directoryUpload == false) {
					fileChooser.setFileFilter(new FileNameExtensionFilter(".c3d session file", "c3d")); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				}
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
								if (UploadDialog.this.directoryUpload == false) {
									WebServiceInstance.getDatabaseConnection().uploadSessionFile(
											UploadDialog.this.getSession(),
											UploadDialog.this.getDescription(),
											UploadDialog.this.getFilePath(),
											new UploadTransferListener());
								} else {
									WebServiceInstance.getDatabaseConnection().uploadSessionFiles(
											UploadDialog.this.getSession(),
											UploadDialog.this.getFilePath(),
											new UploadTransferListener());
								}
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
		if (this.getFilePath().equals("")) { //$NON-NLS-1$
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
