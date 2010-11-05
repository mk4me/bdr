package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import motion.Messages;

public class UserDetailsDialog extends BasicDialog {
	private static String USER_DETAILS_TITLE = Messages.getString("UserDetailsDialog.UserTitle"); //$NON-NLS-1$
	private static String FIRSTNAME = Messages.getString("UserDetailsDialog.FirstName") + Messages.COLON; //$NON-NLS-1$
	private static String LASTNAME = Messages.getString("UserDetailsDialog.LastName") + Messages.COLON; //$NON-NLS-1$
	private static String OK = Messages.getString("OK"); //$NON-NLS-1$
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String WELCOME_TITLE = Messages.getString("UserDetailsDialog.EnterUserDetails"); //$NON-NLS-1$

	private JTextField firstNameText;
	private JTextField lastNameText;
	private JButton okButton;
	private JButton cancelButton;
	
	public static int OK_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	protected String firstName;
	protected String lastName;
	
	public UserDetailsDialog() {
		super(USER_DETAILS_TITLE, WELCOME_TITLE);
		
		this.finishUserInterface();
	}
	
	protected void constructUserInterface() {
		// Text fields
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		JLabel firstNameLabel = new JLabel(FIRSTNAME);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		loginPanel.add(firstNameLabel, gridBagConstraints);
		
		firstNameText = new JTextField(20);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		firstNameLabel.setLabelFor(firstNameText);
		loginPanel.add(firstNameText, gridBagConstraints);
		
		JLabel lastNameLabel = new JLabel(LASTNAME);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		loginPanel.add(lastNameLabel, gridBagConstraints);
		
		lastNameText = new JTextField(20);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		lastNameLabel.setLabelFor(lastNameText);
		loginPanel.add(lastNameText, gridBagConstraints);
		
		this.add(loginPanel, BorderLayout.CENTER);
		
		// Button area
		okButton = new JButton(OK);
		this.addToButtonPanel(okButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
		
	}
	
	protected void finishUserInterface() {
		this.setSize(350, 200);
		this.setResizable(false);
	}
	
	protected void addListeners() {
		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDetailsDialog.this.setResult(OK_PRESSED);
				
				UserDetailsDialog.this.firstName = firstNameText.getText();
				UserDetailsDialog.this.lastName = lastNameText.getText();
				
				UserDetailsDialog.this.setVisible(false);
				UserDetailsDialog.this.dispose();
				
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDetailsDialog.this.setResult(CANCEL_PRESSED);
				
				UserDetailsDialog.this.setVisible(false);
				UserDetailsDialog.this.dispose();
			}
		});
	}
	
	private void setResult(int result) {
		
		this.result = result;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getResult() {
		
		return this.result;
	}
}
