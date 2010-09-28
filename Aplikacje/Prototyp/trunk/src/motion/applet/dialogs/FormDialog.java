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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;

public class FormDialog extends BasicDialog {
	private static String CREATE = Messages.getString("Create"); //$NON-NLS-1$
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	
	protected static String PRESS_CREATE_MESSAGE = "Press Create to finish.";
	
	protected JButton createButton;
	private JButton cancelButton;
	
	protected JPanel formPanel;
	protected GridBagConstraints gridBagConstraints;
	
	public static int CREATE_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	
	private boolean showTabs = false;
	private JTabbedPane formTabs;
	
	protected ArrayList<FormField> definedFormFields = new ArrayList<FormField>();
	
	private static String STATIC_ATTRIBUTE_GROUP = "_static";
	private static String ATTRIBUTES_TAB_NAME = "Attributes";
	
	public FormDialog(String title, String message) {
		super(title, message);
		
		this.finishUserInterface();
	}
	
	public FormDialog(String title, String message, boolean showTabs) {
		super(title, message);
		this.showTabs = showTabs;
		
		this.finishUserInterface();
	}
	
	@Override
	protected void constructUserInterface() {
		// Form panel
		formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
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
		
		JScrollPane scrollPane = new JScrollPane(formPanel);
		
		if (showTabs == true) {
			formTabs = new JTabbedPane();
			formTabs.addTab(ATTRIBUTES_TAB_NAME, scrollPane);
			this.add(formTabs, BorderLayout.CENTER);
		} else {
			this.add(scrollPane, BorderLayout.CENTER);
		}
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
	
	protected JTabbedPane getFormTabbedPane() {
		
		return this.formTabs;
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
	
	protected void addFormFields(EntityAttributeGroup attributes, String groupName) {
		if (!groupName.equals(STATIC_ATTRIBUTE_GROUP)) {
			addFormTextLabel(groupName + ":");
		}
		
		for (EntityAttribute a : attributes) {
			if (a.getEnumValues() != null) {
				if (a.getType().equals(EntityAttribute.INTEGER_TYPE_SHORT)) {
					FormListField field = new FormListField(a, gridBagConstraints, formPanel, a.getEnumValues().toArray(new String[0]), true);
					definedFormFields.add(field);
				} else {
					FormListField field = new FormListField(a, gridBagConstraints, formPanel, a.getEnumValues().toArray(new String[0]), false);
					definedFormFields.add(field);
				}
			} else if (a.getType().equals(EntityAttribute.STRING_TYPE)) {
				if (a.getSubtype().equals(EntityAttribute.SUBTYPE_SHORT_STRING)) {
					FormTextField field = new FormTextField(a, gridBagConstraints, formPanel);
					definedFormFields.add(field);
				} else if (a.getSubtype().equals(EntityAttribute.SUBTYPE_LONG_STRING)) {
					FormTextAreaField field = new FormTextAreaField(a, gridBagConstraints, formPanel);
					definedFormFields.add(field);
				} else if (a.getSubtype().equals(EntityAttribute.SUBTYPE_DATE)) {	// FIXME: duplicate date as subtype
					FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, false);
					definedFormFields.add(field);
				} else if (a.getSubtype().equals(EntityAttribute.SUBTYPE_DATE_TIME)) {
					FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, true);
					definedFormFields.add(field);
				}
			} else if (a.getType().equals(EntityAttribute.INTEGER_TYPE) || a.getType().equals(EntityAttribute.INTEGER_TYPE_SHORT)) {
				FormNumberField field = new FormNumberField(a, gridBagConstraints, formPanel);
				definedFormFields.add(field);
			} else if (a.getType().equals(EntityAttribute.DATE_TYPE)) { // FIXME: duplicate date as type
				FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, false);
				definedFormFields.add(field);
			}
		}
	}
	
	protected boolean setAttributeValue(FormField f) {
		Object attributeValue = null;
		if (f instanceof FormTextField) {
			attributeValue = ((FormTextField) f).getData();
		} else if (f instanceof FormTextAreaField) {
			attributeValue = ((FormTextAreaField) f).getData();
		} else if (f instanceof FormNumberField) {
			try {
				attributeValue = ((FormNumberField) f).getData();
			} catch (NumberFormatException e) {
				this.messageLabel.setText("Incorrect number in the field " + f.attribute.name + ".");
				
				return false;
			}
		} else if (f instanceof FormDateField) {
			try {
				attributeValue = ((FormDateField) f).getData();
			} catch (ParseException e) {
				this.messageLabel.setText("Incorrect date in the field " + f.attribute.name + ".");
				
				return false;
			} catch (DatatypeConfigurationException e) {
				this.messageLabel.setText("Incorrect date in the field " + f.attribute.name + ".");
				
				return false;
			}
		} else if (f instanceof FormListField) {
			attributeValue = ((FormListField) f).getData().toString();
		}
		
		if (attributeValue != null) {
			f.attribute.setValueFromString(attributeValue);
		}
		
		return true;
	}
	
	protected void setDefinedAttributes(int id) throws Exception {
		for (FormField f : definedFormFields) {
			if (!f.attribute.groupName.equals(STATIC_ATTRIBUTE_GROUP)) {
				setAttributeValue(f);
				WebServiceInstance.getDatabaseConnection().setEntityAttribute(
						id,
						f.attribute,
						false);
			}
		}
	}
	
	protected Object getAttributeValue(EntityKind entityKind, String attribute) {
		for (FormField f : definedFormFields) {
			if (f.attribute.name.equals(attribute)) {
				setAttributeValue(f);
				
				return f.attribute.value;
			}
		}
		
		return null;
	}
	
	protected boolean validateResult() {
		for (FormField f : definedFormFields) {
			if (!f.attribute.groupName.equals(STATIC_ATTRIBUTE_GROUP)) {
				if (setAttributeValue(f) == false) {
					
					return false;
				}
			}
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return true;
	}
}

class FormField {
	protected JLabel label;
	protected JTextField text;
	protected GridBagConstraints gridBagConstraints;
	protected JPanel formPanel;
	public EntityAttribute attribute;
	
	public FormField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super();
		this.attribute = attribute;
		this.gridBagConstraints = gridBagConstraints;
		this.formPanel = formPanel;
		//System.out.println(attribute.toString() + " " + attribute.getType() + " " + attribute.getSubType() + " " + attribute.getUnit() + " " + attribute.getEnumValues());
		prepareField();
	}
	
	protected void prepareField() {
		addLabel();
		
		text = new JTextField(20);
		label.setLabelFor(text);
		formPanel.add(text, gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy++;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
	}
	
	protected void addLabel() {
		if (attribute.getUnit() != null) {
			label = new JLabel(attribute.toString() + " (" + attribute.getUnit() + ") " + ":");
		} else {
			label = new JLabel(attribute.toString() + ":");
		}
		formPanel.add(label, gridBagConstraints);
		
		gridBagConstraints.gridx++;
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
	}
}

class FormTextField extends FormField {
	
	public FormTextField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		
	}
	
	public String getData() {
		
		return text.getText();
	}
}

class FormTextAreaField extends FormField {
	private JTextArea textArea;
	
	public FormTextAreaField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		
	}
	
	public String getData() {
		
		return textArea.getText();
	}
	
	protected void prepareField() {
		addLabel();
		
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
	
	public FormNumberField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		finishField();
	}
	
	private void finishField() {
		text.setColumns(5);
	}
	
	public int getData() throws NumberFormatException {
		
		return Integer.parseInt(text.getText());
	}
}

class FormDateField extends FormField {
	//private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dateFormat;
	private boolean fullDate = false;
	
	public FormDateField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, boolean fullDate) {
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
	private boolean dataAsIndex = false;
	
	public FormListField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, String[] list, boolean dataAsIndex) {
		super(attribute, gridBagConstraints, formPanel);
		this.list = list;
		this.dataAsIndex = dataAsIndex;
		finishField();
	}
	
	public String getData() {
		if (dataAsIndex == false) {
			
			return comboBox.getSelectedItem().toString();
		} else {
			
			return "" + comboBox.getSelectedIndex();
		}
	}
	
	protected void prepareField() {
		addLabel();
	}
	
	protected void finishField() {
		comboBox = new JComboBox(list);
		label.setLabelFor(comboBox);
		formPanel.add(comboBox, gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy++;
		
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
	}
}