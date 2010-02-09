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

import motion.database.DatabaseConnection;

public class UploadDialog extends JDialog {
	private static String UPLOAD_TITLE = "Upload session file";
	private static String UPLOAD_FILE = "Upload";
	private static String FILE_PATH = "File:";
	private static String CANCEL_UPLOAD = "Cancel";
	private static String WELCOME_MESSAGE = "Choose a file to upload.";
	private static String MISSING_FILE_PATH_MESSAGE = "No file selected.";
	private static String PRESS_UPLOAD_MESSAGE = "Press Upload to send file.";
	private static String BROWSE = "Browse";
	private JButton browseButton;
	private JButton uploadButton;
	private JButton cancelButton;
	private JLabel messageLabel;
	private JTextField filePathText;
	
	private DatabaseConnection databaseConnection;
	
	public UploadDialog() {
		super((JFrame) null, UPLOAD_TITLE, true);
		
		databaseConnection = DatabaseConnection.getInstance();
		databaseConnection.setWSCredentials("applet", "motion#motion2X", "pjwstk");
		databaseConnection.setFTPSCredentials("dbpawell", "testUser", "testUser");
		
		this.setSize(380, 200);
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
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.ipadx = 10;
		
		JLabel filePathLabel = new JLabel(FILE_PATH);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(filePathLabel, gridBagConstraints);
		filePathLabel.setLabelFor(filePathText);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		this.filePathText = new JTextField(20);
		formPanel.add(filePathText, gridBagConstraints);
		
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
						UploadDialog.this.databaseConnection.uploadFile(1, "Plik wgrany przez applet", UploadDialog.this.filePathText.getText());
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
	
	private boolean validateResult() {
		if (this.getFilePath().equals("")) {
			this.messageLabel.setText(MISSING_FILE_PATH_MESSAGE);
			
			return false;
		}
		
		this.messageLabel.setText(PRESS_UPLOAD_MESSAGE);
		
		return true;
	}
}
