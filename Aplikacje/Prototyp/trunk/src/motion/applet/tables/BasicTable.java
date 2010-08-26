package motion.applet.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

import motion.applet.database.TableName;
import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.ExceptionDialog;
import motion.applet.panels.BottomSplitPanel;
import motion.applet.toolbars.AppletToolBar;
import motion.applet.webservice.client.WebServiceInstance;
import motion.database.DbElementsList;
import motion.database.model.AttributeName;
import motion.database.model.EntityAttribute;
import motion.database.model.GenericDescription;
import motion.database.model.GenericResult;

public class BasicTable extends AbstractTableModel {
	private ArrayList<ArrayList<Object>> contents = new ArrayList<ArrayList<Object>>();
	private ArrayList<String> attributeNames = new ArrayList<String>();
	private ArrayList<Class> classes = new ArrayList<Class>();
	private ArrayList<Integer> recordIds = new ArrayList<Integer>();
	private TableName tableName;
	public int recordId;
	public TableName fromTableName;	// Used for listing files for different tables.
	public String fromBasket;	// Used for baskets.
	private int CHECKBOX_COLUMN = 0;
	
	public BasicTable(TableName tableName) {
		this.tableName = tableName;
		this.recordId = -1;
		getTableContentsFromAttributes();
	}
	
	public BasicTable(TableName tableName, int recordId) {
		this.tableName = tableName;
		this.recordId = recordId;
		
		getTableContentsFromAttributes();
	}
	
	public BasicTable(TableName tableName, int recordId, TableName fromTableName) {
		this.tableName = tableName;
		this.recordId = recordId;
		this.fromTableName = fromTableName;
		
		getTableContentsFromAttributes();
	}
	
	public BasicTable(List<GenericResult> result) {
		getTableContentsFromResult(result);
	}
	
	public BasicTable(TableName tableName, DbElementsList<? extends GenericDescription<?>> records, String fromBasket) {
		this.tableName = tableName;
		this.fromBasket = fromBasket;
		getTableContentsFromRecords(records);
	}
	
	public BasicTable() {
		
	}
	
	public TableName getTableName() {
		
		return this.tableName;
	}
	
	private void getTableContentsFromAttributes() {

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
					DbElementsList<? extends GenericDescription<?>> records = new DbElementsList<GenericDescription<?>>();
					ArrayList<String> selectedAttributes = BottomSplitPanel.getCheckedAttributes(tableName);
					ArrayList<AttributeName> attributes = new ArrayList<AttributeName>();
					if (selectedAttributes != null) {
						attributes = tableName.getSelectedAttributes(selectedAttributes);
					} else {
						attributes = tableName.getAllAttributes();
					}
					for (AttributeName a : attributes) {
						attributeNames.add(a.toString());
						classes.add(a.getAttributeClass());
					}
					
					if (tableName.equals(TableNamesInstance.PERFORMER)) {
						records = WebServiceInstance.getDatabaseConnection().listLabPerformersWithAttributes(AppletToolBar.getLabId());
					} else if (tableName.equals(TableNamesInstance.SESSION)) {
						if (recordId > -1) {
							records = WebServiceInstance.getDatabaseConnection().listPerformerSessionsWithAttributes(recordId);
						} else {
							records = WebServiceInstance.getDatabaseConnection().listLabSessionsWithAttributes(AppletToolBar.getLabId());
						}
					} else if (tableName.equals(TableNamesInstance.TRIAL)) {
						if (recordId > -1) {
							records = WebServiceInstance.getDatabaseConnection().listSessionTrialsWithAttributes(recordId);
						}
					} else if (tableName.equals(TableNamesInstance.FILE)) {
						if (recordId > -1 && fromTableName != null) {
							if (fromTableName.equals(TableNamesInstance.PERFORMER)) {
								records = WebServiceInstance.getDatabaseConnection().listPerformerFiles(recordId);
							} else if (fromTableName.equals(TableNamesInstance.SESSION)) {
								records = WebServiceInstance.getDatabaseConnection().listSessionFiles(recordId);
							} else if (fromTableName.equals(TableNamesInstance.TRIAL)) {
								records = WebServiceInstance.getDatabaseConnection().listTrialFiles(recordId);
							}
						}
					}
					
					for (GenericDescription<?> r : records) {
						ArrayList<Object> cellList = new ArrayList<Object>();
						cellList.add(new Boolean(false));	// checkboxes initially unchecked
						for (AttributeName a : attributes) {
							EntityAttribute entityAttribute = r.get(a.toString());
							if (entityAttribute != null) {
								cellList.add(entityAttribute.value);
							} else {
								cellList.add(null);
							}
						}
						recordIds.add(r.getId());
						contents.add(cellList);
					}
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				BasicTable.this.fireTableStructureChanged();
				//table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxCellEditor());
				//table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  
			}
		};
		worker.execute();
	}
	
	private void addCheckboxColumn() {
		this.attributeNames.add("checkbox");	// checkbox column
		this.classes.add(Boolean.class);
	}
	
	private void getTableContentsFromResult(final List<GenericResult> result) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
					//TODO: Needs improvement.
					//BasicTable.this.tableName = TableNamesInstance.toTableName(result.get(0).entityKind.toString());
					//BasicTable.this.tableName = TableNamesInstance.SESSION;
					if (result.get(0).keySet().contains("FirstName")) {
						BasicTable.this.tableName = TableNamesInstance.PERFORMER;
					} else if (result.get(0).keySet().contains("SessionDate")) {
						BasicTable.this.tableName = TableNamesInstance.SESSION;
					} else if (result.get(0).keySet().contains("Duration")) {
						BasicTable.this.tableName = TableNamesInstance.TRIAL;
					}
					
					// Don't filter attributes.
					ArrayList<AttributeName> attributes = tableName.getAllAttributes();
					for (AttributeName a : attributes) {
						//TODO: sessionID / SessionID
						BasicTable.this.attributeNames.add(a.toString().toLowerCase());
						BasicTable.this.classes.add(a.getAttributeClass());
					}
					
					/*//Filter attributes from view configuration.
					ArrayList<AttributeName> attributes = null;
					if (tableName.equals(TableNamesInstance.PERFORMER)) {
						attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedPerformerAttributes());
					} else if (tableName.equals(TableNamesInstance.SESSION)) {
						attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedSessionAttributes());
					} else if (tableName.equals(TableNamesInstance.TRIAL)) {
						attributes = tableName.getSelectedAttributes(BottomSplitPanel.getCheckedTrialAttributes());
					}*/
					
					if (attributes != null) {
						/*//Filter attributes from view configuration.
						for (String key : result.get(0).keySet()) {
							for (AttributeName a : attributes) {
								//TODO: sessionID / SessionID
								if (a.toString().toLowerCase().equals(key.toLowerCase())) {
									BasicTable.this.attributeNames.add(key);
								}
							}
						}
						*/
						
						for (GenericResult r : result) {
							Object[] cellList = new Object[attributeNames.size()];
							
							for (String key : r.keySet()) {
								//TODO: sessionID / SessionID
								int i = attributeNames.indexOf(key.toLowerCase());
								if (i >= 0)
									cellList[i] = r.get(key).value;
							}
							//TODO: sessionID / SessionID
							if (tableName.equals(TableNamesInstance.PERFORMER)) {
								BasicTable.this.recordIds.add(Integer.parseInt(r.get("PerformerID").value.toString()));
							} else if (tableName.equals(TableNamesInstance.SESSION)) {
								BasicTable.this.recordIds.add(Integer.parseInt(r.get("SessionID").value.toString()));
							} else if (tableName.equals(TableNamesInstance.TRIAL)) {
								BasicTable.this.recordIds.add(Integer.parseInt(r.get("TrialID").value.toString()));
							}
							ArrayList<Object> list = new ArrayList<Object>();
							list.add(new Boolean(false));	// checkboxes initially unchecked
							for (int i = 1; i < cellList.length; i++) {
								list.add(cellList[i]);
							}
							BasicTable.this.contents.add(list);
						}
					}
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				BasicTable.this.fireTableStructureChanged();
			}
		};
		worker.execute();
	}
	
	private void getTableContentsFromRecords(final DbElementsList<? extends GenericDescription<?>> records) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
					// Don't filter attributes.
					ArrayList<AttributeName> attributes = tableName.getAllAttributes();
					for (AttributeName a : attributes) {
						//TODO: sessionID / SessionID
						BasicTable.this.attributeNames.add(a.toString().toLowerCase());
						BasicTable.this.classes.add(a.getAttributeClass());
					}
					
					if (attributes != null) {
						for (GenericDescription<?> r : records) {
							Object[] cellList = new Object[attributeNames.size()];
							
							for (String key : r.keySet()) {
								//TODO: sessionID / SessionID
								int i = attributeNames.indexOf(key.toLowerCase());
								if (i >= 0)
									cellList[i] = r.get(key).value;
							}
							BasicTable.this.recordIds.add(r.getId());
							ArrayList<Object> list = new ArrayList<Object>();
							list.add(new Boolean(false));	// checkboxes initially unchecked
							for (int i = 1; i < cellList.length; i++) {
								list.add(cellList[i]);
							}
							BasicTable.this.contents.add(list);
						}
					}
				} catch (Exception e1) {
					ExceptionDialog exceptionDialog = new ExceptionDialog(e1);
					exceptionDialog.setVisible(true);
				}
				
				return null;
			}
			
			@Override
			protected void done() {
				BasicTable.this.fireTableStructureChanged();
			}
		};
		worker.execute();
	}
	
	@Override
	public int getColumnCount() {
		if (this.contents.size() == 0) {
			return 0;
		} else {
			return this.contents.get(0).size();
		}
	}
	
	@Override
	public int getRowCount() {
		
		return this.contents.size();
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		
		return this.contents.get(row).get(column);
	}
	
	public Class getColumnClass(int column) {
		
		return this.classes.get(column);
	}
	
	public String getColumnName(int column) {
		
		return this.attributeNames.get(column);
	}
	
	public int getRecordId(int row) {
		
		return this.recordIds.get(row);
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return (columnIndex == CHECKBOX_COLUMN);	// enable columns for editing/checkboxes  
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == CHECKBOX_COLUMN) {
			this.contents.get(rowIndex).set(columnIndex, value);
		}
		//super.fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	public int[] getCheckedRecordIds() {
		int[] checkedRecordIds = new int[this.getRowCount()];
		int i = 0;
		int j = 0;
		for (ArrayList<Object> row : this.contents) {
			if ((Boolean) row.get(CHECKBOX_COLUMN) == Boolean.TRUE) {
				checkedRecordIds[j] = getRecordId(i);
				j++;
			}
			i++;
		}
		
		return Arrays.copyOf(checkedRecordIds, j);
	}
	
	public void removeCheckedRecords() {
		Iterator<ArrayList<Object>> i = this.contents.iterator();
		Iterator<Integer> j = this.recordIds.iterator();
		while (i.hasNext()) {
			ArrayList<Object> row = i.next();
			j.next();
			if ((Boolean) row.get(CHECKBOX_COLUMN) == Boolean.TRUE) {
				i.remove();
				j.remove();
			}
		}
		this.fireTableDataChanged();
	}
}
