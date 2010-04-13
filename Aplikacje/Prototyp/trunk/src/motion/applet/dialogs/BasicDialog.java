package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

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
	
	// Welcome message and form validation messages.
	private void createMessageArea(String message) {
		JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.white);
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		messagePanel.setBorder(border);
		this.messageLabel = new JLabel(message);
		messagePanel.add(this.messageLabel);
		this.add(messagePanel, BorderLayout.PAGE_START);
	}
	
	// Form buttons, e.g.: OK, Cancel, Add, etc. 
	private void createButtonArea() {
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		this.buttonPanel.setLayout(buttonPanelLayout);
		JPanel separatorPanel = new JPanel();
		separatorPanel.setLayout(new BoxLayout(separatorPanel, BoxLayout.Y_AXIS));
		separatorPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		separatorPanel.add(buttonPanel);
		this.add(separatorPanel, BorderLayout.PAGE_END);
	}
	
	protected void addToButtonPanel(JComponent component) {
		this.buttonPanel.add(component);
	}
}
