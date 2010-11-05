package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
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

public class OkCancelDialog extends BasicDialog {
	
	private static String DIALOG_TITLE = "Motion Dialog";
	private static String WELCOME_TITLE = "Press OK to continue";
	
	public static int OK_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	public static int UNKNOWN = 3;

	private int result = UNKNOWN;
	public boolean finished = false;
	
	private JButton okButton;
	private JButton cancelButton;
	private FormValidator validator;

	
	public OkCancelDialog(String message) {
		this( new JLabel( message ) );
	}
	
	public OkCancelDialog(Component content) {
		super(DIALOG_TITLE, WELCOME_TITLE);

		JPanel container = new JPanel( new BorderLayout() );
		container.add( content, BorderLayout.CENTER );
		container.add( new JPanel(), BorderLayout.NORTH );
		container.add( new JPanel(), BorderLayout.WEST );
		container.add( new JPanel(), BorderLayout.EAST );
		container.add( new JPanel(), BorderLayout.SOUTH );
		
		this.add( container );
		this.setLocationByPlatform( true );
		this.pack();
	}
	
	public void setMessage(String message)
	{
		this.messageLabel.setText( message );
		this.messageLabel.invalidate();
	}
	
	protected void constructUserInterface() {
		
		// Button area
		okButton = new JButton(Messages.getString("OK"));
		this.addToButtonPanel(okButton);
		this.getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton(Messages.getString("Cancel"));
		this.addToButtonPanel(cancelButton);
	}
	
	protected void finishUserInterface() {
	}
	
	protected void addListeners() {
		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (validator!=null)
				{
					if (validator.validate())
						finish();
					else if (validator.getErrorMessage() != null)
						OkCancelDialog.this.setMessage( validator.getErrorMessage() );	
				}
				else 
					finish();
			}
			
			private void finish()
			{
				setResult(OK_PRESSED);
				OkCancelDialog.this.finished = true;
				OkCancelDialog.this.setVisible(false);
				OkCancelDialog.this.dispose();
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setResult(CANCEL_PRESSED);
				OkCancelDialog.this.setVisible(false);
				OkCancelDialog.this.finished = true;
				OkCancelDialog.this.dispose();
			}
		});
	}
	
	private void setResult(int result) {
		
		this.result = result;
	}
	
	public int getResult() {
		
		return this.result;
	}

	public void setValidator(FormValidator formValidator) {
		this.validator = formValidator;
		
	}
}
