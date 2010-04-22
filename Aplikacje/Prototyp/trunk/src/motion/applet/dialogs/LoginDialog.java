package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import motion.applet.Messages;

public class LoginDialog extends BasicDialog {
	private static String LOGIN_TITLE = Messages.getString("LoginDialog.LoginTitle"); //$NON-NLS-1$
	private static String USER = Messages.getString("LoginDialog.UserName") + Messages.COLON; //$NON-NLS-1$
	private static String PASSWORD = Messages.getString("LoginDialog.Password") + Messages.COLON; //$NON-NLS-1$
	private static String LOGIN = Messages.getString("LoginDialog.Login"); //$NON-NLS-1$
	private static String WELCOME_TITLE = Messages.getString("LoginDialog.EnterUserNameAndPassword"); //$NON-NLS-1$
	
	private JButton loginButton;
	
	public LoginDialog() {
		super(LOGIN_TITLE, WELCOME_TITLE);
		
		this.finishUserInterface();
	}
	
	protected void constructUserInterface() {
		// Text fields
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.ipadx = 10;
		
		JLabel loginLabel = new JLabel(USER);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		loginPanel.add(loginLabel, gridBagConstraints);
		
		JTextField loginText = new JTextField(10);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		loginLabel.setLabelFor(loginText);
		loginPanel.add(loginText, gridBagConstraints);
		
		JLabel passwordLabel = new JLabel(PASSWORD);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		loginPanel.add(passwordLabel, gridBagConstraints);
		
		JPasswordField passwordText = new JPasswordField(10);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		passwordLabel.setLabelFor(passwordText);
		loginPanel.add(passwordText, gridBagConstraints);
		
		this.add(loginPanel, BorderLayout.CENTER);
		
		// Button area
		loginButton = new JButton(LOGIN);
		this.addToButtonPanel(loginButton);
	}
	
	protected void finishUserInterface() {
		this.setSize(250, 200);
		this.setResizable(false);
		// Disable the close button
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	protected void addListeners() {
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LoginDialog.this.setVisible(false);
				LoginDialog.this.dispose();
			}
		});
	}
}
