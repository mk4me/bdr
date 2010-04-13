package motion.applet.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import motion.applet.database.AttributeName;
import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.filter.Filter;
import motion.applet.filter.model.PredicateComposition;
import motion.applet.filter.model.SimplePredicate;

public class FilterDialog extends BasicDialog {
	private static String FILTER_TITLE = "Add filter";
	private static String FILTER_NAME = "Name:";
	private static String TABLE_LABEL = "Table:";
	private static String QUERY_LABEL = "Query:";
	private static String ADD_FILTER = "Add";
	private static String CANCEL_FILTER = "Cancel";
	private static String EDIT_FILTER = "Edit";
	private static String ADD_CONDITION = "+";
	private static String WELCOME_MESSAGE = "Add new filter.";
	private static String MISSING_NAME_MESSAGE = "Please type the name of the filter.";
	
	private JTextField nameText;
	private JButton addButton;
	private JButton cancelButton;
	private JButton addConditionButton;
	public static int ADD_PRESSED = 1;
	public static int CANCEL_PRESSED = 0;
	private int result = CANCEL_PRESSED;
	
	private JLabel tableNameLabel;
	
	private JPanel conditionPanel;
	
	private SimplePredicate predicate;
	
	// Predicates
	private ArrayList<ColumnCondition> columnConditions = new ArrayList<ColumnCondition>();
	
	// ContextEntity
	private TableName tableName;
	
	public FilterDialog(TableName tableName) {
		super(FILTER_TITLE, WELCOME_MESSAGE);
		this.tableName = tableName;
		this.finishUserInterface();
	}
	
	public FilterDialog(Filter filter) {
		this(TableNamesInstance.toTableName(filter.getPredicate().getContextEntity()));
		this.nameText.setText(filter.getName());
		this.predicate = filter.getPredicate();
		this.addButton.setText(EDIT_FILTER);
		this.setPredicate(this.predicate);
	}
	
	protected void constructUserInterface() {
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
		
		tableNameLabel = new JLabel();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		formPanel.add(tableNameLabel, gridBagConstraints);
		
		JLabel queryLabel = new JLabel(QUERY_LABEL);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		formPanel.add(queryLabel, gridBagConstraints);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		JPanel centerPanel2 = new JPanel();
		centerPanel2.setLayout(new FlowLayout());
		
		JPanel formPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		formPanel2.add(formPanel);
		centerPanel.add(formPanel2);
		
		// Column condition area
		conditionPanel = new JPanel();
		conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
		conditionPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
		
		centerPanel.add(conditionPanel);
		centerPanel2.add(centerPanel);
		this.add(centerPanel2, BorderLayout.WEST);
		
		// Button area
		addConditionButton = new JButton(ADD_CONDITION);
		this.addToButtonPanel(addConditionButton);
		
		addButton = new JButton(ADD_FILTER);
		this.addToButtonPanel(addButton);
		
		cancelButton = new JButton(CANCEL_FILTER);
		this.addToButtonPanel(cancelButton);
	}
	
	protected void finishUserInterface() {
		this.setSize(440, 400);
		this.tableNameLabel.setText(this.tableName.toString());
		this.addColumnCondition(true);
	}
	
	private void setPredicate(SimplePredicate predicate) {
		columnConditions.get(0).setContentsFromPredicate(predicate);
		if (predicate.getNextComposition() != null) {
			do {
				predicate = (SimplePredicate) predicate.getNextComposition().getPredicate();
				addColumnCondition(false).setContentsFromPredicate(predicate);
			} while (predicate.getNextComposition() != null);
		}
	}
	
	private ColumnCondition addColumnCondition(boolean firstCondition) {
		try {
			ColumnCondition columnCondition = new ColumnCondition(tableName, firstCondition);
			//columnCondition.setAlignmentX(Component.LEFT_ALIGNMENT);
			conditionPanel.add(columnCondition);
			columnConditions.add(columnCondition);
			conditionPanel.revalidate();
			
			return columnCondition;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void removeColumnCondition(ColumnCondition columnCondition) {
		this.columnConditions.remove(columnCondition);
		this.conditionPanel.remove(columnCondition);
		this.conditionPanel.revalidate();
	}
	
	protected void addListeners() {
		this.addConditionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterDialog.this.addColumnCondition(false);
			}
		});
		
		this.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FilterDialog.this.validateResult() == true) {
					FilterDialog.this.setResult(ADD_PRESSED);
					FilterDialog.this.setVisible(false);
					FilterDialog.this.dispose();
				}
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterDialog.this.setResult(CANCEL_PRESSED);
				FilterDialog.this.setVisible(false);
				FilterDialog.this.dispose();
			}
		});
	}
	
	// Get form contents.
	public String getName() {
		
		return this.nameText.getText();
	}
	
	public SimplePredicate getPredicate() {
		ColumnCondition firstColumnCondition = columnConditions.get(0);
		SimplePredicate firstPredicate = new SimplePredicate(
				tableName.getTableName(),
				firstColumnCondition.getFeature(),
				firstColumnCondition.getOperator(),
				firstColumnCondition.getValue());
		
		SimplePredicate previousPredicate = firstPredicate;
		boolean first = true;
		for (ColumnCondition cc : columnConditions) {
			if (first) {
				first = false;
			} else {
				SimplePredicate currentPredicate = new SimplePredicate(
						tableName.getTableName(),
						cc.getFeature(),
						cc.getOperator(),
						cc.getValue(),
						cc.getLogicalOperator(),
						previousPredicate);
				previousPredicate = currentPredicate;
			}
		}
		
		return firstPredicate;
	}
	
	// Check from contents.
	private boolean validateResult() {
		if (this.getName().equals("")) {
			this.messageLabel.setText(MISSING_NAME_MESSAGE);
			
			return false;
		}
		
		return true;
	}
	
	// Button press result.
	private void setResult(int result) {
		
		this.result = result;
	}
	
	public int getResult() {
		
		return this.result;
	}
	
	// Filter ColumnCondition, Predicate UI
	private class ColumnCondition extends JPanel {
		private JComboBox columnComboBox;
		private JComboBox operatorComboBox;
		private JComboBox logicalComboBox;
		private JTextField conditionText = new JTextField(10);
		private TableName tableName;
		
		
		
		private String REMOVE_CONDITION = "X";
		private boolean firstCondition;
		
		private JButton removeButton;
		
		public ColumnCondition(TableName tableName, boolean firstCondition) throws SQLException {
			super();
			this.tableName = tableName;
			this.firstCondition = firstCondition;
			
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			this.operatorComboBox = new JComboBox();
			fillOperators(this.tableName.getAllAttributes().get(0));
			
			if (this.firstCondition == false) {
				logicalComboBox = new JComboBox(PredicateComposition.logicalOperators);
				this.add(logicalComboBox);
			}
			
			columnComboBox = new JComboBox(this.tableName.getAllAttributes().toArray());
			this.add(columnComboBox);
			this.add(operatorComboBox);
			this.add(conditionText);
			
			if (this.firstCondition == false) {
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
					ColumnCondition.this.fillOperators((AttributeName) ColumnCondition.this.columnComboBox.getSelectedItem());
				}
			});
		}
		
		private void fillOperators(AttributeName attributeName) {
			String[] operators = attributeName.getOperators();
			
			this.operatorComboBox.removeAllItems();
			for (int i = 0; i < operators.length; i++) {
				this.operatorComboBox.addItem(operators[i]);
			}
		}
		
		// Get ColumnCondition contents.
		private AttributeName getFeature() {
			
			return (AttributeName) columnComboBox.getSelectedItem();
		}
		
		private String getOperator() {
			
			return (String) operatorComboBox.getSelectedItem();
		}
		
		private String getValue() {
			
			return conditionText.getText();
		}
		
		private String getLogicalOperator() {
			
			return (String) logicalComboBox.getSelectedItem();
		}
		
		// Set ColumnCondition contents.
		private void setFeature(AttributeName feature) {
			columnComboBox.setSelectedItem(feature);
		}
		
		private void setOperator(String operator) {
			operatorComboBox.setSelectedItem(operator);
		}
		
		private void setValue(String value) {
			conditionText.setText(value);
		}
		
		private void setLogicalOperator(String logicalOperator) {
			logicalComboBox.setSelectedItem(logicalOperator);
		}
		
		protected void setContentsFromPredicate(SimplePredicate predicate) {
			setFeature(predicate.getFeature());
			setOperator(predicate.getOperator());
			setValue(predicate.getValue());
			PredicateComposition predicateComposition = predicate.getPreviousComposition();
			if (predicateComposition != null) {
				setLogicalOperator(predicateComposition.getLogicalOperator());
			}
		}
	}
}
