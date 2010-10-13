package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import motion.applet.toolbars.AppletToolBar;
import motion.applet.trees.AttributeTree;
import motion.applet.trees.ConfigurationTree;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DatabaseConnection;
import motion.database.DbElementsList;
import motion.database.FileTransferListener;
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
	
	private JButton groupAddButton;
	private AttributeTree tree;
	private JPanel formPanel;
	private EntityKind entityKind;
	
	public AttributeEditDialog(EntityKind entityKind) {
		super(TITLE, NORMAL_MESSAGE);
		this.entityKind = entityKind;

		this.finishUserInterface();
	}
	
	protected void constructUserInterface() {
	
		// buttons
		this.groupAddButton = new JButton("New Group");
		
		// Form area
		formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridx = 11;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		formPanel.add(groupAddButton, gridBagConstraints);

		gridBagConstraints.gridx = 10;
		formPanel.add( new JPanel(), gridBagConstraints);

		this.add(formPanel, BorderLayout.CENTER);
		
		// Button area
		this.addToButtonPanel( new JButton("Save") );
		this.addToButtonPanel( new JButton("Cancel") );
	}
	
	protected void finishUserInterface() {
		this.setSize(400, 450);
		this.setLocation(200, 200);
		this.tree = new AttributeTree(entityKind);

		// Tree 
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		JLabel label1 = new JLabel( entityKind.getGUIName() );		
		this.formPanel.add( label1, gridBagConstraints);

		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		JLabel label2 = new JLabel("generic properties:");
		this.formPanel.add( label2, gridBagConstraints);
		gridBagConstraints.gridy = 2;
		this.formPanel.add( new JPanel() );
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 8;
		gridBagConstraints.gridwidth = 8;
		JScrollPane treeScrollPane = new JScrollPane(tree.tree);
		this.formPanel.add( treeScrollPane, gridBagConstraints);

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				return null;
			}
			
			@Override
			protected void done() {
			}
		};
		worker.execute();
	}

	@Override
	protected void addListeners() {
		// TODO Auto-generated method stub
		
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
