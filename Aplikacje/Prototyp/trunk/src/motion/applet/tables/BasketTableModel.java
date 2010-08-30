package motion.applet.tables;

import java.util.ArrayList;

import javax.swing.SwingWorker;

import motion.applet.database.TableName;
import motion.applet.dialogs.ExceptionDialog;
import motion.database.DbElementsList;
import motion.database.model.AttributeName;
import motion.database.model.GenericDescription;

public class BasketTableModel extends BasicTableModel {
	private DbElementsList<? extends GenericDescription<?>> records;
	public String fromBasket;	// Used for baskets.
	
	public BasketTableModel(TableName tableName, DbElementsList<? extends GenericDescription<?>> records, String fromBasket) {
		this.records = records;
		this.tableName = tableName;
		this.fromBasket = fromBasket;
		getTableContentsFromRecords();
	}
	
	private void getTableContentsFromRecords() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				try {
					addCheckboxColumn(); // first column
					// Don't filter attributes.
					ArrayList<AttributeName> attributes = tableName.getAllAttributes();
					for (AttributeName a : attributes) {
						//TODO: sessionID / SessionID
						attributeNames.add(a.toString().toLowerCase());
						classes.add(a.getAttributeClass());
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
							recordIds.add(r.getId());
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
		getTableContentsFromRecords();
		fireTableStructureChanged();
	}
}
