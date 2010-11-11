package motion.applet.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import motion.Messages;
import motion.applet.panels.WizardPanel;

public class WizardDialog extends BasicDialog {
	private static String BACK = "< Back";
	private static String NEXT = "Next >";
	private static String FINISH = "Finish";
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	
	private JButton backButton;
	private JButton nextButton;
	private JButton finishButton;
	private JButton cancelButton;
	
	private  ArrayList<WizardPanel> wizardPanels;
	
	public WizardDialog(String title, ArrayList<WizardPanel> wizardPanels) {
		super(title, "");
		this.wizardPanels = wizardPanels;
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		
		// Button area
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
		
		backButton = new JButton(BACK);
		this.addToButtonPanel(backButton);
		
		nextButton = new JButton(NEXT);
		this.addToButtonPanel(nextButton);
		
		finishButton = new JButton(FINISH);
		this.addToButtonPanel(finishButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(450, 350);
		
		if (wizardPanels.isEmpty() == false) {
			switchWizardPanel(wizardPanels.get(0));
		}
	}
	
	@Override
	protected void addListeners() {
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WizardDialog.this.setVisible(false);
				WizardDialog.this.dispose();
			}
		});
	}
	
	private void switchWizardPanel(WizardPanel wizardPanel) {
		this.messageLabel.setText(wizardPanel.getStepMessage());
		cancelButton.setEnabled(wizardPanel.enableCancel);
		backButton.setEnabled(wizardPanel.enableBack);
		nextButton.setEnabled(wizardPanel.enableNext);
		finishButton.setEnabled(wizardPanel.enableFinish);
	}
}