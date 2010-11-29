package motion.applet.panels;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFileChooser;

public class WizardSessionDirectoryPanel extends WizardPanel {
	private JFileChooser fileChooser;
	
	public WizardSessionDirectoryPanel(String stepMessage,
			boolean enableCancel, boolean enableBack, boolean enableNext, boolean enableFinish) {
		super(stepMessage, enableCancel, enableBack, enableNext, enableFinish);
		
	}
	
	@Override
	protected void createWizardContents() {this.setLayout(new BorderLayout());
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setControlButtonsAreShown(false);
		this.add(fileChooser, BorderLayout.CENTER);
	}
	
	@Override
	public boolean nextPressed() {
		File file = fileChooser.getSelectedFile();
		if (file != null) {
			System.out.println(file);
			return true;
		} else {
			errorMessage = "No directory selected";
			
			return false;
		}
	}
}