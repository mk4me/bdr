package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import motion.applet.widgets.TagWidget;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.MeasurementStaticAttributes;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.SessionStaticAttributes;

public class FormDialog extends BasicDialog {
	private static String CREATE = Messages.getString("Create"); //$NON-NLS-1$
	private static String CANCEL = Messages.getString("Cancel"); //$NON-NLS-1$
	private static String EDIT = Messages.getString("OK"); //$NON-NLS-1$
	
	protected static String PRESS_CREATE_MESSAGE = Messages.getString("FormDialog.PressCreateMessage"); //$NON-NLS-1$
	protected static String UPDATING_MESSAGE = Messages.getString("FormDialog.UpdatingMessage"); //$NON-NLS-1$
	
	private static String ATTRIBUTES_TAB_NAME = Messages.getString("FormDialog.AttributesTabName"); //$NON-NLS-1$
	private static String INCORRECT_NUMBER = Messages.getString("FormDialog.IncorrectNumber"); //$NON-NLS-1$
	private static String INCORRECT_DATE = Messages.getString("FormDialog.IncorrectDate"); //$NON-NLS-1$
	private static String SET_BUTTON = Messages.getString("FormDialog.SetButton"); //$NON-NLS-1$
	private static String CLEAR_BUTTON = Messages.getString("FormDialog.ClearButton"); //$NON-NLS-1$
	private static String EDIT_BUTTON = Messages.getString("Edit"); //$NON-NLS-1$
	
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
	
	protected int recordId = -1;
	
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
		if (!groupName.equals(EntityKind.STATIC_ATTRIBUTE_GROUP) &&
				!attributes.isEmpty()) {
			addFormTextLabel(groupName + ":");
		}
		
		for (EntityAttribute a : attributes) {
			if (a.getEnumValues() != null) {
				//if (attribute.getType().equals(EntityAttribute.TYPE_INT)) {
					//FormListField field = new FormListField(attribute, gridBagConstraints, formPanel, attribute.getEnumValues().toArray(new String[0]), true);
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
				if (a.name.equals(SessionStaticAttributes.Tags.toString())) {
					FormTagField field = new FormTagField(a, gridBagConstraints, formPanel);
					formFields.add(field);
				} else {
					FormTextField field = new FormTextField(a, gridBagConstraints, formPanel);
					formFields.add(field);
				}
			} else if (a.getType().equals(EntityAttribute.TYPE_LONG_STRING)) {
				FormTextAreaField field = new FormTextAreaField(a, gridBagConstraints, formPanel);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_DATE)) {
				FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, false);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_DATE_TIME)) {
				FormDateField field = new FormDateField(a, gridBagConstraints, formPanel, true);
				formFields.add(field);
			} else if (a.getType().equals(EntityAttribute.TYPE_ID)) {
				if (a.name.equals(PerformerStaticAttributes.PerformerID.toString())) {
					FormNumberField field = new FormNumberField(a, gridBagConstraints, formPanel, true);
					formFields.add(field);
				} else {
					FormListField field = new FormListField(a, gridBagConstraints, formPanel);
					formFields.add(field);
				}
			}
		}
	}
	
	protected boolean setAttributeValue(FormField f) {
		Object attributeValue = null;
		
		// Non static attributes are optional
		if (!f.attribute.groupName.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
			if (f.isDataEmpty() == true) {
				f.attribute.value = null;
				
				return true;
			}
		}
		
		if (f instanceof FormTextField) {
			attributeValue = ((FormTextField) f).getData();
		} else if (f instanceof FormTextAreaField) {
			attributeValue = ((FormTextAreaField) f).getData();
		} else if (f instanceof FormNumberField) {
			try {
				attributeValue = ((FormNumberField) f).getData();
			} catch (NumberFormatException e) {
				this.messageLabel.setText(INCORRECT_NUMBER + " " + f.attribute.name + ".");
				
				return false;
			}
		} else if (f instanceof FormDateField) {
			try {
				attributeValue = ((FormDateField) f).getData();
			} catch (ParseException e) {
				this.messageLabel.setText(INCORRECT_DATE + " " + f.attribute.name + ".");
				
				return false;
			} catch (DatatypeConfigurationException e) {
				this.messageLabel.setText(INCORRECT_DATE + " " + f.attribute.name + ".");
				
				return false;
			}
		} else if (f instanceof FormListField) {
			attributeValue = ((FormListField) f).getData();
		} else if (f instanceof FormTagField) {
			attributeValue = ((FormTagField) f).getData();
		}
		
		if (attributeValue != null) {
			f.attribute.setValueFromString(attributeValue);
		} else {
			f.attribute.value = null;
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
				
			} else {
				if (recordId > -1) {
					if (!f.attribute.groupName.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
						// Clear only those attributes which exist, extra flag in FormField if attribute was not empty before editing.
						if (f.hasValue == true) {
							WebServiceInstance.getDatabaseConnection().clearEntityAttribute(recordId, f.attribute);
						}
					}
				}
			}
		}
	}
	
	protected Object getAttributeValue(EntityKind entityKind, String attribute) {
		for (FormField f : formFields) {
			if (f.attribute.name.equals(attribute)) {
				if (setAttributeValue(f) == true) {
					return f.attribute.value;
				} else {
					return null;
				}
			}
		}
		
		return null;
	}
	
	protected void fillFormFields(GenericDescription<?> record) {
		recordId = record.getId();
		makeEditButton();
		for (FormField f : formFields) {
			EntityAttribute attribute = record.get(f.attribute.name);
			if (attribute != null) {
				f.setData(attribute.value);
				f.hasValue = true;
			} else {
				f.setData("");
			}
		}
	}
	
	protected void makeEditButton() {
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

	private abstract class FormField {
		protected JLabel label;
		protected JTextField text;
		protected JButton clearButton;
		protected GridBagConstraints gridBagConstraints;
		protected JPanel formPanel;
		public EntityAttribute attribute;
		public boolean hasValue = false;
		
		public FormField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
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
			addClearButton();
			
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
		
		protected void addClearButton() {
			if (!attribute.groupName.equals(EntityKind.STATIC_ATTRIBUTE_GROUP)) {
				gridBagConstraints.gridx++;
				
				clearButton = new JButton(CLEAR_BUTTON);
				formPanel.add(clearButton, gridBagConstraints);
				clearButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws InterruptedException {
								clearButton.setEnabled(false);
								try {
									if (recordId > -1) {
										cancelButton.setEnabled(false);
										WebServiceInstance.getDatabaseConnection().clearEntityAttribute(recordId, attribute);
									}
									setData("");
								} catch (Exception e1) {
									ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
									exceptionDialog.setVisible(true);
								}
								
								return null;
							}
							
							@Override
							protected void done() {
								clearButton.setEnabled(true);
							}
						};
						worker.execute();
					}
				});
			}
		}
		
		public void setData(Object value) {
			text.setText(value.toString());
		}
		
		public boolean isDataEmpty() {
			if (text.getText().equals("")) {
				
				return true;
			} else {
				
				return false;
			}
		}
	}
	
	private class FormTextField extends FormField {
		
		public FormTextField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
			super(attribute, gridBagConstraints, formPanel);
		}
		
		public String getData() {
			
			return text.getText();
		}
	}
	
	private class FormTextAreaField extends FormField {
		private JTextArea textArea;
		
		public FormTextAreaField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
			super(attribute, gridBagConstraints, formPanel);
			
		}
		
		public String getData() {
			
			return textArea.getText();
		}
		
		public boolean isDataEmpty() {
			if (textArea.getText().equals("")) {
				
				return true;
			} else {
				
				return false;
			}
		}
		
		protected void prepareField() {
			addLabel();
			
			JScrollPane textAreaScroll = new JScrollPane(textArea = new JTextArea(5, 20));
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			label.setLabelFor(textArea);
			formPanel.add(textAreaScroll, gridBagConstraints);
			
			addClearButton();
			
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy++;
			gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		}
		
		public void setData(Object value) {
			textArea.setText(value.toString());
		}
	}
	
	private class FormNumberField extends FormField {
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
	
	private class FormDateField extends FormField {
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
			// Do not put initial current date in the field.
			// setCurrentDate();
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
		
		private Date getDate() {
			try {
				
				return dateFormat.parse(text.getText());
			} catch (ParseException e) {
				
				return null;
			}
		}
		
		protected void prepareField() {
			addLabel();
			text = new JTextField(20);
			label.setLabelFor(text);;
			formPanel.add(text, gridBagConstraints);
			
			gridBagConstraints.gridx++;
		}
		
		protected void finishField() {
			String dateFormatString = ((SimpleDateFormat) dateFormat).toPattern();
			label.setText("<html>" + label.getText() + "<br/>" + "(" + dateFormatString + ")" + "</html>");
			text.setToolTipText(dateFormatString);
			setDateTimeButton = new JButton(SET_BUTTON);
			setDateTimeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CalendarWidget calendarWidget = new CalendarWidget();
					Date date = getDate();
					if (date != null) {
						calendarWidget.setDate(date);
					}
					calendarWidget.setVisible(true);
					if (calendarWidget.getDate() != null) {
						String dateString = dateFormat.format(calendarWidget.getDate()).toString();
						text.setText(dateString);
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
	
	private class FormListField extends FormField {
		private JComboBox comboBox;
		private Object[] list;
		private int id = -1;
		
		public FormListField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel, String[] list) {
			super(attribute, gridBagConstraints, formPanel);
			this.list = list;
			finishField();
		}
		
		public FormListField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
			super(attribute, gridBagConstraints, formPanel);
			this.list = new Object[0];
			finishField();
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws InterruptedException {
					try {
						getComboBoxContents();
					} catch (Exception e1) {
						ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
						exceptionDialog.setVisible(true);
					}
					
					return null;
				}
				
				@Override
				protected void done() {
					for (int i = 0; i < list.length; i++) {
						comboBox.addItem(list[i]);
						if (((GenericDescription<?>) list[i]).getId() == id) {
							comboBox.setSelectedIndex(i);
						}
					}
				}
			};
			worker.execute();
			
		}
		
		private void getComboBoxContents() throws Exception {
			if (attribute.name.equals(MeasurementStaticAttributes.MeasurementConfID.toString())) {
				list = WebServiceInstance.getDatabaseConnection().listMeasurementConfigurationsWithAttributes().toArray(new Object[0]);
			}
		}
		
		public Object getData() {
			
			return comboBox.getSelectedItem();
		}
		
		public boolean isDataEmpty() {
			
			return false;
		}
		
		protected void prepareField() {
			addLabel();
		}
		
		protected void finishField() {
			comboBox = new JComboBox(list);
			comboBox.setSize(new Dimension(200, comboBox.getPreferredSize().height));
			comboBox.setPreferredSize(new Dimension(200, comboBox.getPreferredSize().height));
			label.setLabelFor(comboBox);
			formPanel.add(comboBox, gridBagConstraints);
			
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy++;
			
			gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		}
		
		public void setData(Object value) {
			try {
				int i = Integer.parseInt(value.toString());
				if ((attribute.name.equals(MeasurementStaticAttributes.MeasurementConfID.toString()))) {
					id = i;
				} else if (i > 0 && i <= comboBox.getItemCount()) {
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
	
	private class FormTagField extends FormField {
		private JButton editButton;
		
		public FormTagField(EntityAttribute attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
			super(attribute, gridBagConstraints, formPanel);
			
			//finishField();
		}
		
		public String getData() {
			
			return text.getText();
		}
		
		protected void prepareField() {
			addLabel();
			text = new JTextField(20);
			label.setLabelFor(text);;
			formPanel.add(text, gridBagConstraints);
			
			gridBagConstraints.gridx++;
		}
		
		protected void finishField() {
			String tagFormat = "tag1(option), tag2(option),...";
			label.setText("<html>" + label.getText() + "<br/>" + "(" + "tag(option)" + ")" + "</html>");
			text.setToolTipText("tag1(option), tag2(option),...");
			editButton = new JButton(EDIT_BUTTON);
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TagWidget tagWidget = new TagWidget();
					tagWidget.setVisible(true);
					
					text.setText(tagWidget.getTags());
				}
			});
			formPanel.add(editButton, gridBagConstraints);
			
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy++;
			
			gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		}
	}
}