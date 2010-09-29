package motion.applet.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import motion.applet.dialogs.UserSelectionDialog;
import motion.applet.tables.EntityTableModel;
import motion.database.DatabaseConnection;
import motion.database.DatabaseProxy;
import motion.database.DbElementsList;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityKind;
import motion.database.model.Privileges;
import motion.database.model.User;
import motion.database.model.UserPrivileges;
import motion.database.model.UserPrivilegesStaticAttributes;
import motion.database.model.UserStaticAttributes;

public class PrivilegesPanel extends JPanel {

	String[] buttonLabels = { "private", 
							  "public for reading", 
							  "public for writing",
							  "custom"};
	String[] buttonExplanations = { "(Only you can read or write.)",
									"(Only you can write. All can read.)",
									"(All can read and write.)",
									"<html>(Customized for selected users.<br>You will always read and write.)</html>"};
	
	Privileges[] values = { Privileges.PRIVATE, 
							Privileges.PUBLIC_READ,
							Privileges.PUBLIC_WRITE,
							Privileges.CUSTOM };

	DbElementsList<UserPrivileges> privileges;
	
	public DbElementsList<UserPrivileges> getPrivileges() {
		return privileges;
	}


	JRadioButton[] buttons;
	ButtonGroup privilegesGroup; 
	ActionListener radioListener;

	JPanel bottomPanel;
	JPanel containerPanel;
	JButton addButton;
	JButton removeButton;
	JButton canWriteButton;
	Window frame;
	JTable privList;
	private JButton modifyButton;

	public PrivilegesPanel(Window outerFrame)
	{
		this( outerFrame, -1 );
	}

	public PrivilegesPanel(Window outerFrame, int sessionId)
	{
		super();
		containerPanel = this;
		this.frame = outerFrame;
		JPanel centerPanel = new JPanel();
		setLayout( new BorderLayout() );
		add( centerPanel, BorderLayout.NORTH );
		centerPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		JLabel loginLabel = new JLabel("Choose privileges:");
		gridBagConstraints.ipady = 20;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(loginLabel, gridBagConstraints);

		gridBagConstraints.ipady = 0;

		radioListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				if ( buttons[3].isSelected() )
					bottomPanel.setVisible( true );
				else
					bottomPanel.setVisible( false );
				frame.pack();
			}
		};
		
		privilegesGroup = new ButtonGroup();
		buttons = new JRadioButton[ buttonLabels.length ];

		for (int i=0; i<buttons.length; i++)
		{
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = i+1;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			buttons[i] = new JRadioButton( buttonLabels[i] );
			buttons[i].setHorizontalTextPosition( SwingConstants.RIGHT );
			buttons[i].addActionListener(radioListener);
			privilegesGroup.add( buttons[i] );
			centerPanel.add(buttons[i], gridBagConstraints);
			gridBagConstraints.gridx = 1;
			centerPanel.add(new JLabel( buttonExplanations[i] ), gridBagConstraints );
		}
		buttons[1].setSelected( true );
		//buttons[3].setEnabled( false );
		
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.setBorder(border);
		
    	Dimension marginDimension = new Dimension();
		marginDimension.width = 20;
		JPanel marginLeft = new JPanel();
		marginLeft.setPreferredSize( marginDimension );
		add( marginLeft, BorderLayout.WEST );
		JPanel marginRight = new JPanel();
		marginRight.setPreferredSize( marginDimension );
		add( marginRight, BorderLayout.EAST );
		
		bottomPanel = createPrivilegesListPanel(sessionId);
		add( bottomPanel, BorderLayout.CENTER );	
		
		bottomPanel.setVisible( false );
		
	}
	
	private JPanel createPrivilegesListPanel(int sessionId)
	{
		String columns[] = { "user login", "can read?", "can write?" };
		String data [][] = { {"", "", "" } };

		if(sessionId!=-1)
			try {
				privileges = DatabaseConnection.getInstance().listSessionPrivileges(sessionId);
				data = new String[privileges.size()][];
				int i = 0;
				for (UserPrivileges p : privileges)
					data[i++] = p.toStringArray();
				
			} catch (Exception e) {
				e.printStackTrace();
				privileges = new DbElementsList<UserPrivileges>();
			}
		else
			privileges = new DbElementsList<UserPrivileges>();
		
		
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout());

		EntityTableModel model = new EntityTableModel(privileges, new String[]{"login", "canRead", "canWrite"} ); 
		privList = new JTable( model );
		model.addTableModelListener( privList );
		Dimension tableSize = new Dimension();
		tableSize.width = privList.getPreferredSize().width;
		tableSize.height = 150;
		privList.setPreferredScrollableViewportSize(tableSize);
		
		for (int i=0; i<columns.length; i++)
			privList.getColumnModel().getColumn(i).setHeaderValue( columns[i] );
		
		JScrollPane pane = new JScrollPane( privList );
		
		panel.add( pane, BorderLayout.CENTER );
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridy = 0;
		constr.gridx = 1;
		constr.insets = new Insets( 0, 5, 5, 5 );
		addButton = new JButton ("Add Users");
		buttonPanel.add( addButton, constr );
		addButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() throws InterruptedException {
						addButton.setEnabled(false);
						return null;
					}
					
					@Override
					protected void done() {
						UserSelectionDialog b = new UserSelectionDialog();
						b.setSize( 500, 500 );
						b.setVisible( true );
						
						DbElementsList<User> users = b.getSelectedUsers();
						for (User u : users)
						{
							UserPrivileges up = new UserPrivileges();
							up.put( UserPrivilegesStaticAttributes.login, ((EntityAttribute)u.get( UserStaticAttributes.login )).value );
							up.put( UserPrivilegesStaticAttributes.canRead, true );
							up.put( UserPrivilegesStaticAttributes.canWrite, false );
							privileges.add( up );
						}
						privList.repaint();
						privList.invalidate();
						frame.pack();
						addButton.setEnabled(true);
					}
				};
				worker.execute();
			}
		});
		constr.gridy = 1;
		constr.gridx = 1;
		removeButton = new JButton("Remove");
		removeButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() throws InterruptedException {
						removeButton.setEnabled(false);
				
						if (privList.getSelectedRowCount() > 0)
							for (int row: privList.getSelectedRows() )
								privileges.remove( row );
						return null;
					}
					
					@Override
					protected void done() {
						removeButton.setEnabled(true);
						privList.repaint();
						privList.invalidate();
						frame.pack();
					}
				};
				worker.execute();
			}
		} );
		buttonPanel.add( removeButton, constr);
		
		modifyButton = new JButton("Can write");
		constr.gridy = 2;
		constr.gridx = 1;
		modifyButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() throws InterruptedException {
						modifyButton.setEnabled(false);
				
						if (privList.getSelectedRowCount() > 0)
							for (int row: privList.getSelectedRows() )
							{	
								boolean canWrite = (Boolean) ((EntityAttribute)privileges.get( row ).get( UserPrivilegesStaticAttributes.canWrite )).value;
								privileges.get( row ).remove( UserPrivilegesStaticAttributes.canWrite );
								privileges.get( row ).put( UserPrivilegesStaticAttributes.canWrite, !canWrite );
							}
						return null;
					}
					
					@Override
					protected void done() {
						modifyButton.setEnabled(true);
						privList.repaint();
						privList.invalidate();
						frame.pack();
					}
				};
				worker.execute();
			}
		} );
		buttonPanel.add( modifyButton, constr);
		
		panel.add(buttonPanel, BorderLayout.EAST);
		
		return panel;
	}
	
	
	
	public Privileges getResult()
	{
		for (int i=0; i<buttons.length; i++)
			if (buttons[i].isSelected())
				return values[i];
		return values[0];
	}
	
	
	public static void main(String [] args)
	{
		System.out.println( EntityKind.performer.getKeys() );
		
		DatabaseProxy database = DatabaseConnection.getInstanceWCF();
		database.setWSCredentials("applet_user", "aplet4Motion", "dbpawell");
		database.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");

		JFrame b = new JFrame();
		b.setLayout( new BorderLayout() );
		b.add( new PrivilegesPanel(b, 2), BorderLayout.CENTER );
		
		b.pack();
		b.setVisible( true );
		b.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);		
			}
		});
	}
}
