package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Savepoint;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import motion.Messages;
import motion.applet.MotionApplet;
import motion.applet.MotionAppletFrame;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.trees.AttributeTree;
import motion.applet.trees.ConfigurationTree;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;
import motion.database.ws.DatabaseConnection2;

public class AttributeEditDialog extends BasicDialog {

	private static String NORMAL_MESSAGE = "Press Save to permanently store entity attributes";
	private static String TITLE = "Editing generic attributes and groups"; //$NON-NLS-1$
	
	private AttributeTree tree;
	private JPanel formPanel;
	private JButton saveButton;
	private JButton cancelButton;
	
	private EntityKind entityKind;
	
	
	public AttributeEditDialog(EntityKind entityKind) {
		super(TITLE, NORMAL_MESSAGE);
		
		this.entityKind = entityKind;

		this.finishUserInterface();
	}
	
	protected void constructUserInterface() {
	
		// Form area
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		gridBagConstraints.gridwidth = 10; 
		
		formPanel = new JPanel();
		formPanel.setLayout( new BorderLayout());

		JPanel container = new JPanel ( new BorderLayout() );
		container.add(formPanel, BorderLayout.CENTER );
		container.add( new JPanel(), BorderLayout.NORTH );
		container.add( new JPanel(), BorderLayout.WEST );
		container.add( new JPanel(), BorderLayout.EAST );
		container.add( new JPanel(), BorderLayout.SOUTH );
		this.add( container );
		
		// Button area
		saveButton = new JButton("Save");
		this.addToButtonPanel( saveButton );
		cancelButton = new JButton("Cancel");
		this.addToButtonPanel( cancelButton );
	}
	
	protected void finishUserInterface() {
		this.setSize(400, 450);
		this.setLocation(200, 200);
		this.tree = new AttributeTree(entityKind);

		// Labels
		JLabel label1 = new JLabel( entityKind.getGUIName() );		
		JLabel label2 = new JLabel("generic properties:");
		JPanel topPanel = new JPanel( new GridLayout(3,1) );
		topPanel.add( label1 );
		topPanel.add( label2 );
		this.formPanel.add( topPanel, BorderLayout.NORTH );
		
		
		// Tree 
		JScrollPane treeScrollPane = new JScrollPane(tree.tree);
		this.formPanel.add( treeScrollPane, BorderLayout.CENTER);

		this.pack();
	}

	@Override
	protected void addListeners() {

			this.saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					StringBuffer message = new StringBuffer();
					message.append("<html>");
					if (tree.removeGroups.size() != 0)
						message.append("Groups to be removed:<br>").append(asString( tree.removeGroups ));
					if (tree.newGroups.size() != 0)
						message.append("Groups to be created:<br>").append(asString( tree.newGroups ));
					if (tree.removeAttributes.size() != 0)
						message.append("Attributes to be removed:<br>").append(asString( tree.removeAttributes ));
					if (tree.newAttributes.size() != 0)
						message.append("Attributes to be created:<br>").append(asString( tree.newAttributes ));
					if (tree.removeAttributes.size() == 0 &&
							tree.removeGroups.size() == 0 &&
							tree.newAttributes.size() == 0 &&
							tree.newGroups.size() == 0 )
						message.append("Nothing to do!");
					
					message.append("</html>");
					
					JLabel label = new JLabel( message.toString() );

					OkCancelDialog question = new OkCancelDialog( label );
					question.setVisible( true );
					
					if (question.getResult() == question.OK_PRESSED)
					{
						try {
							for (EntityAttributeGroup g:tree.removeGroups)
								DatabaseConnection.getInstanceWCF().removeAttributeGroup(g.name, g.kind.getName());
							for (EntityAttribute g:tree.removeAttributes)
								DatabaseConnection.getInstanceWCF().removeAttribute(g, null);
							for (EntityAttributeGroup g:tree.newGroups)
								DatabaseConnection.getInstanceWCF().defineAttributeGroup(g.name, g.kind.getName());
							for (EntityAttribute g:tree.newAttributes)
								DatabaseConnection.getInstanceWCF().defineAttribute(g, "");

							AttributeEditDialog.this.entityKind.rescanGenericAttributeGroups();
							MotionApplet.refreshTables();
						
						} catch (Exception e1) {
							
							e1.printStackTrace();
							DatabaseConnection.log.severe( e1.getMessage() );
						}
					}
					AttributeEditDialog.this.setVisible(false);
					AttributeEditDialog.this.dispose();
				}

				private void printVector( Vector<?> modifyGroups) {
					
					for ( Object o : modifyGroups )
						System.out.println( o );
				}

				private String asString( Vector<?> modifyGroups) {
					StringBuffer res = new StringBuffer();
					for ( Object o : modifyGroups )
						res.append( o.toString() ).append( "<br>" );
					return res.toString();
				}
			});

			this.cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AttributeEditDialog.this.setVisible(false);
					AttributeEditDialog.this.dispose();
				}
			});
	}

	public static void main(String [] args)
	{
		DatabaseConnection2 database = (DatabaseConnection2) DatabaseConnection.getInstanceWCF();
		database.setWSCredentials("applet_user", "aplet4Motion", "dbpawell");
		database.setFTPSCredentials("dbpawell.pjwstk.edu.pl", "testUser", "testUser");

		AttributeEditDialog d = new AttributeEditDialog( EntityKind.session );
		d.setVisible(true);
	}

}
