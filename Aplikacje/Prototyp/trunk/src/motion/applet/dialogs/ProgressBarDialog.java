package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import motion.database.FileTransferListener;

public class ProgressBarDialog extends BasicDialog {
	private JProgressBar progressBar;
	private JButton cancelButton;
	public UploadTransferListener uploadTransferListener;
	
	public ProgressBarDialog(String title, String message) {
		super(title, message);
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		// Form area
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		formPanel.add(progressBar, gridBagConstraints);
		
		
		this.add(formPanel, BorderLayout.CENTER);
		// Button area
		cancelButton = new JButton("Cancel");
		cancelButton.setEnabled(false);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(250, 160);
		this.setResizable(false);
		
		uploadTransferListener = new UploadTransferListener();
	}
	
	@Override
	protected void addListeners() {
		// TODO Auto-generated method stub
		
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
			progressBar.setValue(percent);
		}
	}
}