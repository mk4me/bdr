package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.treetable.JTreeTable;
import javax.xml.datatype.DatatypeConfigurationException;

import motion.applet.Messages;
import motion.applet.models.EntityEditorModel;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;

public class PerformerDialog extends BasicDialog {
	private static String TITLE = "New performer";
	private static String WELCOME_MESSAGE = "Create new performer.";
	private static String CREATE = Messages.CREATE;
	private static String CANCEL = Messages.CANCEL;
	private static String SESSION_ATTRIBUTES_LABEL = "Performer" + Messages.COLON;
	private static String MISSING_PERFORMER_FIRST_NAME_MESSAGE = "Please input the first name of the performer.";
	private static String MISSING_PERFORMER_LAST_NAME_MESSAGE = "Please input the last name of the performer.";
	
	private JPanel centerPanel;
	
	private JButton createButton;
	private JButton cancelButton;
	
	private JTreeTable treeTable;
	
	public PerformerDialog() {
		super(TITLE, WELCOME_MESSAGE);
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
			Performer performer = new Performer();
			HashMap<String, EntityAttributeGroup> attributesDefined = WebServiceInstance.getDatabaseConnection().listGrouppedAttributesDefined("performer");
			performer.put(PerformerStaticAttributes.performerID, 0);	// Error if there is no sessionID.
			
			performer.put(PerformerStaticAttributes.firstName, "");	// [1, 1]
			performer.put(PerformerStaticAttributes.lastName, "");	// [2, 1]
			
			performer.addEmptyGenericAttributes(attributesDefined);
			treeTable = new JTreeTable(new EntityEditorModel(performer));
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
				if (PerformerDialog.this.validateResult() == true) {
					//UploadDialog.this.result = CANCEL_PRESSED;
					try {/*
						//System.out.println(SessionDialog.this.getSessionDate());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = dateFormat.parse(PerformerDialog.this.getSessionDate());
						DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
						XMLGregorianCalendar birthDate = datatypeFactory.newXMLGregorianCalendar();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						birthDate.setDay(calendar.get(Calendar.DAY_OF_MONTH));
						birthDate.setMonth(calendar.get(Calendar.MONTH));
						birthDate.setYear(calendar.get(Calendar.YEAR));
						*/
						int performerID = WebServiceInstance.getDatabaseConnection().createPerformer(
								PerformerDialog.this.getPerformerFirstName(),
								PerformerDialog.this.getPerformerLastName());
						if (!PerformerDialog.this.getPerformerBirthDate().equals("")) {
							WebServiceInstance.getDatabaseConnection().setPerformerAttribute(
									performerID,
									PerformerDialog.this.treeTable.getModel().getValueAt(4, 0).toString(),
									PerformerDialog.this.getPerformerBirthDate(),
									false);
						}
						if (!PerformerDialog.this.getPerformerDiagnosis().equals("")) {
							WebServiceInstance.getDatabaseConnection().setPerformerAttribute(
									performerID,
									PerformerDialog.this.treeTable.getModel().getValueAt(5, 0).toString(),
									PerformerDialog.this.getPerformerDiagnosis(),
									false);
						}
						PerformerDialog.this.setVisible(false);
						PerformerDialog.this.dispose();
					} catch (ParseException e1) {
						//SessionDialog.this.messageLabel.setText(INCORRECT_SESSION_DATE_MESSAGE);
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
				PerformerDialog.this.setVisible(false);
				PerformerDialog.this.dispose();
			}
		});
	}
	
	// Get form contents.
	private String getPerformerFirstName() {
		
		return this.treeTable.getModel().getValueAt(1, 1).toString();
	}
	
	private String getPerformerLastName() {
		
		return this.treeTable.getModel().getValueAt(2, 1).toString();
	}
	
	private String getPerformerBirthDate() {
		
		return this.treeTable.getModel().getValueAt(4, 1).toString();
	}
	
	private String getPerformerDiagnosis() {
		
		return this.treeTable.getModel().getValueAt(5, 1).toString();
	}
	
	private boolean validateResult() {
		if (this.getPerformerFirstName().equals("")) {
			this.messageLabel.setText(MISSING_PERFORMER_FIRST_NAME_MESSAGE);
			
			return false;
		} else if (this.getPerformerLastName().equals("")) {
			this.messageLabel.setText(MISSING_PERFORMER_LAST_NAME_MESSAGE);
			
			return false;
		}
		
		return true;
	}
}