package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import motion.applet.panels.WizardPanel;

public class WizardSessionDialog extends WizardDialog {
	public static JProgressBar progressBar;
	private File[] files;
	
	public WizardSessionDialog(String title, ArrayList<WizardPanel> wizardPanels) {
		super(title, wizardPanels);
	}
	
	@Override
	protected void constructUserInterface() {
		// Button area
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		this.addToButtonPanel(progressBar);
		progressBar.setVisible(false);
		
		super.constructUserInterface();
	}
	
	@Override
	protected void addListeners() {
		super.addListeners();
		
		this.finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setVisible(true);
				finishButton.setEnabled(false);
				backButton.setEnabled(false);
				messageLabel.setText("Uploading session...");
				wizardPanels.get(currentStep).finishPressed();
			}
		});
	}
}