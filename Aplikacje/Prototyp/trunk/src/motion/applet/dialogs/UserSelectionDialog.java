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

import motion.applet.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;

public class UserSelectionDialog extends BasicDialog {
	private static String LOGIN_TITLE = Messages.getString("LoginDialog.LoginTitle"); //$NON-NLS-1$
	private static String USER = Messages.getString("LoginDialog.UserName") + Messages.COLON; //$NON-NLS-1$
	private static String DOMAIN = Messages.getString("LoginDialog.Domain") + Messages.COLON; //$NON-NLS-1$
	private static String PASSWORD = Messages.getString("LoginDialog.Password") + Messages.COLON; //$NON-NLS-1$
	private static String LOGIN = Messages.getString("LoginDialog.Login"); //$NON-NLS-1$
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String WELCOME_TITLE = Messages.getString("LoginDialog.EnterUserNameAndPassword"); //$NON-NLS-1$
	
	private JButton okButton;
	private JButton cancelButton;
	
	public static int OK_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	
	public UserSelectionDialog() {
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
		
		
		this.add(loginPanel, BorderLayout.CENTER);
		
		// Button area
		okButton = new JButton(LOGIN);
		this.addToButtonPanel(okButton);
		this.getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
		
	}
	
	protected void finishUserInterface() {
		this.setSize(250, 200);
		this.setResizable(false);
	}
	
	protected void addListeners() {
		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() throws InterruptedException {
						UserSelectionDialog.this.okButton.setEnabled(false);
						
						return null;
					}
					
					@Override
					protected void done() {

						UserSelectionDialog.this.setVisible(false);

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
							UserSelectionDialog.this.setResult(OK_PRESSED);
							
						} catch (Exception e) {
							
							DatabaseConnection.log.severe( e.getMessage() );
							UserSelectionDialog.this.setResult(CANCEL_PRESSED);
						}
						
						// Login always successful, add login check

						UserSelectionDialog.this.dispose();
					}
				};
				worker.execute();
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserSelectionDialog.this.setResult(CANCEL_PRESSED);
				
				UserSelectionDialog.this.setVisible(false);
				UserSelectionDialog.this.dispose();
			}
		});
	}
	
	private void setResult(int result) {
		
		this.result = result;
	}
	
	public int getResult() {
		
		return this.result;
	}
}
