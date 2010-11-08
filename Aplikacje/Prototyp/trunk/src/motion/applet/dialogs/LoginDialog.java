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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import motion.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;

public class LoginDialog extends BasicDialog {
	private static String LOGIN_TITLE = Messages.getString("LoginDialog.LoginTitle"); //$NON-NLS-1$
	private static String USER = Messages.getString("LoginDialog.UserName") + Messages.COLON; //$NON-NLS-1$
	private static String DOMAIN = Messages.getString("LoginDialog.Domain") + Messages.COLON; //$NON-NLS-1$
	private static String PASSWORD = Messages.getString("LoginDialog.Password") + Messages.COLON; //$NON-NLS-1$
	private static String LOGIN = Messages.getString("LoginDialog.Login"); //$NON-NLS-1$
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String WELCOME_TITLE = Messages.getString("LoginDialog.EnterUserNameAndPassword"); //$NON-NLS-1$
	
	private JTextField loginText;
	private JTextField domainText;
	private JPasswordField passwordText;
	private JButton loginButton;
	private JButton cancelButton;
	
	public static enum LoginResult
	{
		LOGIN_SUCCESSFUL, LOGIN_UNSUCCESSFUL, CANCEL_PRESSED, UNKNOWN 
	};
	
	public static int LOGIN_SUCCESSFUL = 1;
	public static int LOGIN_UNSUCCESSFUL = 2;
	public static int CANCEL_PRESSED = 0;
	public static int UNKNOWN = 3;
	private LoginResult result = LoginResult.UNKNOWN;
	public boolean finished = false;
	
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
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		JLabel loginLabel = new JLabel(USER);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		loginPanel.add(loginLabel, gridBagConstraints);
		
		loginText = new JTextField(10);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		loginLabel.setLabelFor(loginText);
		loginPanel.add(loginText, gridBagConstraints);
		
		JLabel passwordLabel = new JLabel(PASSWORD);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		loginPanel.add(passwordLabel, gridBagConstraints);
		
		passwordText = new JPasswordField(10);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		passwordLabel.setLabelFor(passwordText);
		loginPanel.add(passwordText, gridBagConstraints);
		
		JLabel domainLabel = new JLabel(DOMAIN);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		loginPanel.add(domainLabel, gridBagConstraints);
		
		domainText = new JTextField(10);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		domainLabel.setLabelFor(domainText);
		loginPanel.add(domainText, gridBagConstraints);
		
		this.add(loginPanel, BorderLayout.CENTER);
		
		// Button area
		loginButton = new JButton(LOGIN);
		this.addToButtonPanel(loginButton);
		this.getRootPane().setDefaultButton(loginButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
		
		// Initial text
		loginText.setText( "applet_user" );
		passwordText.setText( "aplet4Motion" );
		domainText.setText("dbpawell");
		//domainText.setText("pjwstk");
		
	}
	
	protected void finishUserInterface() {
		this.setSize(250, 200);
		this.setResizable(false);
	}
	
	protected void addListeners() {
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				
				LoginDialog.this.loginButton.setEnabled(false);
				WebServiceInstance.getDatabaseConnection().setWSCredentials( loginText.getText().trim(), passwordText.getText(), domainText.getText());
				WebServiceInstance.getDatabaseConnection().setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");

				// Check user credentials
				try {
					boolean exists = WebServiceInstance.getDatabaseConnection().checkUserAccount();
					if (!exists)
					{
						// Login dialog
						UserDetailsDialog userDetailsDialog = new UserDetailsDialog();
						userDetailsDialog.setVisible(true);
						
						// Check if login was successful
						if (userDetailsDialog.getResult() == UserDetailsDialog.OK_PRESSED)									
							WebServiceInstance.getDatabaseConnection().createUserAccount(
									userDetailsDialog.getFirstName(), userDetailsDialog.getLastName());
					}
					setResult(LoginResult.LOGIN_SUCCESSFUL);
					
				} catch (Exception e) {
					
					DatabaseConnection.log.severe( e.getMessage() );
					setResult(LoginResult.LOGIN_UNSUCCESSFUL);
				}
				
				LoginDialog.this.finished = true;
				LoginDialog.this.setVisible(false);
				LoginDialog.this.dispose();
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginDialog.this.setResult(LoginResult.CANCEL_PRESSED);
				
				LoginDialog.this.setVisible(false);
				LoginDialog.this.finished = true;
				LoginDialog.this.dispose();
			}
		});
	}
	
	private void setResult(LoginResult result) {
		
		this.result = result;
	}
	
	public LoginResult getResult() {
		
		return this.result;
	}
}
