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
import javax.swing.SwingWorker;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.Messages;
import motion.applet.webservice.client.WebServiceInstance;
import motion.applet.widgets.CalendarWidget;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;

public class FormDialog extends BasicDialog {
	private static String CREATE = Messages.getString("Create"); //$NON-NLS-1$
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String EDIT = Messages.getString("OK"); //$NON-NLS-1$
	
	protected static String PRESS_CREATE_MESSAGE = "Press Create to finish.";
	protected static String UPDATING_MESSAGE = "Updating attributes...";
	
	protected JButton createButton;
	private JButton cancelButton;
	
	protected JPanel formPanel;
	protected GridBagConstraints gridBagConstraints;
	
	public static int CANCEL_PRESSED = 0;
	public static int CREATE_PRESSED = 1;
	public static int EDIT_PRESSED = 2;
	
	private int result = CANCEL_PRESSED;
	
	private boolean showTabs = false;
	private JTabbedPane formTabs;
	
	protected ArrayList<FormField> formFields = new ArrayList<FormField>();
	
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
		this.setSize(480, 400);
		
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
		if (!groupName.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
			addFormTextLabel(groupName + ":");
		}
		
		for (EntityAttribute a : attributes) {
			if (a.getEnumValues() != null) {
				System.out.println("sdfdddddd");
				//if (a.getType().equals(EntityAttribute.TYPE_INT)) {
					//FormListField field = new FormListField(a, gridBagConstraints, formPanel, a.getEnumValues().toArray(new String[0]), true);
					//formFields.add(field);
				//} else {
					FormListField field = new FormListField(a, gridBagConstraints, formPanel, a.getEnumValues().toArray(new String[0]));//, false);
					formFields.add(field);
				//}
			} else if (a.getType().equals(EntityAttribute.TYPE_INT)) {
				FormNumberField field = new FormNumberField(a, gridBagConstraints, formPanel, false);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_NON_NEGATIVE_INTEGER)) {
				FormNumberField field = new FormNumberField(a, gridBagConstraints, formPanel, true);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_SHORT_STRING)) {
				FormTextField field = new FormTextField(a, gridBagConstraints, formPanel);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_LONG_STRING)) {
				FormTextAreaField field = new FormTextAreaField(a, gridBagConstraints, formPanel);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_DATE)) {	// FIXME: duplicate date as subtype
				FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, false);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_DATE_TIME)) {
				FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, true);
				formFields.add(field);
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
		for (FormField f : formFields) {
			if (!f.attribute.groupName.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
				setAttributeValue(f);
				if (f.attribute.value != null) {
					WebServiceInstance.getDatabaseConnection().setEntityAttribute(
							id,
							f.attribute,
							false);
				}
			}
		}
	}
	
	protected void updateAttributes(int id) throws Exception {
		for (FormField f : formFields) {
			setAttributeValue(f);
			if (f.attribute.value != null) {
				WebServiceInstance.getDatabaseConnection().setEntityAttribute(
						id,
						f.attribute,
						true);
				
			}
		}
	}
	
	protected Object getAttributeValue(EntityKind entityKind, String attribute) {
		for (FormField f : formFields) {
			if (f.attribute.name.equals(attribute)) {
				setAttributeValue(f);
				
				return f.attribute.value;
			}
		}
		
		return null;
	}
	
	protected void fillFormFields(GenericDescription<?> record) {
		makeEditButton(record.getId());
		for (FormField f : formFields) {
			EntityAttribute attribute = record.get(f.attribute.name);
			if (attribute != null) {
				f.setData(attribute.value);
			} else {
				f.setData("");
			}
		}
	}
	
	private void makeEditButton(final int recordId) {
		createButton.setText(EDIT);
		ActionListener[] actionListeners = createButton.getActionListeners();
		for (int i = 0; i < actionListeners.length; i++) {
			createButton.removeActionListener(actionListeners[i]);
		}
		
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateResult() == true) {
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws InterruptedException {
							messageLabel.setText(UPDATING_MESSAGE);
							createButton.setEnabled(false);
							FormDialog.this.setResult(EDIT_PRESSED);
							try {
								updateAttributes(recordId);
							} catch (Exception e1) {
								ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
								exceptionDialog.setVisible(true);
							}
							
							return null;
						}
						
						@Override
						protected void done() {
							setVisible(false);
							dispose();
						}
					};
					worker.execute();
				}
			}
		});
	}
	
	protected boolean validateResult() {
		for (FormField f : formFields) {
			if (!f.attribute.groupName.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
				if (setAttributeValue(f) == false) {
					
					return false;
				}
			}
		}
		
		this.messageLabel.setText(PRESS_CREATE_MESSAGE);
		
		return true;
	}
}

abstract class FormField {
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
	
	public void setData(Object value) {
		text.setText(value.toString());
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
	
	public void setData(Object value) {
		textArea.setText(value.toString());
	}
}

class FormNumberField extends FormField {
	private boolean nonNegative = false;
	
	public FormNumberField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, boolean nonNegative) {
		super(attribute, gridBagConstraints, formPanel);
		this.nonNegative = nonNegative;
		finishField();
	}
	
	private void finishField() {
		text.setColumns(5);
	}
	
	public Integer getData() throws NumberFormatException {
		String value = text.getText();
		if (value.equals("")) {
			
			return null;
		} else {
			int number = Integer.parseInt(text.getText());
			if (nonNegative == false) {
				
				return number;
			} else {
				if (number >= 0) {
					return number;
				} else {
					throw(new NumberFormatException());
				}
			}
		}
	}
}

class FormDateField extends FormField {
	//private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dateFormat;
	private boolean fullDate = false;
	private JButton setDateTimeButton;
	
	public FormDateField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, boolean fullDate) {
		super(attribute, gridBagConstraints, formPanel);
		this.fullDate = fullDate;
		if (fullDate == true) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		}
		setCurrentDate();
		finishField();
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
		data.setMonth(calendar.get(Calendar.MONTH)+1);	// Adjust to proper month.
		data.setYear(calendar.get(Calendar.YEAR));
		
		if (fullDate == true) {
			data.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			data.setMinute(calendar.get(Calendar.MINUTE));
			data.setSecond(calendar.get(Calendar.SECOND));
		}
		
		return data;
	}
	
	protected void prepareField() {
		addLabel();
		text = new JTextField(20);
		label.setLabelFor(text);
		formPanel.add(text, gridBagConstraints);
		
		gridBagConstraints.gridx++;
	}
	
	protected void finishField() {
		setDateTimeButton = new JButton("Set");
		setDateTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarWidget calendarWidget = new CalendarWidget();
				calendarWidget.setVisible(true);
				if (calendarWidget.getDate() != null) {
					String date = dateFormat.format(calendarWidget.getDate()).toString();
					text.setText(date);
				}
			}
		});
		formPanel.add(setDateTimeButton, gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy++;
		
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
	}
	
	public void setData(Object value) {
		text.setText(value.toString().replace('T', ' '));
	}
}

class FormListField extends FormField {
	private JComboBox comboBox;
	private String[] list;
	//private boolean dataAsIndex = false;
	
	public FormListField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, String[] list) {//, boolean dataAsIndex) {
		super(attribute, gridBagConstraints, formPanel);
		this.list = list;
		//this.dataAsIndex = dataAsIndex;
		finishField();
	}
	
	public String getData() {
		//if (dataAsIndex == false) {
			
			return comboBox.getSelectedItem().toString();
		//} else {
			
			//return "" + (comboBox.getSelectedIndex()+1);	// Database values start with 1.
		//}
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
	
	public void setData(Object value) {
		try {
			int i = Integer.parseInt(value.toString());
			if (i > 0 && i <= comboBox.getItemCount()) {
				comboBox.setSelectedIndex(i-1);
			}
		} catch (NumberFormatException e) {
			for (int i = 0; i < comboBox.getItemCount(); i++) {
				if (comboBox.getItemAt(i).toString().equals(value.toString())) {
					comboBox.setSelectedIndex(i);
				}
			}
		}
	}
}