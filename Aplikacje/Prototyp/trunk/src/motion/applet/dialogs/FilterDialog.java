package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.datatype.XMLGregorianCalendar;

import motion.applet.database.Connector;
import motion.applet.database.ConnectorInstance;
import motion.database.SessionStaticAttributes;

public class FilterDialog extends JDialog {
	private static String FILTER_TITLE = "Add filter";
	private static String FILTER_NAME = "Name:";
	private static String TABLE_LABEL = "Table:";
	private static String ADD_FILTER = "Add";
	private static String CANCEL_FILTER = "Cancel";
	private static String EDIT_FILTER = "Edit";
	private static String ADD_CONDITION = "+";
	private JTextField nameText;
	private JButton addButton;
	private JButton cancelButton;
	private JButton addConditionButton;
	public static int ADD_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	private JLabel messageLabel;
	private static String WELCOME_MESSAGE = "Add new filter.";
	private static String MISSING_NAME_MESSAGE = "Please type the name of the filter.";
	
	private JPanel conditionPanel;
	private ArrayList<ColumnCondition> columnConditions = new ArrayList<ColumnCondition>();
	
	private String tableName = "Sesja";
	
	public FilterDialog(String tableName) {
		super((JFrame) null, FILTER_TITLE, true);
		this.setSize(400, 400);
		this.setLocation(200, 200);
		
		this.tableName = tableName;
		
		constructUserInterface();
		addListeners();
	}
	
	public FilterDialog(String name, String tableName) {
		this(tableName);
		this.nameText.setText(name);
		this.addButton.setText(EDIT_FILTER);
	}
	
	private void constructUserInterface() {
		// Message area
		JPanel messagePanel = new JPanel();
		this.messageLabel = new JLabel(WELCOME_MESSAGE);
		messagePanel.add(this.messageLabel);
		this.add(messagePanel, BorderLayout.PAGE_START);
		
		JPanel centerPanel = new JPanel();
		// Form area
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gridBagConstraints.ipadx = 10;
		
		JLabel nameLabel = new JLabel(FILTER_NAME);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		formPanel.add(nameLabel, gridBagConstraints);
		
		this.nameText = new JTextField(10);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		nameLabel.setLabelFor(nameText);
		formPanel.add(nameText, gridBagConstraints);
		
		JLabel tableLabel = new JLabel(TABLE_LABEL);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		formPanel.add(tableLabel, gridBagConstraints);
		
		JLabel tableNameLabel = new JLabel(this.tableName);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		formPanel.add(tableNameLabel, gridBagConstraints);
		
		centerPanel.add(formPanel, BorderLayout.CENTER);
		
		// Column condition area
		conditionPanel = new JPanel();
		conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
		
		this.addColumnCondition(true);
		
		centerPanel.add(conditionPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		
		// Button area
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = new FlowLayout();
		buttonPanelLayout.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setLayout(buttonPanelLayout);
		
		addConditionButton = new JButton(ADD_CONDITION);
		buttonPanel.add(addConditionButton);
		
		addButton = new JButton(ADD_FILTER);
		buttonPanel.add(addButton);
		
		cancelButton = new JButton(CANCEL_FILTER);
		buttonPanel.add(cancelButton);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
	}
	
	private void addColumnCondition(boolean firstCondition) {
		try {
			ColumnCondition columnCondition = new ColumnCondition(ConnectorInstance.getConnector(), tableName, firstCondition);
			conditionPanel.add(columnCondition);
			columnConditions.add(columnCondition);
			conditionPanel.revalidate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeColumnCondition(ColumnCondition columnCondition) {
		this.columnConditions.remove(columnCondition);
		this.conditionPanel.remove(columnCondition);
		this.conditionPanel.revalidate();
	}
	
	private void addListeners() {
		this.addConditionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterDialog.this.addColumnCondition(false);
			}
		});
		
		this.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FilterDialog.this.validateResult() == true) {
					FilterDialog.this.result = ADD_PRESSED;
					FilterDialog.this.setVisible(false);
					FilterDialog.this.dispose();
				}
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterDialog.this.result = CANCEL_PRESSED;
				FilterDialog.this.setVisible(false);
				FilterDialog.this.dispose();
			}
		});
	}
	
	public String getName() {
		
		return this.nameText.getText();
	}
	
	private boolean validateResult() {
		if (this.getName().equals("")) {
			this.messageLabel.setText(MISSING_NAME_MESSAGE);
			
			return false;
		}
		
		return true;
	}
	
	private int setResult() {
		
		return this.result;
	}
	
	public int getResult() {
		
		return this.result;
	}
	
	private class ColumnCondition extends JPanel {
		private JComboBox columnComboBox;
		private JComboBox operatorComboBox;
		private JTextField conditionText = new JTextField(10);
		private java.sql.Connection connection;
		private String databaseName;
		private String tableName;
		private ArrayList<String> columnNameList;
		private ArrayList<Object> columnClassList;
		
		private String[] integerOperators = {"=", "<>", ">", "<"};
		private String[] stringOperators = {"=", "<>"};
		private String REMOVE_CONDITION = "X";
		private boolean firstCondition;
		
		private JButton removeButton;
		
		public ColumnCondition(Connector connector, String tableName, boolean firstCondition) throws SQLException {
			super();
			this.tableName = tableName;
			this.databaseName = connector.getDatabaseName();
			this.firstCondition = firstCondition;
			
			// Change to fillColumnsFromAttributes
			if (this.tableName.equals("Performer")) {
				this.connection = connector.openConnection();
				fillColumns();
				connector.closeConnection();
			} else if (this.tableName.equals("Sesja")) {
				fillColumnsFromAttributes();
			}
			
			this.operatorComboBox = new JComboBox();
			fillOperators(1);
			
			if (firstCondition == false) {
				String[] andOr = {"AND", "OR"};
				this.add(new JComboBox(andOr));
			}
			
			this.add(columnComboBox);
			this.add(operatorComboBox);
			this.add(conditionText);
			
			if (firstCondition == false) {
				this.removeButton = new JButton(REMOVE_CONDITION);
				this.add(removeButton);
				this.removeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						FilterDialog.this.removeColumnCondition(ColumnCondition.this);
					}
				});
			}
			
			this.columnComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ColumnCondition.this.fillOperators(ColumnCondition.this.columnComboBox.getSelectedIndex());
				}
			});
		}
		
		private void fillColumns() throws SQLException {
			DatabaseMetaData databaseMetaData = this.connection.getMetaData();
			ResultSet resultSet = databaseMetaData.getColumns(this.databaseName, null, this.tableName, null);
			this.columnNameList = new ArrayList<String>();
			this.columnClassList = new ArrayList<Object>();
			
			while (resultSet.next()) {
				columnNameList.add(resultSet.getString("COLUMN_NAME"));
				int dataType = resultSet.getInt("DATA_TYPE");
				
				switch (dataType) {
				case Types.INTEGER:
					columnClassList.add(Integer.class);
					break;
				case Types.FLOAT:
					columnClassList.add(Float.class);
					break;
				case Types.DOUBLE:
				case Types.REAL:
					columnClassList.add(String.class);
					break;
				case Types.DATE:
				case Types.TIME:
				case Types.TIMESTAMP:
					columnClassList.add(Date.class);
					break;
				default:
					columnClassList.add(String.class);
					break;
				}
			}
			
			this.columnComboBox = new JComboBox(columnNameList.toArray());
			
			resultSet.close();
		}
		
		private void fillColumnsFromAttributes() {
			this.columnNameList = new ArrayList<String>();
			this.columnClassList = new ArrayList<Object>();
			
			if (this.tableName.equals("Sesja")) { // change to some superclass and use instanceOf?
				columnNameList.add(SessionStaticAttributes.sessionID.toString());
				columnClassList.add(BigInteger.class);
				columnNameList.add(SessionStaticAttributes.userID.toString());
				columnClassList.add(BigInteger.class);
				columnNameList.add(SessionStaticAttributes.labID.toString());
				columnClassList.add(BigInteger.class);
				columnNameList.add(SessionStaticAttributes.motionKindID.toString());
				columnClassList.add(BigInteger.class);
				columnNameList.add(SessionStaticAttributes.performerID.toString());
				columnClassList.add(BigInteger.class);
				columnNameList.add(SessionStaticAttributes.sessionDate.toString());
				columnClassList.add(XMLGregorianCalendar.class);
				columnNameList.add(SessionStaticAttributes.sessionDescription.toString());
				columnClassList.add(String.class);
			}
			
			this.columnComboBox = new JComboBox(columnNameList.toArray());
		}
		
		private void fillOperators(int columnNumber) {
			Object columnClass = this.columnClassList.get(columnNumber);
			String[] operators = {""};
			if (columnClass == String.class) {
				operators = this.stringOperators;
			} else if (columnClass == Integer.class || columnClass == BigInteger.class) {
				operators = this.integerOperators;
			}
			this.operatorComboBox.removeAllItems();
			for (int i = 0; i < operators.length; i++) {
				this.operatorComboBox.addItem(operators[i]);
			}
			//this.operatorComboBox = new JComboBox(operators);
		}
	}
}
