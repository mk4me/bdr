package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.treetable.JTreeTable;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.Messages;
import motion.applet.models.EntityEditorModel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;

public class SessionDialog extends BasicDialog {
	private static String TITLE = "New session";
	private static String WELCOME_MESSAGE = "Create new session.";
	private static String MISSING_SESSION_DESCRIPTION_MESSAGE = "Please write a session description.";
	private static String MISSING_SESSION_DATE_MESSAGE = "Please input the session date.";
	private static String INCORRECT_SESSION_DATE_MESSAGE = "Incorrect session date.";
	private static String CREATE = Messages.CREATE;
	private static String CANCEL = Messages.CANCEL;
	private static String PERFORMER_LABEL = "Performer:";
	private static String SESSION_ATTRIBUTES_LABEL = "Session:";
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
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		
		JLabel performerLabel = new JLabel(PERFORMER_LABEL);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(performerLabel, gridBagConstraints);
		
		performerNameLabel = new JLabel();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		formPanel.add(performerNameLabel, gridBagConstraints);
		
		JLabel sessionAttributesLabel = new JLabel(SESSION_ATTRIBUTES_LABEL);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		formPanel.add(sessionAttributesLabel, gridBagConstraints);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		JPanel formPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		formPanel2.add(formPanel);
		centerPanel.add(formPanel2, BorderLayout.NORTH);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		// Button area
		createButton = new JButton(CREATE);
		this.addToButtonPanel(createButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(620, 400);
		
		try {
			Performer performer = WebServiceInstance.getDatabaseConnection().getPerformerById(this.performerID);
			this.performerNameLabel.setText(
					performer.get(PerformerStaticAttributes.firstName.toString()).value + " " +
					performer.get(PerformerStaticAttributes.lastName.toString()).value + " (" +
					performer.getId() + ")");
		} catch (Exception e) {
			ExceptionDialog exceptionDialog = new ExceptionDialog(e);
			exceptionDialog.setVisible(true);
		}
		
		try {
			Session session = new Session();
			HashMap<String, EntityAttributeGroup> attributesDefined = WebServiceInstance.getDatabaseConnection().listGrouppedAttributesDefined("session");
			session.put(SessionStaticAttributes.sessionID, 0);	// Error if there is no sessionID.
			//session.put(SessionStaticAttributes.performerID, this.performerID);
			session.put(SessionStaticAttributes.sessionDescription, "");	// [1, 1]
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(Calendar.getInstance().getTime()).toString();
			session.put(SessionStaticAttributes.sessionDate, date);	// [2, 1]
			//session.put(SessionStaticAttributes.motionKindID, 1);
			//session.put(SessionStaticAttributes.labID, 1);
			//session.put(SessionStaticAttributes.userID, 1);
			session.addEmptyGenericAttributes(attributesDefined);
			treeTable = new JTreeTable(new EntityEditorModel(session));
			centerPanel.add(new JScrollPane(new JScrollPane(treeTable)), BorderLayout.CENTER);
		} catch (Exception e) {
			ExceptionDialog exceptionDialog = new ExceptionDialog(e);
			exceptionDialog.setVisible(true);
		}
	}
	
	@Override
	protected void addListeners() {
		this.createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SessionDialog.this.validateResult() == true) {
					//UploadDialog.this.result = CANCEL_PRESSED;
					try {
						//System.out.println(SessionDialog.this.getSessionDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = dateFormat.parse(SessionDialog.this.getSessionDate());
						DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
						XMLGregorianCalendar sessionDate = datatypeFactory.newXMLGregorianCalendar();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						sessionDate.setDay(calendar.get(Calendar.DAY_OF_MONTH));
						sessionDate.setMonth(calendar.get(Calendar.MONTH));
						sessionDate.setYear(calendar.get(Calendar.YEAR));
						WebServiceInstance.getDatabaseConnection().createSession(
								SessionDialog.this.performerID,
								new int[]{},
								SessionDialog.this.getSessionDescription(),
								1,
								1,
								sessionDate,
								"kopniak z pó³obrotu");
						SessionDialog.this.setVisible(false);
						SessionDialog.this.dispose();
					} catch (ParseException e1) {
						SessionDialog.this.messageLabel.setText(INCORRECT_SESSION_DATE_MESSAGE);
						ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
						exceptionDialog.setVisible(true);
					} catch (DatatypeConfigurationException e2) {
						ExceptionDialog exceptionDialog = new ExceptionDialog(e2);
						exceptionDialog.setVisible(true);
					} catch (Exception e3) {
						ExceptionDialog exceptionDialog = new ExceptionDialog(e3);
						exceptionDialog.setVisible(true);
					}
				}
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
	
	private boolean validateResult() {
		if (this.getSessionDescription().equals("")) {
			this.messageLabel.setText(MISSING_SESSION_DESCRIPTION_MESSAGE);
			
			return false;
		} else if (this.getSessionDate().equals("")) {
			this.messageLabel.setText(MISSING_SESSION_DATE_MESSAGE);
			
			return false;
		}
		
		return true;
	}
}
