package motion.applet.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import motion.applet.database.TableNamesInstance;
import motion.applet.dialogs.ExceptionDialog;
import motion.database.model.AttributeName;
import motion.database.model.GenericResult;

public class QueryTableModel extends BasicTableModel {
	private List<GenericResult> result;
	
	public QueryTableModel(List<GenericResult> result) {
		this.result = result;
		getTableContents();
	}
	
	private void getTableContents() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
					//TODO: Needs improvement.
					//BasicTable.this.tableName = TableNamesInstance.toTableName(result.get(0).entityKind.toString());
					//BasicTable.this.tableName = TableNamesInstance.SESSION;
					if (result.get(0).keySet().contains("FirstName")) {
						tableName = TableNamesInstance.PERFORMER;
					} else if (result.get(0).keySet().contains("SessionDate")) {
						tableName = TableNamesInstance.SESSION;
					} else if (result.get(0).keySet().contains("Duration")) {
						tableName = TableNamesInstance.TRIAL;
					}
					
					// Don't filter attributes.
					ArrayList<AttributeName> attributes = tableName.getAllAttributes();
					for (AttributeName a : attributes) {
						//TODO: sessionID / SessionID
						attributeNames.add(a.toString().toLowerCase());
						classes.add(a.getAttributeClass());
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
								recordIds.add(Integer.parseInt(r.get("PerformerID").value.toString()));
							} else if (tableName.equals(TableNamesInstance.SESSION)) {
								recordIds.add(Integer.parseInt(r.get("SessionID").value.toString()));
							} else if (tableName.equals(TableNamesInstance.TRIAL)) {
								recordIds.add(Integer.parseInt(r.get("TrialID").value.toString()));
							}
							ArrayList<Object> list = new ArrayList<Object>();
							list.add(new Boolean(false));	// checkboxes initially unchecked
							for (int i = 1; i < cellList.length; i++) {
								list.add(cellList[i]);
							}
							contents.add(list);
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
				fireTableStructureChanged();
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
