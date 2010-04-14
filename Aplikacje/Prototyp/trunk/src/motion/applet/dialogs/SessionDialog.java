package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.treetable.JTreeTable;

import motion.applet.models.EntityEditorModel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.EntityAttributeGroup;
import motion.database.GenericDescription;
import motion.database.Performer;
import motion.database.PerformerStaticAttributes;

public class SessionDialog extends BasicDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create new session.";
	private static String CANCEL = "Cancel";
	private static String PERFORMER_LABEL = "Performer:";
	private int performerID;
	private JLabel performerNameLabel;
	private JPanel centerPanel;
	
	private JButton cancelButton;
	
	public SessionDialog() {
		super(TITLE, WELCOME_MESSAGE);
	}
	
	public SessionDialog(int performerID) {
		super(TITLE, WELCOME_MESSAGE);
		this.performerID = performerID;
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		// Form area
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		
		JLabel performerLabel = new JLabel(PERFORMER_LABEL);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(performerLabel, gridBagConstraints);
		
		performerNameLabel = new JLabel();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		formPanel.add(performerNameLabel, gridBagConstraints);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(formPanel, BorderLayout.NORTH);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		// Button area
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		try {
			Performer performer = WebServiceInstance.getDatabaseConnection().getPerformerById(this.performerID);
			this.performerNameLabel.setText(performer.getId() + " " +
					performer.get(PerformerStaticAttributes.firstName).toString() + " " +
					performer.get(PerformerStaticAttributes.lastName).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			GenericDescription<?> entity = WebServiceInstance.getDatabaseConnection().getSessionById(this.performerID);
			HashMap<String, EntityAttributeGroup> g = WebServiceInstance.getDatabaseConnection().listGrouppedAttributesDefined("session");
			entity.addEmptyGenericAttributes( g );
			JTreeTable treeTable = new JTreeTable(new EntityEditorModel( entity ));
			centerPanel.add(new JScrollPane(new JScrollPane(treeTable)), BorderLayout.CENTER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void addListeners() {
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
				SessionDialog.this.setVisible(false);
				SessionDialog.this.dispose();
			}
		});
	}
}
