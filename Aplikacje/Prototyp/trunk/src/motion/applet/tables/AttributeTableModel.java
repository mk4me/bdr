package motion.applet.tables;

import java.util.ArrayList;

import javax.swing.SwingWorker;

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

public class AttributeTableModel extends BasicTableModel {
	public TableName fromTableName;	// Used for listing files for different tables.
	
	public AttributeTableModel(TableName tableName) {
		this.tableName = tableName;
		this.recordId = -1;
		getTableContents();
	}
	
	public AttributeTableModel(TableName tableName, int recordId) {
		this.tableName = tableName;
		this.recordId = recordId;
		
		getTableContents();
	}
	
	public AttributeTableModel(TableName tableName, int recordId, TableName fromTableName) {
		this.tableName = tableName;
		this.recordId = recordId;
		this.fromTableName = fromTableName;
		
		getTableContents();
	}
	
	private void getTableContents() {

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
				AttributeTableModel.this.fireTableStructureChanged();
				//table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxCellEditor());
				//table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  
			}
		};
		worker.execute();
	}
	
	public void refresh() {
		super.refresh();
		getTableContents();
		fireTableStructureChanged();
	}
}
