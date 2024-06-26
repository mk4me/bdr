package motion.applet.panels;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import motion.applet.dialogs.WizardDialog;

public class WizardPanel extends JPanel {	//FIXME: change to abstract class
	protected WizardDialog wizardDialog;
	private String stepMessage;
	protected String errorMessage;
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
		this.errorMessage = "";
		
		this.setLayout(new GridBagLayout());
		
		this.createWizardContents();
	}
	
	public void setWizardDialog(WizardDialog wizardDialog) {
		this.wizardDialog = wizardDialog;
	}
	
	protected void createWizardContents() {	//FIXME: change to abstract
		
	}
	
	public String getStepMessage() {
		
		return stepMessage;
	}
	
	public String getErrorMessage() {
		
		return errorMessage;
	}
	
	public boolean nextPressed() {
		
		return true;
	}
	
	public void backPressed() {
		
	}
	
	public void finishPressed() {
		
	}
	
	public void cancelPressed() {
		
	}
	
	public void afterNextPressed(WizardPanel previousWizardPanel) {
		
	}
}