package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import motion.Messages;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.model.EntityKind;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;

public class UploadDialog extends BasicDialog {
	private static String UPLOAD_TITLE = Messages.getString("UploadDialog.UploadTitle"); //$NON-NLS-1$
	private static String UPLOAD_FILE = Messages.getString("Upload"); //$NON-NLS-1$
	private static String LABEL_PATH = Messages.getString("UploadDialog.LabelPath"); //$NON-NLS-1$
	private static String DESCRIPTION = Messages.getString("Description") + Messages.COLON; //$NON-NLS-1$
	private static String CANCEL_UPLOAD = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String CHOOSE_FILE_MESSAGE = Messages.getString("UploadDialog.ChooseAFileToUpload"); //$NON-NLS-1$
	private static String MISSING_FILE_PATH_MESSAGE = Messages.getString("UploadDialog.NoFileSelected"); //$NON-NLS-1$
	private static String MISSING_PERFORMER_MESSAGE = Messages.getString("UploadDialog.NoPerformerSelected"); //$NON-NLS-1$
	private static String MISSING_SESSION_MESSAGE = Messages.getString("UploadDialog.NoSessionSelected"); //$NON-NLS-1$
	private static String MISSING_TRIAL_MESSAGE = Messages.getString("UploadDialog.NoTrialSelected"); //$NON-NLS-1$
	private static String PRESS_UPLOAD_MESSAGE = Messages.getString("UploadDialog.PressUploadToSendFile"); //$NON-NLS-1$
	private static String BROWSE = Messages.getString("Browse"); //$NON-NLS-1$
	
	private JButton browseButton;
	private JButton uploadButton;
	private JButton cancelButton;
	private JTextField filePathText;
	private JTextField descriptionText;
	private JLabel idLabel;
	private JComboBox entityComboBox;
	
	private JProgressBar progressBar;
	
	private EntityKind entityKind;
	private int recordId;
	private boolean selectId = true;
	protected File[] selectedFiles;
	
	public UploadDialog(EntityKind entityKind) {
		super(UPLOAD_TITLE, CHOOSE_FILE_MESSAGE);
		this.entityKind = entityKind;
		
		this.finishUserInterface();
	}
	
	public UploadDialog(EntityKind entityKind, int recordId) {
		super(UPLOAD_TITLE, CHOOSE_FILE_MESSAGE);
		this.entityKind = entityKind;
		this.recordId = recordId;
		this.selectId = false;
		
		this.finishUserInterface();
	}
	
	protected void constructUserInterface() {
		// Form area
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		// Path
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add( new JLabel(LABEL_PATH), gridBagConstraints);
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
		
		// ID number
		idLabel = new JLabel();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		formPanel.add(idLabel, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		
		entityComboBox = new JComboBox();
		entityComboBox.setSize(new Dimension(200, entityComboBox.getPreferredSize().height));
		entityComboBox.setPreferredSize(new Dimension(200, entityComboBox.getPreferredSize().height));
		formPanel.add(entityComboBox, gridBagConstraints);
		
		
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
		this.setSize(480, 210);
		
		this.idLabel.setText(this.entityKind.getGUIName() + Messages.COLON);
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					UploadDialog.this.getEntityComboBoxContents();
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				UploadDialog.this.validateResult();
			}
		};
		worker.execute();
	}
	
	private void getEntityComboBoxContents() throws Exception {
		if (this.selectId == false) {
			entityComboBox.setEnabled(false);
			entityComboBox.addItem(WebServiceInstance.getDatabaseConnection().getById(this.recordId, entityKind));
		} else {
			if (this.entityKind.equals(EntityKind.session)) {
				DbElementsList<Session> list = WebServiceInstance.getDatabaseConnection().listLabSessionsWithAttributes(AppletToolBar.getLabId());
				for (Session s : list) {
					entityComboBox.addItem(s);
				}
			} else if (this.entityKind.equals(EntityKind.performer)) {
				DbElementsList<Performer> list = WebServiceInstance.getDatabaseConnection().listLabPerformersWithAttributes(AppletToolBar.getLabId());
				for (Performer p : list) {
					entityComboBox.addItem(p);
				}
			} else if (this.entityKind.equals(EntityKind.trial)) {
				DbElementsList<Trial> list = WebServiceInstance.getDatabaseConnection().listSessionTrialsWithAttributes(1);
				for (Trial t : list) {
					entityComboBox.addItem(t);
				}
			}
		}
	}	

	protected void addListeners() {
		this.browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = createFileChooser();
				fileChooser.showOpenDialog(UploadDialog.this);
				
				File[] files = fileChooser.getSelectedFiles();
				if (files.length > 0 ) {
					StringBuffer sb = new StringBuffer();
					for (File f : files)
						sb.append(' ').append('\"').append( f.getName()).append('\"');
					UploadDialog.this.filePathText.setText( sb.toString());
					UploadDialog.this.selectedFiles = files;
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
								UploadDialog.this.uploadButton.setEnabled( false );
								int id = UploadDialog.this.getId();
								String description = UploadDialog.this.getDescription();
								uploadFilesDirectories( id, selectedFiles, description );
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
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
			
			private void uploadFilesDirectories(int id, File[] selectedFiles, String description) throws Exception {
				
				WebServiceInstance.getDatabaseConnection().uploadFilesDirectories(id, UploadDialog.this.entityKind, description, selectedFiles, new UploadTransferListener());
			}

		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WebServiceInstance.getDatabaseConnection().cancelCurrentFileTransfer();
				UploadDialog.this.setVisible(false);
				UploadDialog.this.dispose();
			}
		});
	}
	
	public static JFileChooser createFileChooser()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(".zip", "zip"));
		fileChooser.setFileFilter(new FileNameExtensionFilter(".asf", "asf"));
		fileChooser.setFileFilter(new FileNameExtensionFilter(".amc", "amc"));
		fileChooser.setFileFilter(new FileNameExtensionFilter(".c3d", "c3d"));
		fileChooser.setFileFilter(new FileNameExtensionFilter(".avi", "avi"));
		fileChooser.setFileFilter(new FileNameExtensionFilter(
				".avi .c3d .amc .asf .zip",
				"avi", "c3d", "amc", "asf", "zip"
				));
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		return fileChooser;
	}
	
	
	private String getDescription() {
		
		return this.descriptionText.getText();
	}
	
	private int getId() {
		int id = -1;
		
		if (this.selectId == true) {
			if (this.entityComboBox.getSelectedItem() != null) {
				try {
					if (this.entityKind.equals(EntityKind.session)) {
						id = Integer.parseInt(((Session) this.entityComboBox.getSelectedItem()).
								get(SessionStaticAttributes.SessionID.toString()).
								value.toString());
					} else if (this.entityKind.equals(EntityKind.performer)) {
						id = Integer.parseInt(((Performer) this.entityComboBox.getSelectedItem()).
								get(PerformerStaticAttributes.PerformerID.toString()).
								value.toString());
					} else if (this.entityKind.equals(EntityKind.trial)) {
						id = Integer.parseInt(((Trial) this.entityComboBox.getSelectedItem()).
								get(TrialStaticAttributes.TrialID.toString()).
								value.toString());
					}
				} catch (NumberFormatException e) {
					this.messageLabel.setText(getMessage());
				}
			}
		} else {
			
			return this.recordId;
		}
		
		return id;
	}
	
	private String getMessage() {
		if (this.entityKind.equals(EntityKind.performer)) {
			
			return MISSING_PERFORMER_MESSAGE;
		} else if (this.entityKind.equals(EntityKind.session)) {
			
			return MISSING_SESSION_MESSAGE;
		} else {
			
			return MISSING_TRIAL_MESSAGE;
		}
	}
	
	private boolean validateResult() {
		if (this.selectedFiles == null || this.selectedFiles.length == 0 ) 
		{ 
			this.messageLabel.setText(MISSING_FILE_PATH_MESSAGE);
			return false;
		} 
		else if (this.getId() < 0) {
			this.messageLabel.setText(getMessage());
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
