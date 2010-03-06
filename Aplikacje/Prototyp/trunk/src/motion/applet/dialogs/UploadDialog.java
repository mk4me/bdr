package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;

public class UploadDialog extends JDialog {
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
	private JLabel messageLabel;
	private JTextField filePathText;
	private JTextField sessionNumber;
	private JTextField descriptionText;
	
	private DatabaseConnection databaseConnection;
	
	public UploadDialog() {
		super((JFrame) null, UPLOAD_TITLE, true);
		
		databaseConnection = DatabaseConnection.getInstance();
		databaseConnection.setWSCredentials("applet", "motion#motion2X", "pjwstk");
		databaseConnection.setFTPSCredentials("dbpawell", "testUser", "testUser");
		
		this.setSize(420, 200);
		this.setLocation(200, 200);
		
		this.constructUserInterface();
		this.addListeners();
	}
	
	private void constructUserInterface() {
		// Message area
		JPanel messagePanel = new JPanel();
		this.messageLabel = new JLabel(WELCOME_MESSAGE);
		messagePanel.add(this.messageLabel);
		this.add(messagePanel, BorderLayout.PAGE_START);
		
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
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setLayout(buttonPanelLayout);
		
		uploadButton = new JButton(UPLOAD_FILE);
		buttonPanel.add(uploadButton);
		
		cancelButton = new JButton(CANCEL_UPLOAD);
		buttonPanel.add(cancelButton);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	private void addListeners() {
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
					try {
						WebServiceInstance.getDatabaseConnection().uploadSessionFile(
								UploadDialog.this.getSession(),
								UploadDialog.this.getDescription(),
								UploadDialog.this.getFilePath());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					UploadDialog.this.setVisible(false);
					UploadDialog.this.dispose();
				}
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
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
}
