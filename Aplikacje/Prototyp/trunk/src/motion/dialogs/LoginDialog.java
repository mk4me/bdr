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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
	private static String LOGIN_TITLE = "Login";
	private static String USER = "User name:";
	private static String PASSWORD = "Password:";
	private static String LOGIN = "Login";
	private static String WELCOME_TITLE = "<html>Enter user name and password.</html>";
	
	private JButton loginButton;
	
	public LoginDialog() {
		super((JFrame) null, LOGIN_TITLE, true);
		this.setSize(250, 200);
		this.setLocation(200, 200);
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		// Disable the close button
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		constructUserInterface();
		addListeners();
	}
	
	private void constructUserInterface() {
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
		
		// Welcome text
		JPanel welcomePanel = new JPanel();
		JLabel welcomeTitleLabel = new JLabel(WELCOME_TITLE);
		welcomePanel.add(welcomeTitleLabel);
		
		this.add(welcomePanel, BorderLayout.PAGE_START);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		loginButton = new JButton(LOGIN);
		buttonPanel.add(loginButton);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	private void addListeners() {
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LoginDialog.this.setVisible(false);
				LoginDialog.this.dispose();
			}
		});
	}
}
