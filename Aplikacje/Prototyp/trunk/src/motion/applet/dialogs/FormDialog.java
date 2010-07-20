package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.Messages;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.AttributeName;

public class FormDialog extends BasicDialog {
	private static String CREATE = Messages.CREATE;
	private static String CANCEL = Messages.CANCEL;
	
	protected static String PRESS_CREATE_MESSAGE = "Press Create to finish.";
	
	protected JButton createButton;
	private JButton cancelButton;
	
	protected JPanel formPanel;
	protected JPanel userPanel;
	protected GridBagConstraints gridBagConstraints;
	
	public static int CREATE_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	
	protected ArrayList<FormField> definedFormFields = new ArrayList<FormField>();
	
	public FormDialog(String title, String message) {
		super(title, message);
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		
		userPanel = new JPanel();
		userPanel.setLayout( new BorderLayout() );
		
		// Form panel
		formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		JScrollPane scrollPane = new JScrollPane(formPanel);
		
		this.add( userPanel, BorderLayout.CENTER );
		userPanel.add(scrollPane, BorderLayout.CENTER);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		
		// Button area
		createButton = new JButton(CREATE);
		this.addToButtonPanel(createButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		this.setSize(460, 400);
	}
	
	@Override
	protected void addListeners() {
		this.createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormDialog.this.setResult(CREATE_PRESSED);
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormDialog.this.setResult(CANCEL_PRESSED);
				FormDialog.this.setVisible(false);
				FormDialog.this.dispose();
			}
		});
	}
	
	// Button press result.
	private void setResult(int result) {
		
		this.result = result;
	}
	
	public int getResult() {
		
		return this.result;
	}
	
	protected void addFormTextLabel(String label) {
		JLabel groupNameLabel = new JLabel(label);
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		formPanel.add(groupNameLabel, gridBagConstraints);
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.gridy++;
	}
	
	protected void addDefinedFormFields(ArrayList<AttributeName> attributes, String groupName) {
		addFormTextLabel("Defined attributes" +  " (" + groupName + "):");
		
		for (AttributeName a : attributes) {
			if (a.getType().equals(AttributeName.STRING_TYPE)) {
				FormTextField field = new FormTextField(a, gridBagConstraints, formPanel);
				definedFormFields.add(field);
			} else if (a.getType().equals(AttributeName.INTEGER_TYPE)) {
				FormNumberField field = new FormNumberField(a, gridBagConstraints, formPanel);
				definedFormFields.add(field);
			} else if (a.getType().equals(AttributeName.DATE_TYPE)) {
				FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, false);
				definedFormFields.add(field);
			}
		}
	}
	
	protected void setDefinedAttributes(TableName tableName, int id) throws Exception {
		for (FormField f : definedFormFields) {
			String attributeValue = "";
			if (f instanceof FormTextField) {
				attributeValue = ((FormTextField) f).getData();
			} else if (f instanceof FormNumberField) {
				try {
					attributeValue = "" + ((FormNumberField) f).getData();
				} catch (NumberFormatException e) {
				}
			} else if (f instanceof FormDateField) {
				try {
					attributeValue = "" + ((FormDateField) f).getData().toString();
				} catch (ParseException e) {
				} catch (DatatypeConfigurationException e) {
				}
			}
			
			if (tableName.equals(TableNamesInstance.PERFORMER)) {
				WebServiceInstance.getDatabaseConnection().setPerformerAttribute(
						id,
						f.attribute.toString(),
						attributeValue,
						false);
			} else if (tableName.equals(TableNamesInstance.SESSION)) {
				WebServiceInstance.getDatabaseConnection().setSessionAttribute(
						id,
						f.attribute.toString(),
						attributeValue,
						false);
			} else if (tableName.equals(TableNamesInstance.TRIAL)) {
				WebServiceInstance.getDatabaseConnection().setTrialAttribute(
						id,
						f.attribute.toString(),
						attributeValue,
						false);
			}
		}
	}
}

class FormField {
	protected JLabel label;
	protected JTextField text;
	protected GridBagConstraints gridBagConstraints;
	protected JPanel formPanel;
	public AttributeName attribute;
	
	public FormField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super();
		this.attribute = attribute;
		this.gridBagConstraints = gridBagConstraints;
		this.formPanel = formPanel;
		prepareField();
	}
	
	protected void prepareField() {
		label = new JLabel(attribute.toString() + ":");
		formPanel.add(label, gridBagConstraints);
		
		gridBagConstraints.gridx++;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		
		text = new JTextField(20);
		label.setLabelFor(text);
		formPanel.add(text, gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy++;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
	}
}

class FormTextField extends FormField {
	
	public FormTextField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		
	}
	
	public String getData() {
		
		return text.getText();
	}
}

class FormTextAreaField extends FormField {
	private JTextArea textArea;
	
	public FormTextAreaField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		
	}
	
	public String getData() {
		
		return textArea.getText();
	}
	
	protected void prepareField() {
		label = new JLabel(attribute.toString() + ":");
		formPanel.add(label, gridBagConstraints);
		
		gridBagConstraints.gridx++;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		
		JScrollPane textAreaScroll = new JScrollPane(textArea = new JTextArea(5, 20));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		label.setLabelFor(textArea);
		formPanel.add(textAreaScroll, gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy++;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
	}
}

class FormNumberField extends FormField {
	private String units;
	
	public FormNumberField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		finishField();
	}
	
	public FormNumberField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, String units) {
		super(attribute, gridBagConstraints, formPanel);
		this.units = units;
		finishField();
	}
	
	private void finishField() {
		text.setColumns(5);
		if (units != null) {
			label.setText(attribute.toString() + " (" + units + "):");
		}
	}
	
	public int getData() throws NumberFormatException {
		
		return Integer.parseInt(text.getText());
	}
}

class FormDateField extends FormField {
	//private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dateFormat;
	private boolean fullDate = false;
	
	public FormDateField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, boolean fullDate) {
		super(attribute, gridBagConstraints, formPanel);
		this.fullDate = fullDate;
		if (fullDate == true) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		setCurrentDate();
	}
	
	private void setCurrentDate() {
		String date = dateFormat.format(Calendar.getInstance().getTime()).toString();
		text.setText(date);
	}
	
	public XMLGregorianCalendar getData() throws ParseException, DatatypeConfigurationException {
		Date date = dateFormat.parse(text.getText());
		DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		XMLGregorianCalendar data = datatypeFactory.newXMLGregorianCalendar();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		data.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		data.setMonth(calendar.get(Calendar.MONTH));
		data.setYear(calendar.get(Calendar.YEAR));
		
		if (fullDate == true) {
			data.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			data.setMinute(calendar.get(Calendar.MINUTE));
			data.setSecond(calendar.get(Calendar.SECOND));
		}
		
		return data;
	}
}

class FormListField extends FormField {
	private JComboBox comboBox;
	private String[] list;
	
	public FormListField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, String[] list) {
		super(attribute, gridBagConstraints, formPanel);
		this.list = list;
		finishField();
	}
	
	public String getData() {
		
		return comboBox.getSelectedItem().toString();
	}
	
	protected void prepareField() {
		label = new JLabel(attribute.toString() + ":");
		formPanel.add(label, gridBagConstraints);
	}
	
	protected void finishField() {
		gridBagConstraints.gridx++;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		
		comboBox = new JComboBox(list);
		label.setLabelFor(comboBox);
		formPanel.add(comboBox, gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy++;
		
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
	}
}