package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BasicDialog extends JDialog {
	protected JLabel messageLabel;
	private JPanel buttonPanel = new JPanel();
	
	public BasicDialog(String title, String message) {
		super((JFrame) null, title, true);
		this.setSize(400, 400);
		this.setLocation(200, 200);
		this.createMessageArea(message);
		this.constructUserInterface();
		this.createButtonArea();
		this.addListeners();
	}
	
	protected abstract void constructUserInterface();
	protected abstract void addListeners();
	// Call after super(...)
	protected abstract void finishUserInterface();
	
	private void createMessageArea(String message) {
		JPanel messagePanel = new JPanel();
		this.messageLabel = new JLabel(message);
		messagePanel.add(this.messageLabel);
		this.add(messagePanel, BorderLayout.PAGE_START);
	}
	
	private void createButtonArea() {
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		this.buttonPanel.setLayout(buttonPanelLayout);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	protected void addToButtonPanel(JComponent component) {
		this.buttonPanel.add(component);
	}
}
