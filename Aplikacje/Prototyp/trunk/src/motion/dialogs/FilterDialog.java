package motion.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilterDialog extends JDialog {
	private static String LOGIN_TITLE = "Add filter";
	private static String FILTER_NAME = "Name:";
	private static String ADD_FILTER = "Add";
	private static String CANCEL_FILTER = "Cancel";
	private static String EDIT_FILTER = "Edit";
	private JTextField nameText;
	private JButton addButton;
	private JButton cancelButton;
	public static int ADD_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	private JLabel messageLabel;
	private static String WELCOME_MESSAGE = "Add new filter.";
	private static String MISSIN_NAME_MESSAGE = "Please type the name of the filter.";
	
	public FilterDialog() {
		super((JFrame) null, LOGIN_TITLE, true);
		this.setSize(250, 200);
		this.setLocation(200, 200);
		
		constructUserInterface();
		addListeners();
	}
	
	public FilterDialog(String name) {
		this();
		this.nameText.setText(name);
		this.addButton.setText(EDIT_FILTER);
	}
	
	private void constructUserInterface() {
		// Message area
		JPanel messagePanel = new JPanel();
		this.messageLabel = new JLabel(WELCOME_MESSAGE);
		messagePanel.add(this.messageLabel);
		this.add(messagePanel, BorderLayout.PAGE_START);
		
		
		// Form area
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.ipadx = 10;
		
		JLabel nameLabel = new JLabel(FILTER_NAME);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(nameLabel, gridBagConstraints);
		
		this.nameText = new JTextField(10);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		nameLabel.setLabelFor(nameText);
		formPanel.add(nameText, gridBagConstraints);
		
		this.add(formPanel, BorderLayout.CENTER);
		
		
		// Button area
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setLayout(buttonPanelLayout);
		
		addButton = new JButton();
		addButton = new JButton(ADD_FILTER);
		buttonPanel.add(addButton);
		
		cancelButton = new JButton();
		cancelButton = new JButton(CANCEL_FILTER);
		buttonPanel.add(cancelButton);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	private void addListeners() {
		this.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FilterDialog.this.validateResult() == true) {
					FilterDialog.this.result = ADD_PRESSED;
					FilterDialog.this.setVisible(false);
					FilterDialog.this.dispose();
				}
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterDialog.this.result = CANCEL_PRESSED;
				FilterDialog.this.setVisible(false);
				FilterDialog.this.dispose();
			}
		});
	}
	
	public String getName() {
		
		return this.nameText.getText();
	}
	
	private boolean validateResult() {
		if (this.getName().equals("")) {
			this.messageLabel.setText(MISSIN_NAME_MESSAGE);
			
			return false;
		}
		
		return true;
	}
	
	private int setResult() {
		
		return this.result;
	}
	
	public int getResult() {
		
		return this.result;
	}
}
