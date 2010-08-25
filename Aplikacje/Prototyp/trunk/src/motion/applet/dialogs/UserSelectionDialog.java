package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import motion.applet.Messages;
import motion.applet.tables.EntityTableModel;
import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.model.EntityKind;
import motion.database.model.User;

public class UserSelectionDialog extends BasicDialog {
	private static String LOGIN_TITLE = Messages.getString("UserSelectionDialog.Title"); //$NON-NLS-1$
	private static String WELCOME_TITLE = Messages.getString("UserSelectionDialog.Welcome"); //$NON-NLS-1$
	private static String OK = Messages.getString("OK"); //$NON-NLS-1$
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	
	private JButton okButton;
	private JButton cancelButton;
	
	public static int OK_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	JTable usersTable;
	DbElementsList<User> usersList;
	
	public UserSelectionDialog() {
		super(LOGIN_TITLE, WELCOME_TITLE);
		
		this.finishUserInterface();
	}
	
	protected void constructUserInterface() {

		try {
			usersList = DatabaseConnection.getInstance().listUsers();
		} catch (Exception e) {
			usersList = new DbElementsList<User>();
			e.printStackTrace();
		}
		
		String columns[] = { Messages.getString( "User.Column.Login" ), 
				Messages.getString( "User.Column.FirstName" ),
				Messages.getString( "User.Column.LastName" ) };

		// Text fields
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout());
		
		usersTable = new JTable( new EntityTableModel(usersList) );//
		Dimension tableSize = new Dimension();
		tableSize.width = usersTable.getPreferredSize().width;
		tableSize.height = 150;
		usersTable.setPreferredScrollableViewportSize(tableSize);
		
		for (int i=0; i<columns.length; i++)
			usersTable.getColumnModel().getColumn(i).setHeaderValue( columns[i] );
		
		JScrollPane pane = new JScrollPane( usersTable );
		
		panel.add( pane, BorderLayout.CENTER );

		this.add( panel, BorderLayout.CENTER );
		
		// Button area
		okButton = new JButton(OK);
		this.addToButtonPanel(okButton);
		this.getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
		
	}
	
	protected void finishUserInterface() {
		this.setSize(250, 200);
		this.setResizable(true);
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
						UserSelectionDialog.this.setResult(OK_PRESSED);
						
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

	public DbElementsList<User> getSelectedUsers()
	{
		DbElementsList<User> users = new DbElementsList<User>();
		if (result == OK_PRESSED)
		{
			if ( usersTable.getSelectedRowCount() != 0 )
				for (int row : usersTable.getSelectedRows() )
					users.add( usersList.get( row ) );
		}
		return users;
	}
	
	public static void main(String [] args)
	{
		System.out.println( EntityKind.performer.getKeys() );
		
		DatabaseProxy database = DatabaseConnection.getInstanceWCF();
		database.setWSCredentials("applet_user", "aplet4Motion", "dbpawell");
		database.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");

		UserSelectionDialog b = new UserSelectionDialog();
		//b.setLayout( new BorderLayout() );
		
		b.setSize( 500, 500 );
		b.setVisible( true );
		b.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);		
			}
		});
	}
}
