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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.Messages;
import motion.database.model.AttributeName;

public class FormDialog extends BasicDialog {
	private static String CREATE = Messages.CREATE;
	private static String CANCEL = Messages.CANCEL;
	
	private JButton createButton;
	private JButton cancelButton;
	
	protected JPanel formPanel;
	protected GridBagConstraints gridBagConstraints;
	
	public FormDialog(String title, String message) {
		super(title, message);
	}
	
	@Override
	protected void constructUserInterface() {
		// Form panel
		formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		this.add(formPanel, BorderLayout.CENTER);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gridBagConstraints.ipadx = 10;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		
		// Button area
		createButton = new JButton(CREATE);
		this.addToButtonPanel(createButton);
		
		cancelButton = new JButton(CANCEL);
		this.addToButtonPanel(cancelButton);
	}
	
	@Override
	protected void finishUserInterface() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void addListeners() {
		this.createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormDialog.this.setVisible(false);
				FormDialog.this.dispose();
			}
		});
	}
}

class FormField {
	protected JLabel label;
	protected JTextField text;
	protected GridBagConstraints gridBagConstraints;
	protected JPanel formPanel;
	protected AttributeName attribute;
	
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
		
		text = new JTextField(10);
		label.setLabelFor(text);
		formPanel.add(text, gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy++;
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

class FormNumberField extends FormField {
	
	public FormNumberField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		
	}
	
	public Integer getData() throws NumberFormatException {
		
		return Integer.parseInt(text.getText());
	}
}

class FormDateField extends FormField {
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public FormDateField(AttributeName attribute, GridBagConstraints gridBagConstraints, JPanel formPanel) {
		super(attribute, gridBagConstraints, formPanel);
		
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
		data.setHour(calendar.get(Calendar.HOUR_OF_DAY));
		data.setMinute(calendar.get(Calendar.MINUTE));
		data.setSecond(calendar.get(Calendar.SECOND));
		
		return data;
	}
}