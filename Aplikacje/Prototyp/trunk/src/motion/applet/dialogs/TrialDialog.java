package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;

public class TrialDialog extends BasicDialog {
	private static String TITLE = "New trial";
	private static String WELCOME_MESSAGE = "Create new trial.";
	private static String CREATE = Messages.CREATE;
	private static String CANCEL = Messages.CANCEL;
	private static String SESSION_ATTRIBUTES_LABEL = "Performer" + Messages.COLON;
	private static String MISSING_TRIAL_DESCRIPTION_MESSAGE = "Please input the description of the trial.";
	private static String MISSING_TRIAL_DURATION_MESSAGE = "Please input the duration of the trial.";
	
	private JPanel centerPanel;
	
	private JButton createButton;
	private JButton cancelButton;
	
	private JTreeTable treeTable;
	
	private int sessionID;
	
	public TrialDialog() {
		super(TITLE, WELCOME_MESSAGE);
		this.finishUserInterface();
	}
	
	public TrialDialog(int sessionID) {
		super(TITLE, WELCOME_MESSAGE);
		this.sessionID = sessionID;
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
			Trial trial = new Trial();
			HashMap<String, EntityAttributeGroup> attributesDefined = WebServiceInstance.getDatabaseConnection().listGrouppedAttributesDefined("trial");
			trial.put(TrialStaticAttributes.trialID, 0);	// Error if there is no sessionID.
			
			trial.put(TrialStaticAttributes.trialDescription, "");	// [1, 1]
			trial.put(TrialStaticAttributes.duration, "");	// [2, 1]
			
			trial.addEmptyGenericAttributes(attributesDefined);
			treeTable = new JTreeTable(new EntityEditorModel(trial));
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
				if (TrialDialog.this.validateResult() == true) {
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
						int trialID = WebServiceInstance.getDatabaseConnection().createTrial(
								TrialDialog.this.sessionID,
								TrialDialog.this.getTrialDescription(),
								TrialDialog.this.getTrialDuration());
						/*if (!PerformerDialog.this.getPerformerBirthDate().equals("")) {
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
									PerformerDialog.this.getPerformerBirthDate(),
									false);
						}*/
						TrialDialog.this.setVisible(false);
						TrialDialog.this.dispose();
					} catch (ParseException e1) {
						//SessionDialog.this.messageLabel.setText(INCORRECT_SESSION_DATE_MESSAGE);
						//e1.printStackTrace();
					} catch (DatatypeConfigurationException e2) {
						// TODO Auto-generated catch block
						//e2.printStackTrace();
					} catch (Exception e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				}
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UploadDialog.this.result = CANCEL_PRESSED;
				TrialDialog.this.setVisible(false);
				TrialDialog.this.dispose();
			}
		});
	}
	
	// Get form contents.
	private String getTrialDescription() {
		
		return this.treeTable.getModel().getValueAt(1, 1).toString();
	}
	
	private int getTrialDuration() {
		int trialDuration = -1;
		try {
			trialDuration = Integer.parseInt(this.treeTable.getModel().getValueAt(2, 1).toString());
			
		} catch (NumberFormatException e) {
			
		}
		
		return trialDuration;
	}
	
	private boolean validateResult() {
		if (this.getTrialDescription().equals("")) {
			this.messageLabel.setText(MISSING_TRIAL_DESCRIPTION_MESSAGE);
			
			return false;
		} else if (this.getTrialDuration() <= -1) {
			this.messageLabel.setText(MISSING_TRIAL_DURATION_MESSAGE);
			
			return false;
		}
		
		return true;
	}
}