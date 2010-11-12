package motion.applet.panels;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class WizardPanel extends JPanel {	// change to abstract class
	private String stepMessage;
	public boolean enableCancel;
	public boolean enableBack;
	public boolean enableNext;
	public boolean enableFinish;
	
	public String cardName;
	
	public WizardPanel(String stepMessage,
			boolean enableCancel, boolean enableBack, boolean enableNext, boolean enableFinish) {
		super();
		this.stepMessage = stepMessage;
		this.enableCancel = enableCancel;
		this.enableBack = enableBack;
		this.enableNext = enableNext;
		this.enableFinish = enableFinish;
		
		this.setLayout(new GridBagLayout());
	}
	
	//protected abstract void createWizardContents();
	
	public String getStepMessage() {
		
		return stepMessage;
	}
}