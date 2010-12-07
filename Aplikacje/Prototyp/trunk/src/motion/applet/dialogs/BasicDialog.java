package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import motion.applet.MotionApplet;

public abstract class BasicDialog extends JDialog {
	protected JTextPane messageLabel;
	private JPanel buttonPanel = new JPanel();
	
	public BasicDialog(String title, String message) {
		super((JFrame) null, title, true);
		this.setSize(400, 400);
		//this.setLocation(200, 200);
		this.setLocationRelativeTo(MotionApplet.contentPane);
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
		messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		messagePanel.setBackground(Color.white);
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		messagePanel.setBorder(border);
		messageLabel = new JTextPane();
		messageLabel.setText(message);
		messageLabel.setEditable(false);
		messageLabel.setMargin(new Insets(10, 10, 10, 10));
		Font font = messageLabel.getFont().deriveFont(Font.BOLD);
		messageLabel.setFont(font);
		
		Style doc = messageLabel.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(doc, StyleConstants.ALIGN_CENTER);
		messageLabel.setParagraphAttributes(doc, true);
		
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
