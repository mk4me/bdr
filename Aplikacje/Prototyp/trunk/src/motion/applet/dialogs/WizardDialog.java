package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import motion.Messages;
import motion.applet.panels.WizardPanel;

public class WizardDialog extends BasicDialog {
	private static String BACK = "< Back";
	private static String NEXT = "Next >";
	private static String FINISH = "Finish";
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	
	protected JButton backButton;
	protected JButton nextButton;
	protected JButton finishButton;
	protected JButton cancelButton;
	
	protected  ArrayList<WizardPanel> wizardPanels;
	protected int currentStep = 0;
	
	private CardLayout cardLayout;
	private JPanel formPanel;
	
	public static int CANCEL_PRESSED = 0;
	public static int FINISH_PRESSED = 1;
	private int result = CANCEL_PRESSED;
	
	public WizardDialog(String title, ArrayList<WizardPanel> wizardPanels) {
		super(title, "");
		this.wizardPanels = wizardPanels;
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		// Form area
		formPanel = new JPanel();
		cardLayout = new CardLayout();
		formPanel.setLayout(cardLayout);
		this.add(formPanel, BorderLayout.CENTER);
		
		
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
		this.setSize(520, 350);
		this.setMinimumSize(new Dimension(520, 350));
		
		int i = 0;
		for (WizardPanel w : wizardPanels) {
			w.setWizardDialog(this);
			w.cardName = String.valueOf(i);
			formPanel.add(w, w.cardName);
			i++;
		}
		
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
		
		this.nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentStep+1 < wizardPanels.size()) {
					WizardPanel currentWizardPanel = wizardPanels.get(currentStep);
					if (currentWizardPanel.nextPressed() == true) {
						currentStep++;
						WizardPanel nextWizardPanel = wizardPanels.get(currentStep);
						nextWizardPanel.afterNextPressed(currentWizardPanel);
						switchWizardPanel(nextWizardPanel);
					} else {
						String errorMessage = currentWizardPanel.getErrorMessage();
						if (!errorMessage.equals("")) {
							messageLabel.setText(errorMessage);
						}
					}
				}
			}
		});
		
		this.backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentStep-1 >= 0) {
					wizardPanels.get(currentStep).backPressed();
					currentStep--;
					switchWizardPanel(wizardPanels.get(currentStep));
				}
			}
		});
	}
	
	private void switchWizardPanel(WizardPanel wizardPanel) {
		this.messageLabel.setText(wizardPanel.getStepMessage());
		cardLayout.show(formPanel, wizardPanel.cardName);
		//this.add(wizardPanel, BorderLayout.CENTER);
		
		cancelButton.setEnabled(wizardPanel.enableCancel);
		backButton.setEnabled(wizardPanel.enableBack);
		nextButton.setEnabled(wizardPanel.enableNext);
		finishButton.setEnabled(wizardPanel.enableFinish);
	}
	
	// Button press result.
	protected void setResult(int result) {
		
		this.result = result;
	}
	
	public int getResult() {
		
		return this.result;
	}
}