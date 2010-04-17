package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.treetable.JTreeTable;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.models.EntityEditorModel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.EntityAttributeGroup;
import motion.database.Performer;
import motion.database.PerformerStaticAttributes;
import motion.database.Session;
import motion.database.SessionStaticAttributes;

public class SessionDialog extends BasicDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create new session.";
	private static String CREATE = "Create";
	private static String CANCEL = "Cancel";
	private static String PERFORMER_LABEL = "Performer:";
	private int performerID;
	private JLabel performerNameLabel;
	private JPanel centerPanel;
	
	private JButton createButton;
	private JButton cancelButton;
	
	private JTreeTable treeTable;
	
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
		createButton = new JButton(CREATE);
		this.addToButtonPanel(createButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		try {
			Performer performer = WebServiceInstance.getDatabaseConnection().getPerformerById(this.performerID);
			this.performerNameLabel.setText(performer.getId() + " " +
					performer.get(PerformerStaticAttributes.firstName.toString()).value + " " +
					performer.get(PerformerStaticAttributes.lastName.toString()).value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Session session = new Session();
			HashMap<String, EntityAttributeGroup> attributesDefined = WebServiceInstance.getDatabaseConnection().listGrouppedAttributesDefined("session");
			session.put(SessionStaticAttributes.sessionID, 0);	// Error if there is no sessionID.
			//session.put(SessionStaticAttributes.performerID, this.performerID);
			session.put(SessionStaticAttributes.sessionDescription, "");	// [1, 1]
			session.put(SessionStaticAttributes.sessionDate, "");	// [1, 2]
			//session.put(SessionStaticAttributes.motionKindID, 1);
			//session.put(SessionStaticAttributes.labID, 1);
			//session.put(SessionStaticAttributes.userID, 1);
			session.addEmptyGenericAttributes(attributesDefined);
			treeTable = new JTreeTable(new EntityEditorModel(session));
			centerPanel.add(new JScrollPane(new JScrollPane(treeTable)), BorderLayout.CENTER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void addListeners() {
		this.createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
				try {
					//System.out.println(SessionDialog.this.getSessionDate());
					//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					//Date date = dateFormat.parse(SessionDialog.this.getSessionDate());
					DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
					XMLGregorianCalendar sessionDate = datatypeFactory.newXMLGregorianCalendar();
					sessionDate.setDay(1);
					sessionDate.setMonth(1);
					sessionDate.setYear(2010);
					WebServiceInstance.getDatabaseConnection().createSession(
							SessionDialog.this.performerID,
							new int[]{},
							SessionDialog.this.getSessionDescription(),
							1,
							1,
							sessionDate,
							"kopniak z pó³obrotu");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SessionDialog.this.setVisible(false);
				SessionDialog.this.dispose();
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
				SessionDialog.this.setVisible(false);
				SessionDialog.this.dispose();
			}
		});
	}
	
	// Get form contents.
	private String getSessionDescription() {
		
		return this.treeTable.getModel().getValueAt(1, 1).toString();
	}
	
	private String getSessionDate() {
		
		return this.treeTable.getModel().getValueAt(2, 1).toString();
	}
}
